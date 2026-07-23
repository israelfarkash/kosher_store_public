package com.kosherstore.privateappstore.ui.detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.kosherstore.privateappstore.databinding.ItemScreenshotBinding;
import com.kosherstore.privateappstore.ui.imageviewer.ImageViewerActivity;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0013B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\bH\u0016J\u0018\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\bH\u0016J\u0014\u0010\u0011\u001a\u00020\n2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/kosherstore/privateappstore/ui/detail/ScreenshotAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/kosherstore/privateappstore/ui/detail/ScreenshotAdapter$ScreenshotViewHolder;", "()V", "screenshots", "", "", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "submitList", "urls", "ScreenshotViewHolder", "app_debug"})
public final class ScreenshotAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.kosherstore.privateappstore.ui.detail.ScreenshotAdapter.ScreenshotViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private java.util.List<java.lang.String> screenshots;
    
    public ScreenshotAdapter() {
        super();
    }
    
    public final void submitList(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> urls) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.kosherstore.privateappstore.ui.detail.ScreenshotAdapter.ScreenshotViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.ui.detail.ScreenshotAdapter.ScreenshotViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J$\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\n2\u0006\u0010\u000b\u001a\u00020\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/kosherstore/privateappstore/ui/detail/ScreenshotAdapter$ScreenshotViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/kosherstore/privateappstore/databinding/ItemScreenshotBinding;", "(Lcom/kosherstore/privateappstore/databinding/ItemScreenshotBinding;)V", "bind", "", "url", "", "allUrls", "", "position", "", "app_debug"})
    public static final class ScreenshotViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final com.kosherstore.privateappstore.databinding.ItemScreenshotBinding binding = null;
        
        public ScreenshotViewHolder(@org.jetbrains.annotations.NotNull()
        com.kosherstore.privateappstore.databinding.ItemScreenshotBinding binding) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        java.lang.String url, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> allUrls, int position) {
        }
    }
}