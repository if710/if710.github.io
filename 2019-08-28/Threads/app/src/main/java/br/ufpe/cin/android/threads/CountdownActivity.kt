package br.ufpe.cin.android.threads

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_countdown.*

class CountdownActivity : Activity() {

    internal var pontoPartida = 5
    internal var pontoChegada = 0
    internal var delay = 1000L
    internal var tarefa: ContagemRegressivaTask? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countdown)

        btn_iniciarCountdown.setOnClickListener {
            //contagemRegressiva(5)
            tarefa = ContagemRegressivaTask()
            tarefa?.execute(pontoPartida, pontoChegada)
        }

        btn_cancelarCountdown.setOnClickListener { tarefa?.cancel(true) }
    }

    internal fun contagemRegressiva(passos: Int) {
        for (i in passos downTo 0) {
            try {
                Thread.sleep(delay)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            valorContagem.text = Integer.toString(i)
        }
    }

    internal inner class ContagemRegressivaTask : AsyncTask<Int, String, Unit>() {

        override fun onPreExecute() {
            valorContagem.text = "vai começar!"
        }

        override fun doInBackground(vararg integers: Int?): Unit {
            //seria interessante checar se tem dois argumentos - pegar valores de integers, etc...
            if (integers!=null) {
                var valorAtual = 5
                val fim = 0
                while (valorAtual >= fim) {
                    Log.d("IF1001-Threads", "Valor atual: $valorAtual - Alvo: $fim")
                    if (!isCancelled) {
                        SystemClock.sleep(delay)
                        //atualizar o TextView com o valor atual
                        //não pode dar setText... (thread separada)
                        publishProgress(valorAtual.toString())
                        valorAtual = valorAtual - 1
                    }
                    else {
                        break
                    }
                }
            }
        }

        override fun onProgressUpdate(vararg values: String) {
            //garantido de rodar na thread principal
            valorContagem.text = values[0]
        }

        override fun onPostExecute(aVoid: Unit) {
            Toast.makeText(applicationContext, "Terminou!", Toast.LENGTH_SHORT).show()
        }
    }

}