package com.kosherstore.privateappstore.data.install;

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
public final class InstallStatusReceiver_MembersInjector implements MembersInjector<InstallStatusReceiver> {
  private final Provider<InstallCoordinator> installCoordinatorProvider;

  public InstallStatusReceiver_MembersInjector(
      Provider<InstallCoordinator> installCoordinatorProvider) {
    this.installCoordinatorProvider = installCoordinatorProvider;
  }

  public static MembersInjector<InstallStatusReceiver> create(
      Provider<InstallCoordinator> installCoordinatorProvider) {
    return new InstallStatusReceiver_MembersInjector(installCoordinatorProvider);
  }

  @Override
  public void injectMembers(InstallStatusReceiver instance) {
    injectInstallCoordinator(instance, installCoordinatorProvider.get());
  }

  @InjectedFieldSignature("com.kosherstore.privateappstore.data.install.InstallStatusReceiver.installCoordinator")
  public static void injectInstallCoordinator(InstallStatusReceiver instance,
      InstallCoordinator installCoordinator) {
    instance.installCoordinator = installCoordinator;
  }
}
