package com.example.myapplication14;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.database.Cursor;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;

public class RegistroActivity extends AppCompatActivity {

    private EditText edtCedula, edtNombre, edtContrasena, edtConfirmarContrasena;
    private Spinner spLocalidad, spRol;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        edtCedula = findViewById(R.id.edtCedula);
        edtNombre = findViewById(R.id.edtNombre);
        edtContrasena = findViewById(R.id.edtContrasena);
        edtConfirmarContrasena = findViewById(R.id.edtConfirmarContrasena);
        spLocalidad = findViewById(R.id.spLocalidad);
        spRol = findViewById(R.id.spRol);

        ArrayAdapter<CharSequence> adapterLocalidad = ArrayAdapter.createFromResource(this, R.array.localidades, android.R.layout.simple_spinner_item);
        adapterLocalidad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLocalidad.setAdapter(adapterLocalidad);

        ArrayAdapter<CharSequence> adapterRol = ArrayAdapter.createFromResource(this, R.array.roles, android.R.layout.simple_spinner_item);
        adapterRol.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRol.setAdapter(adapterRol);
    }


    public void registrarUsuario(View view) {

        String cedula = edtCedula.getText().toString();
        String nombre = edtNombre.getText().toString();
        String contrasena = edtContrasena.getText().toString();
        String confirmarContrasena = edtConfirmarContrasena.getText().toString();

        if (cedula.isEmpty() || nombre.isEmpty() || contrasena.isEmpty() || confirmarContrasena.isEmpty()) {
            Toast.makeText(this, "Por favor, llene todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!contrasena.equals(confirmarContrasena)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }


        DBHelper dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();


        boolean cedulaExistente = verificarCedulaExistente(cedula);
        if (cedulaExistente) {
            Toast.makeText(this, "La cédula ya está registrada", Toast.LENGTH_SHORT).show();
            return;
        }


        ContentValues values = new ContentValues();
        values.put("cedula", cedula);
        values.put("nombre", nombre);
        values.put("contrasena", contrasena);
        values.put("localidad", spLocalidad.getSelectedItem().toString());
        values.put("rol", spRol.getSelectedItem().toString());

        long id = db.insert("Usuarios", null, values);

        if (id == -1) {
            Toast.makeText(this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
            // Redirigir al login
            finish();
        }
    }

    private boolean verificarCedulaExistente(String cedula) {
        String[] columns = {"cedula"};
        String selection = "cedula = ?";
        String[] selectionArgs = {cedula};
        SQLiteDatabase dbRead = new DBHelper(this).getReadableDatabase();
        Cursor cursor = dbRead.query("Usuarios", columns, selection, selectionArgs, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}

