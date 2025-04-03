package be.marche.pinpoint.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ConnectivityManager(private val context: Context) {

    private val _isConnected = MutableStateFlow(false)
    private val _capabilities = MutableStateFlow<Int?>(null)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()
    val capabilities: StateFlow<Int?> = _capabilities.asStateFlow()

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    init {
        checkInitialConnection()
        registerNetworkCallback()
    }

    private fun checkInitialConnection() {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        _isConnected.value =
            capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    private fun registerNetworkCallback() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(
            networkRequest,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    _isConnected.value = true
                }

                override fun onLost(network: Network) {
                    _isConnected.value = false
                }

                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    super.onCapabilitiesChanged(network, networkCapabilities)
                    _capabilities.value = getCapabilities(networkCapabilities)
                    //Unmetered (Free Usage) → Typically applies to Wi-Fi and Ethernet networks.
                    //Metered (Limited Usage) → Typically applies to Cellular (Mobile Data) networks, where usage
                    val unmetered =
                        networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
                }
            })
    }

    fun getCapabilities(capabilities: NetworkCapabilities?): Int? {

        /*  val connectivityManager =
              context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
          val capabilities =
              connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)*/
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return NetworkCapabilities.TRANSPORT_CELLULAR
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return NetworkCapabilities.TRANSPORT_WIFI
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                return NetworkCapabilities.TRANSPORT_ETHERNET
            }
        } else {
            return null
        }

        return null
    }

    fun checkingDraft() {

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        val connectivityManager =
            context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallbackDraft)


        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)

    }

    private val networkCallbackDraft = object : ConnectivityManager.NetworkCallback() {
        // network is available for use
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
        }

        // Network capabilities have changed for the network
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            //Unmetered (Free Usage) → Typically applies to Wi-Fi and Ethernet networks.
            //Metered (Limited Usage) → Typically applies to Cellular (Mobile Data) networks, where usage
            val unmetered =
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
        }
    }
}