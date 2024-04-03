package com.pertemuan_4.prak_pmobile_101

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.util.Locale


//class MainActivity : AppCompatActivity() {
//
//    @SuppressLint("MissingInflatedId", "SetTextI18n")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        // Temukan TextView di layout
//        val textView = findViewById<TextView>(R.id.textView)
//        val textTime = findViewById<TextView>(R.id.text_time)
//        val user = findViewById<TextView>(R.id.User)
//        // Set text pada TextView
//        textView.text = "EcoBank"
//        textTime.text = "17.06"
//        user.text = "Selamat Datang User"
//
//    }
//}


class MainActivity : AppCompatActivity() {

//    private val tvCurrentLocation: Any
    private lateinit var tvCurrentLocation: TextView
    var REQ_PERMISSION = 100
    var strCurrentLatitude = 0.0
    var strCurrentLongitude = 0.0
    lateinit var strCurrentLocation: String
    lateinit var simpleLocation: SimpleLocation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setPermission()
        setStatusBar()
        setLocation()
        setInitLayout()
        setCurrentLocation()
    }

    private fun setLocation() {
        simpleLocation = SimpleLocation(this)
        if (!simpleLocation.hasLocationEnabled()) {
            SimpleLocation.openSettings(this)
        }

        //get location
        strCurrentLatitude = simpleLocation.latitude
        strCurrentLongitude = simpleLocation.longitude

        //set location lat long
        strCurrentLocation = "$strCurrentLatitude,$strCurrentLongitude"
    }

    private fun setInitLayout() {
//        val cvInput
//        cvInput.setOnClickListener { v: View? ->
//            val intent = Intent(this@MainActivity, InputDataActivity::class.java)
//            startActivity(intent)
//        }
        val cvInput = findViewById<View>(R.id.cvInput) // Inisialisasi view dengan id yang sesuai
        cvInput.setOnClickListener {
            val intent = Intent(this@MainActivity, InputDataActivity::class.java)
            startActivity(intent)
        }

//        val cvKategori
//        cvKategori.setOnClickListener { v: View? ->
//            val intent = Intent(this@MainActivity, JenisSampahActivity::class.java)
//            startActivity(intent)
//        }
        val cvKategori = findViewById<View>(R.id.cvKategori) // Inisialisasi view dengan id yang sesuai
        cvKategori.setOnClickListener {
            val intent = Intent(this@MainActivity, JenisSampahActivity::class.java)
            startActivity(intent)
        }

//        val cvHistory
//        cvHistory.setOnClickListener { v: View? ->
//            val intent = Intent(this@MainActivity, RiwayatActivity::class.java)
//            startActivity(intent)
//        }
        val cvHistory = findViewById<View>(R.id.cvHistory) // Inisialisasi view dengan id yang sesuai
        cvHistory.setOnClickListener {
            val intent = Intent(this@MainActivity, RiwayatActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQ_PERMISSION)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (grantResult in grantResults) {
            if (grantResult == PackageManager.PERMISSION_GRANTED) {
                val intent = intent
                finish()
                startActivity(intent)
            }
        }
    }

    class PackageManager {
        companion object {
            val PERMISSION_GRANTED: Any? = null
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_PERMISSION && resultCode == RESULT_OK) { }
    }

    private fun setCurrentLocation() {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addressList = geocoder.getFromLocation(strCurrentLatitude, strCurrentLongitude, 1)
            if (addressList != null && addressList.size > 0) {
                val strCurrentLocation = addressList[0].locality
                tvCurrentLocation.text = strCurrentLocation
                tvCurrentLocation.isSelected = true
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    companion object {
        fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
            val window = activity.window
            val layoutParams = window.attributes
            if (on) {
                layoutParams.flags = layoutParams.flags or bits
            } else {
                layoutParams.flags = layoutParams.flags and bits.inv()
            }
            window.attributes = layoutParams
        }
    }

}

private fun Nothing?.setOnClickListener(function: (View?) -> Unit) {
    TODO("Not yet implemented")
}

class SimpleLocation(private val mainActivity: MainActivity) {
    val longitude: Double = 0.0
    val latitude: Double = 0.0

    fun hasLocationEnabled(): Boolean {
        return false
    }

    companion object {
        fun openSettings(mainActivity: MainActivity) {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            mainActivity.startActivity(intent)
        }
    }

}
