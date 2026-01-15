###### Class com.pairip.licensecheck.LicenseResponseHelper (com.pairip.licensecheck.LicenseResponseHelper)
.class public Lcom/pairip/licensecheck/LicenseResponseHelper;
.super Ljava/lang/Object;
.source "LicenseResponseHelper.java"


# static fields
.field private static final KEY_FACTORY_ALGORITHM:Ljava/lang/String; = "RSA"

.field private static final PAYLOAD_LICENSE_DATA:Ljava/lang/String; = "LICENSE_DATA"

.field private static final PAYLOAD_PACKAGE_NAME:Ljava/lang/String; = "packageName"

.field private static final PAYLOAD_REPEATED_CHECK_DURATION_TO_RETRY_MILLIS:Ljava/lang/String; = "repeatedCheckDurationToRetryMillis"

.field private static final PAYLOAD_REPEATED_CHECK_TIME_TO_RETRY_MILLIS:Ljava/lang/String; = "repeatedCheckTimeToRetryMillis"

.field private static final SIGNATURE_ALGORITHM:Ljava/lang/String; = "SHA256withRSA"

.field private static final UTF_8:Ljava/nio/charset/Charset;


# direct methods
.method static constructor <clinit>()V
    .registers 1

    .line 20
    const-string v0, "UTF-8"

    invoke-static {v0}, Ljava/nio/charset/Charset;->forName(Ljava/lang/String;)Ljava/nio/charset/Charset;

    move-result-object v0

    sput-object v0, Lcom/pairip/licensecheck/LicenseResponseHelper;->UTF_8:Ljava/nio/charset/Charset;

    return-void
.end method

.method private constructor <init>()V
    .registers 1

    .line 155
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method private static base64ToJson(Ljava/lang/String;)Lorg/json/JSONObject;
    .registers 3
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0
        }
        names = {
            "input"
        }
    .end annotation

    .annotation system Ldalvik/annotation/Throws;
        value = {
            Lcom/pairip/licensecheck/LicenseCheckException;
        }
    .end annotation

    const/16 v0, 0x8

    .line 109
    :try_start_2
    invoke-static {p0, v0}, Landroid/util/Base64;->decode(Ljava/lang/String;I)[B

    move-result-object p0

    .line 110
    new-instance v0, Ljava/lang/String;

    sget-object v1, Lcom/pairip/licensecheck/LicenseResponseHelper;->UTF_8:Ljava/nio/charset/Charset;

    invoke-direct {v0, p0, v1}, Ljava/lang/String;-><init>([BLjava/nio/charset/Charset;)V

    .line 111
    new-instance p0, Lorg/json/JSONObject;

    invoke-direct {p0, v0}, Lorg/json/JSONObject;-><init>(Ljava/lang/String;)V
    :try_end_12
    .catch Ljava/lang/IllegalArgumentException; {:try_start_2 .. :try_end_12} :catch_13
    .catch Lorg/json/JSONException; {:try_start_2 .. :try_end_12} :catch_13

    return-object p0

    :catch_13
    move-exception p0

    .line 113
    new-instance v0, Lcom/pairip/licensecheck/LicenseCheckException;

    const-string v1, "Invalid response"

    invoke-direct {v0, v1, p0}, Lcom/pairip/licensecheck/LicenseCheckException;-><init>(Ljava/lang/String;Ljava/lang/Throwable;)V

    throw v0
.end method

.method private static getJwsPartsForLicenseData(Landroid/os/Bundle;)[Ljava/lang/String;
    .registers 4
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0
        }
        names = {
            "responsePayload"
        }
    .end annotation

    .annotation system Ldalvik/annotation/Throws;
        value = {
            Lcom/pairip/licensecheck/LicenseCheckException;
        }
    .end annotation

    .line 95
    const-string v0, "LICENSE_DATA"

    invoke-virtual {p0, v0}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p0

    .line 96
    const-string v0, "Invalid response"

    if-eqz p0, :cond_1c

    .line 99
    const-string v1, "\\."

    const/4 v2, -0x1

    invoke-virtual {p0, v1, v2}, Ljava/lang/String;->split(Ljava/lang/String;I)[Ljava/lang/String;

    move-result-object p0

    .line 100
    array-length v1, p0

    const/4 v2, 0x3

    if-ne v1, v2, :cond_16

    return-object p0

    .line 101
    :cond_16
    new-instance p0, Lcom/pairip/licensecheck/LicenseCheckException;

    invoke-direct {p0, v0}, Lcom/pairip/licensecheck/LicenseCheckException;-><init>(Ljava/lang/String;)V

    throw p0

    .line 97
    :cond_1c
    new-instance p0, Lcom/pairip/licensecheck/LicenseCheckException;

    invoke-direct {p0, v0}, Lcom/pairip/licensecheck/LicenseCheckException;-><init>(Ljava/lang/String;)V

    throw p0
.end method

.method private static getPublicKey()Ljava/security/PublicKey;
    .registers 5
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Lcom/pairip/licensecheck/LicenseCheckException;
        }
    .end annotation

    .line 142
    const-string v0, "RSA"

    const/4 v1, 0x0

    :try_start_3
    invoke-static {}, Lcom/pairip/licensecheck/LicenseClient;->getLicensePubKey()Ljava/lang/String;

    move-result-object v2

    invoke-static {v2, v1}, Landroid/util/Base64;->decode(Ljava/lang/String;I)[B

    move-result-object v2

    .line 143
    invoke-static {v0}, Ljava/security/KeyFactory;->getInstance(Ljava/lang/String;)Ljava/security/KeyFactory;

    move-result-object v3

    .line 144
    new-instance v4, Ljava/security/spec/X509EncodedKeySpec;

    invoke-direct {v4, v2}, Ljava/security/spec/X509EncodedKeySpec;-><init>([B)V

    invoke-virtual {v3, v4}, Ljava/security/KeyFactory;->generatePublic(Ljava/security/spec/KeySpec;)Ljava/security/PublicKey;

    move-result-object v0
    :try_end_18
    .catch Ljava/security/NoSuchAlgorithmException; {:try_start_3 .. :try_end_18} :catch_2b
    .catch Ljava/lang/IllegalArgumentException; {:try_start_3 .. :try_end_18} :catch_22
    .catch Ljava/security/spec/InvalidKeySpecException; {:try_start_3 .. :try_end_18} :catch_19

    return-object v0

    :catch_19
    move-exception v0

    .line 151
    new-instance v1, Lcom/pairip/licensecheck/LicenseCheckException;

    const-string v2, "Could not create key specification from the public key"

    invoke-direct {v1, v2, v0}, Lcom/pairip/licensecheck/LicenseCheckException;-><init>(Ljava/lang/String;Ljava/lang/Throwable;)V

    throw v1

    :catch_22
    move-exception v0

    .line 149
    new-instance v1, Lcom/pairip/licensecheck/LicenseCheckException;

    const-string v2, "Could not decode public key"

    invoke-direct {v1, v2, v0}, Lcom/pairip/licensecheck/LicenseCheckException;-><init>(Ljava/lang/String;Ljava/lang/Throwable;)V

    throw v1

    :catch_2b
    move-exception v2

    .line 146
    new-instance v3, Lcom/pairip/licensecheck/LicenseCheckException;

    const/4 v4, 0x1

    new-array v4, v4, [Ljava/lang/Object;

    aput-object v0, v4, v1

    .line 147
    const-string v0, "%s algorithm not found on device"

    invoke-static {v0, v4}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-direct {v3, v0, v2}, Lcom/pairip/licensecheck/LicenseCheckException;-><init>(Ljava/lang/String;Ljava/lang/Throwable;)V

    throw v3
.end method

.method public static getRepeatedCheckMetadata(Landroid/os/Bundle;)Lcom/pairip/licensecheck/RepeatedCheckMetadata;
    .registers 6
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0
        }
        names = {
            "responsePayload"
        }
    .end annotation

    .annotation system Ldalvik/annotation/Throws;
        value = {
            Lcom/pairip/licensecheck/LicenseCheckException;
        }
    .end annotation

    .line 75
    const/4 v0, 0x0

    sget-object v0, LXh/DtWK/cuoqQ;->KDlvxKzkP:Ljava/lang/String;

    const-string v1, "repeatedCheckDurationToRetryMillis"

    :try_start_5
    invoke-static {p0}, Lcom/pairip/licensecheck/LicenseResponseHelper;->getJwsPartsForLicenseData(Landroid/os/Bundle;)[Ljava/lang/String;

    move-result-object p0

    const/4 v2, 0x1

    .line 76
    aget-object p0, p0, v2

    invoke-static {p0}, Lcom/pairip/licensecheck/LicenseResponseHelper;->base64ToJson(Ljava/lang/String;)Lorg/json/JSONObject;

    move-result-object p0

    .line 78
    invoke-virtual {p0, v1}, Lorg/json/JSONObject;->has(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_2b

    .line 79
    invoke-virtual {p0, v0}, Lorg/json/JSONObject;->has(Ljava/lang/String;)Z

    move-result v2

    if-nez v2, :cond_1d

    goto :goto_2b

    .line 83
    :cond_1d
    invoke-virtual {p0, v1}, Lorg/json/JSONObject;->getLong(Ljava/lang/String;)J

    move-result-wide v1

    .line 84
    invoke-virtual {p0, v0}, Lorg/json/JSONObject;->getLong(Ljava/lang/String;)J

    move-result-wide v3

    .line 85
    new-instance p0, Lcom/pairip/licensecheck/RepeatedCheckMetadata;

    invoke-direct {p0, v1, v2, v3, v4}, Lcom/pairip/licensecheck/RepeatedCheckMetadata;-><init>(JJ)V
    :try_end_2a
    .catch Lorg/json/JSONException; {:try_start_5 .. :try_end_2a} :catch_36
    .catch Ljava/lang/IllegalArgumentException; {:try_start_5 .. :try_end_2a} :catch_2d

    return-object p0

    :cond_2b
    :goto_2b
    const/4 p0, 0x0

    return-object p0

    :catch_2d
    move-exception p0

    .line 89
    new-instance v0, Lcom/pairip/licensecheck/LicenseCheckException;

    const-string v1, "Invalid repeated check payload"

    invoke-direct {v0, v1, p0}, Lcom/pairip/licensecheck/LicenseCheckException;-><init>(Ljava/lang/String;Ljava/lang/Throwable;)V

    throw v0

    :catch_36
    move-exception p0

    .line 87
    new-instance v0, Lcom/pairip/licensecheck/LicenseCheckException;

    const-string v1, "Could not decode json"

    invoke-direct {v0, v1, p0}, Lcom/pairip/licensecheck/LicenseCheckException;-><init>(Ljava/lang/String;Ljava/lang/Throwable;)V

    throw v0
.end method

.method public static validateResponse(Landroid/os/Bundle;Ljava/lang/String;)V
    .registers 7
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0,
            0x0
        }
        names = {
            "responsePayload",
            "packageName"
        }
    .end annotation

    .annotation system Ldalvik/annotation/Throws;
        value = {
            Lcom/pairip/licensecheck/LicenseCheckException;
        }
    .end annotation

    .line 43
    :try_start_0
    invoke-static {p0}, Lcom/pairip/licensecheck/LicenseResponseHelper;->getJwsPartsForLicenseData(Landroid/os/Bundle;)[Ljava/lang/String;

    move-result-object p0

    const/4 v0, 0x0

    .line 44
    aget-object v1, p0, v0

    invoke-static {v1}, Lcom/pairip/licensecheck/LicenseResponseHelper;->base64ToJson(Ljava/lang/String;)Lorg/json/JSONObject;

    move-result-object v1

    const/4 v2, 0x1

    .line 45
    aget-object v3, p0, v2

    invoke-static {v3}, Lcom/pairip/licensecheck/LicenseResponseHelper;->base64ToJson(Ljava/lang/String;)Lorg/json/JSONObject;

    move-result-object v3

    const/4 v4, 0x2

    .line 46
    aget-object v4, p0, v4

    .line 47
    aget-object v0, p0, v0

    aget-object p0, p0, v2

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v0, "."

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    .line 50
    const-string v0, "alg"

    invoke-virtual {v1, v0}, Lorg/json/JSONObject;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "RS256"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_59

    .line 53
    const-string v0, "SHA256withRSA"

    invoke-static {}, Lcom/pairip/licensecheck/LicenseResponseHelper;->getPublicKey()Ljava/security/PublicKey;

    move-result-object v1

    invoke-static {p0, v4, v0, v1}, Lcom/pairip/licensecheck/LicenseResponseHelper;->verifySignature(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/security/PublicKey;)V

    .line 56
    const-string p0, "packageName"

    invoke-virtual {v3, p0}, Lorg/json/JSONObject;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p0

    .line 57
    invoke-virtual {p0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p0

    if-eqz p0, :cond_51

    return-void

    .line 58
    :cond_51
    new-instance p0, Lcom/pairip/licensecheck/LicenseCheckException;

    const-string p1, "Package name doesn\'t match."

    invoke-direct {p0, p1}, Lcom/pairip/licensecheck/LicenseCheckException;-><init>(Ljava/lang/String;)V

    throw p0

    .line 51
    :cond_59
    new-instance p0, Lcom/pairip/licensecheck/LicenseCheckException;

    const-string p1, "Response must be signed with RS256 algorithm."

    invoke-direct {p0, p1}, Lcom/pairip/licensecheck/LicenseCheckException;-><init>(Ljava/lang/String;)V

    throw p0
    :try_end_61
    .catch Lorg/json/JSONException; {:try_start_0 .. :try_end_61} :catch_61

    :catch_61
    move-exception p0

    .line 61
    new-instance p1, Lcom/pairip/licensecheck/LicenseCheckException;

    const-string v0, "Could not decode json"

    invoke-direct {p1, v0, p0}, Lcom/pairip/licensecheck/LicenseCheckException;-><init>(Ljava/lang/String;Ljava/lang/Throwable;)V

    throw p1
.end method

.method private static verifySignature(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/security/PublicKey;)V
    .registers 5
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0,
            0x0,
            0x0,
            0x0
        }
        names = {
            "signedData",
            "signature",
            "signatureAlgorithm",
            "publicKey"
        }
    .end annotation

    .annotation system Ldalvik/annotation/Throws;
        value = {
            Lcom/pairip/licensecheck/LicenseCheckException;
        }
    .end annotation

    .line 122
    :try_start_0
    invoke-static {p2}, Ljava/security/Signature;->getInstance(Ljava/lang/String;)Ljava/security/Signature;

    move-result-object v0

    .line 123
    invoke-virtual {v0, p3}, Ljava/security/Signature;->initVerify(Ljava/security/PublicKey;)V

    .line 124
    sget-object p3, Lcom/pairip/licensecheck/LicenseResponseHelper;->UTF_8:Ljava/nio/charset/Charset;

    invoke-virtual {p0, p3}, Ljava/lang/String;->getBytes(Ljava/nio/charset/Charset;)[B

    move-result-object p0

    invoke-virtual {v0, p0}, Ljava/security/Signature;->update([B)V

    const/16 p0, 0x8

    .line 125
    invoke-static {p1, p0}, Landroid/util/Base64;->decode(Ljava/lang/String;I)[B

    move-result-object p0

    invoke-virtual {v0, p0}, Ljava/security/Signature;->verify([B)Z

    move-result p0

    if-eqz p0, :cond_1d

    return-void

    .line 126
    :cond_1d
    new-instance p0, Lcom/pairip/licensecheck/LicenseCheckException;

    const-string p1, "Signature verification failed."

    invoke-direct {p0, p1}, Lcom/pairip/licensecheck/LicenseCheckException;-><init>(Ljava/lang/String;)V

    throw p0
    :try_end_25
    .catch Ljava/security/NoSuchAlgorithmException; {:try_start_0 .. :try_end_25} :catch_40
    .catch Ljava/security/InvalidKeyException; {:try_start_0 .. :try_end_25} :catch_37
    .catch Ljava/security/SignatureException; {:try_start_0 .. :try_end_25} :catch_2e
    .catch Ljava/lang/IllegalArgumentException; {:try_start_0 .. :try_end_25} :catch_25

    :catch_25
    move-exception p0

    .line 136
    new-instance p1, Lcom/pairip/licensecheck/LicenseCheckException;

    const-string p2, "Could not base64 decode returned signature"

    invoke-direct {p1, p2, p0}, Lcom/pairip/licensecheck/LicenseCheckException;-><init>(Ljava/lang/String;Ljava/lang/Throwable;)V

    throw p1

    :catch_2e
    move-exception p0

    .line 134
    new-instance p1, Lcom/pairip/licensecheck/LicenseCheckException;

    const-string p2, "Could not parse returned signature."

    invoke-direct {p1, p2, p0}, Lcom/pairip/licensecheck/LicenseCheckException;-><init>(Ljava/lang/String;Ljava/lang/Throwable;)V

    throw p1

    :catch_37
    move-exception p0

    .line 132
    new-instance p1, Lcom/pairip/licensecheck/LicenseCheckException;

    const-string p2, "Could not sign data with the public key"

    invoke-direct {p1, p2, p0}, Lcom/pairip/licensecheck/LicenseCheckException;-><init>(Ljava/lang/String;Ljava/lang/Throwable;)V

    throw p1

    :catch_40
    move-exception p0

    .line 129
    new-instance p1, Lcom/pairip/licensecheck/LicenseCheckException;

    const/4 p3, 0x1

    new-array p3, p3, [Ljava/lang/Object;

    const/4 v0, 0x0

    aput-object p2, p3, v0

    .line 130
    const-string p2, "Could not find %s algorithm on the device"

    invoke-static {p2, p3}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p2

    invoke-direct {p1, p2, p0}, Lcom/pairip/licensecheck/LicenseCheckException;-><init>(Ljava/lang/String;Ljava/lang/Throwable;)V

    throw p1
.end method
