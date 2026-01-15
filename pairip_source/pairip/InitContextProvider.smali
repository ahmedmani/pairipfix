###### Class com.pairip.InitContextProvider (com.pairip.InitContextProvider)
.class final Lcom/pairip/InitContextProvider;
.super Ljava/lang/Object;
.source "InitContextProvider.java"


# direct methods
.method private constructor <init>()V
    .registers 1

    .line 78
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static tryToCreateContextForBindingApp()Landroid/content/Context;
    .registers 12

    const/4 v0, 0x0

    .line 20
    :try_start_1
    const-string v1, "android.app.LoadedApk"

    invoke-static {v1}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v1

    .line 22
    const-string v2, "android.app.ActivityThread"

    invoke-static {v2}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v2

    .line 23
    const-string v3, "currentActivityThread"

    const/4 v4, 0x0

    new-array v5, v4, [Ljava/lang/Class;

    invoke-virtual {v2, v3, v0}, Ljava/lang/Class;->getMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v3

    .line 24
    const-string v5, "mBoundApplication"

    invoke-virtual {v2, v5}, Ljava/lang/Class;->getDeclaredField(Ljava/lang/String;)Ljava/lang/reflect/Field;

    move-result-object v5

    const/4 v6, 0x1

    .line 25
    invoke-virtual {v5, v6}, Ljava/lang/reflect/Field;->setAccessible(Z)V

    .line 27
    const-string v7, "android.app.ActivityThread$AppBindData"

    invoke-static {v7}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v7

    .line 28
    const-string v8, "info"

    invoke-virtual {v7, v8}, Ljava/lang/Class;->getDeclaredField(Ljava/lang/String;)Ljava/lang/reflect/Field;

    move-result-object v7

    .line 29
    invoke-virtual {v7, v6}, Ljava/lang/reflect/Field;->setAccessible(Z)V

    .line 31
    const-string v8, "android.app.ContextImpl"

    invoke-static {v8}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v8

    .line 32
    const-string v9, "createAppContext"

    const/4 v10, 0x2

    new-array v11, v10, [Ljava/lang/Class;

    aput-object v2, v11, v4

    aput-object v1, v11, v6

    .line 33
    invoke-virtual {v8, v9, v11}, Ljava/lang/Class;->getDeclaredMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v1

    .line 35
    invoke-virtual {v1, v6}, Ljava/lang/reflect/Method;->setAccessible(Z)V

    .line 37
    invoke-virtual {v3, v0, v0}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    .line 38
    invoke-virtual {v5, v3}, Ljava/lang/reflect/Field;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    .line 39
    invoke-virtual {v7, v5}, Ljava/lang/reflect/Field;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    .line 40
    new-array v7, v10, [Ljava/lang/Object;

    aput-object v3, v7, v4

    aput-object v5, v7, v6

    invoke-virtual {v1, v0, v7}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    .line 42
    invoke-static {v2, v3}, Lcom/pairip/InitContextProvider;->tryToInitializeBasicInstrumentationForStartActivity(Ljava/lang/Class;Ljava/lang/Object;)V

    .line 44
    check-cast v1, Landroid/content/Context;
    :try_end_60
    .catch Ljava/lang/ClassNotFoundException; {:try_start_1 .. :try_end_60} :catch_61
    .catch Ljava/lang/IllegalAccessException; {:try_start_1 .. :try_end_60} :catch_61
    .catch Ljava/lang/reflect/InvocationTargetException; {:try_start_1 .. :try_end_60} :catch_61
    .catch Ljava/lang/NoSuchMethodException; {:try_start_1 .. :try_end_60} :catch_61
    .catch Ljava/lang/NoSuchFieldException; {:try_start_1 .. :try_end_60} :catch_61
    .catch Ljava/lang/RuntimeException; {:try_start_1 .. :try_end_60} :catch_61

    return-object v1

    :catch_61
    return-object v0
.end method

.method private static tryToInitializeBasicInstrumentationForStartActivity(Ljava/lang/Class;Ljava/lang/Object;)V
    .registers 4
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0,
            0x0
        }
        names = {
            "activityThreadClass",
            "activityThread"
        }
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/Class<",
            "*>;",
            "Ljava/lang/Object;",
            ")V"
        }
    .end annotation

    .line 61
    :try_start_0
    const-string v0, "mInstrumentation"

    invoke-virtual {p0, v0}, Ljava/lang/Class;->getDeclaredField(Ljava/lang/String;)Ljava/lang/reflect/Field;

    move-result-object p0

    const/4 v0, 0x1

    .line 62
    invoke-virtual {p0, v0}, Ljava/lang/reflect/Field;->setAccessible(Z)V

    .line 64
    const-string v0, "android.app.Instrumentation"

    invoke-static {v0}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v0

    const/4 v1, 0x0

    .line 65
    new-array v1, v1, [Ljava/lang/Class;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Ljava/lang/Class;->getDeclaredConstructor([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;

    move-result-object v0

    invoke-virtual {v0, v1}, Ljava/lang/reflect/Constructor;->newInstance([Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    .line 66
    invoke-virtual {p0, p1, v0}, Ljava/lang/reflect/Field;->set(Ljava/lang/Object;Ljava/lang/Object;)V
    :try_end_1f
    .catch Ljava/lang/ClassNotFoundException; {:try_start_0 .. :try_end_1f} :catch_1f
    .catch Ljava/lang/IllegalAccessException; {:try_start_0 .. :try_end_1f} :catch_1f
    .catch Ljava/lang/InstantiationException; {:try_start_0 .. :try_end_1f} :catch_1f
    .catch Ljava/lang/reflect/InvocationTargetException; {:try_start_0 .. :try_end_1f} :catch_1f
    .catch Ljava/lang/NoSuchFieldException; {:try_start_0 .. :try_end_1f} :catch_1f
    .catch Ljava/lang/NoSuchMethodException; {:try_start_0 .. :try_end_1f} :catch_1f
    .catch Ljava/lang/RuntimeException; {:try_start_0 .. :try_end_1f} :catch_1f

    :catch_1f
    return-void
.end method
