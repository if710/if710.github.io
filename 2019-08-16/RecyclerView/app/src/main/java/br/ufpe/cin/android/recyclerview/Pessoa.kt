package br.ufpe.cin.android.recyclerview

data class Pessoa (val nome: String, val login: String) {
    val email: String
        //get() = "$login@cin.ufpe.br"
    val site: String
    init {
        this.email = "$login@cin.ufpe.br"
        this.site = "http://www.cin.ufpe.br/~$login"
    }

    override fun toString(): String {
        return "Prof. $nome"
        //return "$nome  ( $login )"
    }
}