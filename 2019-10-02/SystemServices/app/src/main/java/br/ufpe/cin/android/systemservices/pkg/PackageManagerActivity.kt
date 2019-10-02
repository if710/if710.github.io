package br.ufpe.cin.android.systemservices.pkg

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import br.ufpe.cin.android.systemservices.R
import kotlinx.android.synthetic.main.activity_package_manager.*

class PackageManagerActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_package_manager)
        btn1.setOnClickListener { startActivity(Intent(applicationContext, LauncherActivity::class.java)) }
        btn2.setOnClickListener { startActivity(Intent(applicationContext, PrefActivitiesActivity::class.java)) }
        btn3.setOnClickListener {
            val i = Intent(applicationContext, PackageManagerQueryActivity::class.java)
            i.putExtra(QUERY_KEY, GET_PKGS)
            startActivity(i)
        }
        btn4.setOnClickListener {
            val i = Intent(applicationContext, PackageManagerQueryActivity::class.java)
            i.putExtra(QUERY_KEY, GET_APPS)
            startActivity(i)
        }
        btn5.setOnClickListener {
            val i = Intent(applicationContext, PackageManagerQueryActivity::class.java)
            i.putExtra(QUERY_KEY, GET_BROADCASTS)
            startActivity(i)
        }
    }

    companion object {
        val QUERY_KEY = "PackageManagerQuery"
        val GET_PKGS = "getInstalledPackages"
        val GET_APPS = "getInstalledApplications"
        val GET_BROADCASTS = "queryBroadcastReceivers"
    }
}
