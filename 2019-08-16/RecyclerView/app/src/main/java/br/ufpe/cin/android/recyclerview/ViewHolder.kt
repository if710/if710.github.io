package br.ufpe.cin.android.recyclerview

import android.view.View
import android.widget.TextView

internal class ViewHolder(row: View) {
    var nome: TextView? = null
    var login: TextView? = null
    var site: TextView? = null

    init {
        this.nome = row.findViewById(R.id.nome)
        this.login = row.findViewById(R.id.login)
        this.site = row.findViewById(R.id.site)
    }
}