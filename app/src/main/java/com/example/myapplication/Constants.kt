package com.example.myapplication

import android.Manifest


object Constants {

    const val REQUEST_CODE_PERMISSIONS = 123
    val REQUIRED_PERMISSIONS_CAMERA = arrayOf(Manifest.permission.CAMERA)
    val REQUIRED_PERMISSIONS_READ = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    val REQUIRED_PERMISSIONS_WRITE = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
}