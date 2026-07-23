package com.kosherstore.privateappstore.ui;

import com.kosherstore.privateappstore.data.install.InstallCoordinator;
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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<InstallCoordinator> installCoordinatorProvider;

  public MainActivity_MembersInjector(Provider<InstallCoordinator> installCoordinatorProvider) {
    this.installCoordinatorProvider = installCoordinatorProvider;
  }

  public static MembersInjector<MainActivity> create(
      Provider<InstallCoordinator> installCoordinatorProvider) {
    return new MainActivity_MembersInjector(installCoordinatorProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectInstallCoordinator(instance, installCoordinatorProvider.get());
  }

  @InjectedFieldSignature("com.kosherstore.privateappstore.ui.MainActivity.installCoordinator")
  public static void injectInstallCoordinator(MainActivity instance,
      InstallCoordinator installCoordinator) {
    instance.installCoordinator = installCoordinator;
  }
}
