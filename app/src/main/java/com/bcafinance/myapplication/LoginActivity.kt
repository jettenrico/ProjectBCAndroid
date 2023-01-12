package com.bcafinance.myapplication

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val defaultUsername = "user"
    val defaultPassword = "111"

    private val GPS_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener(View.OnClickListener {
            checkPassword()
        })

    }

    fun checkPassword(){
        if(txtUsername.text.contentEquals(defaultUsername) && txtPassword.text.contentEquals(defaultPassword) ){
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
                val permission = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
                requestPermissions(permission, GPS_REQUEST_CODE)
                Toast.makeText(applicationContext, "Maaf permission Denied", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(applicationContext, "Berhasil login", Toast.LENGTH_LONG).show()
                val intent = Intent(this,MenuUtamaActivity::class.java)
                startActivity(intent)
            }
        }else{
            Toast.makeText(applicationContext, "Username/Password salah", Toast.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            GPS_REQUEST_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    checkPassword()
                }else{
                    Toast.makeText(this, "Maaf Permission Denied", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}