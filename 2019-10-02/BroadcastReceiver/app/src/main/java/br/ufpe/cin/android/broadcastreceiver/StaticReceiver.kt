package br.ufpe.cin.android.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Toast

class StaticReceiver : BroadcastReceiver() {
    private val TAG = "StaticReceiver"

    override fun onReceive(context: Context, intent: Intent) {
        Log.i(TAG, "RECEIVER Registrado Estaticamente - INTENT Recebido")
        Toast.makeText(context, "INTENT Recebido pelo StaticReceiver", Toast.LENGTH_LONG).show()
    }
}
