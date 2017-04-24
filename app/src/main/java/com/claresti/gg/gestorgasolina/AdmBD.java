package com.claresti.gg.gestorgasolina;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;
import java.util.Date;

public class AdmBD extends SQLiteOpenHelper{

    public static final int DataBaseVersion = 1;
    public static final String DataBaseName = "GestorGasolina.db";

    public AdmBD(Context context) {
        super(context, DataBaseName, null, DataBaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tablaUsuario = "CREATE TABLE Usuario(" +
            "usuPrimera INTEGER NOT NULL PRIMARY KEY," +
            "usuNombre TEXT," +
            "usuImg TEXT" +
            ")";

        String tablaCombustible = "CREATE TABLE Combustible(" +
            "comId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "comNombre TEXT," +
            "comPrecio DECIMAL(10,2)," +
            "comFecha DATETIME" +
            "comColor TEXT" +
            ")";

        String tablaRegistros = "CREATE TABLE Registros(" +
            "regFecha DATETIME NOT NULL PRIMARY KEY," +
            "comId INTEGER NOT NULL," +
            "regKmRecorridos DECIMAL(10,1)," +
            "regLitros DECIMAL(10,3)," +
            "regDinero DECIMAL(10,2)" +
            ")";

        //Crear tablas
        db.execSQL(tablaUsuario);
        db.execSQL(tablaCombustible);
        db.execSQL(tablaCombustible);

        //Usuario
        ContentValues v = new ContentValues();
        v.put("usuPrimera", "0");
        v.put("usuNombre", "Usuario");
        v.put("usuImg", "imgmenu");
        db.insert("Usuario", null, v);

        //Combustible
        Date fechaActual = Calendar.getInstance().getTime();

        ContentValues v1 = new ContentValues();
        v1.put("comNombre", "Magna");
        v1.put("comPrecio", "15.99");
        v1.put("comFecha", fechaActual.toString());
        v1.put("comColor", "#00e787");
        db.insert("Combustible", null, v1);

        ContentValues v2 = new ContentValues();
        v2.put("comNombre", "Premium");
        v2.put("comPrecio", "17.79");
        v2.put("comFecha", fechaActual.toString());
        v2.put("comColor", "#f12b45");
        db.insert("Combustible", null, v2);

        ContentValues v3 = new ContentValues();
        v3.put("comNombre", "Diésel");
        v3.put("comPrecio", "17.05");
        v3.put("comFecha", fechaActual.toString());
        v3.put("comColor", "#000000");
        db.insert("Combustible", null, v3);

        ContentValues v4 = new ContentValues();
        v4.put("comNombre", "Gas");
        v4.put("comPrecio", "0");
        v4.put("comFecha", fechaActual.toString());
        v4.put("comColor", "#2249ae");
        db.insert("Combustible", null, v4);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
