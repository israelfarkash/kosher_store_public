package com.kosherstore.privateappstore.di;

import android.app.DownloadManager;
import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class AppModule_ProvideDownloadManagerFactory implements Factory<DownloadManager> {
  private final Provider<Context> contextProvider;

  public AppModule_ProvideDownloadManagerFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public DownloadManager get() {
    return provideDownloadManager(contextProvider.get());
  }

  public static AppModule_ProvideDownloadManagerFactory create(Provider<Context> contextProvider) {
    return new AppModule_ProvideDownloadManagerFactory(contextProvider);
  }

  public static DownloadManager provideDownloadManager(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideDownloadManager(context));
  }
}
