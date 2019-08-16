package br.ufpe.cin.android.recyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_ListView.setOnClickListener{
            startActivity(Intent(applicationContext,ListViewActivity::class.java))
        }
        btn_Spinner.setOnClickListener{
            startActivity(Intent(applicationContext,SpinnerActivity::class.java))
        }
        btn_CustomAdapter.setOnClickListener{
            startActivity(Intent(applicationContext,CustomAdapterActivity::class.java))
        }
        btn_ViewHolder.setOnClickListener{
            startActivity(Intent(applicationContext,ViewHolderActivity::class.java))
        }
        btn_RecyclerViewCardView.setOnClickListener{
            startActivity(Intent(applicationContext,RecyclerViewActivity::class.java))
        }
        btn_RecyclerViewAnimation.setOnClickListener{
            startActivity(Intent(applicationContext,RecyclerViewAnimationActivity::class.java))
        }

    }
}
