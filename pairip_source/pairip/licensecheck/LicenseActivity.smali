###### Class com.pairip.licensecheck.LicenseActivity (com.pairip.licensecheck.LicenseActivity)
.class public Lcom/pairip/licensecheck/LicenseActivity;
.super Landroid/app/Activity;
.source "LicenseActivity.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/pairip/licensecheck/LicenseActivity$ActivityType;
    }
.end annotation


# static fields
.field public static final ACTIVITY_TYPE_ARG_NAME:Ljava/lang/String; = "activitytype"

.field public static final PAYWALL_INTENT_ARG_NAME:Ljava/lang/String; = "paywallintent"

.field private static final TAG:Ljava/lang/String; = "LicenseActivity"


# direct methods
.method public static synthetic $r8$lambda$N5_Pzpb-eSKmOONXn3Kn0QvMbys(Lcom/pairip/licensecheck/LicenseActivity;)V
    .registers 1

    invoke-direct {p0}, Lcom/pairip/licensecheck/LicenseActivity;->lambda$showErrorDialog$0()V

    return-void
.end method

.method public static synthetic $r8$lambda$fE_XZ7S0hhHsxQNTfy8mxeJ7kEU(Lcom/pairip/licensecheck/LicenseActivity;Landroid/content/DialogInterface;I)V
    .registers 3

    invoke-direct {p0, p1, p2}, Lcom/pairip/licensecheck/LicenseActivity;->lambda$showErrorDialog$1(Landroid/content/DialogInterface;I)V

    return-void
.end method

.method public static synthetic $r8$lambda$x-JmBIDmuVzGN23Wk7Dd1TBpzO0(Lcom/pairip/licensecheck/LicenseActivity;Landroid/app/PendingIntent;)V
    .registers 2

    invoke-direct {p0, p1}, Lcom/pairip/licensecheck/LicenseActivity;->lambda$showPaywallAndCloseApp$0(Landroid/app/PendingIntent;)V

    return-void
.end method

.method public constructor <init>()V
    .registers 1

    .line 18
    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    return-void
.end method

.method private synthetic lambda$showErrorDialog$0()V
    .registers 4

    .line 91
    :try_start_0
    new-instance v0, Landroid/app/AlertDialog$Builder;

    invoke-direct {v0, p0}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    const-string v1, "Something went wrong"

    .line 92
    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    const-string v1, "Check that Google Play is enabled on your device and that you\'re using an up-to-date version before opening the app. If the problem persists try reinstalling the app."

    .line 93
    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    const-string v1, "Close"

    new-instance v2, Lcom/pairip/licensecheck/LicenseActivity$$ExternalSyntheticLambda2;

    invoke-direct {v2, p0}, Lcom/pairip/licensecheck/LicenseActivity$$ExternalSyntheticLambda2;-><init>(Lcom/pairip/licensecheck/LicenseActivity;)V

    .line 97
    invoke-virtual {v0, v1, v2}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    const/4 v1, 0x0

    .line 98
    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    .line 99
    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->show()Landroid/app/AlertDialog;
    :try_end_24
    .catch Ljava/lang/RuntimeException; {:try_start_0 .. :try_end_24} :catch_25

    return-void

    :catch_25
    move-exception v0

    .line 106
    invoke-static {v0}, Landroid/util/Log;->getStackTraceString(Ljava/lang/Throwable;)Ljava/lang/String;

    move-result-object v0

    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "Couldn\'t show the error dialog. "

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "LicenseActivity"

    invoke-static {v1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method private synthetic lambda$showErrorDialog$1(Landroid/content/DialogInterface;I)V
    .registers 3

    .line 97
    invoke-virtual {p0}, Lcom/pairip/licensecheck/LicenseActivity;->closeApp()V

    return-void
.end method

.method private synthetic lambda$showPaywallAndCloseApp$0(Landroid/app/PendingIntent;)V
    .registers 4

    .line 59
    :try_start_0
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x22

    if-lt v0, v1, :cond_17

    .line 61
    invoke-static {}, Landroid/app/ActivityOptions;->makeBasic()Landroid/app/ActivityOptions;

    move-result-object v0

    const/4 v1, 0x1

    .line 62
    invoke-virtual {v0, v1}, Landroid/app/ActivityOptions;->setPendingIntentBackgroundActivityStartMode(I)Landroid/app/ActivityOptions;

    move-result-object v0

    .line 64
    invoke-virtual {v0}, Landroid/app/ActivityOptions;->toBundle()Landroid/os/Bundle;

    move-result-object v0

    .line 65
    invoke-virtual {p1, v0}, Landroid/app/PendingIntent;->send(Landroid/os/Bundle;)V

    goto :goto_1a

    .line 67
    :cond_17
    invoke-virtual {p1}, Landroid/app/PendingIntent;->send()V

    .line 69
    :goto_1a
    invoke-virtual {p0}, Lcom/pairip/licensecheck/LicenseActivity;->closeApp()V
    :try_end_1d
    .catch Landroid/app/PendingIntent$CanceledException; {:try_start_0 .. :try_end_1d} :catch_1e

    return-void

    :catch_1e
    move-exception p1

    .line 72
    const-string v0, "Paywall intent unexpectedly cancelled."

    invoke-direct {p0, v0, p1}, Lcom/pairip/licensecheck/LicenseActivity;->logAndShowErrorDialog(Ljava/lang/String;Ljava/lang/Exception;)V

    return-void
.end method

.method private logAndShowErrorDialog(Ljava/lang/String;)V
    .registers 3
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0
        }
        names = {
            "message"
        }
    .end annotation

    .line 82
    const-string v0, "LicenseActivity"

    invoke-static {v0, p1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 83
    invoke-direct {p0}, Lcom/pairip/licensecheck/LicenseActivity;->showErrorDialog()V

    return-void
.end method

.method private logAndShowErrorDialog(Ljava/lang/String;Ljava/lang/Exception;)V
    .registers 4
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0,
            0x0
        }
        names = {
            "message",
            "ex"
        }
    .end annotation

    .line 78
    invoke-static {p2}, Landroid/util/Log;->getStackTraceString(Ljava/lang/Throwable;)Ljava/lang/String;

    move-result-object p2

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string p1, " "

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0, p1}, Lcom/pairip/licensecheck/LicenseActivity;->logAndShowErrorDialog(Ljava/lang/String;)V

    return-void
.end method

.method private showErrorDialog()V
    .registers 2

    .line 87
    new-instance v0, Lcom/pairip/licensecheck/LicenseActivity$$ExternalSyntheticLambda1;

    invoke-direct {v0, p0}, Lcom/pairip/licensecheck/LicenseActivity$$ExternalSyntheticLambda1;-><init>(Lcom/pairip/licensecheck/LicenseActivity;)V

    invoke-virtual {p0, v0}, Lcom/pairip/licensecheck/LicenseActivity;->runOnUiThread(Ljava/lang/Runnable;)V

    return-void
.end method

.method private showPaywallAndCloseApp()V
    .registers 3

    .line 50
    invoke-virtual {p0}, Lcom/pairip/licensecheck/LicenseActivity;->getIntent()Landroid/content/Intent;

    move-result-object v0

    const-string v1, "paywallintent"

    invoke-virtual {v0, v1}, Landroid/content/Intent;->getParcelableExtra(Ljava/lang/String;)Landroid/os/Parcelable;

    move-result-object v0

    check-cast v0, Landroid/app/PendingIntent;

    if-nez v0, :cond_14

    .line 52
    const-string v0, "Paywall intent is not provided."

    invoke-direct {p0, v0}, Lcom/pairip/licensecheck/LicenseActivity;->logAndShowErrorDialog(Ljava/lang/String;)V

    return-void

    .line 56
    :cond_14
    new-instance v1, Lcom/pairip/licensecheck/LicenseActivity$$ExternalSyntheticLambda0;

    invoke-direct {v1, p0, v0}, Lcom/pairip/licensecheck/LicenseActivity$$ExternalSyntheticLambda0;-><init>(Lcom/pairip/licensecheck/LicenseActivity;Landroid/app/PendingIntent;)V

    invoke-virtual {p0, v1}, Lcom/pairip/licensecheck/LicenseActivity;->runOnUiThread(Ljava/lang/Runnable;)V

    return-void
.end method


# virtual methods
.method protected closeApp()V
    .registers 2

    .line 113
    invoke-virtual {p0}, Lcom/pairip/licensecheck/LicenseActivity;->finishAndRemoveTask()V

    const/4 v0, 0x0

    .line 114
    invoke-static {v0}, Ljava/lang/System;->exit(I)V

    return-void
.end method

.method public onStart()V
    .registers 3

    .line 32
    invoke-super {p0}, Landroid/app/Activity;->onStart()V

    .line 35
    :try_start_3
    invoke-virtual {p0}, Lcom/pairip/licensecheck/LicenseActivity;->getIntent()Landroid/content/Intent;

    move-result-object v0

    const-string v1, "activitytype"

    invoke-virtual {v0, v1}, Landroid/content/Intent;->getSerializableExtra(Ljava/lang/String;)Ljava/io/Serializable;

    move-result-object v0

    check-cast v0, Lcom/pairip/licensecheck/LicenseActivity$ActivityType;

    .line 36
    invoke-virtual {v0}, Lcom/pairip/licensecheck/LicenseActivity$ActivityType;->ordinal()I

    move-result v0

    if-eqz v0, :cond_1d

    const/4 v1, 0x1

    if-eq v0, v1, :cond_19

    return-void

    .line 41
    :cond_19
    invoke-direct {p0}, Lcom/pairip/licensecheck/LicenseActivity;->showErrorDialog()V

    return-void

    .line 38
    :cond_1d
    invoke-direct {p0}, Lcom/pairip/licensecheck/LicenseActivity;->showPaywallAndCloseApp()V
    :try_end_20
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_20} :catch_21

    return-void

    :catch_21
    move-exception v0

    .line 45
    const-string v1, "Couldn\'t process license activity correctly."

    invoke-direct {p0, v1, v0}, Lcom/pairip/licensecheck/LicenseActivity;->logAndShowErrorDialog(Ljava/lang/String;Ljava/lang/Exception;)V

    return-void
.end method

###### Class com.pairip.licensecheck.LicenseActivity.ActivityType (com.pairip.licensecheck.LicenseActivity$ActivityType)
.class public final enum Lcom/pairip/licensecheck/LicenseActivity$ActivityType;
.super Ljava/lang/Enum;
.source "LicenseActivity.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/pairip/licensecheck/LicenseActivity;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4019
    name = "ActivityType"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/pairip/licensecheck/LicenseActivity$ActivityType;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[Lcom/pairip/licensecheck/LicenseActivity$ActivityType;

.field public static final enum ERROR_DIALOG:Lcom/pairip/licensecheck/LicenseActivity$ActivityType;

.field public static final enum PAYWALL:Lcom/pairip/licensecheck/LicenseActivity$ActivityType;


# direct methods
.method private static synthetic $values()[Lcom/pairip/licensecheck/LicenseActivity$ActivityType;
    .registers 3

    const/4 v0, 0x2

    .line 25
    new-array v0, v0, [Lcom/pairip/licensecheck/LicenseActivity$ActivityType;

    sget-object v1, Lcom/pairip/licensecheck/LicenseActivity$ActivityType;->PAYWALL:Lcom/pairip/licensecheck/LicenseActivity$ActivityType;

    const/4 v2, 0x0

    aput-object v1, v0, v2

    sget-object v1, Lcom/pairip/licensecheck/LicenseActivity$ActivityType;->ERROR_DIALOG:Lcom/pairip/licensecheck/LicenseActivity$ActivityType;

    const/4 v2, 0x1

    aput-object v1, v0, v2

    return-object v0
.end method

.method static constructor <clinit>()V
    .registers 3

    .line 26
    new-instance v0, Lcom/pairip/licensecheck/LicenseActivity$ActivityType;

    const-string v1, "PAYWALL"

    const/4 v2, 0x0

    invoke-direct {v0, v1, v2}, Lcom/pairip/licensecheck/LicenseActivity$ActivityType;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/pairip/licensecheck/LicenseActivity$ActivityType;->PAYWALL:Lcom/pairip/licensecheck/LicenseActivity$ActivityType;

    .line 27
    new-instance v0, Lcom/pairip/licensecheck/LicenseActivity$ActivityType;

    const-string v1, "ERROR_DIALOG"

    const/4 v2, 0x1

    invoke-direct {v0, v1, v2}, Lcom/pairip/licensecheck/LicenseActivity$ActivityType;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/pairip/licensecheck/LicenseActivity$ActivityType;->ERROR_DIALOG:Lcom/pairip/licensecheck/LicenseActivity$ActivityType;

    .line 25
    invoke-static {}, Lcom/pairip/licensecheck/LicenseActivity$ActivityType;->$values()[Lcom/pairip/licensecheck/LicenseActivity$ActivityType;

    move-result-object v0

    sput-object v0, Lcom/pairip/licensecheck/LicenseActivity$ActivityType;->$VALUES:[Lcom/pairip/licensecheck/LicenseActivity$ActivityType;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .registers 3
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x1000,
            0x1000
        }
        names = {
            "$enum$name",
            "$enum$ordinal"
        }
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 25
    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/pairip/licensecheck/LicenseActivity$ActivityType;
    .registers 2
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x8000
        }
        names = {
            "name"
        }
    .end annotation

    .line 25
    const-class v0, Lcom/pairip/licensecheck/LicenseActivity$ActivityType;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object p0

    check-cast p0, Lcom/pairip/licensecheck/LicenseActivity$ActivityType;

    return-object p0
.end method

.method public static values()[Lcom/pairip/licensecheck/LicenseActivity$ActivityType;
    .registers 1

    .line 25
    sget-object v0, Lcom/pairip/licensecheck/LicenseActivity$ActivityType;->$VALUES:[Lcom/pairip/licensecheck/LicenseActivity$ActivityType;

    invoke-virtual {v0}, [Lcom/pairip/licensecheck/LicenseActivity$ActivityType;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Lcom/pairip/licensecheck/LicenseActivity$ActivityType;

    return-object v0
.end method

###### Class com.pairip.licensecheck.LicenseActivity$$ExternalSyntheticLambda0 (com.pairip.licensecheck.LicenseActivity$$ExternalSyntheticLambda0)
.class public final synthetic Lcom/pairip/licensecheck/LicenseActivity$$ExternalSyntheticLambda0;
.super Ljava/lang/Object;
.source "D8$$SyntheticClass"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:Lcom/pairip/licensecheck/LicenseActivity;

.field public final synthetic f$1:Landroid/app/PendingIntent;


# direct methods
.method public synthetic constructor <init>(Lcom/pairip/licensecheck/LicenseActivity;Landroid/app/PendingIntent;)V
    .registers 3

    .line 0
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/pairip/licensecheck/LicenseActivity$$ExternalSyntheticLambda0;->f$0:Lcom/pairip/licensecheck/LicenseActivity;

    iput-object p2, p0, Lcom/pairip/licensecheck/LicenseActivity$$ExternalSyntheticLambda0;->f$1:Landroid/app/PendingIntent;

    return-void
.end method


# virtual methods
.method public final run()V
    .registers 3

    .line 0
    iget-object v0, p0, Lcom/pairip/licensecheck/LicenseActivity$$ExternalSyntheticLambda0;->f$0:Lcom/pairip/licensecheck/LicenseActivity;

    iget-object v1, p0, Lcom/pairip/licensecheck/LicenseActivity$$ExternalSyntheticLambda0;->f$1:Landroid/app/PendingIntent;

    invoke-static {v0, v1}, Lcom/pairip/licensecheck/LicenseActivity;->$r8$lambda$x-JmBIDmuVzGN23Wk7Dd1TBpzO0(Lcom/pairip/licensecheck/LicenseActivity;Landroid/app/PendingIntent;)V

    return-void
.end method

###### Class com.pairip.licensecheck.LicenseActivity$$ExternalSyntheticLambda1 (com.pairip.licensecheck.LicenseActivity$$ExternalSyntheticLambda1)
.class public final synthetic Lcom/pairip/licensecheck/LicenseActivity$$ExternalSyntheticLambda1;
.super Ljava/lang/Object;
.source "D8$$SyntheticClass"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:Lcom/pairip/licensecheck/LicenseActivity;


# direct methods
.method public synthetic constructor <init>(Lcom/pairip/licensecheck/LicenseActivity;)V
    .registers 2

    .line 0
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/pairip/licensecheck/LicenseActivity$$ExternalSyntheticLambda1;->f$0:Lcom/pairip/licensecheck/LicenseActivity;

    return-void
.end method


# virtual methods
.method public final run()V
    .registers 2

    .line 0
    iget-object v0, p0, Lcom/pairip/licensecheck/LicenseActivity$$ExternalSyntheticLambda1;->f$0:Lcom/pairip/licensecheck/LicenseActivity;

    invoke-static {v0}, Lcom/pairip/licensecheck/LicenseActivity;->$r8$lambda$N5_Pzpb-eSKmOONXn3Kn0QvMbys(Lcom/pairip/licensecheck/LicenseActivity;)V

    return-void
.end method

###### Class com.pairip.licensecheck.LicenseActivity$$ExternalSyntheticLambda2 (com.pairip.licensecheck.LicenseActivity$$ExternalSyntheticLambda2)
.class public final synthetic Lcom/pairip/licensecheck/LicenseActivity$$ExternalSyntheticLambda2;
.super Ljava/lang/Object;
.source "D8$$SyntheticClass"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# instance fields
.field public final synthetic f$0:Lcom/pairip/licensecheck/LicenseActivity;


# direct methods
.method public synthetic constructor <init>(Lcom/pairip/licensecheck/LicenseActivity;)V
    .registers 2

    .line 0
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/pairip/licensecheck/LicenseActivity$$ExternalSyntheticLambda2;->f$0:Lcom/pairip/licensecheck/LicenseActivity;

    return-void
.end method


# virtual methods
.method public final onClick(Landroid/content/DialogInterface;I)V
    .registers 4

    .line 0
    iget-object v0, p0, Lcom/pairip/licensecheck/LicenseActivity$$ExternalSyntheticLambda2;->f$0:Lcom/pairip/licensecheck/LicenseActivity;

    invoke-static {v0, p1, p2}, Lcom/pairip/licensecheck/LicenseActivity;->$r8$lambda$fE_XZ7S0hhHsxQNTfy8mxeJ7kEU(Lcom/pairip/licensecheck/LicenseActivity;Landroid/content/DialogInterface;I)V

    return-void
.end method
