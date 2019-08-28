package br.ufpe.cin.android.threads

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.threads.*

class SemThreads : Activity() {
    private var mBitmap: Bitmap? = null
    private val mDelay = 3000
    internal var toasts = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.threads)

        loadButton.setOnClickListener {
            loadIcon()
            if (null != mBitmap) {
                imageView.setImageBitmap(mBitmap)
            }
        }

        otherButton.setOnClickListener {
            toasts++
            contadorToasts.text = getString(R.string.contador_de_toasts) + toasts
            Toast.makeText(applicationContext, "Estou trabalhando... ($toasts)", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadIcon() {
        try {
            Thread.sleep(mDelay.toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        mBitmap = BitmapFactory.decodeResource(resources, R.drawable.painter)
    }


}
