package br.ufpe.cin.android.constraintlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import androidx.annotation.StringRes
import kotlinx.android.synthetic.main.activity_widgets.*

private const val TAG = "ConstraintLayoutApp"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widgets)

        icon.setOnClickListener {
            log(R.string.icon_clicked)
        }
        button.setOnClickListener {
            //onClick(View v)
            _ ->
            log(R.string.button_clicked)
        }
        swytch.setOnCheckedChangeListener {
            _, isChecked ->
            /*
            if (isChecked) {
                  log(R.string.checkbox_checked)
              }
            else {
                  log(R.string.checkbox_unchecked)

             }
          */
            log(
                if(isChecked) R.string.switch_checked
                else R.string.switch_unchecked
            )
        }
        checkbox.setOnCheckedChangeListener {
            v, isChecked ->
            log(
                if(isChecked) R.string.checkbox_checked
                else R.string.checkbox_unchecked
            )
        }

        radioGroup.setOnCheckedChangeListener {
            v, checkedId ->
            log(
                when(checkedId) {
                    R.id.radioButton1 -> R.string.radiobutton1_checked
                    R.id.radioButton2 -> R.string.radiobutton2_checked
                    else -> R.string.radiobutton3_checked
                }
            )
        }

        seekbar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean) {
                    val msg = getString(R.string.seekbar_changed,progress)
                    log.text = msg
                    Log.d(TAG,msg)
                    //log(R.string.seekbar_changed)
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                    //ignorar
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                    //ignorar
                }

            }
        )


    }

    private fun log(@StringRes msg: Int) {
        log.setText(msg)
        Log.d(TAG, getString(msg))
    }
}
