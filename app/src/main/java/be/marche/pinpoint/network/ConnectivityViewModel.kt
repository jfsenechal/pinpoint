package be.marche.pinpoint.network

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ConnectivityViewModel(private val connectivityManager: ConnectivityManager) : ViewModel() {

    val isConnected: StateFlow<Boolean> = connectivityManager.isConnected

    val capabilities: StateFlow<Int?> = connectivityManager.capabilities
}
