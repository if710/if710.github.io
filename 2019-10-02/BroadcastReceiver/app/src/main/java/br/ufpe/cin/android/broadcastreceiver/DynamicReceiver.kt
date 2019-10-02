package br.ufpe.cin.android.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class DynamicReceiver : BroadcastReceiver() {

    private val TAG = "DynamicReceiver"

    override fun onReceive(context: Context, intent: Intent) {
        Log.i(TAG, "INTENT Recebido")
        Toast.makeText(context, "INTENT Recebido pelo DynamicReceiver", Toast.LENGTH_SHORT).show()
        context.startActivity(Intent(context, BroadcastActivity::class.java))

    }

}
