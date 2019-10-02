package br.ufpe.cin.android.systemservices.alarm

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import br.ufpe.cin.android.systemservices.R

class AlarmSimpleActivity : Activity() {
    private var pendingIntent: PendingIntent? = null
    private var alarmManager: AlarmManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_simple)

        //pegando manager...
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        //Criando PendingIntent que envia resultados para Activity em onActivityResult
        pendingIntent = createPendingResult(ALARME_ID, Intent(), 0)

        alarmManager?.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + INTERVALO, INTERVALO.toLong(), pendingIntent)
    }

    override fun onDestroy() {
        //ao remover activity da memoria, cancela o alarme
        //neste caso, basta um PedingIntent que seja equivalente
        alarmManager?.cancel(pendingIntent)
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == ALARME_ID) {
            Toast.makeText(this, "Vou aparecer de novo...", Toast.LENGTH_SHORT).show()
            Log.d(javaClass.simpleName, "executou um alarme...")
        }
    }

    companion object {
        private val ALARME_ID = 710
        private val INTERVALO = 3500
    }

}
