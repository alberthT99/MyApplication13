package com.example.myapplication14;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ResultadosActivity extends AppCompatActivity {

    private TextView tvResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);


        tvResultado = findViewById(R.id.tvResultado);



        String resultado = "Propuesta: Mejoramiento del Parque\nCantidad de votos: 45";
        tvResultado.setText(resultado);
    }


    public void cerrarSesion(View view) {

        Intent intent = new Intent(ResultadosActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
