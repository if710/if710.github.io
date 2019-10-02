package br.ufpe.cin.android.broadcastreceiver

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_bateria.*

class BateriaActivity : Activity() {

    internal var onBattery: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val pct = 100 * intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 1) / intent.getIntExtra(BatteryManager.EXTRA_SCALE, 1)

            barra_carga.progress = pct
            nivel_carga.text = pct.toString() + "%"


            when (intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)) {
                BatteryManager.BATTERY_STATUS_CHARGING -> status_carga!!.text = "carregando"

                BatteryManager.BATTERY_STATUS_FULL -> {
                    val plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)

                    if (plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB) {
                        status_carga.text = "bateria cheia, plugada"
                    } else {
                        status_carga.text = "bateria cheia, mas descarregando"
                    }
                }

                else -> status_carga.text = "bateria descarregando"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bateria)
    }

    override fun onStart() {
        super.onStart()
        val f = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(onBattery, f)

    }

    override fun onStop() {
        unregisterReceiver(onBattery)
        super.onStop()
    }
}
