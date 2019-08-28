package br.ufpe.cin.android.threads

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class PostDelayedActivity : Activity(), Runnable {

    private var mainView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_delayed)
        mainView = findViewById(android.R.id.content)
    }

    override fun onStart() {
        super.onStart()
        run()
    }

    override fun onStop() {
        mainView!!.removeCallbacks(this)
        super.onStop()
    }


    override fun run() {
        Toast.makeText(this@PostDelayedActivity, "vou aparecer de novo...", Toast.LENGTH_SHORT).show()
        mainView!!.postDelayed(this, INTERVALO.toLong())
    }

    companion object {
        private val INTERVALO = 3500
    }
}
