package br.ufpe.cin.android.room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    var estados: Array<Estado>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //O que acontece ao clicar no botão Adicionar Estado
        btnAdicionaEstado.setOnClickListener {
            startActivity(Intent(getApplicationContext(), AdicionarEstadoActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()
        val db = EstadosDB.getDatabase(this)
        doAsync {
            val estados = db.estadosDAO().todosEstados()
            uiThread {
                val adapter = ArrayAdapter<Estado> (
                    applicationContext,
                    R.layout.estado,
                    estados
                )
                listaEstados.setAdapter(adapter)
            }
        }

    }
}
