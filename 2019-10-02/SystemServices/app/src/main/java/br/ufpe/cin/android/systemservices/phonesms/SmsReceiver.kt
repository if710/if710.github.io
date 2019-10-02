package br.ufpe.cin.android.systemservices.phonesms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log

class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val rawMsgs = intent.extras!!.get("pdus") as Array<Any>

        for (raw in rawMsgs) {
            //int activePhone = TelephonyManager.getDefault().getCurrentPhoneType();
            //String format = (PHONE_TYPE_CDMA == activePhone) ? SmsConstants.FORMAT_3GPP2 : SmsConstants.FORMAT_3GPP;
            val msg = SmsMessage.createFromPdu(raw as ByteArray)

            Log.w("SMS:" + msg.originatingAddress, msg.messageBody)
            if (msg.messageBody.toUpperCase().contains("IF710")) {
                val i = Intent(context, SmsReceivedActivity::class.java)
                i.putExtra("msgFrom", msg.originatingAddress)
                i.putExtra("msgBody", msg.messageBody)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(i)

                abortBroadcast()
            }
        }
    }
}
