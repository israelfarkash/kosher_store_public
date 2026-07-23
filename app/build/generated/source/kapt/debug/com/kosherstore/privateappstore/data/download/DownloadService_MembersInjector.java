package com.kosherstore.privateappstore.data.download;

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
public final class DownloadService_MembersInjector implements MembersInjector<DownloadService> {
  private final Provider<DownloadCoordinator> downloadCoordinatorProvider;

  public DownloadService_MembersInjector(
      Provider<DownloadCoordinator> downloadCoordinatorProvider) {
    this.downloadCoordinatorProvider = downloadCoordinatorProvider;
  }

  public static MembersInjector<DownloadService> create(
      Provider<DownloadCoordinator> downloadCoordinatorProvider) {
    return new DownloadService_MembersInjector(downloadCoordinatorProvider);
  }

  @Override
  public void injectMembers(DownloadService instance) {
    injectDownloadCoordinator(instance, downloadCoordinatorProvider.get());
  }

  @InjectedFieldSignature("com.kosherstore.privateappstore.data.download.DownloadService.downloadCoordinator")
  public static void injectDownloadCoordinator(DownloadService instance,
      DownloadCoordinator downloadCoordinator) {
    instance.downloadCoordinator = downloadCoordinator;
  }
}
