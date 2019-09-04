package br.ufpe.cin.android.datamanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.alterar_estado.*
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AdicionarEstadoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alterar_estado)

        btnInserirEstado.setOnClickListener {
            //Roda a Task de Inserir Estado, passando o que foi digitado nos campos de texto como argumento
            val estadoUF = txtUF.text.toString()
            val estadoNOME = txtNome.text.toString()
            doAsync {
                database.use {
                    insert(SqlEstadosHelperAnko.DATABASE_TABLE,
                        SqlEstadosHelperAnko.STATE_CODE to estadoUF,
                        SqlEstadosHelperAnko.STATE_NAME to estadoNOME)
                }
                uiThread { finish() }
            }
        }
    }
}
