package com.example.myapplication14;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

public class AdminActivity extends AppCompatActivity {

    private ListView listViewPropuestas;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        listViewPropuestas = findViewById(R.id.listViewPropuestas);
        dbHelper = new DBHelper(this);

        mostrarPropuestasPendientes();
    }

    private void mostrarPropuestasPendientes() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_PROPUESTAS + " WHERE aprobacion = 0", null);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NOMBRE_PROPUESTA));
            @SuppressLint("Range") String lugar = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_LUGAR));
            String propuesta = nombre + " - " + lugar;
            adapter.add(propuesta);
        }

        listViewPropuestas.setAdapter(adapter);

        listViewPropuestas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parentView, View view, int position, long id) {
                aprobarPropuesta(id);
            }
        });
    }

    private void aprobarPropuesta(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_APROBACION, 1);

        int result = db.update(DBHelper.TABLE_PROPUESTAS, values, "_id = ?", new String[]{String.valueOf(id)});

        if (result > 0) {
            Toast.makeText(this, "Propuesta aprobada", Toast.LENGTH_SHORT).show();
            mostrarPropuestasPendientes();
        } else {
            Toast.makeText(this, "Error al aprobar la propuesta", Toast.LENGTH_SHORT).show();
        }
    }
}
