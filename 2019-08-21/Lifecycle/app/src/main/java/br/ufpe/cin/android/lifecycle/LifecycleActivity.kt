package br.ufpe.cin.android.lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_lifecycle.*
import org.jetbrains.anko.alert

class LifecycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle)
        //val teste = "Leopoldo"
        //teste.loucura()

        textoDigitado.text = savedInstanceState?.getString("oQueFoiDigitado")
        lifecycleLog.text = savedInstanceState?.getString("lifeCycleLog")

        atualizaLifecycle("onCreate()")
        botaoAdicionaTexto.setOnClickListener {
            val oQueFoiDigitado = campoTexto.text
            if (oQueFoiDigitado.isEmpty()) {
                toast("Digite algo, por favor")
            }
            else {
                textoDigitado.text = oQueFoiDigitado
            }
        }

        botaoDialog.setOnClickListener {
            alert ("Alguma mensagem") {
                title = "Titulo"
                positiveButton("OK!") {
                    toast("clicou em beleza!")
                }
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("oQueFoiDigitado",textoDigitado.text.toString())
        outState.putString("lifeCycleLog",lifecycleLog.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        atualizaLifecycle("onStart()")
    }

    override fun onResume() {
        super.onResume()
        atualizaLifecycle("onResume()")
    }

    override fun onRestart() {
        super.onRestart()
        atualizaLifecycle("onRestart()")
    }

    override fun onPause() {
        atualizaLifecycle("onPause()")
        super.onPause()
    }

    override fun onStop() {
        atualizaLifecycle("onStop()")
        super.onStop()
    }

    override fun onDestroy() {
        atualizaLifecycle("onDestroy()")
        // Não faça isso -> super.onCreate(null)
        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun atualizaLifecycle(msg: String) {
        val m = lifecycleLog.text.toString()
        Log.d("LifecycleActivity",msg)
        lifecycleLog.text = "$msg \n $m"
    }

    fun String.loucura() {
        //this
        //aospdoaispdoaisd
    }
    fun Any.toast(msg:String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }
}
