package br.ufpe.cin.android.datamanagement

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

//TODO restringir o construtor
class SqlEstadosHelperAnko(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "if710", null, 1) {

        internal val estadosBrasil: Array<String> = ctx.resources.getStringArray(R.array.estadosBrasil)

    //public static String DATABASE_TABLE = "estados";
    companion object {
        //Nome da tabela do Banco a ser usada
        val DATABASE_TABLE = "estados"
        //Versão atual do banco
        private val DB_VERSION = 1
        //colunas
        val STATE_CODE = "uf"
        val STATE_NAME = "name"
        val _ID = "_id"
        val columns = arrayOf(_ID, STATE_CODE, STATE_NAME)

        private var instance: SqlEstadosHelperAnko? = null

        @Synchronized
        fun getInstance(ctx: Context): SqlEstadosHelperAnko {
            if (instance == null) {
                instance = SqlEstadosHelperAnko(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(DATABASE_TABLE, true,
                _ID to INTEGER + PRIMARY_KEY,
                STATE_CODE to TEXT,
                STATE_NAME to TEXT)

        var i = 1
        val nomeEstado = "indefinido"
        for (siglaEstado in estadosBrasil) {
            db.insert(DATABASE_TABLE,
                    _ID to i,
                    STATE_CODE to siglaEstado,
                    STATE_NAME to nomeEstado)
            i++
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //nao estamos usando
    }

}
// Access property for Context
val Context.database: SqlEstadosHelperAnko
    get() = SqlEstadosHelperAnko.getInstance(getApplicationContext())
