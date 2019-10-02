package br.ufpe.cin.android.systemservices.pkg

import android.app.ListActivity
import android.content.ComponentName
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView

import java.util.Collections

import br.ufpe.cin.android.systemservices.R


class LauncherActivity : ListActivity() {
    internal var pm: PackageManager? = null
    internal var adapter: CustomAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //pegando o manager
        pm = packageManager

        //criando intent que identifica activities 'launchable'
        val i = Intent(Intent.ACTION_MAIN, null)
        i.addCategory(Intent.CATEGORY_LAUNCHER)

        //pega todas as activities que casam com o intent fornecido - launchers
        val launcherActivities = pm?.queryIntentActivities(i, 0)

        //ordena
        Collections.sort(launcherActivities, ResolveInfo.DisplayNameComparator(pm))

        //usa um custom adapter para definir o conteudo dos itens da lista
        adapter = CustomAdapter(pm, launcherActivities)
        listAdapter = adapter
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        //ResolveInfo rInfoAlt = (ResolveInfo) l.getAdapter().getItem(position);
        val resolveInfo = adapter?.getItem(position)
        val activityInfo = resolveInfo!!.activityInfo
        val cName = ComponentName(activityInfo.applicationInfo.packageName,
                activityInfo.name)

        val i = Intent(Intent.ACTION_MAIN)
        i.addCategory(Intent.CATEGORY_LAUNCHER)
        //inicia novo task
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        i.component = cName
        startActivity(i)
    }

    internal inner class CustomAdapter(pm: PackageManager?, apps: List<ResolveInfo>?) : ArrayAdapter<ResolveInfo>(this@LauncherActivity, R.layout.app, apps) {
        private val packageManager: PackageManager? = pm

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var convertView = convertView
            if (convertView == null) {
                convertView = newView(parent)
            }

            bindView(position, convertView)

            return convertView
        }

        private fun newView(parent: ViewGroup): View {
            return layoutInflater.inflate(R.layout.app, parent, false)
        }

        private fun bindView(position: Int, row: View) {
            val label = row.findViewById<View>(R.id.appName) as TextView
            label.text = getItem(position)!!.loadLabel(packageManager)
            val icon = row.findViewById<View>(R.id.icon) as ImageView
            icon.setImageDrawable(getItem(position)!!.loadIcon(packageManager))
        }
    }
}