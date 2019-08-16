package br.ufpe.cin.android.recyclerview

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import kotlinx.android.synthetic.main.activity_recycler_view_animation.*
import kotlinx.android.synthetic.main.item_cardview.view.*
import java.util.*

class RecyclerViewAnimationActivity : Activity() {
    private var listaPessoas: SortedList<Pessoa>? = null
    private var adapter: PessoaAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_animation)

        //diferente de ListView e GridView, RecyclerView não sabe nada sobre como organizar elementos
        //esta tarefa eh delegada para o LayoutManager, possibilitando diferentes abordagens
        //neste caso, temos um layout linear para estruturar elementos verticalmente...
        listRecyclerView.layoutManager = LinearLayoutManager(this)
        //outras opcoes incluem GridLayoutManager, por ex.
        //tambem pode ser implementado um LayoutManager customizado

        adapter = PessoaAdapter()
        //definindo o adapter (semelhante a listadapter...)
        listRecyclerView.adapter = adapter
        listaPessoas = SortedList(Pessoa::class.java, metodosCallback)
        btn_Adiciona.setOnClickListener {
            val i = Random().nextInt(Constants.maisPessoas.size)
            listaPessoas?.add(Constants.maisPessoas[i])
        }


    }

    private inner class PessoaAdapter : RecyclerView.Adapter<CardChangeHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardChangeHolder {
            //cria, configura e retorna um ViewHolder para uma linha da lista
            //parent é o ViewGroup que contem as views, usado pelo layout inflater
            //viewType é para o caso de ter múltiplos tipos de Views, em um RecyclerView
            val v = layoutInflater.inflate(R.layout.item_cardview, parent, false)
            return CardChangeHolder(v)
        }

        override fun onBindViewHolder(holder: CardChangeHolder, position: Int) {
            //responsavel por atualizar ViewHolder com dados de um elemento na posição 'position'
            holder.bindModel(listaPessoas!!.get(position))
        }

        override fun getItemCount(): Int {
            //total de elementos
            return listaPessoas!!.size()
        }
    }

    internal class CardChangeHolder//poderia tambem passar algum objeto aqui construido no adapter, para nao adicionar atributos
    (row: View) : RecyclerView.ViewHolder(row), View.OnClickListener {
        val nome: TextView = row.nome
        val login: TextView = row.login
        val icone: ImageView = row.icone
        var site: Uri? = null

        init {
            //definindo listener para linha/card inteiro
            //poderia definir click listener para cada view (nome, login...)
            row.setOnClickListener(this)
        }

        fun bindModel(p: Pessoa) {
            nome.text = p.nome
            login.text = p.login
            site = Uri.parse(p.site)

            if (p.login.equals("lmt")) {
                icone.setImageResource(R.drawable.ok);
            }
            else {
                icone.setImageResource(R.drawable.delete)
            }
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            Toast.makeText(v.context, "Clicou no item da posição: $position", Toast.LENGTH_SHORT).show()
        }
    }

    internal var metodosCallback: SortedList.Callback<Pessoa> = object : SortedList.Callback<Pessoa>() {
        override fun compare(o1: Pessoa, o2: Pessoa): Int {
            //return o1.getLogin().compareTo(o2.getLogin());
            return o1.nome.compareTo(o2.nome)
        }

        override fun onInserted(position: Int, count: Int) {
            adapter!!.notifyItemRangeInserted(position, count)
        }

        override fun onRemoved(position: Int, count: Int) {
            adapter!!.notifyItemRangeRemoved(position, count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            adapter!!.notifyItemMoved(fromPosition, toPosition)
        }

        override fun onChanged(position: Int, count: Int) {
            adapter!!.notifyItemRangeChanged(position, count)
        }

        override fun areContentsTheSame(oldItem: Pessoa, newItem: Pessoa): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }

        override fun areItemsTheSame(item1: Pessoa, item2: Pessoa): Boolean {
            return compare(item1, item2) == 0
        }
    }
}
