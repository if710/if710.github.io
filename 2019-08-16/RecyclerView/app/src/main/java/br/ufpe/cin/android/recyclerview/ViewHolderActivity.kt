package br.ufpe.cin.android.recyclerview

import android.app.ListActivity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class ViewHolderActivity : ListActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listAdapter = PessoaAdapter(this, Constants.maisPessoas)
    }


    internal inner class PessoaAdapter(private val context: Context, private val pessoas: Array<Pessoa>) : BaseAdapter() {

        override fun getCount(): Int {
            return pessoas.size
        }

        override fun getItem(position: Int): Any {
            return pessoas[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val v: View
            val holder: ViewHolder

            if (convertView == null) {
                //Não está reusando, então precisa inflar o layout
                v = LayoutInflater.from(context).inflate(R.layout.item_custom_adapter, parent, false)
                //Inicializa novo ViewHolder, para armazenar referências aos IDs
                holder = ViewHolder(v)
                //"Pendurando" o ViewHolder na View, permitindo acesso uma vez que tenhamos este objeto
                v.tag = holder
            } else {
                //já existe uma View a ser reutilizada
                v = convertView
                //Basta recuperar o objeto ViewHolder associado com a Tag da View reutilizada
                holder = v.tag as ViewHolder
            }

            val p = getItem(position) as Pessoa
            //Setando valores por meio do holder
            holder.nome?.text = p.nome
            holder.login!!.text = p.email
            holder.site!!.text = p.site

            return v
        }
    }

}