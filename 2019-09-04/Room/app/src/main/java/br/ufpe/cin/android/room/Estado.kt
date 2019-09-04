package br.ufpe.cin.android.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="estados")
data class Estado (
    @PrimaryKey var uf:String,
    var nome:String
) {
    override fun toString(): String {
        return nome
    }
}