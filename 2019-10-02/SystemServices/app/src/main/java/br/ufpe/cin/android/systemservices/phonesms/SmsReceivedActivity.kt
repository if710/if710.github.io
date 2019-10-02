package br.ufpe.cin.android.systemservices.phonesms

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import br.ufpe.cin.android.systemservices.R
import kotlinx.android.synthetic.main.activity_sms_received.*

class SmsReceivedActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms_received)

        val i = intent
        val msgFromExtra = i.getStringExtra("msgFrom")
        val msgBodyExtra = i.getStringExtra("msgBody")
        if (msgFromExtra != null && msgBodyExtra != null) {
            msgFrom.text = msgFromExtra
            msgBody.text = msgBodyExtra
        } else {
            Toast.makeText(this, "Intent vazio", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
