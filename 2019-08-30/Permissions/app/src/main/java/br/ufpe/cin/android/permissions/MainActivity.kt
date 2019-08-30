package br.ufpe.cin.android.permissions

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private val INITIAL_PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CONTACTS)
        private val CAMERA_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private val CONTACTS_PERMISSIONS = arrayOf(Manifest.permission.READ_CONTACTS)
        private val LOCATION_PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        private val ON_CREATE_REQUEST = 710
        private val CAMERA_REQUEST = ON_CREATE_REQUEST + 1
        private val CONTACTS_REQUEST = ON_CREATE_REQUEST + 2
        private val LOCATION_REQUEST = ON_CREATE_REQUEST + 3
    }

    //Inclua checagens e solicitação de permissão para armazenamento.
    // no caso de Internet, coloque uma checagem de permissão para habilitar ou desabilitar o botão

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_location.setOnClickListener {
            if (podeLocation()) {
                acessarLocation()
            } else {
                ActivityCompat.requestPermissions(this@MainActivity, LOCATION_PERMISSIONS, LOCATION_REQUEST)
            }
        }

        btn_camera.setOnClickListener {
            if (podeCamera()) {
                acessarCamera()
            } else {
                ActivityCompat.requestPermissions(this@MainActivity, CAMERA_PERMISSIONS, CAMERA_REQUEST)
            }
        }

        btn_contacts.setOnClickListener {
            if (podeContacts()) {
                acessarContacts()
            } else {
                ActivityCompat.requestPermissions(this@MainActivity, CONTACTS_PERMISSIONS, CONTACTS_REQUEST)
            }
        }

        if (!podeLocation() || !podeContacts()) {
            ActivityCompat.requestPermissions(this, INITIAL_PERMISSIONS, ON_CREATE_REQUEST)
        }
    }

    override fun onResume() {
        super.onResume()
        atualizarInfo()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        atualizarInfo()

        when (requestCode) {
            CAMERA_REQUEST ->
                if (podeCamera()) {
                    acessarCamera()
                } else {
                    sem_permissao()
                }

            CONTACTS_REQUEST ->
                if (podeContacts()) {
                    acessarContacts()
                } else {
                    sem_permissao()
                }

            LOCATION_REQUEST ->
                if (podeLocation()) {
                    acessarLocation()
                } else {
                    sem_permissao()
                }
        }
    }

    private fun atualizarInfo() {
        btn_location.text = resources.getText(R.string.btn_txt_location).toString() + ": " + podeLocation().toString()
        btn_camera.text = resources.getText(R.string.btn_txt_camera).toString() + ": " + podeCamera().toString()
        btn_internet.text = resources.getText(R.string.btn_txt_internet).toString() + ": " + hasPermission(Manifest.permission.INTERNET).toString()
        btn_contacts.text = resources.getText(R.string.btn_txt_contacts).toString() + ": " + podeContacts().toString()
        btn_storage.text = resources.getText(R.string.btn_txt_storage).toString() + ": " + hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).toString()
    }

    private fun podeLocation(): Boolean {
        return hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun podeCamera(): Boolean {
        return hasPermission(Manifest.permission.CAMERA)
    }

    private fun podeContacts(): Boolean {
        return hasPermission(Manifest.permission.READ_CONTACTS)
    }

    private fun hasPermission(perm: String): Boolean {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, perm)
    }

    private fun sem_permissao() {
        Toast.makeText(this, R.string.msg_sem_permissao, Toast.LENGTH_LONG).show()
    }

    private fun acessarCamera() {
        Toast.makeText(this, R.string.msg_camera, Toast.LENGTH_SHORT).show()
    }

    private fun acessarContacts() {
        Toast.makeText(this, R.string.msg_contacts, Toast.LENGTH_SHORT).show()
    }

    private fun acessarLocation() {
        Toast.makeText(this, R.string.msg_location, Toast.LENGTH_SHORT).show()
    }
}
