package com.ghettowhitestar.picfavor.data.repositories.connection

interface ConnectionRepository {
    fun isNetworkAvailable() : Boolean
}