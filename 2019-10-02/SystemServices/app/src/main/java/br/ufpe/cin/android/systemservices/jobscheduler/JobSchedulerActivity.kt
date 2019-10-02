package br.ufpe.cin.android.systemservices.jobscheduler

import android.Manifest
import android.app.Activity
import android.app.AlarmManager
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.*
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import br.ufpe.cin.android.systemservices.R
import kotlinx.android.synthetic.main.activity_job_scheduler.*

class JobSchedulerActivity : Activity() {

    internal var jobScheduler: JobScheduler? = null

    private val onDownloadCompleteEvent = object : BroadcastReceiver() {
        override fun onReceive(ctxt: Context, i: Intent) {
            toggleWidgets(true)
            Toast.makeText(ctxt, "Download finalizado!", Toast.LENGTH_LONG).show()
            startActivity(Intent(ctxt, DownloadViewActivity::class.java))
        }
    }

    fun podeEscrever(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_scheduler)


        val periods = ArrayAdapter(this,
                android.R.layout.simple_spinner_item,
                resources.getStringArray(R.array.periods))
        periods.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        period.adapter = periods

        botaoAgendar.setOnClickListener {
            agendarJob()
            toggleWidgets(false)
        }
        botaoCancelar.setOnClickListener {
            cancelarJobs()
            toggleWidgets(true)
        }

        jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        if (!podeEscrever()) {
            ActivityCompat.requestPermissions(this@JobSchedulerActivity, STORAGE_PERMISSIONS, WRITE_EXTERNAL_STORAGE_REQUEST)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            WRITE_EXTERNAL_STORAGE_REQUEST -> if (!podeEscrever()) {
                Toast.makeText(this, "Sem permissão para escrita", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val f = IntentFilter(DownloadService.DOWNLOAD_COMPLETE)
        LocalBroadcastManager.getInstance(applicationContext).registerReceiver(onDownloadCompleteEvent, f)
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(onDownloadCompleteEvent)
    }

    private fun toggleWidgets(enable: Boolean) {
        botaoAgendar.isEnabled = enable
        period.isEnabled = enable
    }

    private fun agendarJob() {

        val b = JobInfo.Builder(JOB_ID, ComponentName(this, DownloadJobService::class.java))

        //criterio de rede
        b.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
        //b.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE);

        //define intervalo de periodicidade
        //b.setPeriodic(getPeriod());

        //exige (ou nao) que esteja conectado ao carregador
        b.setRequiresCharging(false)

        //persiste (ou nao) job entre reboots
        //se colocar true, tem que solicitar permissao action_boot_completed
        //b.setPersisted(true)

        //exige (ou nao) que dispositivo esteja idle
        b.setRequiresDeviceIdle(false)

        //backoff criteria (linear ou exponencial)
        //b.setBackoffCriteria(1500, JobInfo.BACKOFF_POLICY_EXPONENTIAL);

        //periodo de tempo minimo pra rodar
        //so pode ser chamado se nao definir setPeriodic...
        b.setMinimumLatency(3000)

        //mesmo que criterios nao sejam atingidos, define um limite de tempo
        //so pode ser chamado se nao definir setPeriodic...
        b.setOverrideDeadline(6000)

        jobScheduler?.schedule(b.build())
    }

    private fun cancelarJobs() {
        jobScheduler?.cancel(JOB_ID)
        // cancela todos os jobs da aplicacao
        // jobScheduler.cancelAll();
    }

    private fun getPeriod(): Long {
        return PERIODS[period.selectedItemPosition]
    }

    companion object {
        private val JOB_ID = 710
        private val PERIODS = longArrayOf(AlarmManager.INTERVAL_FIFTEEN_MINUTES, AlarmManager.INTERVAL_HALF_HOUR, AlarmManager.INTERVAL_HOUR)
        private val STORAGE_PERMISSIONS = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        private val WRITE_EXTERNAL_STORAGE_REQUEST = 710
    }
}
