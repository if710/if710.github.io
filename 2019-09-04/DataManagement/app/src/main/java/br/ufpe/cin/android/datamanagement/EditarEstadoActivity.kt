package br.ufpe.cin.android.datamanagement

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.alterar_estado.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class EditarEstadoActivity : AppCompatActivity() {

    val codigoEstado: String = intent.getStringExtra(SqlEstadosHelperAnko.STATE_CODE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alterar_estado)

        //Roda uma Task para pegar os dados associados ao estado a ser editado
        doAsync {
            val selection = SqlEstadosHelperAnko.STATE_CODE + " = ?"
            val selectionArgs = arrayOf(codigoEstado)
            val result = database.readableDatabase
                .query(
                    SqlEstadosHelperAnko.DATABASE_TABLE,
                    SqlEstadosHelperAnko.columns,
                    selection,
                    selectionArgs, null, null, null
                )

            uiThread {
                if (result.moveToFirst()) {
                    //Atualiza os campos de texto para os valores de código e estado
                    txtUF.setText(result.getString(result.getColumnIndex(SqlEstadosHelperAnko.STATE_CODE)))
                    txtNome.setText(result.getString(result.getColumnIndex(SqlEstadosHelperAnko.STATE_NAME)))
                    //Associa um listener para o botão de editar
                    btnInserirEstado.setOnClickListener {
                        doAsync {
                            //Para editar, temos que definir qual código de estado queremos digitar
                            val selection = SqlEstadosHelperAnko.STATE_CODE + " = ?"
                            //Perceba que neste caso, estamos usando a String de atributo
                            // se usarmos o que foi digitado, teremos um problema (como visto na aula)
                            val selectionArgs = arrayOf(codigoEstado)

                            //Cria objeto com novos valores para UF e Nome do estado
                            val cv = ContentValues()
                            cv.put(SqlEstadosHelperAnko.STATE_CODE, txtUF.text.toString())
                            cv.put(SqlEstadosHelperAnko.STATE_NAME, txtNome.text.toString())

                            //Roda a atualização no banco
                            //UPDATE estados SET uf='PE', nome='Pernambuco Imortal' WHERE uf='PE'
                            database.writableDatabase.update(
                                SqlEstadosHelperAnko.DATABASE_TABLE,
                                cv,
                                selection,
                                selectionArgs
                            )
                            uiThread { finish() }
                        }
                    }
                } else {
                    //Em caso de problemas, encerra a Activity
                    toast("Erro na consulta")
                    finish()
                }
            }

        }
    }
}
