package br.ufpe.cin.android.systemservices.alarm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.app.NotificationCompat

import java.text.DateFormat
import java.util.Date

import br.ufpe.cin.android.systemservices.MainActivity
import br.ufpe.cin.android.systemservices.R

class AlarmNotificationReceiver : BroadcastReceiver() {

    // Elementos a compor Notification
    private val tickerText = "Mensagem de texto que aparece na barra superior do telefone"
    private val contentTitle = "Algum lembrete"
    private val contentText = "Alguma mensagem..."

    // Ações da Notification
    private var mNotificationIntent: Intent? = null
    private var mContentIntent: PendingIntent? = null

    // Som e padrão de vibração
    //private final Uri soundURI = Uri.parse("android.resource://"+ MainActivity.PACKAGE_NAME+"/" + R.raw.alarm_rooster);
    private val soundURI = Uri.parse("android.resource://br.ufpe.cin.android.systemservices/" + R.raw.alarm_rooster)
    private val mVibratePattern = longArrayOf(0, 200, 200, 300)


    override fun onReceive(context: Context, intent: Intent) {
        // Intent a ser usado quando usuário clicar na Notification
        mNotificationIntent = Intent(context, MainActivity::class.java)

        // Objeto PendingIntent que envolve o Intent
        mContentIntent = PendingIntent.getActivity(context, 0, mNotificationIntent, 0)

        // Cria Notification
        val notificationBuilder = NotificationCompat.Builder(context)
        notificationBuilder.setTicker(tickerText)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(android.R.drawable.stat_sys_warning)
                .setAutoCancel(true).setContentTitle(contentTitle)
                .setContentText(contentText).setContentIntent(mContentIntent)
                .setSound(soundURI).setVibrate(mVibratePattern)

        // Pega NotificationManager
        val mNotificationManager = context
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Passa Notification para o NotificationManager
        mNotificationManager.notify(MY_NOTIFICATION_ID, notificationBuilder.build())

        // Faz o Log da chamada ao método notify()
        Log.i(TAG, "Enviando notification em:" + DateFormat.getDateTimeInstance().format(Date()))
    }

    companion object {
        // ID --- permite atualizar a notificação
        private val MY_NOTIFICATION_ID = 1
        private val TAG = "NotificationReceiver"
    }
}
