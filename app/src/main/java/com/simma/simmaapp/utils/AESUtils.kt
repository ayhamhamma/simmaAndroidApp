package com.simma.simmaapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AESUtils {
    fun generateAESKey(size: Int): ByteArray {
        val secureRandom = SecureRandom()
        val randomKey = ByteArray(size)
        secureRandom.nextBytes(randomKey)
        return randomKey
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun encryptWithAES(plaintext: String, key: ByteArray, iv: ByteArray): String {
        val secretKey: SecretKey = SecretKeySpec(key, "AES")
        val ivParameterSpec = IvParameterSpec(iv)

        // Create AES cipher
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)

        // Encrypt the plaintext
        val encryptedBytes = cipher.doFinal(plaintext.toByteArray())
        return bytesToHex(encryptedBytes)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun decryptWithAES(encryptedText: String, key: ByteArray, iv: ByteArray): String {
        val secretKey: SecretKey = SecretKeySpec(key, "AES")
        val ivParameterSpec = IvParameterSpec(iv)

        // Create AES cipher
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec)

        // Convert hexadecimal string to bytes
        val encryptedBytes = hexToBytes(encryptedText)

        // Decrypt the bytes
        val decryptedBytes = cipher.doFinal(encryptedBytes)

        // Convert the decrypted bytes to a string
        return String(decryptedBytes)
    }

    // Function to convert bytes to hexadecimal string
    fun bytesToHex(bytes: ByteArray): String {
        val hexChars = CharArray(bytes.size * 2)
        for (i in bytes.indices) {
            val v = bytes[i].toInt() and 0xFF
            hexChars[i * 2] = "0123456789abcdef"[v ushr 4]
            hexChars[i * 2 + 1] = "0123456789abcdef"[v and 0x0F]
        }
        return String(hexChars)
    }

    // Function to convert hexadecimal string to bytes
    private fun hexToBytes(hexString: String): ByteArray {
        val len = hexString.length
        val data = ByteArray(len / 2)
        var i = 0
        while (i < len) {
            data[i / 2] = ((Character.digit(hexString[i], 16) shl 4)
                    + Character.digit(hexString[i + 1], 16)).toByte()
            i += 2
        }
        return data
    }
}


