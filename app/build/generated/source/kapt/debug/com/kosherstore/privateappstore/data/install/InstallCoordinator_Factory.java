package com.kosherstore.privateappstore.data.install;

import android.content.Context;
import com.kosherstore.privateappstore.data.local.dao.ManagedInstallDao;
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
public final class InstallCoordinator_Factory implements Factory<InstallCoordinator> {
  private final Provider<Context> contextProvider;

  private final Provider<ManagedInstallDao> managedInstallDaoProvider;

  private final Provider<PackageChangeNotifier> packageChangeNotifierProvider;

  private final Provider<StoreStatsStore> statsStoreProvider;

  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  public InstallCoordinator_Factory(Provider<Context> contextProvider,
      Provider<ManagedInstallDao> managedInstallDaoProvider,
      Provider<PackageChangeNotifier> packageChangeNotifierProvider,
      Provider<StoreStatsStore> statsStoreProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    this.contextProvider = contextProvider;
    this.managedInstallDaoProvider = managedInstallDaoProvider;
    this.packageChangeNotifierProvider = packageChangeNotifierProvider;
    this.statsStoreProvider = statsStoreProvider;
    this.ioDispatcherProvider = ioDispatcherProvider;
  }

  @Override
  public InstallCoordinator get() {
    return newInstance(contextProvider.get(), managedInstallDaoProvider.get(), packageChangeNotifierProvider.get(), statsStoreProvider.get(), ioDispatcherProvider.get());
  }

  public static InstallCoordinator_Factory create(Provider<Context> contextProvider,
      Provider<ManagedInstallDao> managedInstallDaoProvider,
      Provider<PackageChangeNotifier> packageChangeNotifierProvider,
      Provider<StoreStatsStore> statsStoreProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new InstallCoordinator_Factory(contextProvider, managedInstallDaoProvider, packageChangeNotifierProvider, statsStoreProvider, ioDispatcherProvider);
  }

  public static InstallCoordinator newInstance(Context context, ManagedInstallDao managedInstallDao,
      PackageChangeNotifier packageChangeNotifier, StoreStatsStore statsStore,
      CoroutineDispatcher ioDispatcher) {
    return new InstallCoordinator(context, managedInstallDao, packageChangeNotifier, statsStore, ioDispatcher);
  }
}
