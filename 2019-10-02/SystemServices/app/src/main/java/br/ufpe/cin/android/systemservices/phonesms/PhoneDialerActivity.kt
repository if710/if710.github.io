package br.ufpe.cin.android.systemservices.phonesms

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import br.ufpe.cin.android.systemservices.R
import kotlinx.android.synthetic.main.activity_phone_dial_call.*

class PhoneDialerActivity : Activity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_dial_call)
        btnDial.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val numberToDial = numberToDial.text.toString()
        val data = Uri.parse("tel:$numberToDial")
        val i = Intent(Intent.ACTION_DIAL, data)
        startActivity(i)
    }
}
