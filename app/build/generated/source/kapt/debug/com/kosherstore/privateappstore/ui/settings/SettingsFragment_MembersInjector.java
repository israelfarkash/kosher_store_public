package com.kosherstore.privateappstore.ui.settings;

import android.content.SharedPreferences;
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
public final class SettingsFragment_MembersInjector implements MembersInjector<SettingsFragment> {
  private final Provider<SharedPreferences> sharedPreferencesProvider;

  public SettingsFragment_MembersInjector(Provider<SharedPreferences> sharedPreferencesProvider) {
    this.sharedPreferencesProvider = sharedPreferencesProvider;
  }

  public static MembersInjector<SettingsFragment> create(
      Provider<SharedPreferences> sharedPreferencesProvider) {
    return new SettingsFragment_MembersInjector(sharedPreferencesProvider);
  }

  @Override
  public void injectMembers(SettingsFragment instance) {
    injectSharedPreferences(instance, sharedPreferencesProvider.get());
  }

  @InjectedFieldSignature("com.kosherstore.privateappstore.ui.settings.SettingsFragment.sharedPreferences")
  public static void injectSharedPreferences(SettingsFragment instance,
      SharedPreferences sharedPreferences) {
    instance.sharedPreferences = sharedPreferences;
  }
}
