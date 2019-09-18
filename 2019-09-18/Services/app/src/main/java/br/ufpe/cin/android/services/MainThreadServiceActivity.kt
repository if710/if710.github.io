package br.ufpe.cin.android.services

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main_thread_service.*

class MainThreadServiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_thread_service)
        val serviceIntent = Intent(applicationContext, MainThreadService::class.java)

        btn_StartServiceMainThread.setOnClickListener {
            startService(serviceIntent)
        }

        btn_StopServiceMainThread.setOnClickListener {
            stopService(serviceIntent)
        }

        btn_Toast.setOnClickListener {
            Toast.makeText(applicationContext,"Alguma mensagem", Toast.LENGTH_SHORT).show()
        }

    }
}
