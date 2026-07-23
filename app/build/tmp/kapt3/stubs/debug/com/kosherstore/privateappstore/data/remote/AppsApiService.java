package com.kosherstore.privateappstore.data.remote;

import com.kosherstore.privateappstore.data.remote.dto.StoreAppDto;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007\u00a8\u0006\b"}, d2 = {"Lcom/kosherstore/privateappstore/data/remote/AppsApiService;", "", "fetchApps", "", "Lcom/kosherstore/privateappstore/data/remote/dto/StoreAppDto;", "url", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface AppsApiService {
    
    @retrofit2.http.GET()
    @retrofit2.http.Headers(value = {"Cache-Control: no-cache", "Pragma: no-cache"})
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object fetchApps(@retrofit2.http.Url()
    @org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.kosherstore.privateappstore.data.remote.dto.StoreAppDto>> $completion);
}