package br.ufpe.cin.android.room

import androidx.room.*

@Dao
interface EstadosDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserirEstados(vararg estados:Estado)
    @Update
    fun atualizarEstados(vararg estados:Estado)
    @Delete
    fun removerEstados(vararg estados:Estado)
    @Query("SELECT * FROM estados")
    fun todosEstados() : Array<Estado>
    @Query("SELECT * FROM estados WHERE nome LIKE :q")
    fun buscaEstadoPeloNome(q : String) : List<Estado>
    @Query("SELECT * FROM estados WHERE uf LIKE :q")
    fun buscaEstadoPelaSigla(q : String) : List<Estado>
}