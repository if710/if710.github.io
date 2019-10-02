package br.ufpe.cin.android.systemservices.sensor

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import br.ufpe.cin.android.systemservices.R
import kotlinx.android.synthetic.main.activity_sensor_single_value.*

class SensorSingleValueActivity : Activity(), SensorEventListener {
    internal var sensorManager: SensorManager? = null
    internal var sensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        setContentView(R.layout.activity_sensor_single_value)

        val i = intent
        val sName = i.getStringExtra(SensorListActivity.SENSOR_NAME)
        if (sName == null) {
            finish()
        }

        val sType = i.getIntExtra(SensorListActivity.SENSOR_TYPE, Sensor.TYPE_ALL)

        val listSensors = sensorManager?.getSensorList(sType)
        if (listSensors != null) {
            for (s in listSensors) {
                if (s.name == sName) {
                    sensor = s
                }
            }
        }

        if (sensor == null) {
            finish()
        } else {
            sensorName.text = sensor!!.name
        }
    }

    override fun onStart() {
        super.onStart()
        //SensorManager.SENSOR_DELAY_UI
        //SensorManager.SENSOR_DELAY_GAME
        //SensorManager.SENSOR_DELAY_FASTEST
        sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onStop() {
        //deixar de registrar é prejudicial, pois continua consumindo eventos do sensor
        //ignora activity lifecycle, então é similar a um memory leak
        //battery drain!
        sensorManager?.unregisterListener(this)
        super.onStop()
    }

    //recebe objeto SensorEvent representando leitura do Sensor
    override fun onSensorChanged(event: SensorEvent) {
        val values = arrayOfNulls<Float>(3)
        values[0] = event.values[0]
        updateValues(values)
        //event.accuracy
        //event.sensor
        //event.timestamp

        //Don't block the onSensorChanged() method
        /*
        Sensor data can change at a high rate, which means the system may call the
        onSensorChanged(SensorEvent) method quite often. As a best practice, you
        should do as little as possible within the onSensorChanged(SensorEvent) method
        so you don't block it. If your application requires you to do any data filtering
        or reduction of sensor data, you should perform that work outside of the
        onSensorChanged(SensorEvent) method.
        */
    }

    //mudança na precisão das leituras do sensor
    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        //nao estamos utilizando...
    }

    private fun updateValues(values: Array<Float?>) {
        singleValue.text = values[0].toString()
    }

}
