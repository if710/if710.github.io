package br.ufpe.cin.android.systemservices.power

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.PowerManager
import br.ufpe.cin.android.systemservices.R

class WakeLockActivity : Activity() {
    internal var powerManager: PowerManager? = null
    internal var wakeLock: PowerManager.WakeLock? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wake_lock)
        powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
    }

    override fun onResume() {
        super.onResume()
        wakeLock = powerManager?.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "app:MyWakelockTag")
        wakeLock?.acquire()

        /*
        *
        *
        * Flag Value	            CPU     Screen	Keyboard
          PARTIAL_WAKE_LOCK         On*	    Off     Off
          SCREEN_DIM_WAKE_LOCK      On	    Dim     Off
          SCREEN_BRIGHT_WAKE_LOCK   On	    Bright  Off
          FULL_WAKE_LOCK            On	    Bright  Bright
	    *
        * */


    }

    override fun onPause() {
        super.onPause()
        wakeLock?.release()
    }
}