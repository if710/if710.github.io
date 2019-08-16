package br.ufpe.cin.android.recyclerview

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_recycler_view.*

class RecyclerViewActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        //listRecyclerView.setHasFixedSize(true)

        //Diferente de ListView e GridView, RecyclerView não sabe nada sobre como organizar elementos.
        //Esta tarefa é delegada para o LayoutManager, possibilitando diferentes abordagens.
        //Neste caso, temos um layout linear para estruturar elementos verticalmente...
        listRecyclerView.layoutManager = LinearLayoutManager(this)
        //Outras opções incluem GridLayoutManager, por exemplo, como veremos em outra Activity
        //Também pode ser implementado um LayoutManager customizado.

        //Definindo o adapter - aqui não tem muita diferença de ListView
        listRecyclerView.adapter = PessoaAdapter(Constants.maisPessoas, this)

        //ItemDecoration permite adicionar dividers
        //Só é suportado a partir de targetSDKversion 22+
        listRecyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

    }
}
