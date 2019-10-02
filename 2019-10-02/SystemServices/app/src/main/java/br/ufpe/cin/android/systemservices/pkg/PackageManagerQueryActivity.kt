package br.ufpe.cin.android.systemservices.pkg

import android.app.ListActivity
import android.content.Intent
import android.content.pm.PackageManager.*
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast

class PackageManagerQueryActivity : ListActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val i = intent
        val query = i.getStringExtra(PackageManagerActivity.QUERY_KEY)
        val packageManager = getPackageManager()

        if (query == PackageManagerActivity.GET_PKGS) {
            val info = packageManager.getInstalledPackages(GET_ACTIVITIES)
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, info)
            listAdapter = adapter
        } else if (query == PackageManagerActivity.GET_APPS) {
            val info = packageManager.getInstalledApplications(GET_META_DATA)
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, info)
            listAdapter = adapter
        } else if (query == PackageManagerActivity.GET_BROADCASTS) {
            var intent = Intent(Intent.ACTION_BOOT_COMPLETED)
            //intent = Intent(Intent.ACTION_BATTERY_LOW)
            val info = packageManager.queryBroadcastReceivers(intent, MATCH_ALL)
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, info)
            listAdapter = adapter
        } else {
            Toast.makeText(this, "Ainda a implementar...", Toast.LENGTH_SHORT).show()
        }

    }
}
