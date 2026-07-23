package com.kosherstore.privateappstore.ui.main;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.kosherstore.privateappstore.databinding.ItemAppCardBinding;
import com.kosherstore.privateappstore.domain.model.StoreApp;
import com.kosherstore.privateappstore.ui.common.AppCardMode;
import com.kosherstore.privateappstore.ui.common.AppPresentationUtils;
import com.kosherstore.privateappstore.util.CategoryNormalizer;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003R\u00020\u00000\u0001:\u0002\u0014\u0015BI\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\u0002\u0010\u000bJ\u001c\u0010\f\u001a\u00020\b2\n\u0010\r\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u001c\u0010\u0010\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000fH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/kosherstore/privateappstore/ui/main/AppListAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/kosherstore/privateappstore/domain/model/StoreApp;", "Lcom/kosherstore/privateappstore/ui/main/AppListAdapter$AppViewHolder;", "mode", "Lcom/kosherstore/privateappstore/ui/common/AppCardMode;", "onCardClicked", "Lkotlin/Function1;", "", "onPrimaryAction", "onSecondaryAction", "(Lcom/kosherstore/privateappstore/ui/common/AppCardMode;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "onBindViewHolder", "holder", "position", "", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "AppViewHolder", "DiffCallback", "app_debug"})
public final class AppListAdapter extends androidx.recyclerview.widget.ListAdapter<com.kosherstore.privateappstore.domain.model.StoreApp, com.kosherstore.privateappstore.ui.main.AppListAdapter.AppViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private final com.kosherstore.privateappstore.ui.common.AppCardMode mode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.kosherstore.privateappstore.domain.model.StoreApp, kotlin.Unit> onCardClicked = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.kosherstore.privateappstore.domain.model.StoreApp, kotlin.Unit> onPrimaryAction = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.kosherstore.privateappstore.domain.model.StoreApp, kotlin.Unit> onSecondaryAction = null;
    
    public AppListAdapter(@org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.ui.common.AppCardMode mode, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.kosherstore.privateappstore.domain.model.StoreApp, kotlin.Unit> onCardClicked, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.kosherstore.privateappstore.domain.model.StoreApp, kotlin.Unit> onPrimaryAction, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.kosherstore.privateappstore.domain.model.StoreApp, kotlin.Unit> onSecondaryAction) {
        super(null);
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.kosherstore.privateappstore.ui.main.AppListAdapter.AppViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.kosherstore.privateappstore.ui.main.AppListAdapter.AppViewHolder holder, int position) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/kosherstore/privateappstore/ui/main/AppListAdapter$AppViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/kosherstore/privateappstore/databinding/ItemAppCardBinding;", "(Lcom/kosherstore/privateappstore/ui/main/AppListAdapter;Lcom/kosherstore/privateappstore/databinding/ItemAppCardBinding;)V", "bind", "", "app", "Lcom/kosherstore/privateappstore/domain/model/StoreApp;", "app_debug"})
    public final class AppViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final com.kosherstore.privateappstore.databinding.ItemAppCardBinding binding = null;
        
        public AppViewHolder(@org.jetbrains.annotations.NotNull()
        com.kosherstore.privateappstore.databinding.ItemAppCardBinding binding) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.kosherstore.privateappstore.domain.model.StoreApp app) {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u00c2\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016J\u0018\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016\u00a8\u0006\t"}, d2 = {"Lcom/kosherstore/privateappstore/ui/main/AppListAdapter$DiffCallback;", "Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "Lcom/kosherstore/privateappstore/domain/model/StoreApp;", "()V", "areContentsTheSame", "", "oldItem", "newItem", "areItemsTheSame", "app_debug"})
    static final class DiffCallback extends androidx.recyclerview.widget.DiffUtil.ItemCallback<com.kosherstore.privateappstore.domain.model.StoreApp> {
        @org.jetbrains.annotations.NotNull()
        public static final com.kosherstore.privateappstore.ui.main.AppListAdapter.DiffCallback INSTANCE = null;
        
        private DiffCallback() {
            super();
        }
        
        @java.lang.Override()
        public boolean areItemsTheSame(@org.jetbrains.annotations.NotNull()
        com.kosherstore.privateappstore.domain.model.StoreApp oldItem, @org.jetbrains.annotations.NotNull()
        com.kosherstore.privateappstore.domain.model.StoreApp newItem) {
            return false;
        }
        
        @java.lang.Override()
        public boolean areContentsTheSame(@org.jetbrains.annotations.NotNull()
        com.kosherstore.privateappstore.domain.model.StoreApp oldItem, @org.jetbrains.annotations.NotNull()
        com.kosherstore.privateappstore.domain.model.StoreApp newItem) {
            return false;
        }
    }
}