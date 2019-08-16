package br.ufpe.cin.android.recyclerview

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_cardview.view.*

class PessoaAdapter (private val pessoas: Array<Pessoa>, private val c : Context) : RecyclerView.Adapter<PessoaAdapter.ViewHolder>() {

    override fun getItemCount(): Int = pessoas.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(c).inflate(R.layout.item_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p = pessoas[position]
        holder.nome?.text = p.nome
        holder.login?.text = p.login
        holder.site = Uri.parse(p.site)
        if (p.login == "lmt") {
            holder.icone?.setImageResource(R.drawable.ok)
        }
        else {
            holder.icone?.setImageResource(R.drawable.delete)
        }
    }

    class ViewHolder (item : View) : RecyclerView.ViewHolder(item), View.OnClickListener {
        val nome = item.nome
        val login = item.login
        val icone = item.icone
        var site: Uri? = null

        init {
            item.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            Toast.makeText(v.context, "Clicou no item da posição: $position", Toast.LENGTH_SHORT).show()
        }
    }
}