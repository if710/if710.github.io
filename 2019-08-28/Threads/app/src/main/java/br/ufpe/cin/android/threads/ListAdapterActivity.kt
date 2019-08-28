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
    internal var task: AdicionarPessoaTask? = null

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
        task = AdicionarPessoaTask()
    }

    override fun onResume() {
        super.onResume()

        task?.execute(*Constants.maisPessoas)
    }

    override fun onPause() {
        super.onPause()
        task?.cancel(true)
    }
    /**/

    internal inner class AdicionarPessoaTask : AsyncTask<Pessoa, Pessoa, Void>() {

        override fun doInBackground(vararg pessoas: Pessoa): Void? {
            for (p in pessoas) {
                try {
                    Thread.sleep(500)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                publishProgress(p)
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Pessoa) {
            val a = lv_pessoas.adapter as ArrayAdapter<Pessoa>
            a.add(values[0])
        }
    }

}
