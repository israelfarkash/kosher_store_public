package com.kosherstore.privateappstore.data.worker;

import android.content.Context;
import androidx.hilt.work.HiltWorker;
import androidx.work.Constraints;
import androidx.work.CoroutineWorker;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.WorkManager;
import androidx.work.WorkerParameters;
import com.kosherstore.privateappstore.domain.repository.StoreAppRepository;
import com.kosherstore.privateappstore.util.NotificationHelper;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import java.util.concurrent.TimeUnit;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 \f2\u00020\u0001:\u0001\fB#\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\t\u001a\u00020\nH\u0096@\u00a2\u0006\u0002\u0010\u000bR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/kosherstore/privateappstore/data/worker/UpdateCheckWorker;", "Landroidx/work/CoroutineWorker;", "appContext", "Landroid/content/Context;", "workerParams", "Landroidx/work/WorkerParameters;", "repository", "Lcom/kosherstore/privateappstore/domain/repository/StoreAppRepository;", "(Landroid/content/Context;Landroidx/work/WorkerParameters;Lcom/kosherstore/privateappstore/domain/repository/StoreAppRepository;)V", "doWork", "Landroidx/work/ListenableWorker$Result;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
@androidx.hilt.work.HiltWorker()
public final class UpdateCheckWorker extends androidx.work.CoroutineWorker {
    @org.jetbrains.annotations.NotNull()
    private final com.kosherstore.privateappstore.domain.repository.StoreAppRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String UNIQUE_WORK_NAME = "daily_update_check";
    @org.jetbrains.annotations.NotNull()
    public static final com.kosherstore.privateappstore.data.worker.UpdateCheckWorker.Companion Companion = null;
    
    @dagger.assisted.AssistedInject()
    public UpdateCheckWorker(@dagger.assisted.Assisted()
    @org.jetbrains.annotations.NotNull()
    android.content.Context appContext, @dagger.assisted.Assisted()
    @org.jetbrains.annotations.NotNull()
    androidx.work.WorkerParameters workerParams, @org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.domain.repository.StoreAppRepository repository) {
        super(null, null);
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object doWork(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super androidx.work.ListenableWorker.Result> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/kosherstore/privateappstore/data/worker/UpdateCheckWorker$Companion;", "", "()V", "UNIQUE_WORK_NAME", "", "enqueuePeriodic", "", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final void enqueuePeriodic(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
        }
    }
}