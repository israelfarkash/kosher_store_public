package com.kosherstore.privateappstore.data.remote.dto

import com.google.gson.annotations.SerializedName

data class StoreAppDto(
    @SerializedName("name") val name: String = "",
    @SerializedName("packageName") val packageName: String = "",
    @SerializedName("versionCode") val versionCode: Long = 0L,
    @SerializedName("versionName") val versionName: String? = null,
    @SerializedName("apkUrl") val apkUrl: String = "",
    @SerializedName("iconUrl") val iconUrl: String = "",
    @SerializedName("description") val description: String? = null,
    @SerializedName("category") val category: String? = null,
    @SerializedName("size") val size: String? = null,
    @SerializedName("checksum") val checksum: String? = null,
    @SerializedName("checksumType") val checksumType: String? = null,
    @SerializedName("screenshots") val screenshots: List<String>? = null
)
