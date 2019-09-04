package br.ufpe.cin.android.datamanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_prefs_edit.*
import org.jetbrains.anko.defaultSharedPreferences

class PrefsEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prefs_edit)
        val prefs = defaultSharedPreferences
        val username = prefs.getString(PrefsActivity.USERNAME, "nada");
        editTextUsername.setText(username)

        salvarUsername.setOnClickListener {
            //pega o que foi digitado
            val oQueFoiDigitado = editTextUsername.text.toString()
            //obtém objeto que permite manipular SharedPreferences
            val editor = prefs.edit()
            //Atualiza o valor associado com USERNAME
            editor.putString(PrefsActivity.USERNAME, oQueFoiDigitado)
            //Salva a alteração
            editor.commit()
            //Fecha a Activity
            finish()
        }
    }
}