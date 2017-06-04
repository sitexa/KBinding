package com.sitexa.android

import android.content.Context
import com.sitexa.android.network.service.caishuo.User
import com.sitexa.android.utils.fromJson
import com.sitexa.android.utils.toJson
import org.jetbrains.anko.defaultSharedPreferences
import java.util.*
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 * Created by open on 04/06/2017.
 *
 */

object ApplicationContext {
    private lateinit var context: Context
    var user: User? by Delegates.observable(null) { property: KProperty<*>, oldValue: User?, newValue: User? ->
        saveUser(newValue)
    }

    val userId: String?
        get() = user?.id

    val accessToken: String?
        get() = user?.accessToken

    val hasLogin: Boolean
        get() = user != null

    val version: String
        get() = context.packageManager.getPackageInfo(context.packageName, 0).versionName

    val deviceId: String
        get() {
            val deviceId: String = UUID.randomUUID().toString()
            context.defaultSharedPreferences.edit().putString(Constants.PREF_DEVICE_UUID, deviceId).apply()
            return deviceId
        }

    fun init(context: Context) {
        this.context = context
        user = loadUser()
    }

    private fun loadUser(): User? {
        return fromJson(context.defaultSharedPreferences.getString(Constants.PREF_USER, ""), User::class.java)
    }

    private fun saveUser(user: User?) {
        context.defaultSharedPreferences.edit().putString(Constants.PREF_USER, if (user == null) "" else toJson(user)).apply()
    }
}