package com.pairipfix.hooks

import android.content.Context
import com.pairipfix.utils.Logger
import com.pairipfix.utils.PairIPClasses

/**
 * Bypasses PairIP VM/Bytecode protection layer
 * 
 * Target classes:
 * - com.pairip.StartupLauncher: One-time VM bytecode execution on startup
 * - com.pairip.VMRunner: Core VM execution engine
 * - com.pairip.VmDecryptor: Bytecode decryption (currently passthrough)
 * 
 * PairIP uses a custom bytecode VM to execute protected code that runs
 * through a native library (libpairipcore.so). This bypass disables
 * the Java-side initialization to prevent VM execution.
 * 
 * Methods hooked:
 * StartupLauncher:
 * - launch(): One-time startup VM execution
 * 
 * VMRunner:
 * - invoke(String, Object[]): Load bytecode and execute via native VM
 * - executeVM(byte[], Object[]): Native method (can't directly hook)
 * - setContext(Context): Store app context for VM
 * - createInvokeRunnable(String, Object[]): Create runnable for async execution
 * 
 * VmDecryptor:
 * - decrypt(byte[], int, boolean): Bytecode decryption (already passthrough)
 */
class VMRunnerBypass : BaseHook() {

    override val name = "VMRunnerBypass"

    override fun apply(): Boolean {
        logApplying()
        
        val batch = hookBatch {
            // ==================== StartupLauncher ====================
            
            // Hook 1: launch() - CRITICAL - Prevents VM bytecode execution
            hook(PairIPClasses.STARTUP_LAUNCHER, "launch") {
                doNothing()
            }
            
            // ==================== VMRunner ====================
            
            // Hook 2: invoke(String, Object[]) - Skip VM bytecode execution
            // Note: Object[] in Java is represented as [Ljava.lang.Object; 
            hook(PairIPClasses.VM_RUNNER, "invoke") {
                params(String::class.java, arrayOf<Any>()::class.java)
                returnConstant(null)
            }
            
            // Hook 3: createInvokeRunnable(String, Object[]) - Return no-op runnable
            hook(PairIPClasses.VM_RUNNER, "createInvokeRunnable") {
                params(String::class.java, arrayOf<Any>()::class.java)
                replace {
                    // Return a no-op Runnable using Kotlin lambda
                    Runnable { Logger.d { "VMRunner.createInvokeRunnable: No-op executed" } }
                }
            }
            
            // Hook 4: setContext(Context) - Allow but log
            hook(PairIPClasses.VM_RUNNER, "setContext") {
                params(Context::class.java)
                before {
                    Logger.d { "VMRunner.setContext() called" }
                }
            }
            
            // ==================== VmDecryptor ====================
            
            // Hook 5: decrypt(byte[], int, boolean) - Return input unchanged
            hook(PairIPClasses.VM_DECRYPTOR, "decrypt") {
                params(
                    ByteArray::class.java,
                    Int::class.javaPrimitiveType!!,
                    Boolean::class.javaPrimitiveType!!
                )
                replace { param ->
                    // Return the input bytecode unchanged
                    param.args[0]
                }
            }
        }
        
        // Prevent any future calls to launch() from executing
        setStaticField(PairIPClasses.STARTUP_LAUNCHER, "launchCalled", true)
        
        return batch.hasAnySuccess.also { logResult(it) }
    }
}
