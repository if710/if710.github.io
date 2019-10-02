package br.ufpe.cin.android.systemservices.phonesms

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import br.ufpe.cin.android.systemservices.R
import kotlinx.android.synthetic.main.activity_phone_dial_call.*

class PhoneCallerActivity : Activity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_dial_call)
        btnDial.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val numberToDial = numberToDial.text.toString()
        val data = Uri.parse("tel:$numberToDial")
        //precisa de permissao CALL_PHONE
        val i = Intent(Intent.ACTION_CALL, data)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(i)
        }
        else {
            //Toast.make...
        }
    }
}