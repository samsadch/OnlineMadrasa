package com.onlinemadrasa.utils

import android.content.Context
import android.content.SharedPreferences

val USER_NAME = "USER_NAME.Type"
val USER_MAIL = "USER_MAIL"


val LOGIN_PREFERENCES = "Login"

class PrefManager(context: Context) {

    var context: Context
    var sharedpreferences: SharedPreferences
    var editor: SharedPreferences.Editor

    init {
        this.context = context
        sharedpreferences = context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE)
        editor = sharedpreferences?.edit()
    }


    companion object {
        private var instance: PrefManager? = null
        fun getInstance(context: Context): PrefManager {
            if (instance == null) {
                instance = PrefManager(context)
            }
            return instance!!;
        }
    }


    fun getUserName(): String? {
        return getSharedString(USER_NAME, "")
    }

    fun saveUserName(userName: String) {
        putSharedString(USER_NAME, userName)
    }

    fun getUserMail(): String? {
        return getSharedString(USER_MAIL, "")
    }

    fun saveUserMail(mail: String) {
        putSharedString(USER_MAIL, mail)
    }


    fun saveDeviceName(deviceName: String) {
        putSharedString("Device_Name", deviceName)
    }

    fun getDeviceName(): String {
        return getSharedString("Device_Name", "789")
    }


    fun clear() {
        if (!isValid(sharedpreferences)!!)
            sharedpreferences =
                context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE)
        if (!isValid(editor)!!)
            editor = sharedpreferences.edit()
        editor.clear()
        editor.commit()
    }


    private fun getSharedString(KEY: String, defValue: String): String {
        if (!isValid(sharedpreferences)!!)
            sharedpreferences =
                context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE)
        if (!isValid(editor)!!)
            editor = sharedpreferences.edit()
        return sharedpreferences.getString(KEY, defValue).toString()
    }

    private fun getSharedInteger(KEY: String, defValue: Int): Int {
        if (!isValid(sharedpreferences)!!)
            sharedpreferences =
                context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE)
        if (!isValid(editor)!!)
            editor = sharedpreferences.edit()
        return sharedpreferences.getInt(KEY, defValue!!)
    }

    private fun putSharedString(KEY: String, value: String) {
        if (!isValid(sharedpreferences)!!)
            sharedpreferences =
                context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE)
        if (!isValid(editor)!!)
            editor = sharedpreferences.edit()
        editor.putString(KEY, value).commit()

    }

    private fun putSharedInteger(KEY: String, value: Int) {
        if (!isValid(sharedpreferences)!!)
            sharedpreferences =
                context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE)
        if (!isValid(editor)!!)
            editor = sharedpreferences.edit()
        editor.putInt(KEY, value!!).commit()

    }


    private fun getSharedBoolean(KEY: String, defValue: Boolean): Boolean {
        if (!isValid(sharedpreferences)!!)
            sharedpreferences =
                context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE)
        if (!isValid(editor)!!)
            editor = sharedpreferences.edit()
        return sharedpreferences.getBoolean(KEY, defValue)
    }

    private fun putSharedBoolean(KEY: String, value: Boolean) {
        if (!isValid(sharedpreferences)!!)
            sharedpreferences =
                context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE)
        if (!isValid(editor)!!)
            editor = sharedpreferences.edit()
        editor.putBoolean(KEY, value).commit()
    }

    private fun getSharedLong(KEY: String): Long {
        if (!isValid(sharedpreferences)!!)
            sharedpreferences =
                context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE)
        if (!isValid(editor)!!)
            editor = sharedpreferences.edit()
        return sharedpreferences.getLong(KEY, 0L)
    }

    private fun putSharedLong(KEY: String, value: Long) {
        if (!isValid(sharedpreferences)!!)
            sharedpreferences =
                context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE)
        if (!isValid(editor)!!)
            editor = sharedpreferences.edit()
        editor.putLong(KEY, value).commit()
    }


    private fun getSharedFloat(KEY: String): Float {
        if (!isValid(sharedpreferences)!!)
            sharedpreferences =
                context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE)
        if (!isValid(editor)!!)
            editor = sharedpreferences.edit()
        return sharedpreferences.getFloat(KEY, 11.5f)
    }

    private fun putSharedFloat(KEY: String, value: Float) {
        if (!isValid(sharedpreferences)!!)
            sharedpreferences =
                context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE)
        if (!isValid(editor)!!)
            editor = sharedpreferences.edit()
        editor.putFloat(KEY, value).commit()
    }


    fun clearSharedAll() {
        if (!isValid(editor)!!) {
            if (!isValid(sharedpreferences)!!)
                sharedpreferences =
                    context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE)
            editor = sharedpreferences.edit()
        }
        editor.clear().commit()
    }

    fun isValid(`object`: Any?): Boolean? {
        return `object` != null

    }

    private fun isValid(text: String?): Boolean? {
        return if (text != null) !text.trim { it <= ' ' }.equals("", ignoreCase = true) else false

    }

    private fun getString(KEY: String, defValue: String): String? {
        return sharedpreferences.getString(KEY, defValue)
    }

    private fun putString(KEY: String, value: String) {
        editor.putString(KEY, value).commit()
    }


}