package com.kosherstore.privateappstore.data.install;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class PackageChangeNotifier_Factory implements Factory<PackageChangeNotifier> {
  @Override
  public PackageChangeNotifier get() {
    return newInstance();
  }

  public static PackageChangeNotifier_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static PackageChangeNotifier newInstance() {
    return new PackageChangeNotifier();
  }

  private static final class InstanceHolder {
    private static final PackageChangeNotifier_Factory INSTANCE = new PackageChangeNotifier_Factory();
  }
}
