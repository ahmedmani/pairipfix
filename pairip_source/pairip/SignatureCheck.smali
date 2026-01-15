###### Class com.pairip.SignatureCheck (com.pairip.SignatureCheck)
.class public Lcom/pairip/SignatureCheck;
.super Ljava/lang/Object;
.source "SignatureCheck.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/pairip/SignatureCheck$SignatureTamperedException;
    }
.end annotation


# static fields
.field private static final ALLOWLISTED_SIG:Ljava/lang/String; = "Vn3kj4pUblROi2S+QfRRL9nhsaO2uoHQg6+dpEtxdTE="

.field private static final TAG:Ljava/lang/String; = "SignatureCheck"

.field private static expectedLegacyUpgradedSignature:Ljava/lang/String; = "OJbelZQ2MYE0IFb/mzgFVx8rcB6XsWxZpMs9q6iXlFs="

.field private static expectedSignature:Ljava/lang/String; = "OJbelZQ2MYE0IFb/mzgFVx8rcB6XsWxZpMs9q6iXlFs="

.field private static expectedTestSignature:Ljava/lang/String; = "OJbelZQ2MYE0IFb/mzgFVx8rcB6XsWxZpMs9q6iXlFs="


# direct methods
.method private constructor <init>()V
    .registers 1

    .line 77
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static verifyIntegrity(Landroid/content/Context;)V
    .registers 3
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0
        }
        names = {
            "context"
        }
    .end annotation

    .line 42
    :try_start_0
    invoke-virtual {p0}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v0

    .line 43
    invoke-virtual {p0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object p0

    const/16 v1, 0x40

    invoke-virtual {v0, p0, v1}, Landroid/content/pm/PackageManager;->getPackageInfo(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;

    move-result-object p0

    .line 44
    const-string v0, "SHA-256"

    .line 46
    invoke-static {v0}, Ljava/security/MessageDigest;->getInstance(Ljava/lang/String;)Ljava/security/MessageDigest;

    move-result-object v0

    iget-object p0, p0, Landroid/content/pm/PackageInfo;->signatures:[Landroid/content/pm/Signature;

    const/4 v1, 0x0

    aget-object p0, p0, v1

    invoke-virtual {p0}, Landroid/content/pm/Signature;->toByteArray()[B

    move-result-object p0

    invoke-virtual {v0, p0}, Ljava/security/MessageDigest;->digest([B)[B

    move-result-object p0

    const/4 v0, 0x2

    .line 45
    invoke-static {p0, v0}, Landroid/util/Base64;->encodeToString([BI)Ljava/lang/String;

    move-result-object p0
    :try_end_26
    .catch Ljava/security/NoSuchAlgorithmException; {:try_start_0 .. :try_end_26} :catch_27
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_0 .. :try_end_26} :catch_27

    goto :goto_28

    :catch_27
    const/4 p0, 0x0

    .line 54
    :goto_28
    invoke-static {p0}, Lcom/pairip/SignatureCheck;->verifySignatureMatches(Ljava/lang/String;)Z

    move-result v0

    if-nez v0, :cond_47

    sget-object v0, Lcom/pairip/SignatureCheck;->expectedTestSignature:Ljava/lang/String;

    .line 55
    invoke-virtual {v0, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_47

    const-string v0, "Vn3kj4pUblROi2S+QfRRL9nhsaO2uoHQg6+dpEtxdTE="

    .line 56
    invoke-virtual {v0, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p0

    if-eqz p0, :cond_3f

    goto :goto_47

    .line 57
    :cond_3f
    new-instance p0, Lcom/pairip/SignatureCheck$SignatureTamperedException;

    const-string v0, "Apk signature is invalid."

    invoke-direct {p0, v0}, Lcom/pairip/SignatureCheck$SignatureTamperedException;-><init>(Ljava/lang/String;)V

    throw p0

    .line 59
    :cond_47
    :goto_47
    const-string p0, "SignatureCheck"

    const-string v0, "Signature check ok"

    invoke-static {p0, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public static verifySignatureMatches(Ljava/lang/String;)Z
    .registers 2
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0
        }
        names = {
            "signature"
        }
    .end annotation

    .line 74
    sget-object v0, Lcom/pairip/SignatureCheck;->expectedSignature:Ljava/lang/String;

    invoke-virtual {v0, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_13

    sget-object v0, Lcom/pairip/SignatureCheck;->expectedLegacyUpgradedSignature:Ljava/lang/String;

    invoke-virtual {v0, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p0

    if-eqz p0, :cond_11

    goto :goto_13

    :cond_11
    const/4 p0, 0x0

    return p0

    :cond_13
    :goto_13
    const/4 p0, 0x1

    return p0
.end method

###### Class com.pairip.SignatureCheck.SignatureTamperedException (com.pairip.SignatureCheck$SignatureTamperedException)
.class Lcom/pairip/SignatureCheck$SignatureTamperedException;
.super Ljava/lang/RuntimeException;
.source "SignatureCheck.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/pairip/SignatureCheck;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0xa
    name = "SignatureTamperedException"
.end annotation


# direct methods
.method public constructor <init>(Ljava/lang/String;)V
    .registers 2
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0
        }
        names = {
            "message"
        }
    .end annotation

    .line 18
    invoke-direct {p0, p1}, Ljava/lang/RuntimeException;-><init>(Ljava/lang/String;)V

    return-void
.end method
