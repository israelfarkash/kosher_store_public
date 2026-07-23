package com.kosherstore.privateappstore;

import androidx.hilt.work.HiltWorkerFactory;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class PrivateStoreApp_MembersInjector implements MembersInjector<PrivateStoreApp> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public PrivateStoreApp_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<PrivateStoreApp> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new PrivateStoreApp_MembersInjector(workerFactoryProvider);
  }

  @Override
  public void injectMembers(PrivateStoreApp instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.kosherstore.privateappstore.PrivateStoreApp.workerFactory")
  public static void injectWorkerFactory(PrivateStoreApp instance,
      HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
