package com.kosherstore.privateappstore.di;

import com.kosherstore.privateappstore.data.remote.AppsApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AppModule_ProvideAppsApiServiceFactory implements Factory<AppsApiService> {
  private final Provider<Retrofit> retrofitProvider;

  public AppModule_ProvideAppsApiServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public AppsApiService get() {
    return provideAppsApiService(retrofitProvider.get());
  }

  public static AppModule_ProvideAppsApiServiceFactory create(Provider<Retrofit> retrofitProvider) {
    return new AppModule_ProvideAppsApiServiceFactory(retrofitProvider);
  }

  public static AppsApiService provideAppsApiService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideAppsApiService(retrofit));
  }
}
