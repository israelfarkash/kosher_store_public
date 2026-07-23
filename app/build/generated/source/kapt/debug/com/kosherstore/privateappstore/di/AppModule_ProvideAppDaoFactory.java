package com.kosherstore.privateappstore.di;

import com.kosherstore.privateappstore.data.local.AppDatabase;
import com.kosherstore.privateappstore.data.local.dao.AppDao;
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
public final class AppModule_ProvideAppDaoFactory implements Factory<AppDao> {
  private final Provider<AppDatabase> databaseProvider;

  public AppModule_ProvideAppDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public AppDao get() {
    return provideAppDao(databaseProvider.get());
  }

  public static AppModule_ProvideAppDaoFactory create(Provider<AppDatabase> databaseProvider) {
    return new AppModule_ProvideAppDaoFactory(databaseProvider);
  }

  public static AppDao provideAppDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideAppDao(database));
  }
}
