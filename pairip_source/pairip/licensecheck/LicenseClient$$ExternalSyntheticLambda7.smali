###### Class com.pairip.licensecheck.LicenseClient$$ExternalSyntheticLambda7 (com.pairip.licensecheck.LicenseClient$$ExternalSyntheticLambda7)
.class public final synthetic Lcom/pairip/licensecheck/LicenseClient$$ExternalSyntheticLambda7;
.super Ljava/lang/Object;
.source "D8$$SyntheticClass"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:Lcom/pairip/licensecheck/LicenseClient;

.field public final synthetic f$1:Lcom/pairip/licensecheck/RepeatedCheckMetadata;

.field public final synthetic f$2:Landroid/os/Bundle;


# direct methods
.method public synthetic constructor <init>(Lcom/pairip/licensecheck/LicenseClient;Lcom/pairip/licensecheck/RepeatedCheckMetadata;Landroid/os/Bundle;)V
    .registers 4

    .line 0
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/pairip/licensecheck/LicenseClient$$ExternalSyntheticLambda7;->f$0:Lcom/pairip/licensecheck/LicenseClient;

    iput-object p2, p0, Lcom/pairip/licensecheck/LicenseClient$$ExternalSyntheticLambda7;->f$1:Lcom/pairip/licensecheck/RepeatedCheckMetadata;

    iput-object p3, p0, Lcom/pairip/licensecheck/LicenseClient$$ExternalSyntheticLambda7;->f$2:Landroid/os/Bundle;

    return-void
.end method


# virtual methods
.method public final run()V
    .registers 4

    .line 0
    iget-object v0, p0, Lcom/pairip/licensecheck/LicenseClient$$ExternalSyntheticLambda7;->f$0:Lcom/pairip/licensecheck/LicenseClient;

    iget-object v1, p0, Lcom/pairip/licensecheck/LicenseClient$$ExternalSyntheticLambda7;->f$1:Lcom/pairip/licensecheck/RepeatedCheckMetadata;

    iget-object v2, p0, Lcom/pairip/licensecheck/LicenseClient$$ExternalSyntheticLambda7;->f$2:Landroid/os/Bundle;

    invoke-static {v0, v1, v2}, Lcom/pairip/licensecheck/LicenseClient;->$r8$lambda$ot_XkRbEJeEFG1Hy-d3H6N4DX_I(Lcom/pairip/licensecheck/LicenseClient;Lcom/pairip/licensecheck/RepeatedCheckMetadata;Landroid/os/Bundle;)V

    return-void
.end method
