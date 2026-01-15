###### Class com.pairip.Utils (com.pairip.Utils)
.class public final Lcom/pairip/Utils;
.super Ljava/lang/Object;
.source "Utils.java"


# direct methods
.method private constructor <init>()V
    .registers 1

    .line 28
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static createThreadFactory(ZI)Ljava/util/concurrent/ThreadFactory;
    .registers 3
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0,
            0x0
        }
        names = {
            "isDaemon",
            "priority"
        }
    .end annotation

    .line 17
    new-instance v0, Lcom/pairip/Utils$1;

    invoke-direct {v0, p0, p1}, Lcom/pairip/Utils$1;-><init>(ZI)V

    return-object v0
.end method

###### Class com.pairip.Utils.AnonymousClass1 (com.pairip.Utils$1)
.class Lcom/pairip/Utils$1;
.super Ljava/lang/Object;
.source "Utils.java"

# interfaces
.implements Ljava/util/concurrent/ThreadFactory;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/pairip/Utils;->createThreadFactory(ZI)Ljava/util/concurrent/ThreadFactory;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$isDaemon:Z

.field final synthetic val$priority:I


# direct methods
.method constructor <init>(ZI)V
    .registers 3
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x1010,
            0x1010
        }
        names = {
            "val$isDaemon",
            "val$priority"
        }
    .end annotation

    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 17
    iput-boolean p1, p0, Lcom/pairip/Utils$1;->val$isDaemon:Z

    iput p2, p0, Lcom/pairip/Utils$1;->val$priority:I

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public newThread(Ljava/lang/Runnable;)Ljava/lang/Thread;
    .registers 3
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0
        }
        names = {
            "r"
        }
    .end annotation

    .line 20
    new-instance v0, Ljava/lang/Thread;

    invoke-direct {v0, p1}, Ljava/lang/Thread;-><init>(Ljava/lang/Runnable;)V

    .line 21
    iget-boolean p1, p0, Lcom/pairip/Utils$1;->val$isDaemon:Z

    invoke-virtual {v0, p1}, Ljava/lang/Thread;->setDaemon(Z)V

    .line 22
    iget p1, p0, Lcom/pairip/Utils$1;->val$priority:I

    invoke-virtual {v0, p1}, Ljava/lang/Thread;->setPriority(I)V

    return-object v0
.end method
