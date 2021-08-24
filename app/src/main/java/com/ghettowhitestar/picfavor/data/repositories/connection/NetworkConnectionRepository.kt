package com.ghettowhitestar.picfavor.data.repositories.connection

import android.net.ConnectivityManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkConnectionRepository @Inject constructor (
    private val connectivityManager: ConnectivityManager
    ) : ConnectionRepository {
    override fun isNetworkAvailable() = connectivityManager.activeNetwork != null
}