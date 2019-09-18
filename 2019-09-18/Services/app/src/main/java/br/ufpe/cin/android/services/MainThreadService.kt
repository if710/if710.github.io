package br.ufpe.cin.android.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MainThreadService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        try {
            Thread.sleep(4000)

        }
        catch( e: InterruptedException ) {
            Log.e("MAIN_THREAD_SERVICE",e.message)
        }
        return super.onStartCommand(intent, flags, startId)
    }
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}
