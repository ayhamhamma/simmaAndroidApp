package com.simma.simmaapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.Base64
import javax.crypto.Cipher
object RSAUtils{
    fun generateKeyPair(): Pair<PrivateKey, PublicKey> {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(2048)
        val keyPair = keyPairGenerator.generateKeyPair()

        return Pair(keyPair.private, keyPair.public)
    }

    fun encrypt(data: ByteArray, publicKey: PublicKey): ByteArray {
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        return cipher.doFinal(data)
    }

    fun decrypt(data: ByteArray, privateKey: PrivateKey): ByteArray {
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        return cipher.doFinal(data)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun printKeyPair(privateKey: PrivateKey, publicKey: PublicKey) {
        val privateKeyBase64 = Base64.getEncoder().encodeToString(privateKey.encoded)
        val publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.encoded)

        println("Private Key (Base64): $privateKeyBase64")
        println("Public Key (Base64): $publicKeyBase64")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getPrivateKeyFromString(privateKeyString: String): PrivateKey {
        val keyFactory = KeyFactory.getInstance("RSA")
        val keySpec = PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyString))
        return keyFactory.generatePrivate(keySpec)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getPublicKeyFromString(publicKeyString: String): PublicKey {
        val keyFactory = KeyFactory.getInstance("RSA")
        val keySpec = X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyString))
        return keyFactory.generatePublic(keySpec)
    }
}
