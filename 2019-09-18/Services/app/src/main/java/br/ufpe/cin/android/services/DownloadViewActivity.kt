package br.ufpe.cin.android.services
import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_download_view.*

import java.io.File

class DownloadViewActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download_view)
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            val root = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
            val imageFile = File(root, Uri.parse(DownloadActivity.downloadLink).lastPathSegment)
            if (imageFile.exists()) {
                imagemDownload.setImageURI(Uri.parse("file://" + imageFile.absolutePath))
            } else {
                Toast.makeText(this, "Arquivo nao existe", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Armazenamento externo nao esta montado...", Toast.LENGTH_SHORT).show()
        }
    }
}
