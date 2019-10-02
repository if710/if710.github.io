package br.ufpe.cin.android.systemservices.location

import android.app.ListActivity
import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class ProvidersActivity : ListActivity() {
    private var mgr: LocationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mgr = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val providers = mgr!!.allProviders

        //apenas habilitados
        //providers = mgr.getProviders(true);

        listAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, providers)
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        val provider = l.adapter.getItem(position) as String
        var txt = "desabilitado"
        if (mgr!!.isProviderEnabled(provider)) {
            txt = "habilitado"
        }

        Toast.makeText(this, "$provider - $txt", Toast.LENGTH_SHORT).show()
    }
}