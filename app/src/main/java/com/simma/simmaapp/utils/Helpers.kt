package com.simma.simmaapp.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonObject

object Helpers {

    private val PHONE_NUMBER = "PHONE_NUMBER"
    private const val SHARED_PREF = "SHARED_PREF"
    private const val LOGIN_TOKEN = "LOGIN_TOKEN"
    private const val PLACE_ORDER_TOKEN = "PLACE_ORDER_TOKEN"
    private const val USER_ID = "USER_ID"
    fun showMessage(context: Context , message: String){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }
    fun setToken(context: Context, token: String) {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString(LOGIN_TOKEN, token)
        }
        editor.apply()
    }
    fun setPlaceOrderToken(context: Context, token: String) {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString(PLACE_ORDER_TOKEN, token)
        }
        editor.apply()
    }
    fun setUserPhoneNumber(context: Context,phoneNumber : String){
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString(PHONE_NUMBER, phoneNumber)
        }
        editor.apply()
    }

    fun getUserPhoneNumber(context: Context) : String{
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPreferences.getString(PHONE_NUMBER, "")!!
    }
    fun getToken(context: Context): String {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPreferences.getString(LOGIN_TOKEN, "")!!
    }
    fun getPlaceOrderToken(context: Context): String {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPreferences.getString(PLACE_ORDER_TOKEN, "")!!
    }

    fun setUserId(context: Context, userId: String) {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString(USER_ID, userId)
        }
        editor.apply()
    }
    fun deleteUserData(context: Context) {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString(USER_ID, "")
            putString(LOGIN_TOKEN, "")
        }
        editor.apply()
    }

    fun getUserId(context: Context): String {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPreferences.getString(USER_ID, "")!!
    }

    fun showMessage(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    fun getLang(mContext: Context?): String {
        val sharedPreferences = mContext?.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        Log.e("ayham", sharedPreferences?.getString("lang", "en")!!)
        return sharedPreferences?.getString("lang", "en")!!
    }

    fun setLang(mContext: Context?,lang: String){
        val sharedPreferences = mContext?.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString("lang",lang)
        editor?.apply()
        Log.e("ayham", sharedPreferences?.getString("lang", "en")!!)
    }
    fun convertMapToJsonObject(map: Map<String, Any>): JsonObject {
        val gson = Gson()
        val jsonElement = gson.toJsonTree(map)
        return jsonElement.asJsonObject
    }
    fun isLoggedIn(context: Context):Boolean{
        val userId  = getUserId(context)
        return !userId.isNullOrEmpty() || !userId.isNullOrBlank()
    }

    fun clearUserData(context : Context){
        setToken(context,"")
        setUserId(context,"")
    }
}