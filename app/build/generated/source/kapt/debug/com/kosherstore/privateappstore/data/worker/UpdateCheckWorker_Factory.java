package com.kosherstore.privateappstore.data.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.kosherstore.privateappstore.domain.repository.StoreAppRepository;
import dagger.internal.DaggerGenerated;
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
public final class UpdateCheckWorker_Factory {
  private final Provider<StoreAppRepository> repositoryProvider;

  public UpdateCheckWorker_Factory(Provider<StoreAppRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  public UpdateCheckWorker get(Context appContext, WorkerParameters workerParams) {
    return newInstance(appContext, workerParams, repositoryProvider.get());
  }

  public static UpdateCheckWorker_Factory create(Provider<StoreAppRepository> repositoryProvider) {
    return new UpdateCheckWorker_Factory(repositoryProvider);
  }

  public static UpdateCheckWorker newInstance(Context appContext, WorkerParameters workerParams,
      StoreAppRepository repository) {
    return new UpdateCheckWorker(appContext, workerParams, repository);
  }
}
