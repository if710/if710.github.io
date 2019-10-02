package br.ufpe.cin.android.broadcastreceiver

import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsMessage
import android.telephony.TelephonyManager
import android.telephony.TelephonyManager.PHONE_TYPE_CDMA
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_dyn_rec.*

class SmsActivity : Activity() {

    internal var smsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Toast.makeText(context, "Chegou um SMS", Toast.LENGTH_SHORT).show()
            //val activePhone = TelephonyManager.getDefault().getCurrentPhoneType()
            //val format = (PHONE_TYPE_CDMA == activePhone) ? SmsConstants.FORMAT_3GPP2 : SmsConstants.FORMAT_3GPP
            val rawMsgs = intent.extras!!.get("pdus") as Array<Any>
            for (raw in rawMsgs) {
                val msg = SmsMessage.createFromPdu(raw as ByteArray)
                if (msg.messageBody.toUpperCase().contains("ANDROID")) {
                    Toast.makeText(context, "Tem algo que nos interessa...", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dyn_rec)
        enviarBroadcastSta.setOnClickListener { sendBroadcast(Intent(MainActivity.STA_BROADCAST_ACTION)) }
        enviarBroadcastDyn.setOnClickListener { sendBroadcast(Intent(DynRecActivity.DYN_BROADCAST_ACTION)) }
        if (podeSMS()) {
            Toast.makeText(this, "Aguardando SMS...", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(this, SMS_PERMISSIONS, SMS_REQUEST)
        }
    }

    fun podeSMS(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            SMS_REQUEST -> if (!podeSMS()) {
                Toast.makeText(this, "Sem permissão para SMS", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(smsReceiver, IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION))
        Toast.makeText(this, "Registrando...", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        unregisterReceiver(smsReceiver)
        Toast.makeText(this, "Removendo registro...", Toast.LENGTH_SHORT).show()
        super.onStop()
    }

    companion object {
        private val SMS_PERMISSIONS = arrayOf(Manifest.permission.RECEIVE_SMS)
        private val SMS_REQUEST = 710
    }
}
