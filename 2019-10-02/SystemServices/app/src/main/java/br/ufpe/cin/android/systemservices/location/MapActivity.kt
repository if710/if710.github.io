package br.ufpe.cin.android.systemservices.location

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import br.ufpe.cin.android.systemservices.R

class MapActivity : FragmentActivity(), OnMapReadyCallback {

    private var latitude: Double = 0.toDouble()
    private var longitude: Double = 0.toDouble()

    private var gMap: GoogleMap? = null
    //nao estou checando que tem acesso ao play services como no caso de FusedLocation...

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val i = intent
        latitude = i.getDoubleExtra(LAT_KEY, -8.047)
        longitude = i.getDoubleExtra(LON_KEY, -34.876)

        val mapa = supportFragmentManager.findFragmentById(R.id.mapa) as SupportMapFragment
        mapa.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap
        val location = LatLng(latitude, longitude)
        gMap!!.addMarker(MarkerOptions().position(location).title("Marcador!"))
        gMap!!.moveCamera(CameraUpdateFactory.newLatLng(location))
        gMap!!.animateCamera(CameraUpdateFactory.zoomTo(15f))
    }

    companion object {

        val LAT_KEY = "latitude"
        val LON_KEY = "longitude"
    }
}
