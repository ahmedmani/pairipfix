###### Class com.pairip.licensecheck.LicenseClient$$ExternalSyntheticLambda6 (com.pairip.licensecheck.LicenseClient$$ExternalSyntheticLambda6)
.class public final synthetic Lcom/pairip/licensecheck/LicenseClient$$ExternalSyntheticLambda6;
.super Ljava/lang/Object;
.source "D8$$SyntheticClass"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:Lcom/pairip/licensecheck/LicenseClient;

.field public final synthetic f$1:Landroid/os/IBinder;


# direct methods
.method public synthetic constructor <init>(Lcom/pairip/licensecheck/LicenseClient;Landroid/os/IBinder;)V
    .registers 3

    .line 0
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/pairip/licensecheck/LicenseClient$$ExternalSyntheticLambda6;->f$0:Lcom/pairip/licensecheck/LicenseClient;

    iput-object p2, p0, Lcom/pairip/licensecheck/LicenseClient$$ExternalSyntheticLambda6;->f$1:Landroid/os/IBinder;

    return-void
.end method


# virtual methods
.method public final run()V
    .registers 3

    .line 0
    iget-object v0, p0, Lcom/pairip/licensecheck/LicenseClient$$ExternalSyntheticLambda6;->f$0:Lcom/pairip/licensecheck/LicenseClient;

    iget-object v1, p0, Lcom/pairip/licensecheck/LicenseClient$$ExternalSyntheticLambda6;->f$1:Landroid/os/IBinder;

    invoke-static {v0, v1}, Lcom/pairip/licensecheck/LicenseClient;->$r8$lambda$gb-vmUiJUmqdCloCudVdY-igh7I(Lcom/pairip/licensecheck/LicenseClient;Landroid/os/IBinder;)V

    return-void
.end method
