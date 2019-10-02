package br.ufpe.cin.android.systemservices.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.ListActivity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.core.content.ContextCompat

import java.util.ArrayList

class LocationActivity : ListActivity(), LocationListener {
    private var mgr: LocationManager? = null
    internal var data = ArrayList<Location>()
    internal var adapter: ArrayAdapter<Location>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_location);
        mgr = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        listAdapter = adapter
    }

    //evita warnings do Android Studio (checagem sintática apenas)
    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        //if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        //especificando GPS_PROVIDER
        //atualizacoes de hora em hora
        //o dispositivo deve ter se movido em pelo menos 1km
        //objeto LocationListener
        //mgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3600000, 1000, this);

        //se colocar 0,0 tenta o maximo de updates
        mgr!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0f, this)

        //pode usar com PendingIntent, para disparar um broadcast por ex.
        //addProximityAlert()
        //}
    }

    override fun onPause() {
        super.onPause()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //so recebe updates enquanto visivel
            mgr!!.removeUpdates(this)
        }
    }

    override fun onLocationChanged(location: Location) {
        adapter?.add(location)
        //location.getSpeed();
        //location.getAccuracy();
        //location.getLatitude();
        //location.getLongitude();
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
        //nao utilizado neste exemplo
    }

    override fun onProviderEnabled(provider: String) {
        //nao utilizado neste exemplo
    }

    override fun onProviderDisabled(provider: String) {
        //nao utilizado neste exemplo
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        val loc = l.adapter.getItem(position) as Location
        Toast.makeText(this, loc.latitude.toString() + " " + loc.longitude, Toast.LENGTH_SHORT).show()

        //simplesmente centraliza o mapa na latitude/longitude escolhida
        //nao amarra com google maps
        val locData = "geo:" + loc.latitude + "," + loc.longitude

        //locData = "geo:"+loc.getLatitude()+","+loc.getLongitude()+"(AQUI)";

        //abre streetview
        //locData = "google.streetview:cbll="+loc.getLatitude()+","+loc.getLongitude();

        //abre navigation
        //locData = "google.navigation:q="+loc.getLatitude()+","+loc.getLongitude();

        val locationURI = Uri.parse(locData)

        val i = Intent(Intent.ACTION_VIEW, locationURI)
        if (i.resolveActivity(packageManager) != null) {
            startActivity(i)
        }
    }
}
