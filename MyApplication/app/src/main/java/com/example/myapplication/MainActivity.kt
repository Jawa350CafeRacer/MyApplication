package com.example.myapplication

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.provider.SyncStateContract.Constants
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.example.myapplication.Constants.REQUIRED_PERMISSIONS
import com.example.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val CAMERA_REQUEST_CODE = 1
    private val GALLERY_REQUEST_CODE = 2

    private val adapter = FotoAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

    }
    private fun init(){
        binding.apply {

            val warehouse = resources.getStringArray(R.array.Warehouse)
            val warehouseAdapter = ArrayAdapter(this@MainActivity, R.layout.dropdown_item, warehouse)
            binding.autoCompleteTextView3.setAdapter(warehouseAdapter)

            rcView.layoutManager = GridLayoutManager(this@MainActivity, 5)
            rcView.adapter = adapter
            buttonAdd.setOnClickListener{

                btnCamera.setVisibility(View.VISIBLE)
                btnCamera.setOnClickListener {

                cameraCheckPermission()
                    camera()

                    btnCamera.setVisibility(View.GONE)
                    btnGallery.setVisibility(View.GONE)
                }
                btnGallery.setVisibility(View.VISIBLE)
                btnGallery.setOnClickListener {
                    galleryCheckPermission()
                        gallery()

                    btnCamera.setVisibility(View.GONE)
                    btnGallery.setVisibility(View.GONE)
                }


               // imageView5.setImageResource(foto.imageId)
                }


        }
    }

    private fun cameraCheckPermission() {
        if (allPPermissionGranted()){

        } else {
            ActivityCompat.requestPermissions(
                this, com.example.myapplication.Constants.REQUIRED_PERMISSIONS,
                com.example.myapplication.Constants.REQUEST_CODE_PERMISSIONS

            )
        }
    }

    private fun galleryCheckPermission() {
        if (allPPermissionGrantedG()){

        } else {
            ActivityCompat.requestPermissions(
                this, com.example.myapplication.Constants.REQUIRED_PERMISSIONS,
                com.example.myapplication.Constants.REQUEST_CODE_PERMISSIONS

            )
        }
    }

    private fun allPPermissionGranted() =
        com.example.myapplication.Constants.REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                baseContext, it
            ) == PackageManager.PERMISSION_GRANTED
        }

    private fun allPPermissionGrantedG() =
        com.example.myapplication.Constants.REQUIRED_PERMISSIONS_G.all {
            ContextCompat.checkSelfPermission(
                baseContext, it
            ) == PackageManager.PERMISSION_GRANTED
        }
    private fun camera(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    private fun gallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            when(requestCode){
                CAMERA_REQUEST_CODE->{


                    val bitmap = data?.extras?.get("data") as Bitmap
                    binding.imageView5.load(bitmap)
                }

                GALLERY_REQUEST_CODE->{
                    binding.imageView5.load(data?.data)
            }
        }
    }
}
    }