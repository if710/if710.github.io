package br.ufpe.cin.android.systemservices.notification

import android.app.Activity
import android.os.Bundle
import br.ufpe.cin.android.systemservices.R

class NotificationSubActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_sub)
    }
}
