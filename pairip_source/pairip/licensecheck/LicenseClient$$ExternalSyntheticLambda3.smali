###### Class com.pairip.licensecheck.LicenseClient$$ExternalSyntheticLambda3 (com.pairip.licensecheck.LicenseClient$$ExternalSyntheticLambda3)
.class public final synthetic Lcom/pairip/licensecheck/LicenseClient$$ExternalSyntheticLambda3;
.super Ljava/lang/Object;
.source "D8$$SyntheticClass"

# interfaces
.implements Lcom/pairip/licensecheck/LicenseClient$ImmediateTaskExecutor;


# instance fields
.field public final synthetic f$0:Landroid/os/Handler;


# direct methods
.method public synthetic constructor <init>(Landroid/os/Handler;)V
    .registers 2

    .line 0
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/pairip/licensecheck/LicenseClient$$ExternalSyntheticLambda3;->f$0:Landroid/os/Handler;

    return-void
.end method


# virtual methods
.method public final run(Ljava/lang/Runnable;)V
    .registers 3

    .line 0
    iget-object v0, p0, Lcom/pairip/licensecheck/LicenseClient$$ExternalSyntheticLambda3;->f$0:Landroid/os/Handler;

    invoke-virtual {v0, p1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    return-void
.end method
