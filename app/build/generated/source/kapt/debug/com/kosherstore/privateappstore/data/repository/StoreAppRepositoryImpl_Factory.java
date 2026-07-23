package com.kosherstore.privateappstore.data.repository;

import android.content.Context;
import com.google.gson.Gson;
import com.kosherstore.privateappstore.data.download.DownloadCoordinator;
import com.kosherstore.privateappstore.data.install.InstallCoordinator;
import com.kosherstore.privateappstore.data.install.PackageChangeNotifier;
import com.kosherstore.privateappstore.data.local.dao.AppDao;
import com.kosherstore.privateappstore.data.local.dao.ManagedInstallDao;
import com.kosherstore.privateappstore.data.remote.AppsApiService;
import com.kosherstore.privateappstore.util.StoreStatsStore;
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
public final class StoreAppRepositoryImpl_Factory implements Factory<StoreAppRepositoryImpl> {
  private final Provider<Context> contextProvider;

  private final Provider<AppDao> appDaoProvider;

  private final Provider<ManagedInstallDao> managedInstallDaoProvider;

  private final Provider<AppsApiService> appsApiServiceProvider;

  private final Provider<Gson> gsonProvider;

  private final Provider<DownloadCoordinator> downloadCoordinatorProvider;

  private final Provider<InstallCoordinator> installCoordinatorProvider;

  private final Provider<PackageChangeNotifier> packageChangeNotifierProvider;

  private final Provider<StoreStatsStore> statsStoreProvider;

  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  public StoreAppRepositoryImpl_Factory(Provider<Context> contextProvider,
      Provider<AppDao> appDaoProvider, Provider<ManagedInstallDao> managedInstallDaoProvider,
      Provider<AppsApiService> appsApiServiceProvider, Provider<Gson> gsonProvider,
      Provider<DownloadCoordinator> downloadCoordinatorProvider,
      Provider<InstallCoordinator> installCoordinatorProvider,
      Provider<PackageChangeNotifier> packageChangeNotifierProvider,
      Provider<StoreStatsStore> statsStoreProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    this.contextProvider = contextProvider;
    this.appDaoProvider = appDaoProvider;
    this.managedInstallDaoProvider = managedInstallDaoProvider;
    this.appsApiServiceProvider = appsApiServiceProvider;
    this.gsonProvider = gsonProvider;
    this.downloadCoordinatorProvider = downloadCoordinatorProvider;
    this.installCoordinatorProvider = installCoordinatorProvider;
    this.packageChangeNotifierProvider = packageChangeNotifierProvider;
    this.statsStoreProvider = statsStoreProvider;
    this.ioDispatcherProvider = ioDispatcherProvider;
  }

  @Override
  public StoreAppRepositoryImpl get() {
    return newInstance(contextProvider.get(), appDaoProvider.get(), managedInstallDaoProvider.get(), appsApiServiceProvider.get(), gsonProvider.get(), downloadCoordinatorProvider.get(), installCoordinatorProvider.get(), packageChangeNotifierProvider.get(), statsStoreProvider.get(), ioDispatcherProvider.get());
  }

  public static StoreAppRepositoryImpl_Factory create(Provider<Context> contextProvider,
      Provider<AppDao> appDaoProvider, Provider<ManagedInstallDao> managedInstallDaoProvider,
      Provider<AppsApiService> appsApiServiceProvider, Provider<Gson> gsonProvider,
      Provider<DownloadCoordinator> downloadCoordinatorProvider,
      Provider<InstallCoordinator> installCoordinatorProvider,
      Provider<PackageChangeNotifier> packageChangeNotifierProvider,
      Provider<StoreStatsStore> statsStoreProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new StoreAppRepositoryImpl_Factory(contextProvider, appDaoProvider, managedInstallDaoProvider, appsApiServiceProvider, gsonProvider, downloadCoordinatorProvider, installCoordinatorProvider, packageChangeNotifierProvider, statsStoreProvider, ioDispatcherProvider);
  }

  public static StoreAppRepositoryImpl newInstance(Context context, AppDao appDao,
      ManagedInstallDao managedInstallDao, AppsApiService appsApiService, Gson gson,
      DownloadCoordinator downloadCoordinator, InstallCoordinator installCoordinator,
      PackageChangeNotifier packageChangeNotifier, StoreStatsStore statsStore,
      CoroutineDispatcher ioDispatcher) {
    return new StoreAppRepositoryImpl(context, appDao, managedInstallDao, appsApiService, gson, downloadCoordinator, installCoordinator, packageChangeNotifier, statsStore, ioDispatcher);
  }
}
