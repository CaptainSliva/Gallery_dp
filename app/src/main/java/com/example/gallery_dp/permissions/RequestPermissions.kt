package com.example.gallery_dp.permissions

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class RequestPermissions(
    private val activity: Activity,
) {
    companion object {
        const val PERMISSION_REQUEST_CODE = 101
    }

    fun checkPermissions(): Boolean =
        if (!hasPermissions()) {
            requestPermissions()
            false
        } else {
            true
        }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                    android.Manifest.permission.READ_MEDIA_IMAGES,
                    android.Manifest.permission.READ_MEDIA_VIDEO,
                    // MANAGE_EXTERNAL_STORAGE требует специального запроса через Intent
                    // android.Manifest.permission.MANAGE_EXTERNAL_STORAGE
                ),
                PERMISSION_REQUEST_CODE,
            )
        } else {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                ),
                PERMISSION_REQUEST_CODE,
            )
        }
    }

    private fun hasPermissions(): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ContextCompat.checkSelfPermission(
                activity,
                android.Manifest.permission.READ_MEDIA_IMAGES,
            ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    activity,
                    android.Manifest.permission.READ_MEDIA_VIDEO,
                ) == PackageManager.PERMISSION_GRANTED
            // Проверка MANAGE_EXTERNAL_STORAGE осуществляется через Environment.isExternalStorageManager()
        } else {
            ContextCompat.checkSelfPermission(
                activity,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    activity,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                ) == PackageManager.PERMISSION_GRANTED
        }

    fun handlePermissionsResult(
        requestCode: Int,
        grantResults: IntArray,
        onPermissionsGranted: () -> Unit,
        onPermissionsDenied: (deniedPermissions: List<String>) -> Unit,
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            val deniedPermissions = mutableListOf<String>()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissions.add(android.Manifest.permission.READ_MEDIA_IMAGES)
                }
                if (grantResults.size > 1 && grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissions.add(android.Manifest.permission.READ_MEDIA_VIDEO)
                }
            } else {
                if (grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
                if (grantResults.size > 1 && grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissions.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }

            if (deniedPermissions.isEmpty()) {
                onPermissionsGranted()
            } else {
                onPermissionsDenied(deniedPermissions)
            }
        }
    }
}
