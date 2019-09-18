package br.ufpe.cin.android.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MusicPlayerNoBindingService : Service() {
    private val TAG = "MusicPlayerNoBindingService"

    private var mPlayer: MediaPlayer? = null
    private var mStartID: Int = 0

    override fun onCreate() {
        super.onCreate()
        // configurar media player
        mPlayer = MediaPlayer.create(this, R.raw.moonlightsonata)

        //nao deixa entrar em loop
        mPlayer?.isLooping = false

        // encerrar o service quando terminar a musica
        mPlayer?.setOnCompletionListener {
            // encerra se foi iniciado com o mesmo ID
            stopSelf(mStartID)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (mPlayer != null) {
            mStartID = startId
            if (mPlayer!!.isPlaying()) {
                mPlayer?.seekTo(0)
            }
            else {
                mPlayer?.start()
            }
        }

        return Service.START_NOT_STICKY
    }

    override fun onDestroy() {
        mPlayer?.release()
        super.onDestroy()
    }


    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}
