package br.ufpe.cin.android.systemservices.location

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import br.ufpe.cin.android.systemservices.R
import java.util.*

class LocationMapsActivity : Activity() {
    private var isInPermission = false
    private var state: Bundle? = null

    protected val desiredPermissions: Array<String>
        get() = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.state = savedInstanceState

        if (state != null) {
            isInPermission = state!!.getBoolean(KEY_IN_PERMISSION, false)
        }

        if (hasAllPermissions(desiredPermissions)) {
            onReady()
        } else if (!isInPermission) {
            isInPermission = true
            ActivityCompat.requestPermissions(this, netPermissions(desiredPermissions), ID_PERMISSION_REQUEST)
        }
    }

    protected fun onReady() {
        setContentView(R.layout.activity_location_maps)

        val b1 = findViewById<View>(R.id.btn1) as Button
        b1.setOnClickListener { startActivity(Intent(applicationContext, ProvidersActivity::class.java)) }

        val b2 = findViewById<View>(R.id.btn2) as Button
        b2.setOnClickListener { startActivity(Intent(applicationContext, LocationActivity::class.java)) }

        val b3 = findViewById<View>(R.id.btn3) as Button
        b3.setOnClickListener { startActivity(Intent(applicationContext, FusedLocationActivity::class.java)) }

        val b4 = findViewById<View>(R.id.btn4) as Button
        b4.setOnClickListener { startActivity(Intent(applicationContext, MapActivity::class.java)) }

        val b5 = findViewById<View>(R.id.btn5) as Button
        b5.setOnClickListener { startActivity(Intent(applicationContext, FusedLocationMapActivity::class.java)) }
    }

    protected fun onPermissionDenied() {
        Toast.makeText(this, "Permissao negada!", Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        isInPermission = false

        if (requestCode == ID_PERMISSION_REQUEST) {
            if (hasAllPermissions(desiredPermissions)) {
                onReady()
            } else {
                onPermissionDenied()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_IN_PERMISSION, isInPermission)
    }

    private fun hasAllPermissions(perms: Array<String>): Boolean {
        for (perm in perms) {
            if (!hasPermission(perm)) {
                return false
            }
        }

        return true
    }

    protected fun hasPermission(perm: String): Boolean {
        return ContextCompat.checkSelfPermission(this, perm) == PackageManager.PERMISSION_GRANTED
    }

    private fun netPermissions(wanted: Array<String>): Array<String> {
        val result = ArrayList<String>()

        for (perm in wanted) {
            if (!hasPermission(perm)) {
                result.add(perm)
            }
        }

        return result.toTypedArray()
    }

    companion object {

        //Existem diversos dispositivos Android
        //Diferentes maneiras de obter localização
        //Objetos LocationProvider são usados para obter localização
        // existem zero ou mais instâncias de LocationProvider,
        // uma para cada serviço de localização disponível no device
        // GPS_PROVIDER, NETWORK_PROVIDER
        //Usamos o LocationManager para obter informações.
        // eh papel do Manager escolher o LocationProvider adequado

        //Permissao eh necessaria COARSE ou FINE
        // GPS_PROVIDER so funciona com FINE_LOCATION
        // permissoes sao dangerous, então precisamos pedir em novas APIs


        //1. pegar o LocationManager
        //2. escolher o LocationProvider
        //2.1 oferecer escolha - pega lista de providers getProviders()
        //2.2 escolher automaticamente (de acordo com objeto Criteria)
        //setAltitude, setAccuracy...
        //getBestProvider()

        //3. getLastKnownLocation()
        // retorna objeto Location

        //4. registrar para receber updates
        //Alguns location providers não respondem imediatamente.
        // O GPS exige ativação do rádio e comunicação com satélites.

        private val ID_PERMISSION_REQUEST = 2505
        private val KEY_IN_PERMISSION = "temPermissao"
    }
}
