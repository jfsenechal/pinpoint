package be.marche.pinpoint.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState

//https://developer.android.com/about/versions/14/changes/partial-photo-video-access
//https://github.com/google/accompanist/tree/main/sample/src/main/java/com/google/accompanist/sample/permissions
class PermissionUtil(val context: Context) {

    companion object {
        val listOfPermissions =
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
                arrayOf(
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.POST_NOTIFICATIONS,
                    Manifest.permission.CAMERA,
                    Manifest.permission.FOREGROUND_SERVICE,
                    Manifest.permission.NEARBY_WIFI_DEVICES,
                )
            } else {
                arrayOf(
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.CAMERA,
                    Manifest.permission.FOREGROUND_SERVICE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                )
            }

        @OptIn(ExperimentalPermissionsApi::class)
        fun getPermissionsText(permissions: List<PermissionState>): String {
            val revokedPermissionsSize = permissions.size
            if (revokedPermissionsSize == 0) return ""

            val textToShow = StringBuilder().apply {
                append("Le ")
            }

            for (i in permissions.indices) {
                textToShow.append(permissions[i].permission)
                when {
                    revokedPermissionsSize > 1 && i == revokedPermissionsSize - 2 -> {
                        textToShow.append(", et ")
                    }

                    i == revokedPermissionsSize - 1 -> {
                        textToShow.append(" ")
                    }

                    else -> {
                        textToShow.append(", ")
                    }
                }
            }
            textToShow.append(if (revokedPermissionsSize == 1) "permission est" else "permissions sont")
            return textToShow.toString()
        }
    }

    fun checkSelfPermissions(permission: String): Boolean {
        if (ContextCompat.checkSelfPermission(
                context,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        return true
    }
}