package br.ufpe.cin.android.datamanagement

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_memoria_interna.*
import java.io.*

class MemoriaInternaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memoria_interna)

        //O que acontece ao clicar em salvar
        botaoSalvar.setOnClickListener {
            //pega o texto digitado
            val txt = campoTexto.text.toString()
            try {
                //escreve no arquivo
                escreverArquivo(txt)
                //limpa caixa de texto
                campoTexto.setText("")
            } catch (e: FileNotFoundException) {
                Log.i("MemoriaInterna", e.message)
                e.printStackTrace()
            }
            //como é operação de IO, pode ter exceção
        }

        botaoLimpar.setOnClickListener {
            //limpa o conteúdo abaixo dos botões
            limparConteudo()
        }

        botaoLer.setOnClickListener {
            if (getFileStreamPath(arquivo).exists()) {
                try {
                    //limpa o conteúdo abaixo dos botões
                    limparConteudo()
                    //lê o conteúdo do arquivo linha a linha
                    lerArquivo()
                } catch (e: IOException) {
                    Log.i("MemoriaInterna", e.message)
                    e.printStackTrace()
                }

            } else {
                //toast
                Toast.makeText(applicationContext, "Arquivo inexistente", Toast.LENGTH_SHORT).show()
            }
        }

        botaoApagar.setOnClickListener {
            //apaga o arquivo da memória interna
            apagarArquivo()
            //limpa o conteúdo abaixo dos botões
            limparConteudo()
        }
    }

    @Throws(FileNotFoundException::class)
    private fun escreverArquivo(txt: String) {
        /*
        openFileOutput(arquivo, Context.MODE_APPEND).use {
            it.write(txt.toByteArray())
        }
         */
        //MODE_PRIVATE
        //MODE_APPEND
        //abrindo o arquivo para escrever e manter o que já tem lá (MODE_APPEND)
        val fos = openFileOutput(arquivo, Context.MODE_APPEND)
        //PrintWriter permite que a gente escreva linhas em um arquivo de texto, passando strings
        val pw = PrintWriter(BufferedWriter(OutputStreamWriter(fos)))
        //Escreve o que estiver na String txt e coloca uma quebra de linha após
        pw.println(txt)
        //fecha o arquivo
        pw.close()
        //Alternativa usando lambda expressions
        /*
        PrintWriter(BufferedWriter(OutputStreamWriter(fos))).apply {
            println(txt)
            close()
        }
        */
    }

    @Throws(IOException::class)
    private fun lerArquivo() {
        //abre o arquivo para leitura - bufferedreader
        val br = openFileInput(arquivo).bufferedReader()
        val lineList = mutableListOf<String>()
        br.useLines {
                lines -> lines.forEach {
            lineList.add(it)
        }
        }

        //Para cada linha faça
        lineList.forEach {
            //Criar novo objeto TextView
            val tv = TextView(this)
            //Definir tamanho do texto
            tv.textSize = 16f
            //Colocar neste objeto a string da linha
            tv.text = it
            //Adicionar textview na tela
            ll_conteudoArquivo.addView(tv)
        }
        //fechando o arquivo
        br.close()
    }

    private fun apagarArquivo() {
        //se o arquivo existe, apaga
        if (getFileStreamPath(arquivo).exists()) {
            deleteFile(arquivo)
        } else {
            Toast.makeText(applicationContext, "Arquivo inexistente", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limparConteudo() {
        //apaga todos os textviews que estiverem neste linear layout
        ll_conteudoArquivo.removeAllViews()
    }

    companion object {
        //Nome do arquivo
        private val arquivo = "teste.txt"
    }
}