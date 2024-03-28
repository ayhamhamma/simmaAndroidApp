package com.simma.simmaapp.utils

import android.content.Context
import android.util.Log
import android.util.Log.e
import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale

object Helpers {

    private val PHONE_NUMBER = "PHONE_NUMBER"
    private const val SHARED_PREF = "SHARED_PREF"
    private const val LOGIN_TOKEN = "LOGIN_TOKEN"
    private const val PLACE_ORDER_TOKEN = "PLACE_ORDER_TOKEN"
    private const val USER_ID = "USER_ID"
    private const val COACH_MARK_ENABLE_TAG= "COACH_MARK_ENABLE_TAG"
    private const val FIRST_TIME_TAG= "FIRST_TIME_TAG"
    private const val SECOND_TIME_TAG= "SECOND_TIME_TAG"
    private const val CURRENCY_TAG= "CURRENCY_TAG"

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
        val gson = GsonBuilder().serializeNulls().create()
        val jsonElement = gson.toJsonTree(map)
        return jsonElement.asJsonObject
    }
    fun isLoggedIn(context: Context):Boolean{
        val userId  = getUserId(context)
        return !userId.isNullOrEmpty() || !userId.isNullOrBlank()
    }

    fun setCoachMarkToFalse(mContext: Context?){
        val sharedPreferences = mContext?.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putBoolean(COACH_MARK_ENABLE_TAG,false)
        editor?.apply()
    }
    fun getCoachMarkState(
        mContext: Context?
    ):Boolean{
        val sharedPreferences = mContext?.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPreferences?.getBoolean(COACH_MARK_ENABLE_TAG, true) ?: true
    }
    fun setFirstTimeOpenToFalse(mContext: Context?){
        val sharedPreferences = mContext?.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putBoolean(FIRST_TIME_TAG,false)
        editor?.apply()
    }
    fun isFirstTimeOpen(
        mContext: Context?
    ):Boolean{
        val sharedPreferences = mContext?.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPreferences?.getBoolean(FIRST_TIME_TAG, true) ?: true
    }

    fun clearUserData(context : Context){
        setToken(context,"")
        setUserId(context,"")
    }

    fun formatDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val date = inputFormat.parse(inputDate)
        return outputFormat.format(date)
    }
    fun formatNumber(numberStr: String): String {
        var formattedNumber = numberStr.replace(".0$", "") // Remove ".0" from the end of the string
        val decimalFormat = DecimalFormat("#,###")
        formattedNumber = decimalFormat.format(formattedNumber.toDouble()) // Format number with thousand separators
        return formattedNumber
    }
    fun incrementNumberOfTimes(mContext: Context?){
        val sharedPreferences = mContext?.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        val incrementedNumberOfTimes = (sharedPreferences?.getInt(SECOND_TIME_TAG, 0) ?: 0) + 1
        e("ayham",incrementedNumberOfTimes.toString())
        editor?.putInt(SECOND_TIME_TAG,incrementedNumberOfTimes)
        editor?.apply()
    }
    fun getNumberOfTimes(mContext: Context?) :Int{
        val sharedPreferences = mContext?.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        e("ayham",(sharedPreferences?.getInt(SECOND_TIME_TAG, 0) ?: 0).toString())
        return sharedPreferences?.getInt(SECOND_TIME_TAG, 0) ?: 0
    }

    fun setSelectedCurrency(mContext: Context,text:String){
        val sharedPreferences = mContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString(CURRENCY_TAG,text)
        }
        editor.apply()
    }
    fun getSelectedCurrency(mContext: Context) : String{
        val sharedPreferences = mContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPreferences.getString(CURRENCY_TAG,"962") ?:"962"
    }


}