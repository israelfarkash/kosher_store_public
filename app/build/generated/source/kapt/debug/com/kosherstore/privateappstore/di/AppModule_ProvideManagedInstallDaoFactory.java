package com.kosherstore.privateappstore.di;

import com.kosherstore.privateappstore.data.local.AppDatabase;
import com.kosherstore.privateappstore.data.local.dao.ManagedInstallDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
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
public final class AppModule_ProvideManagedInstallDaoFactory implements Factory<ManagedInstallDao> {
  private final Provider<AppDatabase> databaseProvider;

  public AppModule_ProvideManagedInstallDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ManagedInstallDao get() {
    return provideManagedInstallDao(databaseProvider.get());
  }

  public static AppModule_ProvideManagedInstallDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new AppModule_ProvideManagedInstallDaoFactory(databaseProvider);
  }

  public static ManagedInstallDao provideManagedInstallDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideManagedInstallDao(database));
  }
}
