package com.kosherstore.privateappstore.di;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import androidx.room.Room;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kosherstore.privateappstore.BuildConfig;
import com.kosherstore.privateappstore.data.local.AppDatabase;
import com.kosherstore.privateappstore.data.local.dao.AppDao;
import com.kosherstore.privateappstore.data.local.dao.ManagedInstallDao;
import com.kosherstore.privateappstore.data.remote.AppsApiService;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.Dispatchers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007J\u0012\u0010\u000b\u001a\u00020\u00062\b\b\u0001\u0010\f\u001a\u00020\rH\u0007J\u0012\u0010\u000e\u001a\u00020\u000f2\b\b\u0001\u0010\f\u001a\u00020\rH\u0007J\b\u0010\u0010\u001a\u00020\u0011H\u0007J\b\u0010\u0012\u001a\u00020\u0013H\u0007J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\b\u0010\u0016\u001a\u00020\u0017H\u0007J\u0018\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u0011H\u0007J\u0012\u0010\u001b\u001a\u00020\u001c2\b\b\u0001\u0010\f\u001a\u00020\rH\u0007\u00a8\u0006\u001d"}, d2 = {"Lcom/kosherstore/privateappstore/di/AppModule;", "", "()V", "provideAppDao", "Lcom/kosherstore/privateappstore/data/local/dao/AppDao;", "database", "Lcom/kosherstore/privateappstore/data/local/AppDatabase;", "provideAppsApiService", "Lcom/kosherstore/privateappstore/data/remote/AppsApiService;", "retrofit", "Lretrofit2/Retrofit;", "provideDatabase", "context", "Landroid/content/Context;", "provideDownloadManager", "Landroid/app/DownloadManager;", "provideGson", "Lcom/google/gson/Gson;", "provideIoDispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "provideManagedInstallDao", "Lcom/kosherstore/privateappstore/data/local/dao/ManagedInstallDao;", "provideOkHttpClient", "Lokhttp3/OkHttpClient;", "provideRetrofit", "okHttpClient", "gson", "provideSharedPreferences", "Landroid/content/SharedPreferences;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class AppModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.kosherstore.privateappstore.di.AppModule INSTANCE = null;
    
    private AppModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.google.gson.Gson provideGson() {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final okhttp3.OkHttpClient provideOkHttpClient() {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final retrofit2.Retrofit provideRetrofit(@org.jetbrains.annotations.NotNull()
    okhttp3.OkHttpClient okHttpClient, @org.jetbrains.annotations.NotNull()
    com.google.gson.Gson gson) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.kosherstore.privateappstore.data.remote.AppsApiService provideAppsApiService(@org.jetbrains.annotations.NotNull()
    retrofit2.Retrofit retrofit) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.kosherstore.privateappstore.data.local.AppDatabase provideDatabase(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.kosherstore.privateappstore.data.local.dao.AppDao provideAppDao(@org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.data.local.AppDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.kosherstore.privateappstore.data.local.dao.ManagedInstallDao provideManagedInstallDao(@org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.data.local.AppDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final android.app.DownloadManager provideDownloadManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final android.content.SharedPreferences provideSharedPreferences(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @IoDispatcher()
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.CoroutineDispatcher provideIoDispatcher() {
        return null;
    }
}