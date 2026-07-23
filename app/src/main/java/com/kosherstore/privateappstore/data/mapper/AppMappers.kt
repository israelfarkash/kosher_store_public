package com.kosherstore.privateappstore.data.mapper

import com.google.gson.Gson
import com.kosherstore.privateappstore.data.local.AppEntity
import com.kosherstore.privateappstore.data.remote.dto.StoreAppDto
import com.kosherstore.privateappstore.domain.model.ChecksumType
import com.kosherstore.privateappstore.domain.model.StoreApp
import com.kosherstore.privateappstore.util.CategoryNormalizer

fun StoreAppDto.toEntity(now: Long = System.currentTimeMillis()): AppEntity {
    return AppEntity(
        packageName = packageName,
        name = name,
        versionCode = versionCode,
        versionName = versionName ?: versionCode.toString(),
        apkUrl = apkUrl,
        iconUrl = iconUrl,
        description = description ?: "",
        category = CategoryNormalizer.normalize(category ?: "כללי"),
        size = size ?: "",
        checksum = checksum ?: "",
        checksumType = normalizeChecksumType(checksumType).name,
        screenshotsJson = Gson().toJson(screenshots ?: emptyList<String>()),
        lastSyncedAt = now
    )
}

fun AppEntity.toDomain(): StoreApp {
    return StoreApp(
        name = name,
        packageName = packageName,
        versionCode = versionCode,
        versionName = versionName,
        apkUrl = apkUrl,
        iconUrl = iconUrl,
        description = description,
        category = category,
        size = size,
        checksum = checksum,
        checksumType = normalizeChecksumType(checksumType),
        screenshots = getScreenshots()
    )
}

fun normalizeChecksumType(raw: String?): ChecksumType {
    return when (raw?.uppercase()) {
        "MD5" -> ChecksumType.MD5
        else -> ChecksumType.SHA256
    }
}
