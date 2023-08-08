package com.psijuego.core.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.psijuego.core.Constants

class UtilPermissions {

    private val REQ_WRITE_EXTERNAL_STORAGE = 2
    private val REQ_READ_EXTERNAL_STORAGE = 3
    private val REQ_READ_MEDIA_IMAGES = 4

    companion object {
        fun getInstance(): UtilPermissions {
            return UtilPermissions()
        }
    }

    private fun getListOFPermissions(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
            )
        } else {
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    }

    fun checkAndRequestPermissions(activity: Activity) {
        val requiredPermissions = getListOFPermissions()

        val notGrantedPermissions = mutableListOf<String>()

        for (permission in requiredPermissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                    notGrantedPermissions.add(permission)
            }
        }
        if (notGrantedPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(activity, notGrantedPermissions.toTypedArray(), Constants.REQUEST_CODE)
        }
    }

    fun validatePermissions(activity: Activity): Boolean{
        var granted: Boolean
        for(permission in getListOFPermissions()){
            granted = askPermission(permission, activity)
            if(!granted) return false
        }
        return true

    }

    private fun checkPermission(permission: String, activity: Activity): Boolean {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun askPermission(permission: String, activity: Activity): Boolean {
        if(!checkPermission(permission, activity)){
            ActivityCompat.requestPermissions(activity, arrayOf(permission), getRequestCode(permission))
            return false
        }
        return true
    }

    private fun getRequestCode(permission: String): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (permission == Manifest.permission.READ_MEDIA_IMAGES) return REQ_READ_MEDIA_IMAGES
        } else {
            if (permission == Manifest.permission.WRITE_EXTERNAL_STORAGE) return REQ_WRITE_EXTERNAL_STORAGE
            if (permission == Manifest.permission.READ_EXTERNAL_STORAGE) return REQ_READ_EXTERNAL_STORAGE
        }
        return 0;
    }


}