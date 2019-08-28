package br.ufpe.cin.android.threads

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_nothreads.setOnClickListener {
            val intent = Intent(this@MainActivity, SemThreads::class.java)
            startActivity(intent)
        }

        btn_thread_anr.setOnClickListener {
            val intent = Intent(this@MainActivity, ThreadANR::class.java)
            startActivity(intent)
        }

        btn_thread_simple.setOnClickListener {
            val intent = Intent(this@MainActivity, ThreadSimples::class.java)
            startActivity(intent)
        }

        btn_thread_viewpost.setOnClickListener {
            val intent = Intent(this@MainActivity, ThreadViewPost::class.java)
            startActivity(intent)
        }

        btn_thread_runonui.setOnClickListener {
            val intent = Intent(this@MainActivity, ThreadRunOnUI::class.java)
            startActivity(intent)
        }

        btn_thread_asynctask.setOnClickListener {
            val intent = Intent(this@MainActivity, AsyncTaskActivity::class.java)
            startActivity(intent)
        }

        btn_asynclist.setOnClickListener {
            val intent = Intent(this@MainActivity, ListAdapterActivity::class.java)
            startActivity(intent)
        }

        btn_async_countdown.setOnClickListener {
            val intent = Intent(this@MainActivity, CountdownActivity::class.java)
            startActivity(intent)
        }

        btn_postDelayed.setOnClickListener {
            val intent = Intent(this@MainActivity, PostDelayedActivity::class.java)
            startActivity(intent)
        }

        btn_recyclerView.setOnClickListener {
            val intent = Intent(this@MainActivity, RecyclerViewActivity::class.java)
            startActivity(intent)
        }

    }

}
