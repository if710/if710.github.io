package br.ufpe.cin.android.aulajava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button botao = findViewById(R.id.botao);
        final TextView texto = findViewById(R.id.texto);

        botao.setOnClickListener(
                view -> texto.setText("Clicou")
        );
        botao.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        texto.setText("Clicou");
                    }
                }
        );
    }
}
