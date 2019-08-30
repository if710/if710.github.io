package br.ufpe.cin.android.datamanagement

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnMemoriaExterna.setOnClickListener {
            startActivity(Intent(applicationContext,MemoriaExternaActivity::class.java))
        }
        btnMemoriaInterna.setOnClickListener {
            startActivity(Intent(applicationContext,MemoriaInternaActivity::class.java))
        }
        btnPreferencesActivity.setOnClickListener {
            startActivity(Intent(applicationContext,PrefsActivity::class.java))
        }
        btnPreferencesMenuActivity.setOnClickListener {
            startActivity(Intent(applicationContext,PrefsMenuActivity::class.java))
        }
        btnSharedPreferences.setOnClickListener {
            startActivity(Intent(applicationContext,SharedPrefsActivity::class.java))
        }
        btnSQLite.setOnClickListener {
            //Atualizando o código de SQLite
            //startActivity(Intent(applicationContext,SQLiteActivity::class.java))
        }
    }
}
