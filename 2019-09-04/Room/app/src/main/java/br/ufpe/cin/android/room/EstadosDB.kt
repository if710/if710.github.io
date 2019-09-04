package br.ufpe.cin.android.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities= arrayOf(Estado::class), version=1)
abstract class EstadosDB : RoomDatabase() {
    abstract fun estadosDAO() : EstadosDAO
    companion object {
        private var INSTANCE : EstadosDB? = null
        fun getDatabase(ctx : Context) : EstadosDB {
            if (INSTANCE == null) {
                synchronized(EstadosDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        ctx.applicationContext,
                        EstadosDB::class.java,
                        "estados.db"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}