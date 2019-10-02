package br.ufpe.cin.android.systemservices.sensor

import android.app.ListActivity
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import java.util.*

class SensorListActivity : ListActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        //assim como em LocationManager a gente pega um tipo de provider (GPS_PROVIDER)
        //com sensores, não há APIs dedicadas para sensores, usamos o SensorManager
        //os sensores são identificados por nomes (TYPE_LINEAR_ACCELERATION)

        //existem sensores e tipos de sensores
        //abaixo, estamos pedindo todos os sensores, de todos os tipos
        val allSensors = ArrayList(sensorManager.getSensorList(Sensor.TYPE_ALL))
        var sensors: MutableList<Sensor> = ArrayList()

        //removendo trigger sensors, que entregam apenas uma única leitura
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            for (s in allSensors) {
                val triggerSensor = s.type == Sensor.TYPE_SIGNIFICANT_MOTION ||
                        s.type == Sensor.TYPE_STEP_COUNTER ||
                        s.type == Sensor.TYPE_STEP_DETECTOR

                if (!triggerSensor) {
                    sensors.add(s)
                }
            }
        } else {
            sensors = allSensors
        }

        listAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, sensors)
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        val s = l.adapter.getItem(position) as Sensor
        //s.getPower();//power in mA used by this sensor while in use
        //s.getMaxDelay();//The max delay for this sensor in microseconds.
        //s.getMinDelay();//The min delay for this sensor in microseconds.

        Toast.makeText(this, s.name, Toast.LENGTH_SHORT).show()

        var i = Intent()
        if (isXYZ(s)) {
            i = Intent(this, SensorXYZActivity::class.java)
        } else {
            i = Intent(this, SensorSingleValueActivity::class.java)
        }
        //i.putExtra(SENSOR_ID,s.getId());
        i.putExtra(SENSOR_TYPE, s.type)
        i.putExtra(SENSOR_NAME, s.name)

        startActivity(i)
    }

    //separando sensores que levam em conta 3 eixos (acelerometro, gravidade, giroscopio...)
    //de sensores que usam apenas um valor (pressao, luz...)
    private fun isXYZ(s: Sensor): Boolean {
        when (s.type) {
            Sensor.TYPE_ACCELEROMETER, Sensor.TYPE_GRAVITY, Sensor.TYPE_GYROSCOPE, Sensor.TYPE_LINEAR_ACCELERATION, Sensor.TYPE_MAGNETIC_FIELD, Sensor.TYPE_ROTATION_VECTOR -> return true
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (s.type == Sensor.TYPE_GAME_ROTATION_VECTOR
                    || s.type == Sensor.TYPE_GYROSCOPE_UNCALIBRATED
                    || s.type == Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED) {
                return true
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (s.type == Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) {
                return true
            }
        }

        return false
    }

    companion object {
        val SENSOR_ID = "SENSOR_ID"
        val SENSOR_TYPE = "SENSOR_TYPE"
        val SENSOR_NAME = "SENSOR_NAME"
    }
}
