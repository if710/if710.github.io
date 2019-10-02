package br.ufpe.cin.android.broadcastreceiver

import android.app.Activity
import android.os.Bundle

class BroadcastActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broadcast)
    }
}
