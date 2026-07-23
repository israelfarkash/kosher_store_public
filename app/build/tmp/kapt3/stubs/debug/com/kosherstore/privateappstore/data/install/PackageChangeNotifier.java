package com.kosherstore.privateappstore.data.install;

import javax.inject.Inject;
import javax.inject.Singleton;
import kotlinx.coroutines.flow.StateFlow;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\n\u001a\u00020\u000bR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2 = {"Lcom/kosherstore/privateappstore/data/install/PackageChangeNotifier;", "", "()V", "_changes", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "changes", "Lkotlinx/coroutines/flow/StateFlow;", "getChanges", "()Lkotlinx/coroutines/flow/StateFlow;", "notifyChanged", "", "app_debug"})
public final class PackageChangeNotifier {
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long> _changes = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Long> changes = null;
    
    @javax.inject.Inject()
    public PackageChangeNotifier() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Long> getChanges() {
        return null;
    }
    
    public final void notifyChanged() {
    }
}