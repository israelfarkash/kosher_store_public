package com.kosherstore.privateappstore.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import com.google.android.material.snackbar.Snackbar;
import com.kosherstore.privateappstore.R;
import com.kosherstore.privateappstore.databinding.FragmentAppDetailBinding;
import com.kosherstore.privateappstore.domain.model.StoreApp;
import com.kosherstore.privateappstore.ui.common.AppCardMode;
import com.kosherstore.privateappstore.ui.common.AppPresentationUtils;
import com.kosherstore.privateappstore.ui.common.UiEvent;
import com.kosherstore.privateappstore.util.CategoryNormalizer;
import dagger.hilt.android.AndroidEntryPoint;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0002J$\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u0014H\u0016J\u001a\u0010\u001e\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020\u00162\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\u0010\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\"H\u0002J\b\u0010#\u001a\u00020\u0014H\u0002J\b\u0010$\u001a\u00020\u0014H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u001b\u0010\b\u001a\u00020\t8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000bR\u001b\u0010\u000e\u001a\u00020\u000f8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0012\u0010\r\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006%"}, d2 = {"Lcom/kosherstore/privateappstore/ui/detail/AppDetailFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/kosherstore/privateappstore/databinding/FragmentAppDetailBinding;", "binding", "getBinding", "()Lcom/kosherstore/privateappstore/databinding/FragmentAppDetailBinding;", "screenshotAdapter", "Lcom/kosherstore/privateappstore/ui/detail/ScreenshotAdapter;", "getScreenshotAdapter", "()Lcom/kosherstore/privateappstore/ui/detail/ScreenshotAdapter;", "screenshotAdapter$delegate", "Lkotlin/Lazy;", "viewModel", "Lcom/kosherstore/privateappstore/ui/detail/AppDetailViewModel;", "getViewModel", "()Lcom/kosherstore/privateappstore/ui/detail/AppDetailViewModel;", "viewModel$delegate", "observeViewModel", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onViewCreated", "view", "render", "app", "Lcom/kosherstore/privateappstore/domain/model/StoreApp;", "setupScreenshotsRecycler", "setupToolbar", "app_debug"})
public final class AppDetailFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable()
    private com.kosherstore.privateappstore.databinding.FragmentAppDetailBinding _binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy screenshotAdapter$delegate = null;
    
    public AppDetailFragment() {
        super();
    }
    
    private final com.kosherstore.privateappstore.databinding.FragmentAppDetailBinding getBinding() {
        return null;
    }
    
    private final com.kosherstore.privateappstore.ui.detail.AppDetailViewModel getViewModel() {
        return null;
    }
    
    private final com.kosherstore.privateappstore.ui.detail.ScreenshotAdapter getScreenshotAdapter() {
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
    
    private final void setupScreenshotsRecycler() {
    }
    
    private final void setupToolbar() {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
    
    private final void observeViewModel() {
    }
    
    private final void render(com.kosherstore.privateappstore.domain.model.StoreApp app) {
    }
}