package br.ufpe.cin.android.systemservices.location

import android.annotation.SuppressLint
import android.app.ListActivity
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import java.util.ArrayList

class FusedLocationActivity : ListActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, Runnable {
    //E se o GPS estiver desabilitado, ou indisponível?
    //FusedLocationProvider tenta atacar estas situações
    //Mistura dados do GPS com triangulação de torres celulares e hotspots WiFi
    //Consome dados dos sensores, pode identificar que não há movimento

    //parte do GooglePlay Services, ou seja, proprietário e closed source
    //apenas disponível em dispositivos que tem a play store
    //dispositivos mais antigos não funcionam
    private var playServices: GoogleApiClient? = null

    internal var data = ArrayList<Location>()
    internal var adapter: ArrayAdapter<Location>? = null
    internal var fusedLocationClient: FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //configurando a API que vamos utilizar, no caso LOCATION
        playServices = GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build()
    }

    @SuppressLint("MissingPermission")
    override fun onStart() {
        super.onStart()

        if (playServices != null) {
            playServices!!.connect()
        }
    }

    override fun onStop() {
        if (playServices != null) {
            playServices!!.disconnect()
        }

        super.onStop()
    }

    override fun onConnectionFailed(result: ConnectionResult) {
        Toast.makeText(this, "Algum problema de conexao... " + result.errorMessage!!, Toast.LENGTH_LONG).show()
        finish()
    }

    //ao ter acesso a API de Location, o que faremos
    override fun onConnected(bundle: Bundle?) {
        //inicializa list view
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        listAdapter = adapter
        //metodo que tenta obter localizacao
        run()
    }

    @SuppressLint("MissingPermission")
    override fun run() {
        fusedLocationClient!!.lastLocation
                .addOnSuccessListener { location: Location? ->
                    adapter?.add(location)
                }

        //getListView().postDelayed(this, 5000);
    }

    //se houver algum problema, ou algo do tipo...
    public override fun onConnectionSuspended(i: Int) {
        Log.w((this as Any).javaClass.getSimpleName(), "onConnectionSuspended() foi executado")
        Toast.makeText(this, "Algum problema de conexao suspensa... " + i, Toast.LENGTH_LONG).show()
        finish()
    }

    protected override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        val loc = l.getAdapter().getItem(position) as Location
        Toast.makeText(this, "${loc.latitude} ${loc.longitude}", Toast.LENGTH_SHORT).show()

        //simplesmente centraliza o mapa na latitude/longitude escolhida
        //nao amarra com google maps
        var locData = "geo:" + loc.getLatitude() + "," + loc.getLongitude()

        //abre streetview
        //locData = "google.streetview:cbll=" + loc.latitude + "," + loc.longitude

        //abre navigation
        //locData = "google.navigation:q="+loc.getLatitude()+","+loc.getLongitude();

        val locationURI = Uri.parse(locData)

        val i = Intent(Intent.ACTION_VIEW, locationURI)
        if (i.resolveActivity(getPackageManager()) != null) {
            startActivity(i)
        }
    }

}