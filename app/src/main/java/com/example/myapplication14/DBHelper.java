package com.example.myapplication14;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "votacionesDB";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_USUARIOS = "Usuarios";
    public static final String TABLE_PROPUESTAS = "Propuestas";

    public static final String COLUMN_CEDULA = "cedula";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_LOCALIDAD = "localidad";
    public static final String COLUMN_ROL = "rol";
    public static final String COLUMN_CONTRASENA = "contrasena";

    public static final String COLUMN_ID = "_id"; // Nuevo identificador único
    public static final String COLUMN_NOMBRE_PROPUESTA = "nombre";
    public static final String COLUMN_LUGAR = "lugar";
    public static final String COLUMN_LOCALIDAD_PROPUESTA = "localidad";
    public static final String COLUMN_VALOR = "valor";
    public static final String COLUMN_COORDENADAS = "coordenadas";
    public static final String COLUMN_FECHA_INICIO = "fecha_inicio";
    public static final String COLUMN_FECHA_FIN = "fecha_fin";
    public static final String COLUMN_CANTIDAD = "cantidad";
    public static final String COLUMN_APROBACION = "aprobacion";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USUARIOS_TABLE = "CREATE TABLE " + TABLE_USUARIOS + " (" +
                COLUMN_CEDULA + " TEXT PRIMARY KEY, " +
                COLUMN_NOMBRE + " TEXT, " +
                COLUMN_LOCALIDAD + " TEXT, " +
                COLUMN_ROL + " TEXT, " +
                COLUMN_CONTRASENA + " TEXT);";
        db.execSQL(CREATE_USUARIOS_TABLE);

        String CREATE_PROPUESTAS_TABLE = "CREATE TABLE " + TABLE_PROPUESTAS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // Clave primaria autoincremental
                COLUMN_NOMBRE_PROPUESTA + " TEXT, " +
                COLUMN_LUGAR + " TEXT, " +
                COLUMN_LOCALIDAD_PROPUESTA + " TEXT, " +
                COLUMN_VALOR + " TEXT, " +
                COLUMN_COORDENADAS + " TEXT, " +
                COLUMN_FECHA_INICIO + " TEXT, " +
                COLUMN_FECHA_FIN + " TEXT, " +
                COLUMN_CANTIDAD + " INTEGER DEFAULT 0, " + // Valor inicial 0
                COLUMN_APROBACION + " INTEGER DEFAULT 0);";
        db.execSQL(CREATE_PROPUESTAS_TABLE);


        String INSERT_USER = "INSERT INTO " + TABLE_USUARIOS + " (" +
                COLUMN_CEDULA + ", " +
                COLUMN_NOMBRE + ", " +
                COLUMN_LOCALIDAD + ", " +
                COLUMN_ROL + ", " +
                COLUMN_CONTRASENA + ") VALUES ('000000000', 'Admin', 'Bogotá', 'admin', '12345');";
        db.execSQL(INSERT_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPUESTAS);
        onCreate(db);
    }
}


