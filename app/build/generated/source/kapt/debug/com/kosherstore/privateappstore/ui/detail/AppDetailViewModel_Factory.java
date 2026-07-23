package com.kosherstore.privateappstore.ui.detail;

import androidx.lifecycle.SavedStateHandle;
import com.kosherstore.privateappstore.domain.repository.StoreAppRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class AppDetailViewModel_Factory implements Factory<AppDetailViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<StoreAppRepository> repositoryProvider;

  public AppDetailViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<StoreAppRepository> repositoryProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public AppDetailViewModel get() {
    return newInstance(savedStateHandleProvider.get(), repositoryProvider.get());
  }

  public static AppDetailViewModel_Factory create(
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<StoreAppRepository> repositoryProvider) {
    return new AppDetailViewModel_Factory(savedStateHandleProvider, repositoryProvider);
  }

  public static AppDetailViewModel newInstance(SavedStateHandle savedStateHandle,
      StoreAppRepository repository) {
    return new AppDetailViewModel(savedStateHandle, repository);
  }
}
