package com.sitexa.android.viewmodel

import com.benny.library.kbinding.annotation.Command
import com.benny.library.kbinding.annotation.Property
import com.benny.library.kbinding.viewmodel.ViewModel
import kotlin.properties.Delegates

/**
 * Created by open on 04/06/2017.
 */
class LoginViewModel(val loginDelegate: LoginViewModel.LoginDelegate) : ViewModel() {

    @delegate:Property
    var name: String by Delegates.property("wangbin@xxx.com")

    @delegate:Property
    var password: String by Delegates.property("xxxxxxxxx")

    @Command
    fun login() {
        if (name.equals("wangbin@xxx.com")) loginDelegate.onLoginSuccess("wangbin@xxx.com")
        else loginDelegate.onLoginFailed(RuntimeException("incorrect name or password"))
    }

    interface LoginDelegate {
        fun onLoginSuccess(user: String)
        fun onLoginFailed(e: Throwable)
    }
}

