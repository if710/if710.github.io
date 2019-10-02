package br.ufpe.cin.android.systemservices.alarm

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import br.ufpe.cin.android.systemservices.R
import kotlinx.android.synthetic.main.activity_alarm_manager.*

class AlarmManagerActivity : Activity() {
    private var mAlarmManager: AlarmManager? = null
    private var mNotificationReceiverIntent: Intent? = null
    private var mLoggingReceiverIntent: Intent? = null
    private var mNotificationReceiverPendingIntent: PendingIntent? = null
    private var mLoggingReceiverPendingIntent: PendingIntent? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_manager)

        // Botao para abrir activity de alarme simples - repetido
        simple_alarm_button.setOnClickListener {
            startActivity(Intent(applicationContext, AlarmSimpleActivity::class.java))
        }


        // Pegando o servico
        mAlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        //mAlarmManager = getSystemService(AlarmManager.class);

        // Criando Intent para enviar broadcast para o NotificationReceiver
        mNotificationReceiverIntent = Intent(this, AlarmNotificationReceiver::class.java)

        // Criando PendingIntent que guarda o mNotificationReceiverIntent - metodo getBroadcast
        mNotificationReceiverPendingIntent = PendingIntent.getBroadcast(this, 0, mNotificationReceiverIntent, 0)

        // Criando Intent para enviar broadcast para o LoggingReceiver
        mLoggingReceiverIntent = Intent(this, AlarmLogReceiver::class.java)

        // Criando PendingIntent que guarda o mLoggingReceiverIntent
        mLoggingReceiverPendingIntent = PendingIntent.getBroadcast(this, 0, mLoggingReceiverIntent, 0)

        // Botao para definir single alarm Button
        single_alarm_button.setOnClickListener {
            // Definindo single alarm com RTC_WAKEUP
            // (nao eh o mais indicado neste caso, mas apenas para exercicio)
            mAlarmManager?.setExact(AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + INITIAL_ALARM_DELAY,
                    mNotificationReceiverPendingIntent)

            // Define outro single alarm que dispara logo apos o anterior
            //inexact por default...
            mAlarmManager?.set(AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + INITIAL_ALARM_DELAY
                            + JITTER, mLoggingReceiverPendingIntent)


            // Mostra Toast
            Toast.makeText(applicationContext, "One-shot alarm definido",
                    Toast.LENGTH_LONG).show()
        }

        // Botao para definir repeating alarm
        repeating_alarm_button.setOnClickListener {
            // Define repeating alarm, usando ELAPSED_REALTIME
            //nao tem comportamento diferente de setInexactRepeating a partir de Android 4.4+ API 19+
            mAlarmManager?.setRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime() + INITIAL_ALARM_DELAY,
                    AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                    mNotificationReceiverPendingIntent)

            // Define outro repeating alarm que dispara logo apos o anterior
            mAlarmManager?.setRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime() + INITIAL_ALARM_DELAY
                            + JITTER,
                    AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                    mLoggingReceiverPendingIntent)

            // Mostra Toast
            Toast.makeText(applicationContext, "Repeating alarm definido",
                    Toast.LENGTH_LONG).show()
        }

        // Botao para definir inexact repeating alarm
        inexact_repeating_alarm_button.setOnClickListener {
            // Define inexact repeating alarm, usando ELAPSED_REALTIME
            mAlarmManager?.setInexactRepeating(
                    AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime() + INITIAL_ALARM_DELAY,
                    AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                    mNotificationReceiverPendingIntent)

            // Define outro inexact repeating alarm que dispara logo apos o anterior
            mAlarmManager?.setInexactRepeating(
                    AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime() + INITIAL_ALARM_DELAY
                            + JITTER,
                    AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                    mLoggingReceiverPendingIntent)

            // Mostra Toast
            Toast.makeText(applicationContext,
                    "Inexact Repeating Alarm definido", Toast.LENGTH_LONG)
                    .show()
        }

        // Botao para cancelar repeating alarm
        cancel_repeating_alarm_button.setOnClickListener {
            // Cancela todos os alarmes usando mNotificationReceiverPendingIntent
            mAlarmManager?.cancel(mNotificationReceiverPendingIntent)

            // Cancela todos os alarmes usando mLoggingReceiverPendingIntent
            mAlarmManager?.cancel(mLoggingReceiverPendingIntent)

            // Mostra Toast
            Toast.makeText(applicationContext,
                    "Repeating Alarms cancelados", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        private val INITIAL_ALARM_DELAY = 1000L
        protected val JITTER = 5000L
    }
}
