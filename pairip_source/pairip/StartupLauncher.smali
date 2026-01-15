###### Class com.pairip.StartupLauncher (com.pairip.StartupLauncher)
.class public final Lcom/pairip/StartupLauncher;
.super Ljava/lang/Object;
.source "StartupLauncher.java"


# static fields
.field private static launchCalled:Z = false

.field private static startupProgramName:Ljava/lang/String; = "aKja6rD08SHDQfuc"


# direct methods
.method static constructor <clinit>()V
    .registers 0

    return-void
.end method

.method private constructor <init>()V
    .registers 1

    .line 17
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static declared-synchronized launch()V
    .registers 3

    const-class v0, Lcom/pairip/StartupLauncher;

    monitor-enter v0

    .line 10
    :try_start_3
    sget-boolean v1, Lcom/pairip/StartupLauncher;->launchCalled:Z
    :try_end_5
    .catchall {:try_start_3 .. :try_end_5} :catchall_14

    if-eqz v1, :cond_9

    .line 11
    monitor-exit v0

    return-void

    :cond_9
    const/4 v1, 0x1

    .line 13
    :try_start_a
    sput-boolean v1, Lcom/pairip/StartupLauncher;->launchCalled:Z

    .line 14
    sget-object v1, Lcom/pairip/StartupLauncher;->startupProgramName:Ljava/lang/String;

    const/4 v2, 0x0

    invoke-static {v1, v2}, Lcom/pairip/VMRunner;->invoke(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_12
    .catchall {:try_start_a .. :try_end_12} :catchall_14

    .line 15
    monitor-exit v0

    return-void

    :catchall_14
    move-exception v1

    :try_start_15
    monitor-exit v0
    :try_end_16
    .catchall {:try_start_15 .. :try_end_16} :catchall_14

    throw v1
.end method
