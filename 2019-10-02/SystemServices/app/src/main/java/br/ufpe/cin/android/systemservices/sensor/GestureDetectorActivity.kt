package br.ufpe.cin.android.systemservices.sensor

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.GestureDetector
import android.view.MotionEvent
import br.ufpe.cin.android.systemservices.R
import kotlinx.android.synthetic.main.activity_gesture_detector.*

class GestureDetectorActivity : Activity(), GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener {

    private var gestureDetector: GestureDetector? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gesture_detector)

        //o primeiro this é pq GestureDetectorActivity é uma Activity (óbvio!)
        //o segundo é pq a gente precisa de um listener, no caso OnGestureListener
        gestureDetector = GestureDetector(this, this)

        //esse this é pq esta Activity implementa OnDoubleTapListener
        gestureDetector!!.setOnDoubleTapListener(this)

        //vamos monitorar longpress
        gestureDetector!!.setIsLongpressEnabled(true)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector!!.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    private fun formatSingleMotionEvent(event: String, me: MotionEvent): Spanned {
        val sb = StringBuilder()

        sb.append("<h2>")
        sb.append(event)
        sb.append("</h2>")

        sb.append("<p>")
        sb.append("<b>X:</b> ")
        sb.append(me.x)
        sb.append("</p>")
        sb.append("<p>")
        sb.append("<b>Y:</b> ")
        sb.append(me.y)
        sb.append("</p>")
        sb.append("<p>")
        sb.append("<b>Pressure:</b> ")
        sb.append(me.pressure)
        sb.append("</p>")
        //me.get...

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(sb.toString())
        }
    }

    private fun formatComplexMotionEvent(event: String, me1: MotionEvent, me2: MotionEvent, attr: String, attrX: Float, attrY: Float): Spanned {
        val sb = StringBuilder()
        sb.append("<h2>")
        sb.append(event)
        sb.append("</h2>")

        sb.append("<p>")
        sb.append("<b>X:</b> ")
        sb.append(me1.x)
        sb.append(" | ")
        sb.append("<b>Y:</b> ")
        sb.append(me1.y)
        sb.append("</p>")
        sb.append("<p>")
        sb.append("<b>Pressure:</b> ")
        sb.append(me1.pressure)
        sb.append("</p>")

        sb.append("<p>")
        sb.append("<b>X:</b> ")
        sb.append(me2.x)
        sb.append(" | ")
        sb.append("<b>Y:</b> ")
        sb.append(me2.y)
        sb.append("</p>")
        sb.append("<p>")
        sb.append("<b>Pressure:</b> ")
        sb.append(me2.pressure)
        sb.append("</p>")

        sb.append("<h2>")
        sb.append(attr)
        sb.append("</h2>")
        sb.append("<p>")
        sb.append("<b>X:</b> ")
        sb.append(attrX)
        sb.append(" | ")
        sb.append("<b>Y:</b> ")
        sb.append(attrY)
        sb.append("</p>")

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(sb.toString())
        }
    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        tvGesture.text = formatSingleMotionEvent("onSingleTapConfirmed", e)
        return false
    }


    override fun onDoubleTap(e: MotionEvent): Boolean {
        tvGesture.text = formatSingleMotionEvent("onDoubleTap", e)
        return false
    }

    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        tvGesture.text = formatSingleMotionEvent("onDoubleTapEvent", e)
        return false
    }

    override fun onDown(e: MotionEvent): Boolean {
        tvGesture.text = formatSingleMotionEvent("onDown", e)
        return false
    }

    override fun onShowPress(e: MotionEvent) {
        tvGesture.text = formatSingleMotionEvent("onShowPress", e)
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        tvGesture.text = formatSingleMotionEvent("onSingleTapUp", e)
        return false
    }

    override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
        tvGesture.text = formatComplexMotionEvent("onScroll", e1, e2, "Distance", distanceX, distanceY)
        return false
    }

    override fun onLongPress(e: MotionEvent) {
        tvGesture.text = formatSingleMotionEvent("onLongPress", e)
    }

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        tvGesture.text = formatComplexMotionEvent("onFling", e1, e2, "Velocity", velocityX, velocityY)
        return false
    }
}