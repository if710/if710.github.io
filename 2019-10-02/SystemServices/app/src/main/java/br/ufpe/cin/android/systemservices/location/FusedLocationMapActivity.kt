package br.ufpe.cin.android.systemservices.location

import android.annotation.SuppressLint
import android.app.ListActivity
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResult
import com.google.android.gms.location.LocationSettingsStatusCodes

import java.util.ArrayList

class FusedLocationMapActivity : ListActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<LocationSettingsResult>, LocationListener {

    private var playServices: GoogleApiClient? = null

    internal var data = ArrayList<Location>()
    internal var adapter: ArrayAdapter<Location>? = null

    private var locationRequest: LocationRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //configurando a API que vamos utilizar, no caso LOCATION
        playServices = GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build()
    }

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

    override fun onPause() {
        super.onPause()
        LocationServices.FusedLocationApi.removeLocationUpdates(playServices, this)
    }

    override fun onConnectionFailed(result: ConnectionResult) {
        Toast.makeText(this, "Algum problema de conexao... " + result.errorMessage!!, Toast.LENGTH_LONG).show()
        finish()
    }

    //ao ter acesso a API de Location, o que faremos
    override fun onConnected(bundle: Bundle?) {

        //inicializa list view
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        listAdapter = adapter

        //define requisicao para obter localizacao
        //objeto define quantos updates serao necessarios
        //deadline para desistir se nao conseguir obter location
        //intervalo
        //otimizacao de energia, caso aplicavel
        locationRequest = LocationRequest()
                .setNumUpdates(5)
                .setExpirationDuration(60000)
                .setInterval(1000)
                .setPriority(LocationRequest.PRIORITY_LOW_POWER)


        val b = LocationSettingsRequest.Builder().addLocationRequest(locationRequest!!)
        val result = LocationServices.SettingsApi.checkLocationSettings(playServices, b.build())
        result.setResultCallback(this)
    }


    //se houver algum problema, ou algo do tipo...
    override fun onConnectionSuspended(i: Int) {
        Log.w((this as Any).javaClass.simpleName, "onConnectionSuspended() foi executado")
        Toast.makeText(this, "Algum problema de conexao suspensa... $i", Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        val loc = l.adapter.getItem(position) as Location
        Toast.makeText(this, loc.latitude.toString() + " " + loc.longitude, Toast.LENGTH_SHORT).show()

        val i = Intent(this, MapActivity::class.java)
        i.putExtra(MapActivity.LAT_KEY, loc.latitude)
        i.putExtra(MapActivity.LON_KEY, loc.longitude)
        startActivity(i)
    }

    @SuppressLint("MissingPermission")
    override fun onResult(locationSettingsResult: LocationSettingsResult) {
        val statusCode = locationSettingsResult.status.statusCode
        if (statusCode == LocationSettingsStatusCodes.SUCCESS) {
            val result = LocationServices.FusedLocationApi.requestLocationUpdates(playServices, locationRequest, this)

            result.setResultCallback { status ->
                if (status.isSuccess) {
                    Toast.makeText(applicationContext, "Pedido esta na fila", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, "Erro ao solicitar location! " + status.statusMessage!!, Toast.LENGTH_LONG).show()
                    finish()
                }
            }

        } else if (statusCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
            //obter permissao para usar location
        } else {
            Toast.makeText(this, "Algum problema ao tentar obter location", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    override fun onLocationChanged(location: Location) {
        adapter?.add(location)
    }
}
