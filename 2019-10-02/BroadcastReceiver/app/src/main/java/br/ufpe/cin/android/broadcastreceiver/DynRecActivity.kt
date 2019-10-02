package br.ufpe.cin.android.broadcastreceiver

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_dyn_rec.*

class DynRecActivity : Activity() {
    private val intentFilter = IntentFilter(DYN_BROADCAST_ACTION)
    private val receiver = DynamicReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dyn_rec)

        enviarBroadcastSta.setOnClickListener { sendBroadcast(Intent(MainActivity.STA_BROADCAST_ACTION)) }
        enviarBroadcastDyn.setOnClickListener { sendBroadcast(Intent(DYN_BROADCAST_ACTION)) }
    }


    override fun onStart() {
        super.onStart()
        registerReceiver(receiver, intentFilter);
        Toast.makeText(this, "Registrando...", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        unregisterReceiver(receiver);
        Toast.makeText(this, "Removendo registro...", Toast.LENGTH_SHORT).show()
        super.onStop()
    }

    companion object {
        val DYN_BROADCAST_ACTION = "br.ufpe.cin.android.broadcasts.dinamico"
    }


}
