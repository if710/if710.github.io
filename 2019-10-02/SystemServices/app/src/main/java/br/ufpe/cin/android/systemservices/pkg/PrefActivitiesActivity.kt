package br.ufpe.cin.android.systemservices.pkg

import android.app.ListActivity
import android.content.ComponentName
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

import java.util.ArrayList

class PrefActivitiesActivity : ListActivity() {
    //CustomAdapter adapter;
    internal var filters = ArrayList<IntentFilter>()
    internal var names = ArrayList<ComponentName>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        packageManager.getPreferredActivities(filters, names, null)
        listAdapter = IntentFilterAdapter()

    }

    internal inner class IntentFilterAdapter : ArrayAdapter<IntentFilter>(this@PrefActivitiesActivity, android.R.layout.simple_list_item_2, android.R.id.text1, filters) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val row = super.getView(position, convertView, parent)
            val filter = row.findViewById<View>(android.R.id.text1) as TextView
            val name = row.findViewById<View>(android.R.id.text2) as TextView

            filter.text = buildTitle(getItem(position))
            name.text = names[position].className

            return row
        }

        fun buildTitle(filter: IntentFilter?): String {
            val buf = StringBuilder()
            var first = true

            if (filter!!.countActions() > 0) {
                for (action in `in`(filter.actionsIterator())) {
                    if (first) {
                        first = false
                    } else {
                        buf.append('/')
                    }

                    buf.append(action.replace("android.intent.action.".toRegex(), ""))
                }
            }

            if (filter.countDataTypes() > 0) {
                first = true

                for (type in `in`(filter.typesIterator())) {
                    if (first) {
                        buf.append(" : ")
                        first = false
                    } else {
                        buf.append('|')
                    }

                    buf.append(type)
                }
            }

            if (filter.countDataSchemes() > 0) {
                buf.append(" : ")
                buf.append(filter.getDataScheme(0))

                if (filter.countDataSchemes() > 1) {
                    buf.append(" (other schemes)")
                }
            }

            if (filter.countDataPaths() > 0) {
                buf.append(" : ")
                buf.append(filter.getDataPath(0))

                if (filter.countDataPaths() > 1) {
                    buf.append(" (other paths)")
                }
            }

            return buf.toString()
        }
    }

    companion object {

        // from http://stackoverflow.com/a/8555153/115145

        fun <T> `in`(iterator: Iterator<T>): Iterable<T> {
            class SingleUseIterable : Iterable<T> {
                private var used = false

                override fun iterator(): Iterator<T> {
                    if (used) {
                        throw IllegalStateException("Already invoked")
                    }
                    used = true
                    return iterator
                }
            }
            return SingleUseIterable()
        }
    }
}