package com.kosherstore.privateappstore.data.download;

import android.app.DownloadManager;
import android.content.Context;
import com.kosherstore.privateappstore.data.install.InstallCoordinator;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata({
    "dagger.hilt.android.qualifiers.ApplicationContext",
    "com.kosherstore.privateappstore.di.IoDispatcher"
})
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
public final class DownloadCoordinator_Factory implements Factory<DownloadCoordinator> {
  private final Provider<Context> contextProvider;

  private final Provider<DownloadManager> downloadManagerProvider;

  private final Provider<InstallCoordinator> installCoordinatorProvider;

  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  public DownloadCoordinator_Factory(Provider<Context> contextProvider,
      Provider<DownloadManager> downloadManagerProvider,
      Provider<InstallCoordinator> installCoordinatorProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    this.contextProvider = contextProvider;
    this.downloadManagerProvider = downloadManagerProvider;
    this.installCoordinatorProvider = installCoordinatorProvider;
    this.ioDispatcherProvider = ioDispatcherProvider;
  }

  @Override
  public DownloadCoordinator get() {
    return newInstance(contextProvider.get(), downloadManagerProvider.get(), installCoordinatorProvider.get(), ioDispatcherProvider.get());
  }

  public static DownloadCoordinator_Factory create(Provider<Context> contextProvider,
      Provider<DownloadManager> downloadManagerProvider,
      Provider<InstallCoordinator> installCoordinatorProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new DownloadCoordinator_Factory(contextProvider, downloadManagerProvider, installCoordinatorProvider, ioDispatcherProvider);
  }

  public static DownloadCoordinator newInstance(Context context, DownloadManager downloadManager,
      InstallCoordinator installCoordinator, CoroutineDispatcher ioDispatcher) {
    return new DownloadCoordinator(context, downloadManager, installCoordinator, ioDispatcher);
  }
}
