package br.ufpe.cin.android.recyclerview

import android.app.Activity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_listview.*
import java.util.*

class ListViewActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listview)

        //k -> v
        //Map<Integer, String> mapa = new...
        //mapa.put(0,"alguma string);
        val opcoes = hashMapOf(
                0 to Constants.lista,
                1 to Constants.listaLonga,
                2 to Constants.palavrasSimilares
        )

        btn_Troca.setOnClickListener {
            val i = Random().nextInt(opcoes.size)
            val opcao = opcoes[i]
            if (opcao != null) {
                val adapter = ArrayAdapter<String>(
                    //this,
                    applicationContext,
                    android.R.layout.simple_list_item_1,
                    opcao
                )
                //listaElementos.setAdapter(adapter)
                listaElementos.adapter = adapter
            }
        }

        listaElementos.setOnItemClickListener {
            parent, view, posicao, _ ->
                val texto = parent.adapter.getItem(posicao)
                val txt = (view as TextView).text
                Toast.makeText(
                    this,
                    //texto.toString(),
                    txt,
                    Toast.LENGTH_SHORT
                ).show()



        }

    }
}
