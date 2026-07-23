package com.kosherstore.privateappstore.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.GridLayoutManager;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;
import com.kosherstore.privateappstore.R;
import com.kosherstore.privateappstore.databinding.FragmentMainBinding;
import com.kosherstore.privateappstore.ui.common.AppCardMode;
import com.kosherstore.privateappstore.ui.common.UiEvent;
import dagger.hilt.android.AndroidEntryPoint;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0002J$\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J\b\u0010\"\u001a\u00020\u0019H\u0016J\u001a\u0010#\u001a\u00020\u00192\u0006\u0010$\u001a\u00020\u001b2\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J\u0010\u0010%\u001a\u00020\u00192\u0006\u0010&\u001a\u00020\'H\u0002J\b\u0010(\u001a\u00020\u0019H\u0002J\b\u0010)\u001a\u00020\u0019H\u0002J\b\u0010*\u001a\u00020+H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u000b\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001b\u0010\u000e\u001a\u00020\u000f8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0012\u0010\n\u001a\u0004\b\u0010\u0010\u0011R\u001b\u0010\u0013\u001a\u00020\u00148BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0017\u0010\n\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006,"}, d2 = {"Lcom/kosherstore/privateappstore/ui/main/MainFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/kosherstore/privateappstore/databinding/FragmentMainBinding;", "adapter", "Lcom/kosherstore/privateappstore/ui/main/AppListAdapter;", "getAdapter", "()Lcom/kosherstore/privateappstore/ui/main/AppListAdapter;", "adapter$delegate", "Lkotlin/Lazy;", "binding", "getBinding", "()Lcom/kosherstore/privateappstore/databinding/FragmentMainBinding;", "categoryAdapter", "Lcom/kosherstore/privateappstore/ui/main/CategoryAdapter;", "getCategoryAdapter", "()Lcom/kosherstore/privateappstore/ui/main/CategoryAdapter;", "categoryAdapter$delegate", "viewModel", "Lcom/kosherstore/privateappstore/ui/main/MainViewModel;", "getViewModel", "()Lcom/kosherstore/privateappstore/ui/main/MainViewModel;", "viewModel$delegate", "observeViewModel", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onViewCreated", "view", "render", "state", "Lcom/kosherstore/privateappstore/ui/main/MainViewModel$MainUiState;", "setupInteractions", "setupRecycler", "spanCount", "", "app_debug"})
public final class MainFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable()
    private com.kosherstore.privateappstore.databinding.FragmentMainBinding _binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy categoryAdapter$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy adapter$delegate = null;
    
    public MainFragment() {
        super();
    }
    
    private final com.kosherstore.privateappstore.databinding.FragmentMainBinding getBinding() {
        return null;
    }
    
    private final com.kosherstore.privateappstore.ui.main.MainViewModel getViewModel() {
        return null;
    }
    
    private final com.kosherstore.privateappstore.ui.main.CategoryAdapter getCategoryAdapter() {
        return null;
    }
    
    private final com.kosherstore.privateappstore.ui.main.AppListAdapter getAdapter() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
    
    private final void setupRecycler() {
    }
    
    private final void setupInteractions() {
    }
    
    private final void observeViewModel() {
    }
    
    private final void render(com.kosherstore.privateappstore.ui.main.MainViewModel.MainUiState state) {
    }
    
    private final int spanCount() {
        return 0;
    }
}