package br.ufpe.cin.android.datamanagement

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context) {
    val PREFS_FILENAME = "br.ufpe.cin.android.prefs"
    val PREF_RECORDE = "recorde"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0);

    var recorde: Int
        get() = prefs.getInt(PREF_RECORDE, 0)
        set(value) = prefs.edit().putInt(PREF_RECORDE, value).apply()
}