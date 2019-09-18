package br.ufpe.cin.android.services

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_music_player_with_binding.*

class MusicPlayerWithBindingActivity : AppCompatActivity() {
    internal var musicPlayerService: MusicPlayerWithBindingService? = null
    internal var isBound = false
    internal val TAG = "MusicBindingActivity"

    private val sConn = object : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {
            musicPlayerService = null
            isBound = false
        }

        override fun onServiceConnected(p0: ComponentName?, b: IBinder?) {
            val binder = b as MusicPlayerWithBindingService.MusicBinder
            musicPlayerService = binder.service
            isBound = true

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player_with_binding)
        // intent usado para iniciar o service
        val musicServiceIntent = Intent(this, MusicPlayerWithBindingService::class.java)
        startService(musicServiceIntent)

        botaoPlay.setOnClickListener {
            if (isBound) {
                musicPlayerService?.playMusic()
            }
        }
        botaoPause.setOnClickListener {
            if (isBound) {
                musicPlayerService?.pauseMusic()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (!isBound) {
            Toast.makeText(applicationContext, "Fazendo o Binding...", Toast.LENGTH_SHORT).show()
            val bindIntent = Intent(this, MusicPlayerWithBindingService::class.java)
            isBound = bindService(bindIntent,sConn, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        Toast.makeText(applicationContext, "Desfazendo o Binding...", Toast.LENGTH_SHORT).show()
        unbindService(sConn)
        super.onStop()
    }
}
