package br.ufpe.cin.android.threads

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import kotlinx.android.synthetic.main.async.*

class AsyncTaskActivity : Activity() {

    private val mDelay = 500
    internal var toasts = 0
    private val TAG = "ThreadingAsyncTask"

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.async)

        loadButton.setOnClickListener {
            LoadIconTask().execute(R.drawable.painter, R.drawable.ok, R.drawable.delete)
        }

        otherButton.setOnClickListener {
            toasts++
            contadorToasts!!.text = getString(R.string.contador_de_toasts) + toasts
            Toast.makeText(applicationContext, "Estou trabalhando... ($toasts)", Toast.LENGTH_SHORT).show()
        }

    }

    //<Entrada, Progresso, Resultado>
    internal inner class LoadIconTask : AsyncTask<Int, Int, Bitmap>() {

        //garantido de rodar na thread principal
        override fun onPreExecute() {
            progressBar.visibility = ProgressBar.VISIBLE
            progressBar.progress = 0
            toasts = 0
            contadorToasts.text = getString(R.string.contador_de_toasts) + toasts
        }

        protected override fun doInBackground(vararg resId: Int?): Bitmap {
            if (resId!=null) {
                val res = resId[0]
                if (res!=null) {
                    val tmp = BitmapFactory.decodeResource(resources, res)
                    // fazendo o migué!
                    for (i in 1..10) {
                        sleep()
                        publishProgress(i * 10, i * 11, i * 12)
                    }
                    return tmp
                }
            }
            return BitmapFactory.decodeResource(resources, R.drawable.painter)
        }

        protected override fun onProgressUpdate(vararg values: Int?) {
            if (values!=null && values[0]!=null) {
                val x = values[0]
                if (x!=null) {
                    progressBar.progress = x
                }
            }

        }

        override fun onPostExecute(result: Bitmap) {
            progressBar.visibility = ProgressBar.INVISIBLE
            imageView.setImageBitmap(result)
        }

        private fun sleep() {
            try {
                Thread.sleep(mDelay.toLong())
            } catch (e: InterruptedException) {
                Log.e(TAG, e.toString())
            }

        }
    }


}
