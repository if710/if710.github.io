package br.ufpe.cin.android.threads

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.threads.*

class ThreadANR : Activity() {

    private var mBitmap: Bitmap? = null

    //aumentando o delay a gente gera ANR
    private val mDelay = 25000
    internal var toasts = 0

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.threads)

        loadButton.setOnClickListener { loadIcon() }

        otherButton.setOnClickListener {
            toasts++
            contadorToasts.text = getString(R.string.contador_de_toasts) + toasts
            Toast.makeText(applicationContext, "Estou trabalhando... ($toasts)", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadIcon() {
        Thread(Runnable {
            try {
                Thread.sleep(mDelay.toLong())
            } catch (e: InterruptedException) {
                Log.e(TAG, e.toString())
            }

            mBitmap = BitmapFactory.decodeResource(resources,
                    R.drawable.painter)

            // vai dar pau...
            imageView.setImageBitmap(mBitmap)
        }).start()
    }

    companion object {

        private val TAG = "SimpleThreading"
    }


}
