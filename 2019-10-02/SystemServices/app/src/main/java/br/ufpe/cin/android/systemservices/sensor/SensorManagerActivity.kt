package br.ufpe.cin.android.systemservices.sensor

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import br.ufpe.cin.android.systemservices.R
import kotlinx.android.synthetic.main.activity_sensor_manager.*

class SensorManagerActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_manager)
        btn1.setOnClickListener { startActivity(Intent(applicationContext, SensorListActivity::class.java)) }
        btn2.setOnClickListener { startActivity(Intent(applicationContext, GestureDetectorActivity::class.java)) }
        btn3.setOnClickListener { }
    }
}
