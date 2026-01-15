package com.pairip.licensecheck;

import Xh.DtWK.cuoqQ;
import android.os.Bundle;
import android.util.Base64;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: /home/ark/Downloads/Kuku TV_5.6.3_antisplit/smali_classes2/com/pairip/licensecheck/LicenseResponseHelper.smali */
public class LicenseResponseHelper {
    private static final String KEY_FACTORY_ALGORITHM = "RSA";
    private static final String PAYLOAD_LICENSE_DATA = "LICENSE_DATA";
    private static final String PAYLOAD_PACKAGE_NAME = "packageName";
    private static final String PAYLOAD_REPEATED_CHECK_DURATION_TO_RETRY_MILLIS = "repeatedCheckDurationToRetryMillis";
    private static final String PAYLOAD_REPEATED_CHECK_TIME_TO_RETRY_MILLIS = "repeatedCheckTimeToRetryMillis";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    public static void validateResponse(Bundle responsePayload, String packageName) throws LicenseCheckException {
        try {
            String[] jwsPartsForLicenseData = getJwsPartsForLicenseData(responsePayload);
            JSONObject jSONObjectBase64ToJson = base64ToJson(jwsPartsForLicenseData[0]);
            JSONObject jSONObjectBase64ToJson2 = base64ToJson(jwsPartsForLicenseData[1]);
            String str = jwsPartsForLicenseData[2];
            String str2 = jwsPartsForLicenseData[0] + "." + jwsPartsForLicenseData[1];
            if (!jSONObjectBase64ToJson.getString("alg").equals("RS256")) {
                throw new LicenseCheckException("Response must be signed with RS256 algorithm.");
            }
            verifySignature(str2, str, SIGNATURE_ALGORITHM, getPublicKey());
            if (!jSONObjectBase64ToJson2.getString(PAYLOAD_PACKAGE_NAME).equals(packageName)) {
                throw new LicenseCheckException("Package name doesn't match.");
            }
        } catch (JSONException e10) {
            throw new LicenseCheckException("Could not decode json", e10);
        }
    }

    public static RepeatedCheckMetadata getRepeatedCheckMetadata(Bundle responsePayload) throws LicenseCheckException {
        String str = cuoqQ.KDlvxKzkP;
        try {
            JSONObject jSONObjectBase64ToJson = base64ToJson(getJwsPartsForLicenseData(responsePayload)[1]);
            if (jSONObjectBase64ToJson.has(PAYLOAD_REPEATED_CHECK_DURATION_TO_RETRY_MILLIS) && jSONObjectBase64ToJson.has(str)) {
                return new RepeatedCheckMetadata(jSONObjectBase64ToJson.getLong(PAYLOAD_REPEATED_CHECK_DURATION_TO_RETRY_MILLIS), jSONObjectBase64ToJson.getLong(str));
            }
            return null;
        } catch (IllegalArgumentException e10) {
            throw new LicenseCheckException("Invalid repeated check payload", e10);
        } catch (JSONException e11) {
            throw new LicenseCheckException("Could not decode json", e11);
        }
    }

    private static String[] getJwsPartsForLicenseData(Bundle responsePayload) throws LicenseCheckException {
        String string = responsePayload.getString(PAYLOAD_LICENSE_DATA);
        if (string == null) {
            throw new LicenseCheckException("Invalid response");
        }
        String[] strArrSplit = string.split("\\.", -1);
        if (strArrSplit.length == 3) {
            return strArrSplit;
        }
        throw new LicenseCheckException("Invalid response");
    }

    private static JSONObject base64ToJson(String input) throws LicenseCheckException {
        try {
            return new JSONObject(new String(Base64.decode(input, 8), UTF_8));
        } catch (IllegalArgumentException | JSONException e10) {
            throw new LicenseCheckException("Invalid response", e10);
        }
    }

    private static void verifySignature(String signedData, String signature, String signatureAlgorithm, PublicKey publicKey) throws LicenseCheckException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        try {
            Signature signature2 = Signature.getInstance(signatureAlgorithm);
            signature2.initVerify(publicKey);
            signature2.update(signedData.getBytes(UTF_8));
            if (signature2.verify(Base64.decode(signature, 8))) {
            } else {
                throw new LicenseCheckException("Signature verification failed.");
            }
        } catch (IllegalArgumentException e10) {
            throw new LicenseCheckException("Could not base64 decode returned signature", e10);
        } catch (InvalidKeyException e11) {
            throw new LicenseCheckException("Could not sign data with the public key", e11);
        } catch (NoSuchAlgorithmException e12) {
            throw new LicenseCheckException(String.format("Could not find %s algorithm on the device", signatureAlgorithm), e12);
        } catch (SignatureException e13) {
            throw new LicenseCheckException("Could not parse returned signature.", e13);
        }
    }

    private static PublicKey getPublicKey() throws LicenseCheckException {
        try {
            return KeyFactory.getInstance(KEY_FACTORY_ALGORITHM).generatePublic(new X509EncodedKeySpec(Base64.decode(LicenseClient.getLicensePubKey(), 0)));
        } catch (IllegalArgumentException e10) {
            throw new LicenseCheckException("Could not decode public key", e10);
        } catch (NoSuchAlgorithmException e11) {
            throw new LicenseCheckException(String.format("%s algorithm not found on device", KEY_FACTORY_ALGORITHM), e11);
        } catch (InvalidKeySpecException e12) {
            throw new LicenseCheckException("Could not create key specification from the public key", e12);
        }
    }

    private LicenseResponseHelper() {
    }
}
