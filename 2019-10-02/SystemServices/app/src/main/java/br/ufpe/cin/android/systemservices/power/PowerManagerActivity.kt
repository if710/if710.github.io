package br.ufpe.cin.android.systemservices.power

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import br.ufpe.cin.android.systemservices.R
import kotlinx.android.synthetic.main.activity_power_manager.*

class PowerManagerActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_power_manager)
        btn_keepScreen1.setOnClickListener {
            startActivity(Intent(this@PowerManagerActivity, KeepScreenOn1Activity::class.java))
        }

        btn_keepScreen2.setOnClickListener {
            startActivity(Intent(this@PowerManagerActivity, KeepScreenOn2Activity::class.java))
        }

        btn_wakelock.setOnClickListener {
            startActivity(Intent(this@PowerManagerActivity, WakeLockActivity::class.java))
        }
    }
}
