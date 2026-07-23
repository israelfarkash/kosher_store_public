package com.kosherstore.privateappstore.data.install;

import com.kosherstore.privateappstore.data.download.DownloadCoordinator;
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
public final class PackageChangeReceiver_MembersInjector implements MembersInjector<PackageChangeReceiver> {
  private final Provider<InstallCoordinator> installCoordinatorProvider;

  private final Provider<DownloadCoordinator> downloadCoordinatorProvider;

  public PackageChangeReceiver_MembersInjector(
      Provider<InstallCoordinator> installCoordinatorProvider,
      Provider<DownloadCoordinator> downloadCoordinatorProvider) {
    this.installCoordinatorProvider = installCoordinatorProvider;
    this.downloadCoordinatorProvider = downloadCoordinatorProvider;
  }

  public static MembersInjector<PackageChangeReceiver> create(
      Provider<InstallCoordinator> installCoordinatorProvider,
      Provider<DownloadCoordinator> downloadCoordinatorProvider) {
    return new PackageChangeReceiver_MembersInjector(installCoordinatorProvider, downloadCoordinatorProvider);
  }

  @Override
  public void injectMembers(PackageChangeReceiver instance) {
    injectInstallCoordinator(instance, installCoordinatorProvider.get());
    injectDownloadCoordinator(instance, downloadCoordinatorProvider.get());
  }

  @InjectedFieldSignature("com.kosherstore.privateappstore.data.install.PackageChangeReceiver.installCoordinator")
  public static void injectInstallCoordinator(PackageChangeReceiver instance,
      InstallCoordinator installCoordinator) {
    instance.installCoordinator = installCoordinator;
  }

  @InjectedFieldSignature("com.kosherstore.privateappstore.data.install.PackageChangeReceiver.downloadCoordinator")
  public static void injectDownloadCoordinator(PackageChangeReceiver instance,
      DownloadCoordinator downloadCoordinator) {
    instance.downloadCoordinator = downloadCoordinator;
  }
}
