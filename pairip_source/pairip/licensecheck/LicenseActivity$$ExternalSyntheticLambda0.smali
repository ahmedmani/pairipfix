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
