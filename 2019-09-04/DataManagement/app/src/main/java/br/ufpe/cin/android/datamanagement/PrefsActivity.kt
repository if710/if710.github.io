package br.ufpe.cin.android.datamanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_prefs.*
import org.jetbrains.anko.defaultSharedPreferences

class PrefsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prefs)

        edit_pref_button.setOnClickListener {
            startActivity(
                Intent(
                    this@PrefsActivity,
                    PrefsEditActivity::class.java)
            )
        }
        check_pref_button.setOnClickListener {
            startActivity(
                Intent(
                    this@PrefsActivity,
                    PrefsMenuActivity::class.java)
            )
        }
    }

    override fun onResume() {
        super.onResume()
        val prefs = defaultSharedPreferences
        //Obtém o valor para a preference de nome de usuário
        val user_name = prefs.getString(USERNAME, "nada escolhido...")
        textoUsername.text = user_name
    }

    companion object {
        val USERNAME = "uname"
    }
}