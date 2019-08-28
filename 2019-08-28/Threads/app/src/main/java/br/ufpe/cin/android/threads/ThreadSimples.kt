/*
 * Quando botão é apertado, a exceção android.view.ViewRootImpl$CalledFromWrongThreadException:
 * é lançada. Apenas a thread de UI, que criou a view, pode mexer nas suas views.
 */

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
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ThreadSimples : Activity() {

    private var mBitmap: Bitmap? = null
    private val mDelay = 5000
    internal var toasts = 0

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.threads)

        loadButton.setOnClickListener {
            loadIconIncorreto()
            //loadIconAsync()
        }

        otherButton.setOnClickListener {
            toasts++
            contadorToasts.text = getString(R.string.contador_de_toasts) + toasts
            Toast.makeText(applicationContext, "Estou trabalhando... ($toasts)", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadIconIncorreto() {

        Thread(Runnable {
            try {
                Thread.sleep(mDelay.toLong())
            } catch (e: InterruptedException) {
                Log.e(TAG, e.toString())
            }

            mBitmap = BitmapFactory.decodeResource(resources,
                    R.drawable.painter)

            imageView.setImageBitmap(mBitmap)
        }).start()

    }

    private fun loadIconAsync() {
        doAsync {
            try {
                Thread.sleep(mDelay.toLong())
            } catch (e: InterruptedException) {
                Log.e(TAG, e.toString())
            }

            mBitmap = BitmapFactory.decodeResource(resources,
                R.drawable.painter)

            uiThread { imageView.setImageBitmap(mBitmap) }
        }
    }


    companion object {
        private val TAG = "SimpleThreading"
    }

    /*
        android.view.ViewRootImpl$CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views.
         at android.view.ViewRootImpl.checkThread(ViewRootImpl.java:6094)
         at android.view.ViewRootImpl.requestLayout(ViewRootImpl.java:824)
         at android.view.View.requestLayout(View.java:16431)
         at android.view.View.requestLayout(View.java:16431)
         at android.view.View.requestLayout(View.java:16431)
         at android.view.View.requestLayout(View.java:16431)
         at android.widget.ImageView.setImageDrawable(ImageView.java:424)
         at android.widget.ImageView.setImageBitmap(ImageView.java:439)
         at br.ufpe.cin.if1001.threads.ThreadSimples$3.run(ThreadSimples.java:67)
         at java.lang.Thread.run(Thread.java:841)
 */
}
