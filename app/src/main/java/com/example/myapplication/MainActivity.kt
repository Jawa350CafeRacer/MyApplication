package com.example.myapplication


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ActivityMainBinding
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity(), FotoAdapter.Listener {

    lateinit var binding: ActivityMainBinding
    private val cameraRequestCode = 1
    private val galleryRequestCode = 2
    private var imageUri: Uri?=null
    private var tempUri: Uri?=null
    private val adapter = FotoAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerForContextMenu(binding.buttonAdd)

        init()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_item,menu)
    }



    override fun onContextItemSelected(item: MenuItem): Boolean{
        when(item.itemId){
            R.id.item_2 -> {
                cameraCheckPermission()
                galleryCheckPermission()
                galleryCheckPermissionR()
                camera()
            }
            R.id.item_3 -> {
                galleryCheckPermission()
                gallery()
            }
            R.id.item_4 -> {


                tempUri?.let { adapter.fotolist.add(it) }
                Glide.with(this@MainActivity).load(imageUri).into(binding.imageView5)
                tempUri = imageUri
                adapter.removeItem(imageUri)
            }
            R.id.item_5 -> {
                adapter.removeItem(imageUri)
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun init(){
        binding.apply {

            val warehouse = resources.getStringArray(R.array.Warehouse)
            val warehouseAdapter = ArrayAdapter(this@MainActivity, R.layout.dropdown_item, warehouse)
            binding.autoCompleteTextView3.setAdapter(warehouseAdapter)
            rcView.layoutManager = GridLayoutManager(this@MainActivity, 5)
            rcView.adapter = adapter
            buttonAdd.setOnClickListener{

                btnCamera.visibility = View.VISIBLE
                btnCamera.setOnClickListener {

                cameraCheckPermission()
                    galleryCheckPermission()
                    galleryCheckPermissionR()

                    camera()

                    btnCamera.visibility = View.GONE
                    btnGallery.visibility = View.GONE
                }
                btnGallery.visibility = View.VISIBLE
                btnGallery.setOnClickListener {
                    galleryCheckPermission()
                        gallery()

                    btnCamera.visibility = View.GONE
                    btnGallery.visibility = View.GONE
                }
            }
        }
    }

    private fun cameraCheckPermission() {
        if (allPPermissionGranted()){
        } else {
            ActivityCompat.requestPermissions(
                this, Constants.REQUIRED_PERMISSIONS,
                Constants.REQUEST_CODE_PERMISSIONS
            )
        }
    }
    private fun galleryCheckPermission() {
        if (allPPermissionGrantedG()){
        } else {
            ActivityCompat.requestPermissions(
                this, Constants.REQUIRED_PERMISSIONS,
                Constants.REQUEST_CODE_PERMISSIONS,
            )
        }
    }
    private fun galleryCheckPermissionR() {
        if (allPPermissionGrantedR()){
        } else {
            ActivityCompat.requestPermissions(
                this, Constants.REQUIRED_PERMISSIONS,
                Constants.REQUEST_CODE_PERMISSIONS,
            )
        }
    }
    private fun allPPermissionGranted() =
        Constants.REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                baseContext, it
            ) == PackageManager.PERMISSION_GRANTED
        }
    private fun allPPermissionGrantedG() =
        Constants.REQUIRED_PERMISSIONS_G.all {
            ContextCompat.checkSelfPermission(
                baseContext, it
            ) == PackageManager.PERMISSION_GRANTED
        }
    private fun allPPermissionGrantedR() =
        Constants.REQUIRED_PERMISSIONS_R.all {
            ContextCompat.checkSelfPermission(
                baseContext, it
            ) == PackageManager.PERMISSION_GRANTED
        }
    private fun camera(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, cameraRequestCode)
    }

    private fun gallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, galleryRequestCode)
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){

            when(requestCode){
                cameraRequestCode-> {

                    val bitmap = data?.extras?.get("data") as Bitmap

                    val uri = getImageUriFromBitmap(this@MainActivity, bitmap)
                    adapter.fotolist.add(uri)
                    binding.rcView.adapter!!.notifyDataSetChanged()

                    val imageData = data.data
                    if (imageData != null) {

                        adapter.fotolist.add(uri)
                        binding.rcView.adapter!!.notifyDataSetChanged()

                    }
                }

                galleryRequestCode->{

                    if (data != null) {
                        val imageData = data.data
                        if (imageData != null) {

                            adapter.fotolist.add(imageData)
                            binding.rcView.adapter!!.notifyDataSetChanged()
                        }
                    }
            }
        }
    }
}

    override fun onclick(uri: Uri) {
        imageUri=uri
        openContextMenu(binding.rcView)
        registerForContextMenu(binding.rcView)
    }

    private fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri{
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path.toString())
    }
}



