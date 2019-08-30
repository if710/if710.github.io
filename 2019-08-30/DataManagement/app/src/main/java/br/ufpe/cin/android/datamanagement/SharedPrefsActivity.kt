package br.ufpe.cin.android.datamanagement

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_shared_prefs.*
import kotlin.random.Random

class SharedPrefsActivity : AppCompatActivity() {

    private var maiorPontuacao = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_prefs)
        val preferences = getPreferences(Context.MODE_PRIVATE)
        maiorPontuacao = preferences.getInt(RECORDE, 0)
        high_score_text.text = maiorPontuacao.toString()

        play_button.setOnClickListener {
            val numero_aleatorio = Random.nextInt(1000)
            game_score_text.text = numero_aleatorio.toString()
            if (numero_aleatorio > maiorPontuacao) {
                high_score_text.text = game_score_text.text
                maiorPontuacao = numero_aleatorio
                val editor = preferences.edit()
                editor.putInt(RECORDE, maiorPontuacao)
                editor.commit()
            }
        }
        reset_button.setOnClickListener {
            game_score_text.text = ""
            high_score_text.text = "0"
            maiorPontuacao = 0
            val editor = preferences.edit()
            editor.remove(RECORDE)
            editor.commit()

        }
    }

    companion object {
        private val RECORDE = "recorde"
    }
}