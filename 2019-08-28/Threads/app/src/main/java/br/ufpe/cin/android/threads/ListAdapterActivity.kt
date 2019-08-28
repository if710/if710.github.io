package br.ufpe.cin.android.threads

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.os.PersistableBundle
import android.os.SystemClock
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_list_adapter.*

import java.util.ArrayList

class ListAdapterActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_adapter)

        val pessoas = ArrayList<Pessoa>()
        val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                pessoas
        )
        lv_pessoas.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()
    }
    /**/

}
