package br.ufpe.cin.android.recyclerview

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_listview.*

class CustomAdapterActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //listAdapter = pessoaAdapter
        setContentView(R.layout.activity_listview)
        listaElementos.adapter =
                PessoaAdapter(this,Constants.maisPessoas)

    }

    internal inner class PessoaAdapter(val c:Context, var pessoas:Array<Pessoa>) : BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val v = LayoutInflater.from(c).inflate(
                R.layout.item_custom_adapter,
                parent,
                false
            )

            /*
            val view : View
            if (convertView != null) {
                view = convertView
            }
            else {
                view = LayoutInflater.from(c).inflate(
                    R.layout.item_custom_adapter,
                    parent,
                    false
                )
            }
            */

            val textView_Nome = v.findViewById<TextView>(R.id.nome)
            val textView_Login = v.findViewById<TextView>(R.id.login)
            val textView_Site = v.findViewById<TextView>(R.id.site)
            //val p = getItem(position) as Pessoa
            val p = pessoas[position]
            textView_Nome.text = p.nome
            textView_Login.text = "("+p.login+")"
            textView_Site.text = p.site
            return v
        }

        override fun getItem(position: Int): Any {
            return pessoas[position]
        }

        override fun getItemId(id: Int): Long {
            return id.toLong()
        }

        override fun getCount() = pessoas.size



    }


}