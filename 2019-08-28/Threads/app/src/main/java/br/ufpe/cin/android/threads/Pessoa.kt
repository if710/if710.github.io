package br.ufpe.cin.android.threads

data class Pessoa (val nome: String, var login: String) {
    var email: String? = null
    var site: String? = null

    init {
        this.email = "$login@cin.ufpe.br"
        this.site = "http://www.cin.ufpe.br/~$login"
    }

    override fun toString(): String {
        return nome
        //return nome + '(' + login + ')';
    }
}
