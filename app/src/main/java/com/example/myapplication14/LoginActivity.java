package com.example.myapplication14;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText edtCedula, edtContrasena;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtCedula = findViewById(R.id.edtCedula);
        edtContrasena = findViewById(R.id.edtContrasena);
    }


    public void loginUsuario(View view) {
        String cedula = edtCedula.getText().toString();
        String contrasena = edtContrasena.getText().toString();


        if (cedula.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese cédula y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }


        DBHelper dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();


        String[] columns = {"cedula", "contrasena", "rol"};
        String selection = "cedula = ? AND contrasena = ?";
        String[] selectionArgs = {cedula, contrasena};
        Cursor cursor = db.query("Usuarios", columns, selection, selectionArgs, null, null, null);


        Log.d("LoginActivity", "Número de registros encontrados: " + cursor.getCount());

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            @SuppressLint("Range") String rol = cursor.getString(cursor.getColumnIndex("rol"));

            Log.d("LoginActivity", "Rol del usuario: " + rol);


            if (rol.equals("admin")) {
                Intent intent = new Intent(this, AdminActivity.class);
                startActivity(intent);
            } else if (rol.equals("Votante")) {
                Intent intent = new Intent(this, VotanteActivity.class);
                startActivity(intent);
            } else if (rol.equals("Proponedor")) {
                Intent intent = new Intent(this, ProponedorActivity.class);
                startActivity(intent);
            }
            cursor.close();
        } else {
            Toast.makeText(this, "Cédula o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
    }
}



