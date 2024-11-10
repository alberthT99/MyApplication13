package com.example.myapplication14;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProponedorActivity extends AppCompatActivity {

    private EditText edtNombrePropuesta, edtLugar, edtLocalidad, edtValor, edtCoordenadas, edtFechaInicio, edtFechaFin;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proponedor);

        edtNombrePropuesta = findViewById(R.id.edtNombrePropuesta);
        edtLugar = findViewById(R.id.edtLugar);
        edtLocalidad = findViewById(R.id.edtLocalidad);
        edtValor = findViewById(R.id.edtValor);
        edtCoordenadas = findViewById(R.id.edtCoordenadas);
        edtFechaInicio = findViewById(R.id.edtFechaInicio);
        edtFechaFin = findViewById(R.id.edtFechaFin);

        dbHelper = new DBHelper(this);
    }


    public void onGuardarPropuesta(View view) {
        String nombre = edtNombrePropuesta.getText().toString();
        String lugar = edtLugar.getText().toString();
        String localidad = edtLocalidad.getText().toString();
        String valor = edtValor.getText().toString();
        String coordenadas = edtCoordenadas.getText().toString();
        String fechaInicio = edtFechaInicio.getText().toString();
        String fechaFin = edtFechaFin.getText().toString();


        if (nombre.isEmpty() || lugar.isEmpty() || localidad.isEmpty() || valor.isEmpty() ||
                coordenadas.isEmpty() || fechaInicio.isEmpty() || fechaFin.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }


        SQLiteDatabase db = dbHelper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NOMBRE_PROPUESTA, nombre);
        values.put(DBHelper.COLUMN_LUGAR, lugar);
        values.put(DBHelper.COLUMN_LOCALIDAD_PROPUESTA, localidad);
        values.put(DBHelper.COLUMN_VALOR, valor);
        values.put(DBHelper.COLUMN_COORDENADAS, coordenadas);
        values.put(DBHelper.COLUMN_FECHA_INICIO, fechaInicio);
        values.put(DBHelper.COLUMN_FECHA_FIN, fechaFin);
        values.put(DBHelper.COLUMN_CANTIDAD, 0);  // Valor inicial de cantidad de votos
        values.put(DBHelper.COLUMN_APROBACION, 0); // Valor inicial de aprobación


        long result = db.insert(DBHelper.TABLE_PROPUESTAS, null, values);

        if (result != -1) {
            Toast.makeText(this, "Propuesta guardada", Toast.LENGTH_SHORT).show();
            edtNombrePropuesta.setText("");
            edtLugar.setText("");
            edtLocalidad.setText("");
            edtValor.setText("");
            edtCoordenadas.setText("");
            edtFechaInicio.setText("");
            edtFechaFin.setText("");
        } else {
            Toast.makeText(this, "Error al guardar la propuesta", Toast.LENGTH_SHORT).show();
        }
    }
}

