package com.example.cryptoone.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class NetworkConnectionInterceptor @Inject constructor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isConnected()){
            throw NoInternetConnectivityException()
        }
        val builder : Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    private fun isConnected(): Boolean {
val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
       val netInfo = connectivityManager.activeNetworkInfo
        return (netInfo!=null && netInfo.isConnected)
    }


}

class NoInternetConnectivityException : IOException(){

    override fun getLocalizedMessage(): String? {
        return "No Internet Connection!"
    }

}