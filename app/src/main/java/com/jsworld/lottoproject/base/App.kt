package com.jsworld.lottoproject.base

import android.app.Application
import androidx.annotation.StringRes
import dagger.hilt.android.HiltAndroidApp
import org.jetbrains.anko.toast


@HiltAndroidApp
class App : Application(){

    companion object {
        private lateinit var application: App
        fun getInstance(): App = application


        fun getString(@StringRes resId: Int): String {
            return application.getString(resId)
        }

        fun toast(msg: String) = application.toast(msg)

    }

    override fun onCreate() {
        super.onCreate()
        application = this

    }

}