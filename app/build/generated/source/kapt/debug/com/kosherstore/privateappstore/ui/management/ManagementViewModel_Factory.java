package com.kosherstore.privateappstore.ui.management;

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
public final class ManagementViewModel_Factory implements Factory<ManagementViewModel> {
  private final Provider<StoreAppRepository> repositoryProvider;

  public ManagementViewModel_Factory(Provider<StoreAppRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ManagementViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static ManagementViewModel_Factory create(
      Provider<StoreAppRepository> repositoryProvider) {
    return new ManagementViewModel_Factory(repositoryProvider);
  }

  public static ManagementViewModel newInstance(StoreAppRepository repository) {
    return new ManagementViewModel(repository);
  }
}
