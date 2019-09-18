package br.ufpe.cin.android.services

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_download.*;

class DownloadActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)
        botaoDownload.setOnClickListener {
            botaoDownload.isEnabled = false
            val downloadService = Intent(applicationContext, DownloadService::class.java)
            downloadService.data = Uri.parse(downloadLink)
            startService(downloadService)
        }

        botaoVisualizar.setOnClickListener {
            val viewFile = Intent(applicationContext, DownloadViewActivity::class.java)
            startActivity(viewFile)
        }
    }

    companion object {
        val downloadLink = "https://www.cin.ufpe.br/~lmt/images/profile.jpg"
    }
}