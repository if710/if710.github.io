package br.ufpe.cin.android.systemservices.jobscheduler

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.net.Uri
import android.os.Build

class DownloadJobService : JobService() {

    override fun onStartJob(params: JobParameters): Boolean {
        val downloadService = Intent(applicationContext, DownloadService::class.java)
        downloadService.data = Uri.parse(downloadLink)
        applicationContext.startService(downloadService)
        return true
    }

    override fun onStopJob(params: JobParameters): Boolean {
        val downloadService = Intent(applicationContext, DownloadService::class.java)
        applicationContext.stopService(downloadService)
        return true
    }

    companion object {
        val downloadLink = "http://s2.glbimg.com/ylmOBAxzbEu_x1usqf2IwfdAfds=/206x116/top/smart/filters:strip_icc()/s2.glbimg.com/vTzgkIRvttN5dxztM0ecQqi_0g0=/0x0:699x392/267x150/i.glbimg.com/og/ig/infoglobo1/f/original/2017/07/27/garotinho.jpg"
    }
}
