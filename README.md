# pairipfix

This LSPosed module bypasses the "Get this app from Play" screen when installing Android apps as an APK instead of from the Google Play Store.  

<div align="center">
  <img src="https://github.com/user-attachments/assets/f8c67ffa-36ca-4e3c-900a-6301152391d1" alt="image" />
</div>

# why does this happen? 

This is a security measure made by Google called "pairipcore" based on the binary file it runs from `libpairpcore.so`. Basically, it’s used to prevent abuse during app runtime. It mainly does three things: 
  - Validate app was installed from Google Play (this is what the module bypasses)
  - Validates app signature to prevent resigning/repackaging
  - Detects and blocks Frida hooks and debuggers such as GDB

# how does it work?

Pairip works by attaching itself to the app resources that it cannot run without it, this is done through rewriting some of the app methods in encrypted? or obfuscated? VM code.
each time a protected method is called a vm code file is loaded from the app storage and executed inside the pairip VM. preventing these calls would simply crash the app, due to missing resources.

# alternative solutions

Currently the module dosent work on some apps, i have yet to fully understand why, if this module dosent work for you or app keeps crashing when using it, try this [guide](https://www.reddit.com/r/magiarecord/wiki/installation-jp/).
 
