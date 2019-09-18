package br.ufpe.cin.android.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class MusicPlayerWithBindingService : Service() {
    private val TAG = "MusicPlayerWithBindingService"
    private var mPlayer: MediaPlayer? = null
    private val mStartID: Int = 0

    private val mBinder = MusicBinder()

    override fun onCreate() {
        super.onCreate()
        // configurar media player
        mPlayer = MediaPlayer.create(this, R.raw.moonlightsonata)

        //fica em loop
        mPlayer?.isLooping = true

        createChannel()
        // cria notificacao na area de notificacoes para usuario voltar p/ Activity
        val notificationIntent = Intent(applicationContext, MusicPlayerWithBindingActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val notification = NotificationCompat.Builder(
            applicationContext,"1")
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setOngoing(true).setContentTitle("Music Service rodando")
            .setContentText("Clique para acessar o player!")
            .setContentIntent(pendingIntent).build()

        // inicia em estado foreground, para ter prioridade na memoria
        // evita que seja facilmente eliminado pelo sistema
        startForeground(NOTIFICATION_ID, notification)
    }
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return Service.START_STICKY
    }

    override fun onDestroy() {
        mPlayer?.release()
        super.onDestroy()
    }

    fun playMusic() {
        if (!mPlayer!!.isPlaying) {
            mPlayer?.start()
        }
    }

    fun pauseMusic() {
        if (mPlayer!!.isPlaying) {
            mPlayer?.pause()
        }
    }

    inner class MusicBinder : Binder() {
        internal val service: MusicPlayerWithBindingService
            get() = this@MusicPlayerWithBindingService
    }

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val mChannel = NotificationChannel("1", "Canal de Notificacoes", NotificationManager.IMPORTANCE_DEFAULT)
            mChannel.description = "Descricao"
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }
    companion object {
        private val NOTIFICATION_ID = 2
    }

}
