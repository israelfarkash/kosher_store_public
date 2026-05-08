package com.kosherstore.privateappstore.data.mapper

import com.kosherstore.privateappstore.data.local.AppEntity
import com.kosherstore.privateappstore.data.remote.dto.StoreAppDto
import com.kosherstore.privateappstore.domain.model.ChecksumType
import com.kosherstore.privateappstore.util.CategoryNormalizer

fun StoreAppDto.toEntity(now: Long = System.currentTimeMillis()): AppEntity {
    return AppEntity(
        packageName = packageName,
        name = name,
        versionCode = versionCode,
        versionName = versionName,
        apkUrl = apkUrl,
        iconUrl = iconUrl,
        description = description,
        category = CategoryNormalizer.normalize(category),
        size = size,
        checksum = checksum,
        checksumType = normalizeChecksumType(checksumType).name,
        lastSyncedAt = now
    )
}

fun normalizeChecksumType(raw: String?): ChecksumType {
    return when (raw?.uppercase()) {
        "MD5" -> ChecksumType.MD5
        else -> ChecksumType.SHA256
    }
}
