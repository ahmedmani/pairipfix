###### Class com.pairip.application.Application (com.pairip.application.Application)
.class public Lcom/pairip/application/Application;
.super Lcom/vlv/aravali/KukuFMApplication;


# direct methods
.method public constructor <init>()V
    .registers 1

    invoke-direct {p0}, Lcom/vlv/aravali/KukuFMApplication;-><init>()V

    return-void
.end method


# virtual methods
.method protected attachBaseContext(Landroid/content/Context;)V
    .registers 2

    invoke-static {p1}, Lcom/pairip/VMRunner;->setContext(Landroid/content/Context;)V

    invoke-static {p1}, Lcom/pairip/SignatureCheck;->verifyIntegrity(Landroid/content/Context;)V

    invoke-super {p0, p1}, Lcom/pairip/application/Application;->attachBaseContext(Landroid/content/Context;)V

    return-void
.end method
