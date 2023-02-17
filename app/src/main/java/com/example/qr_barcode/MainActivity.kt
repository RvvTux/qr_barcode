package com.example.qr_barcode

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.qr_barcode.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btn.setOnClickListener {
            val intent = Intent(this@MainActivity, ScanActivity::class.java)
            startActivity(intent)
        }

        registerPermissionListener()
        checkCameraPermission()
    }

    private fun checkCameraPermission(){
        when{
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED ->{
                Toast.makeText(this,"Camera is available", Toast.LENGTH_LONG).show()
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)->{
                Toast.makeText(this,"\n" +
                        "If you do not give permission to use the camera, then you will not be able to use this application", Toast.LENGTH_LONG).show()
                pLauncher.launch(android.Manifest.permission.CAMERA)
            }
            else -> {
                pLauncher.launch(android.Manifest.permission.CAMERA)
            }
        }
    }

    private fun registerPermissionListener(){
        pLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if (it){
                Toast.makeText(this,"Camera is run", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"Permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {

        var tvresult: TextView? = null
    }


}