package io.github.ahmedmani.pairipfix.hooks;

import android.os.Bundle;

import io.github.ahmedmani.pairipfix.utils.PairIPClasses;

import java.security.PublicKey;

public class LicenseResponseBypass extends BaseHook {
    @Override
    public String getName() {
        return "LicenseResponseBypass";
    }

    @Override
    public boolean apply() {
        boolean success = false;

        // LicenseResponseHelper
        success |= hookDoNothing(PairIPClasses.LICENSE_RESPONSE_HELPER, "validateResponse", Bundle.class, String.class);
        success |= hookDoNothing(PairIPClasses.LICENSE_RESPONSE_HELPER, "verifySignature",
                String.class, String.class, String.class, PublicKey.class);
        success |= hookReturnConstant(PairIPClasses.LICENSE_RESPONSE_HELPER, "getPublicKey", null);
        success |= hookReturnConstant(PairIPClasses.LICENSE_RESPONSE_HELPER, "getRepeatedCheckMetadata", null, Bundle.class);

        // ResponseValidator (newer version)
        if (classExists(PairIPClasses.RESPONSE_VALIDATOR)) {
            success |= hookDoNothing(PairIPClasses.RESPONSE_VALIDATOR, "validateResponse", Bundle.class, String.class);
            success |= hookDoNothing(PairIPClasses.RESPONSE_VALIDATOR, "verifySignature",
                    String.class, String.class, String.class, PublicKey.class);
        }

        // ResponseValidator V3 (older version)
        if (classExists(PairIPClasses.RESPONSE_VALIDATOR_V3)) {
            success |= hookDoNothing(PairIPClasses.RESPONSE_VALIDATOR_V3, "validateResponse", Bundle.class, String.class);
        }

        return success;
    }
}
