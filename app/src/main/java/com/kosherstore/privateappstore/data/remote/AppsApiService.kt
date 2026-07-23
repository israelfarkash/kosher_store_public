package com.kosherstore.privateappstore.data.remote

import com.kosherstore.privateappstore.data.remote.dto.StoreAppDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Url

interface AppsApiService {
    @GET
    @Headers("Cache-Control: no-cache", "Pragma: no-cache")
    suspend fun fetchApps(@Url url: String): List<StoreAppDto>
}
