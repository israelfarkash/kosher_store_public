package com.kosherstore.privateappstore.data.remote

import com.kosherstore.privateappstore.data.remote.dto.StoreAppDto
import retrofit2.http.GET
import retrofit2.http.Url

interface AppsApiService {
    @GET
    suspend fun fetchApps(@Url url: String): List<StoreAppDto>
}
