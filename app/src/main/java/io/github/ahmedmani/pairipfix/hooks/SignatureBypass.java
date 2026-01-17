package io.github.ahmedmani.pairipfix.hooks;

import android.content.Context;

import io.github.ahmedmani.pairipfix.utils.PairIPClasses;

public class SignatureBypass extends BaseHook {
    @Override
    public String getName() {
        return "SignatureBypass";
    }

    @Override
    public boolean apply() {
        boolean hook1 = hookDoNothing(PairIPClasses.SIGNATURE_CHECK, "verifyIntegrity", Context.class);
        boolean hook2 = hookReturnConstant(PairIPClasses.SIGNATURE_CHECK, "verifySignatureMatches", true, String.class);
        return hook1 || hook2;
    }
}
