package br.ufpe.cin.android.systemservices.phonesms

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import br.ufpe.cin.android.systemservices.R
import kotlinx.android.synthetic.main.activity_sms_sender.*

class SmsSenderActivity : Activity() {
    internal var contatoEscolhido = false
    internal var telContato: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms_sender)

        pickContact.setOnClickListener {
            val i = Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"))
            i.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE//apenas contatos com telefone
            startActivityForResult(i, PEGAR_CONTATO_REQ)
        }

        btnSend.setOnClickListener {
            val message = msgToSend.text.toString()
            val data = Uri.parse("smsto:$telContato")
            val i = Intent(Intent.ACTION_SENDTO, data)
            i.putExtra("sms_body", message)
            startActivity(i)
        }
    }

    override fun onStart() {
        super.onStart()
        btnSend.isEnabled = true
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
                //Uri que aponta direto para um contato
                //content://contacts/1 -- que vai direto para um contato especifico
                val contactUri = data.data

                //pegar apenas o numero de telefone
                val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)

                //fazendo query direto na thread principal...
                val cursor = contentResolver.query(contactUri!!, projection, null, null, null)
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

    companion object {
        internal val PEGAR_CONTATO_REQ = 42
    }
}
