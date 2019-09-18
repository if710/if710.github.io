package br.ufpe.cin.android.services

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_music_player_no_binding.*

class MusicPlayerNoBindingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player_no_binding)
        val serviceIntent = Intent(applicationContext, MusicPlayerNoBindingService::class.java)

        btn_StartService.setOnClickListener {
            startService(serviceIntent)
        }

        btn_StopService.setOnClickListener {
            stopService(serviceIntent)
        }
    }
}
