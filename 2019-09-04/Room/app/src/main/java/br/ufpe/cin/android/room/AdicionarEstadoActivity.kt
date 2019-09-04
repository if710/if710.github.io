package br.ufpe.cin.android.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.alterar_estado.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AdicionarEstadoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alterar_estado)
        btnInserirEstado.setOnClickListener {
            val estado_UF = txtUF.text.toString()
            val estado_NOME = txtNome.text.toString()
            val estado = Estado(estado_UF, estado_NOME)
            doAsync {
                val db = EstadosDB.getDatabase(applicationContext)
                db.estadosDAO().inserirEstados(estado)
                uiThread { finish() }
            }
        }
    }
}
