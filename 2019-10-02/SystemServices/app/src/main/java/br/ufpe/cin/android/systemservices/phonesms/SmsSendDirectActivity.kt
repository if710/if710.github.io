package br.ufpe.cin.android.systemservices.phonesms

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.SmsManager
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import br.ufpe.cin.android.systemservices.R
import kotlinx.android.synthetic.main.activity_sms_sender.*

class SmsSendDirectActivity : Activity(), View.OnClickListener {
    internal var contatoEscolhido = false
    internal var telContato: String? = ""

    internal var enviadoReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (resultCode) {
                Activity.RESULT_OK -> Toast.makeText(baseContext, "SMS enviado", Toast.LENGTH_SHORT).show()
                SmsManager.RESULT_ERROR_GENERIC_FAILURE -> Toast.makeText(baseContext, "Falha geral", Toast.LENGTH_SHORT).show()
                SmsManager.RESULT_ERROR_NO_SERVICE -> Toast.makeText(baseContext, "Sem serviço", Toast.LENGTH_SHORT).show()
                SmsManager.RESULT_ERROR_NULL_PDU -> Toast.makeText(baseContext, "Null PDU", Toast.LENGTH_SHORT).show()
                SmsManager.RESULT_ERROR_RADIO_OFF -> Toast.makeText(baseContext, "Radio off", Toast.LENGTH_SHORT).show()
            }

            unregisterReceiver(this)
        }
    }

    internal var entregueReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (resultCode) {
                Activity.RESULT_OK -> Toast.makeText(baseContext, "SMS entregue", Toast.LENGTH_SHORT).show()
                Activity.RESULT_CANCELED -> Toast.makeText(baseContext, "SMS não foi entregue", Toast.LENGTH_SHORT).show()
            }
            unregisterReceiver(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sendSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED

        if (sendSMS) {
            setContentView(R.layout.activity_sms_sender)

            pickContact.setOnClickListener {
                val i = Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"))
                i.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE//apenas contatos com telefone
                startActivityForResult(i, PEGAR_CONTATO_REQ)
            }
            btnSend.setOnClickListener(this)
        } else {
            Toast.makeText(this, "Conceda permissões em settings", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
        btnSend.isEnabled = false
        telContato = ""
        contato.text = "Nenhum contato escolhido!"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == PEGAR_CONTATO_REQ) {
            if (resultCode == Activity.RESULT_OK) {
                val contactUri = data.data

                //pegar apenas o numero de telefone
                val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)

                //fazendo query direto na thread principal...
                val cursor = contentResolver.query(contactUri!!, projection,
                        null, null, null)
                cursor!!.moveToFirst()

                // pega o numero de telefone
                val column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                telContato = cursor.getString(column)
                //altera textview
                contato.text = telContato
                //habilita botao
                btnSend.isEnabled = true
            }
        }
    }

    override fun onClick(v: View) {
        val message = msgToSend.text.toString()
        registerReceiver(enviadoReceiver, IntentFilter(SENT_BROADCAST))
        registerReceiver(entregueReceiver, IntentFilter(DELIVERED_BROADCAST))

        val piEnvio = PendingIntent.getBroadcast(this, 0, Intent(SENT_BROADCAST), 0)
        val piEntrega = PendingIntent.getBroadcast(this, 0, Intent(DELIVERED_BROADCAST), 0)

        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(
                telContato, null,
                message,
                piEnvio,
                piEntrega)
    }

    companion object {
        internal val PEGAR_CONTATO_REQ = 1
        internal val SENT_BROADCAST = "SMS_ENVIADO"
        internal val DELIVERED_BROADCAST = "SMS_ENTREGUE"
    }
}