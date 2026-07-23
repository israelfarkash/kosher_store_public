package com.kosherstore.privateappstore.ui.imageviewer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.kosherstore.privateappstore.R;
import com.kosherstore.privateappstore.databinding.ActivityImageViewerBinding;
import com.kosherstore.privateappstore.databinding.ItemFullscreenZoomPageBinding;
import dagger.hilt.android.AndroidEntryPoint;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0007\u0018\u0000 \u001b2\u00020\u0001:\u0002\u001b\u001cB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0002J\u0010\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\bH\u0002J\u0012\u0010\f\u001a\u00020\u00062\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0014J\u0016\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0002JA\u0010\u0014\u001a\u00020\u0006*\u00020\u00152\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\bH\u0002\u00a2\u0006\u0002\u0010\u001aR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/kosherstore/privateappstore/ui/imageviewer/ImageViewerActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/kosherstore/privateappstore/databinding/ActivityImageViewerBinding;", "bindPageIndicator", "", "total", "", "index", "dp", "v", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "resolveImageUrls", "", "", "intent", "Landroid/content/Intent;", "updateLayoutMargins", "Landroid/view/View;", "left", "top", "right", "bottom", "(Landroid/view/View;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;)V", "Companion", "ZoomScreenshotPagerAdapter", "app_debug"})
public final class ImageViewerActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.kosherstore.privateappstore.databinding.ActivityImageViewerBinding binding;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_IMAGE_URL = "image_url";
    
    /**
     * Multiple URLs for swipe-through gallery mode.
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_IMAGE_URLS = "image_urls";
    
    /**
     * Starting position when [EXTRA_IMAGE_URLS] has more than one item.
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_INITIAL_INDEX = "initial_index";
    @org.jetbrains.annotations.NotNull()
    public static final com.kosherstore.privateappstore.ui.imageviewer.ImageViewerActivity.Companion Companion = null;
    
    public ImageViewerActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void bindPageIndicator(int total, int index) {
    }
    
    private final void updateLayoutMargins(android.view.View $this$updateLayoutMargins, java.lang.Integer left, java.lang.Integer top, java.lang.Integer right, java.lang.Integer bottom) {
    }
    
    private final int dp(int v) {
        return 0;
    }
    
    private final java.util.List<java.lang.String> resolveImageUrls(android.content.Intent intent) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J&\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\f2\b\b\u0002\u0010\r\u001a\u00020\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/kosherstore/privateappstore/ui/imageviewer/ImageViewerActivity$Companion;", "", "()V", "EXTRA_IMAGE_URL", "", "EXTRA_IMAGE_URLS", "EXTRA_INITIAL_INDEX", "start", "", "context", "Landroid/content/Context;", "urls", "", "startIndex", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final void start(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> urls, int startIndex) {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0011B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\bH\u0016J\u0018\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\bH\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/kosherstore/privateappstore/ui/imageviewer/ImageViewerActivity$ZoomScreenshotPagerAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/kosherstore/privateappstore/ui/imageviewer/ImageViewerActivity$ZoomScreenshotPagerAdapter$VH;", "urls", "", "", "(Ljava/util/List;)V", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "VH", "app_debug"})
    static final class ZoomScreenshotPagerAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.kosherstore.privateappstore.ui.imageviewer.ImageViewerActivity.ZoomScreenshotPagerAdapter.VH> {
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<java.lang.String> urls = null;
        
        public ZoomScreenshotPagerAdapter(@org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> urls) {
            super();
        }
        
        @java.lang.Override()
        public int getItemCount() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public com.kosherstore.privateappstore.ui.imageviewer.ImageViewerActivity.ZoomScreenshotPagerAdapter.VH onCreateViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.ViewGroup parent, int viewType) {
            return null;
        }
        
        @java.lang.Override()
        public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
        com.kosherstore.privateappstore.ui.imageviewer.ImageViewerActivity.ZoomScreenshotPagerAdapter.VH holder, int position) {
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/kosherstore/privateappstore/ui/imageviewer/ImageViewerActivity$ZoomScreenshotPagerAdapter$VH;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/kosherstore/privateappstore/databinding/ItemFullscreenZoomPageBinding;", "(Lcom/kosherstore/privateappstore/databinding/ItemFullscreenZoomPageBinding;)V", "bind", "", "url", "", "app_debug"})
        public static final class VH extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
            @org.jetbrains.annotations.NotNull()
            private final com.kosherstore.privateappstore.databinding.ItemFullscreenZoomPageBinding binding = null;
            
            public VH(@org.jetbrains.annotations.NotNull()
            com.kosherstore.privateappstore.databinding.ItemFullscreenZoomPageBinding binding) {
                super(null);
            }
            
            public final void bind(@org.jetbrains.annotations.NotNull()
            java.lang.String url) {
            }
        }
    }
}