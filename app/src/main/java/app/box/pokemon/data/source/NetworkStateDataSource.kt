package app.box.pokemon.data.source

import android.content.Context
import android.net.ConnectivityManager

class NetworkStateDataSource(
    private val context: Context
) {
    fun getCurrentNetworkState(): NetworkState? {
        if (isNetworkAvailable(context) && isWifiAvailable(context)) {
            return NetworkState.WIFI
        }
        return if (isNetworkAvailable(context) && isCellularAvailable(context)) {
            NetworkState.MOBILE
        } else NetworkState.OFFLINE
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private fun isWifiAvailable(context: Context): Boolean {
        val conMan = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiNetworkInfo = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return wifiNetworkInfo != null && wifiNetworkInfo.isConnected
    }

    private fun isCellularAvailable(context: Context): Boolean {
        val conMan = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mobileNetworkInfo = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        return mobileNetworkInfo != null && mobileNetworkInfo.isConnected
    }

    enum class NetworkState {
        WIFI, MOBILE, OFFLINE
    }
}