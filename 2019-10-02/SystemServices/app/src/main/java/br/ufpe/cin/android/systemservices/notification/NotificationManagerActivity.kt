package br.ufpe.cin.android.systemservices.notification

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.NotificationCompat
import br.ufpe.cin.android.systemservices.R
import kotlinx.android.synthetic.main.activity_notification_manager.*

class NotificationManagerActivity : Activity() {

    // contador de notificacoes
    private var mNotificationCount: Int = 0

    // elementos de texto
    private val tickerText = "Mensagem muito, mas muito, mas muito longa para ser exibida!"
    private val contentTitle = "Notificacao"
    private val contentText = "Foi notificado!"

    // Actions (intents a serem transmitidos)
    private var mNotificationIntent: Intent? = null
    private var mContentIntent: PendingIntent? = null

    // Som e padrao de vibracao da Notification
    private val soundURI = Uri.parse("android.resource://br.ufpe.cin.android.systemservices/" + R.raw.alarm_rooster)
    private val mVibratePattern = longArrayOf(0, 200, 200, 300)

    internal var mNotifyManager: NotificationManager? = null
    internal var mBuilder: NotificationCompat.Builder? = null

    internal var mContentView = RemoteViews("br.ufpe.cin.android.systemservices", R.layout.custom_notification)
    internal var mCustomActionView = RemoteViews("br.ufpe.cin.android.systemservices", R.layout.custom_notification_action)

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_notification_manager)
        mNotifyManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //requer minSDKversion 23
        //mNotifyManager = getSystemService(NotificationManager::class.java)

        createNotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Notifications",
                "Exemplo de canal de notificacoes")

        //toast simples
        button1.setOnClickListener {
            Toast.makeText(applicationContext, R.string.toast_simples_string, Toast.LENGTH_LONG).show()
        }

        button2.setOnClickListener {
            val toast = Toast(applicationContext)
            toast.apply {
                //centralizando na tela
                setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                //duracao
                duration = Toast.LENGTH_LONG
                //definindo layout personalizado
                view = layoutInflater.inflate(R.layout.custom_toast, null)
            }
            //exibindo
            toast.show()
        }


        mNotificationIntent = Intent(applicationContext, NotificationSubActivity::class.java)
        //flag activity new task
        mContentIntent = PendingIntent.getActivity(applicationContext, 0, mNotificationIntent, 0)

        button3.setOnClickListener {
            val notification = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.stat_sys_warning)
                    .setContentTitle(contentTitle)
                    .setContentText(contentText)
                    .setTicker(tickerText)
                    .setAutoCancel(true)
                    .setContentIntent(mContentIntent)
                    .setSound(soundURI)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setVibrate(mVibratePattern)
                    .build()

            // passa notificacao para o notification manager
            mNotifyManager?.notify(MY_NOTIFICATION_ID, notification)
        }


        mNotificationIntent = Intent(applicationContext, NotificationSubCustomActivity::class.java)
        //Intent.FLAG_ACTIVITY_NEW_TASK
        mContentIntent = PendingIntent.getActivity(applicationContext, 0, mNotificationIntent, 0)

        button4.setOnClickListener {
            // define a mensagem expandida da notificacao personalizada

            mContentView.setTextViewText(R.id.text, contentText + " (" + ++mNotificationCount + ")")

            // construir notificacao, semelhante ao anterior, apenas atencao para o setContent, que recebe a view

            val notification = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
                    .setTicker(tickerText)
                    .setSmallIcon(android.R.drawable.stat_sys_warning)
                    .setAutoCancel(false)
                    .setContentIntent(mContentIntent)
                    .setSound(soundURI)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setVibrate(mVibratePattern)
                    .setContent(mContentView).build()
            //setCustomContentView - requer API 24

            // passa notificacao para o notification manager
            mNotifyManager?.notify(MY_NOTIFICATION_ID, notification)
        }

        button5.setOnClickListener {
            val builder = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
                    .setContentTitle("Baixando arquivo...")
                    .setContentText("Download em progresso")
                    .setSmallIcon(android.R.drawable.ic_menu_save)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)

            // iniciar uma background thread - na verdade isso seria um Service
            Thread(
                    Runnable {
                        var incr: Int
                        // Repetir 10 vezes
                        incr = 0
                        while (incr <= 100) {
                            // Definir indicador de progresso, de acordo
                            // com o 'estado' atual
                            builder.setProgress(100, incr, false)
                            // atualiza notificacao
                            mNotifyManager?.notify(PROGRESS_NOTIFICATION_ID, builder.build())
                            // simular operação que leva tempo
                            try {
                                // 1 segundo
                                Thread.sleep((1 * 1000).toLong())
                            } catch (e: InterruptedException) {
                                Log.d("ERRO", "sleep failure")
                            }

                            incr += 10
                        }
                        // quando loop for finalizado, atualizar notification
                        // Remover progress bar
                        builder.setContentText("Download completo").setProgress(0, 0, false)
                        // atualiza notificacao
                        mNotifyManager?.notify(PROGRESS_NOTIFICATION_ID, builder.build())
                    }
                    // iniciar thread
            ).start()
        }

        button6.setOnClickListener {
            val int1 = Intent(applicationContext, NotificationCustomActionActivity::class.java).putExtra("informacao", "valor 1")
            val pendInt1 = PendingIntent.getActivity(applicationContext, 0, int1, 0)

            val int2 = Intent(applicationContext, NotificationCustomActionActivity::class.java).putExtra("informacao", "valor 2")
            val pendInt2 = PendingIntent.getActivity(applicationContext, 1, int2, 0)

            val builder = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
                    .setContentTitle("Titulo aqui")
                    .setContentText("Texto adicional")
                    .setSmallIcon(android.R.drawable.stat_sys_upload)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(mContentIntent)


            builder.addAction(android.R.drawable.stat_sys_upload, "Action1", pendInt1)
                    .addAction(android.R.drawable.stat_sys_download, "Action2", pendInt2)

            // passa notificacao para o notification manager
            mNotifyManager?.notify(CUSTOM_ACTION_NOTIFICATION_ID, builder.build())
        }


        button7.setOnClickListener {
            val i = Intent(Settings.ACTION_SECURITY_SETTINGS)
            val pi = PendingIntent.getActivity(applicationContext, 0, i, 0)

            mBuilder = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentTitle("Titulo")
                    .setContentText("Texto")
                    .setContentIntent(pi)
                    .setSmallIcon(android.R.drawable.stat_sys_download_done)
                    .setTicker("Ticker text")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .addAction(android.R.drawable.ic_media_play, "action", pi)

            val big = NotificationCompat.InboxStyle()
            big.setBuilder(mBuilder)

            big.setSummaryText("Summary text")
                    .addLine("Linha 1")
                    .addLine("Linha 2")
                    .addLine("Linha 3")
                    .addLine("Linha 4")
                    .addLine("Linha 5")
            // passa notificacao para o notification manager
            mNotifyManager?.notify(BIG_TEXT_NOTIFICATION_ID, big.build())
        }

        button8.setOnClickListener {
            val i = Intent(Settings.ACTION_SECURITY_SETTINGS)
            val pi = PendingIntent.getActivity(applicationContext, 0, i, 0)

            mBuilder = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentTitle("Titulo")
                    .setContentIntent(pi)
                    .setSmallIcon(android.R.drawable.stat_sys_download_done)
                    .addAction(android.R.drawable.ic_media_play, "alguma action", pi)
                    .setVisibility(Notification.VISIBILITY_PUBLIC);

            //esconde da lock screen
            //.setVisibility(Notification.VISIBILITY_SECRET);

            //heads-up notification
            mBuilder?.priority = NotificationCompat.PRIORITY_HIGH

            // passa notificacao para o notification manager
            mNotifyManager?.notify(HEADS_NOTIFICATION_ID, mBuilder?.build())
        }
    }

    companion object {
        // Notification ID permite associar e agrupar notificacoes no futuro
        private val MY_NOTIFICATION_ID = 1
        private val CUSTOM_NOTIFICATION_ID = 12
        private val PROGRESS_NOTIFICATION_ID = 123
        private val CUSTOM_ACTION_NOTIFICATION_ID = 1234
        private val BIG_TEXT_NOTIFICATION_ID = 1337
        private val HEADS_NOTIFICATION_ID = 1338
        private val FULL_NOTIFICATION_ID = 1339
        private val NOTIFICATION_CHANNEL_ID = "br.ufpe.cin.android.notificacoes"
    }
    private fun createNotificationChannel(id: String, name: String,
                                          desc: String) {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O &&
                mNotifyManager?.getNotificationChannel(NOTIFICATION_CHANNEL_ID)==null) {
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(id, name, importance)
            channel.apply {
                description = desc
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
                vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            }
            mNotifyManager?.createNotificationChannel(channel)
        }
    }


}
