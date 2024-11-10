package com.example.myapplication14;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class VotanteActivity extends AppCompatActivity {

    private ListView listViewPropuestas;
    private DBHelper dbHelper;
    private String localidadUsuario;
    private long propuestaSeleccionadaId = -1;  // Inicializar con -1 para indicar que no se ha seleccionado ninguna propuesta
    private Button btnVotar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votante);

        listViewPropuestas = findViewById(R.id.listViewPropuestas);
        btnVotar = findViewById(R.id.btnVotar);
        dbHelper = new DBHelper(this);


        localidadUsuario = "Engativa"; // Esto debería obtenerse desde la sesión del usuario

        mostrarPropuestas();


        btnVotar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (propuestaSeleccionadaId != -1) {
                    votarPorPropuesta(propuestaSeleccionadaId);
                } else {
                    Toast.makeText(VotanteActivity.this, "Por favor selecciona una propuesta primero", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void mostrarPropuestas() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_PROPUESTAS + " WHERE localidad = ?", new String[]{localidadUsuario});


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NOMBRE_PROPUESTA));
            @SuppressLint("Range") String lugar = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_LUGAR));
            @SuppressLint("Range") String valor = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_VALOR));
            String propuesta = nombre + " - " + lugar + " - " + valor;
            adapter.add(propuesta);
        }

        listViewPropuestas.setAdapter(adapter);


        listViewPropuestas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parentView, View view, int position, long id) {

                propuestaSeleccionadaId = id;
                Toast.makeText(VotanteActivity.this, "Propuesta seleccionada", Toast.LENGTH_SHORT).show();
            }
        });


        listViewPropuestas.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    private void votarPorPropuesta(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        db.execSQL("UPDATE " + DBHelper.TABLE_PROPUESTAS + " SET " +
                        DBHelper.COLUMN_CANTIDAD + " = " + DBHelper.COLUMN_CANTIDAD + " + 1 WHERE _id = ?",
                new Object[]{id});

        Toast.makeText(this, "Voto registrado", Toast.LENGTH_SHORT).show();
        mostrarResultados();
    }

    private void mostrarResultados() {
        Intent intent = new Intent(this, ResultadosActivity.class);
        startActivity(intent);
    }
}

