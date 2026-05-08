package com.kosherstore.privateappstore.data.remote.dto

import com.google.gson.annotations.SerializedName

data class StoreAppDto(
    @SerializedName("name") val name: String,
    @SerializedName("packageName") val packageName: String,
    @SerializedName("versionCode") val versionCode: Long,
    @SerializedName("versionName") val versionName: String? = null,
    @SerializedName("apkUrl") val apkUrl: String,
    @SerializedName("iconUrl") val iconUrl: String,
    @SerializedName("description") val description: String,
    @SerializedName("category") val category: String,
    @SerializedName("size") val size: String,
    @SerializedName("checksum") val checksum: String,
    @SerializedName("checksumType") val checksumType: String? = null
)
