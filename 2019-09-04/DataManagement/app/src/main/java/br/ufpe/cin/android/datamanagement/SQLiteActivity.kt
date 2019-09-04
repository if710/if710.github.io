package br.ufpe.cin.android.datamanagement

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.CursorAdapter
import android.widget.SimpleCursorAdapter
import kotlinx.android.synthetic.main.activity_sqlite.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SQLiteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sqlite)

        //O que acontece ao clicar no botão Adicionar Estado
        btnAdicionaEstado.setOnClickListener {
            startActivity(Intent(getApplicationContext(), AdicionarEstadoActivity::class.java))
        }


        //Definindo um Adapter para a ListView
        // Este adapter envolve dados que vem de uma consulta ao banco
        val adapter = SimpleCursorAdapter(
            //contexto, como estamos acostumados
            this,
            //Layout XML de como se parecem os itens da lista
            R.layout.estado,
            null,
            //Mapeamento das colunas nos IDs do XML.
            // Os dois arrays a seguir devem ter o mesmo tamanho
            arrayOf<String>(SqlEstadosHelperAnko.STATE_NAME, SqlEstadosHelperAnko.STATE_CODE),
            intArrayOf(R.id.estadoNome, R.id.estadoUF),
            //Flags para determinar comportamento do adapter, pode deixar 0.
            0
        )
        //Objeto do tipo Cursor, com os dados retornados do banco.
        //Como ainda não fizemos nenhuma consulta, está nulo.
        //Seta o adapter. Como o Cursor é null, ainda não aparece nada na tela.
        listaEstados.setAdapter(adapter)

        //(object : AdapterView.OnItemClickListener {
        //    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {}
        // }
        listaEstados.setOnItemClickListener { parent, view, position, id ->
            //Obtendo um objeto Cursor a partir do Adapter
            val cursor = (parent.getAdapter() as SimpleCursorAdapter).getCursor()
            //Indo para a posição esperada de acordo com o clique
            cursor.moveToPosition(position)

            //Pegando o valor da UF a partir do Cursor
            val UF = cursor.getString(cursor.getColumnIndex(SqlEstadosHelperAnko.STATE_CODE))

            //Cria Intent explícito passando o código do estado, para uma tela de edição
            /*
            val i = Intent(applicationContext,EditarEstadoActivity::class.java)
            i.putExtra(SqlEstadosHelperAnko.STATE_CODE,UF)
            startActivity(i)
            */

            // Se for descomentada a linha abaixo, vai remover o estado quando clicar
            // Aí tem que comentar a parte de cima do startActivity
            doAsync {
                //Criando a consulta de remoção
                val where = SqlEstadosHelperAnko.STATE_CODE + " LIKE ?"
                //Pegando o argumento passado, para especificar qual estado será removido
                val whereArgs = arrayOf<String>(UF)
                //Roda uma remoção no banco, algo do tipo DELETE FROM estados WHERE `uf` LIKE 'PE'
                database.use {
                    delete(
                        SqlEstadosHelperAnko.DATABASE_TABLE,
                        where,
                        whereArgs
                    )
                }
                val c = doQuery()
                uiThread {
                    (listaEstados.getAdapter() as CursorAdapter).changeCursor(c)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        doAsync {
            val c = doQuery()
            uiThread {
                (listaEstados.getAdapter() as CursorAdapter).changeCursor(c)
            }
        }
    }

    override fun onDestroy() {
        //Fecha o Cursor
        (listaEstados.getAdapter() as SimpleCursorAdapter).getCursor().close()
        //Fecha conexão com banco de dados
        database.close()
        super.onDestroy()
    }

    internal fun doQuery(): Cursor {
        val result = database.readableDatabase.query(
            SqlEstadosHelperAnko.DATABASE_TABLE,
            SqlEstadosHelperAnko.columns,
            null,
            null,
            null,
            null,
            SqlEstadosHelperAnko.STATE_CODE
        )

        //força a query a ser executada
        //so executa quando fazemos algo que precisa do resultset
        result.getCount()

        return result
    }
}
