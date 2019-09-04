package br.ufpe.cin.android.datamanagement

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_memoria_externa.*
import java.io.*

class MemoriaExternaActivity : AppCompatActivity() {

    //Nome do Arquivo
    private val arquivo = "melhorDoNordeste.jpg"
    private val TAG = "DadosMemoriaExterna"


    /* Checa se memoria externa esta disponivel para leitura e escrita */
    val isExternalStorageWritable: Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return if (Environment.MEDIA_MOUNTED == state) {
                true
            } else false
        }

    /* Checa se memoria externa esta disponivel pelo menos para leitura */
    val isExternalStorageReadable: Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return if (Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state) {
                true
            } else false
        }

    //Checa se tem permissão
    fun podeEscrever(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) === PackageManager.PERMISSION_GRANTED
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memoria_externa)

        //Checa se tem permissão, se não tiver, vai pedir
        if (podeEscrever()) {
            //O que acontece ao clicar no botão copiar
            botaoCopiar.setOnClickListener {
                //Checa se a memória externa está disponível para escrita
                if (isExternalStorageWritable) {
                    //Cria um objeto relativo ao arquivo que queremos salvar
                    val f = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), arquivo)
                    //Checa se este arquivo já existe
                    if (!f.exists()) {
                        //se não existir, copia para memória
                        copiarImagemNaMemoria(f)
                        Toast.makeText(applicationContext, "Copiando...", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(applicationContext, "Arquivo já existe...", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(applicationContext, "Memória externa nao disponivel!", Toast.LENGTH_SHORT).show()
                }
            }

            //O que acontece ao clicar no botão ler
            botaoLer.setOnClickListener {
                //checa se memória externa está disponível
                if (isExternalStorageReadable) {
                    //Cria objeto referente ao arquivo
                    val f = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), arquivo)
                    //Se o arquivo existir...
                    if (f.exists()) {
                        //Seta o widget ImageView para conter a imagem que está salva
                        imagem.setImageURI(Uri.parse("file://" + f.absolutePath))
                    } else {
                        Toast.makeText(applicationContext, "Arquivo não existe!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(applicationContext, "Memória externa nao disponivel!", Toast.LENGTH_SHORT).show()
                }
            }

            //O que acontece ao clicar no botão limpar
            botaoLimpar.setOnClickListener { limparConteudo() }

            //O que acontece ao clicar no botão apagar
            botaoApagar.setOnClickListener {
                //Checa se a memória externa está disponível para escrita
                if (isExternalStorageWritable) {
                    //Cria um objeto relativo ao arquivo que queremos apagar
                    val f = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), arquivo)
                    //Checa se o arquivo existe
                    if (f.exists()) {
                        //Deleta o arquivo
                        f.delete()
                        Toast.makeText(applicationContext, "Apagando...", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(applicationContext, "Arquivo inexistente!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(applicationContext, "Memória externa nao disponivel!", Toast.LENGTH_SHORT).show()
                }
                //TODO faz diferença?
                limparConteudo()
            }

        } else {
            //Pedindo permissão para escrever na memória externa
            ActivityCompat.requestPermissions(this, STORAGE_PERMISSIONS, WRITE_EXTERNAL_STORAGE_REQUEST)
        }

    }

    private fun limparConteudo() {
        //Apaga o conteúdo do ImageView (outras opções abaixo)
        imagem.setImageResource(android.R.color.transparent)
        //imagem.setImageResource(0);
        //imagem.setImageDrawable(null);
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            WRITE_EXTERNAL_STORAGE_REQUEST ->
                //Se não conceder a permissão, encerra a Activity
                if (!podeEscrever()) {
                    Toast.makeText(this, "Sem permissão para escrita", Toast.LENGTH_SHORT).show()
                    finish()
                }
        }
    }

    private fun copiarImagemNaMemoria(f: File) {
        try {
            //arquivo destino
            val os = BufferedOutputStream(FileOutputStream(f))
            //arquivo "origem" a ser copiado
            val `is` = BufferedInputStream(resources.openRawResource(R.raw.sport))
            copiar(`is`, os)
        } catch (e: FileNotFoundException) {
            Log.e(TAG, "FileNotFoundException " + e.message)
        }

    }

    private fun copiar(`is`: InputStream, os: OutputStream) {
        val buf = ByteArray(1024)
        var numBytes: Int
        try {
            numBytes = `is`.read(buf)
            while (-1 != numBytes) {
                os.write(buf, 0, numBytes)
                numBytes = `is`.read(buf)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                `is`.close()
                os.close()
            } catch (e: IOException) {
                Log.e(TAG, "IOException " + e.message)

            }

        }
    }

    companion object {
        //Como vai escrever na memória externa, precisa de permissão
        private val STORAGE_PERMISSIONS = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        private val WRITE_EXTERNAL_STORAGE_REQUEST = 710
    }
}
