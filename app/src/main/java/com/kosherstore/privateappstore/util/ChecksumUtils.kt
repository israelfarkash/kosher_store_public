package com.kosherstore.privateappstore.util

import com.kosherstore.privateappstore.domain.model.ChecksumType
import java.io.File
import java.security.MessageDigest

object ChecksumUtils {

    fun isValid(file: File, expectedChecksum: String, type: ChecksumType): Boolean {
        return computeChecksum(file, type).equals(expectedChecksum, ignoreCase = true)
    }

    fun computeChecksum(file: File, type: ChecksumType): String {
        val algorithm = when (type) {
            ChecksumType.MD5 -> "MD5"
            ChecksumType.SHA256 -> "SHA-256"
        }
        val digest = MessageDigest.getInstance(algorithm)
        file.inputStream().buffered().use { input ->
            val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
            while (true) {
                val read = input.read(buffer)
                if (read <= 0) break
                digest.update(buffer, 0, read)
            }
        }
        return digest.digest().joinToString(separator = "") { "%02x".format(it) }
    }
}
