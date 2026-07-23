package com.kosherstore.privateappstore.data.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class UpdateCheckWorker_AssistedFactory_Impl implements UpdateCheckWorker_AssistedFactory {
  private final UpdateCheckWorker_Factory delegateFactory;

  UpdateCheckWorker_AssistedFactory_Impl(UpdateCheckWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public UpdateCheckWorker create(Context arg0, WorkerParameters arg1) {
    return delegateFactory.get(arg0, arg1);
  }

  public static Provider<UpdateCheckWorker_AssistedFactory> create(
      UpdateCheckWorker_Factory delegateFactory) {
    return InstanceFactory.create(new UpdateCheckWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<UpdateCheckWorker_AssistedFactory> createFactoryProvider(
      UpdateCheckWorker_Factory delegateFactory) {
    return InstanceFactory.create(new UpdateCheckWorker_AssistedFactory_Impl(delegateFactory));
  }
}
