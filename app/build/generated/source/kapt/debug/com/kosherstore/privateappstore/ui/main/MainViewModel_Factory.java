package com.kosherstore.privateappstore.ui.main;

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
public final class MainViewModel_Factory implements Factory<MainViewModel> {
  private final Provider<StoreAppRepository> repositoryProvider;

  public MainViewModel_Factory(Provider<StoreAppRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public MainViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static MainViewModel_Factory create(Provider<StoreAppRepository> repositoryProvider) {
    return new MainViewModel_Factory(repositoryProvider);
  }

  public static MainViewModel newInstance(StoreAppRepository repository) {
    return new MainViewModel(repository);
  }
}
