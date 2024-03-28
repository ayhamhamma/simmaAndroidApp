package com.simma.simmaapp.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.util.Base64

object Encryption {
    fun encryptData(data : String, iv :String = "62250c420de42707dfd41059") : String{
        // RSA Public Encryption Key
        val publicKeyString = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA7MQBMwQcrq65wxAlVLe74TWT/qriT1fNPZ5bIE6tEeGlPQIXwkyoH0dk4kXubrFDeDnfwgA5NTn2RXdXHC0xy1QQQYaQxmAUrCR0d6qYSXXHG0xDXRcjFRjU0JM+Z4+pW+119ovNeUjHEhvzHjhtR4UQxCWk47IKCWB2ES6NXe6Hc7G3E4bxxpqfOha+YehqJhW136M22vc5SjT89tbEjDzy2cVGrhc0tW7Cvlw3J+dPWQw2zbFpx/M2aUoOC8sSY/TELLsTR5X7Ypas7ydI40nXIiOQN+v6w9ux+aFx/QS8QttOzNDszSvy81oq6mwHx9EYySh+X4N0tNW0HXKF8QIDAQAB"
        val ivString = iv.takeLast(16)

        // Random Generation Of AES key
        val secretKey = AESUtils.generateAESKey(32)

        // from base64 public key to RSA Key
        val retrievedPublicKey = RSAUtils.getPublicKeyFromString(publicKeyString)

        // encrypt SecretKey Using RSA PUBLIC KEY
        val encryptedKey = RSAUtils.encrypt(secretKey, retrievedPublicKey)
        val encryptedMessage = AESUtils.encryptWithAES(data, secretKey, ivString.toByteArray())

        // concat the result String
        val overallEncryptedData = encryptedKey + encryptedMessage.toByteArray()
        return Base64.getEncoder().encodeToString(overallEncryptedData)

    }
}
fun main(){
    val data = """{
    "password": "Abcd1235",
    "phoneNumber": "+962785756979"
}"""
    println(Encryption.encryptData(data,"62250c420de42707dfd41059"))
}