package br.ufpe.cin.android.systemservices.phonesms

import android.Manifest
import android.app.ListActivity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat

import java.util.ArrayList

class PhoneManagerActivity : ListActivity() {

    internal var data = ArrayList<String>()
    internal var adapter: ArrayAdapter<String>? = null
    internal var telephonyManager: TelephonyManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        listAdapter = adapter
    }

    override fun onStart() {
        super.onStart()
        val readPhoneState = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
        val accessCoarseLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        if (readPhoneState && accessCoarseLocation) {
            adapter?.add("Device ID: " + telephonyManager?.deviceId)
            adapter?.add("Call State: " + telephonyManager?.callState)
            adapter?.add("Device SW Version: " + telephonyManager?.deviceSoftwareVersion)
            adapter?.add("Network Operator: " + telephonyManager?.networkOperator)
            adapter?.add("Network Operator Name: " + telephonyManager?.networkOperatorName)
            adapter?.add("Network Country ISO: " + telephonyManager?.networkCountryIso)
            adapter?.add("Network Type: " + telephonyManager?.networkType)
            adapter?.add("Cell Location: " + telephonyManager?.cellLocation)
            adapter?.add("SIM Operator: " + telephonyManager?.simOperator)
            adapter?.add("SIM Serial Number: " + telephonyManager?.simSerialNumber)
            adapter?.add("SIM State: " + telephonyManager?.simState)
            adapter?.add("Voice Mail Number: " + telephonyManager?.voiceMailNumber)
            adapter?.add("Phone Type: " + telephonyManager?.phoneType)

            //adapter.add...
        } else {
            Toast.makeText(this, "Conceda permissões em settings", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
        adapter?.clear()
    }
}