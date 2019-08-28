package br.ufpe.cin.android.lifecycle

import android.content.Intent
import android.content.Intent.*
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1.setOnClickListener {
            val i = Intent(applicationContext, LifecycleActivity::class.java)
            startActivity(i)
        }

        btn2.setOnClickListener {
            val i = Intent()
            i.action = ACTION_VIEW
            i.data = Uri.parse("http://www.cin.ufpe.br")
            i.addCategory(CATEGORY_DEFAULT)
            i.addCategory(CATEGORY_BROWSABLE)
            startActivity(i)
        }

        btn3.setOnClickListener {
            val i = Intent(ACTION_VIEW)
            i.data = Uri.parse("geo:0,0?q=Rua da Aurora")
            startActivity(i)
        }



        btn4.setOnClickListener {
            //startActivity(Intent(applicationContext, StartActResultActivity::class.java))
        }

        btnImplicit.setOnClickListener {
            val i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            //não são usados para resolver o intent
            i.putExtra(Intent.EXTRA_SUBJECT, assunto.text.toString())
            i.putExtra(Intent.EXTRA_TEXT, msg.text.toString())
            startActivity(i)
        }

        btnExplicit.setOnClickListener {
            /*
            val i = Intent(applicationContext, TxtActivity::class.java)
            i.putExtra(Intent.EXTRA_SUBJECT, assunto.text.toString())
            i.putExtra(Intent.EXTRA_TEXT, msg.text.toString())
            startActivity(i)
            */
        }
    }
}
