package br.ufpe.cin.android.systemservices.phonesms

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import br.ufpe.cin.android.systemservices.R
import kotlinx.android.synthetic.main.activity_phone_sms.*

class PhoneSmsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_sms)
        btn1.setOnClickListener { startActivity(Intent(applicationContext, PhoneDialerActivity::class.java)) }
        btn2.setOnClickListener { startActivity(Intent(applicationContext, PhoneCallerActivity::class.java)) }
        btn3.setOnClickListener { startActivity(Intent(applicationContext, PhoneManagerActivity::class.java)) }
        btn4.setOnClickListener { startActivity(Intent(applicationContext, SmsSenderActivity::class.java)) }
        btn5.setOnClickListener { startActivity(Intent(applicationContext, SmsSendDirectActivity::class.java)) }
        btn6.setOnClickListener { startActivity(Intent(applicationContext, SmsReceivedActivity::class.java)) }
    }
}
