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
public final class DownloadBroadcastReceiver_MembersInjector implements MembersInjector<DownloadBroadcastReceiver> {
  private final Provider<DownloadCoordinator> downloadCoordinatorProvider;

  public DownloadBroadcastReceiver_MembersInjector(
      Provider<DownloadCoordinator> downloadCoordinatorProvider) {
    this.downloadCoordinatorProvider = downloadCoordinatorProvider;
  }

  public static MembersInjector<DownloadBroadcastReceiver> create(
      Provider<DownloadCoordinator> downloadCoordinatorProvider) {
    return new DownloadBroadcastReceiver_MembersInjector(downloadCoordinatorProvider);
  }

  @Override
  public void injectMembers(DownloadBroadcastReceiver instance) {
    injectDownloadCoordinator(instance, downloadCoordinatorProvider.get());
  }

  @InjectedFieldSignature("com.kosherstore.privateappstore.data.download.DownloadBroadcastReceiver.downloadCoordinator")
  public static void injectDownloadCoordinator(DownloadBroadcastReceiver instance,
      DownloadCoordinator downloadCoordinator) {
    instance.downloadCoordinator = downloadCoordinator;
  }
}
