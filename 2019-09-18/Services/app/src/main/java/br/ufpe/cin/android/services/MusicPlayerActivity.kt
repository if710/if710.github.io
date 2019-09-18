package br.ufpe.cin.android.services

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_music_player.*

class MusicPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)

        val mPlayer = MediaPlayer.create(this,R.raw.moonlightsonata)
        botaoPlay.setOnClickListener {
            mPlayer.start()
            Toast.makeText(applicationContext,"play!",Toast.LENGTH_SHORT).show()
        }

        botaoPause.setOnClickListener {
            mPlayer.pause()
            Toast.makeText(applicationContext,"pause!",Toast.LENGTH_SHORT).show()
        }
    }

    //onStop -> parar a música... mPlayer.release()
}
