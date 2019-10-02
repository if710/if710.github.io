package br.ufpe.cin.android.broadcastreceiver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val STA_BROADCAST_ACTION = "br.ufpe.cin.android.broadcasts.exemplo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        enviarBroadcast.setOnClickListener {
            sendBroadcast(Intent(STA_BROADCAST_ACTION))
        }
        enviarBroadcastDinamico.setOnClickListener {
            sendBroadcast(Intent(DynRecActivity.DYN_BROADCAST_ACTION))
        }

        abrirActivity.setOnClickListener { startActivity(Intent(applicationContext, DynRecActivity::class.java)) }
        smsActivity.setOnClickListener { startActivity(Intent(applicationContext, SmsActivity::class.java)) }

        bateriaActivity.setOnClickListener { startActivity(Intent(applicationContext, BateriaActivity::class.java)) }
    }
}
