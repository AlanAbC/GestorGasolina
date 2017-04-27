package com.claresti.gg.gestorgasolina;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
            "comFecha DATETIME," +
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
        db.execSQL(tablaRegistros);

        //ObjUsuario
        ContentValues v = new ContentValues();
        v.put("usuPrimera", "0");
        v.put("usuNombre", "ObjUsuario");
        v.put("usuImg", "imgmenu");
        db.insert("Usuario", null, v);

        //ObjCombustible
        Date fechaActual = Calendar.getInstance().getTime();

        ContentValues v1 = new ContentValues();
        v1.put("comFecha", fechaActual.toString());
        v1.put("comNombre", "Magna");
        v1.put("comPrecio", "15.99");
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

    /**
     * Funcion que actualiza al usuario
     * @param usuario
     * @return String 1 en caso correcto, en caso contrario regresa error
     */
    public String updateUsuario(ObjUsuario usuario){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put("usuNombre", usuario.getUsuNombre());
            v.put("usuPrimera", 1);
            db.update("Usuario", v, "usuPrimera = 0", null);
            return "1";
        }catch(Exception e){
            return e.toString();
        }
    }

    /**
     * Funcion que regresa un Objeto de tipo usuario con la informacion de la base de datos
     * @return ObjUsuario usuario
     */
    public ObjUsuario selectUsuario(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Usuario", null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            ObjUsuario usuario = new ObjUsuario(
                    cursor.getInt(cursor.getColumnIndex("usuPrimera")),
                    cursor.getString(cursor.getColumnIndex("usuNombre")),
                    cursor.getString(cursor.getColumnIndex("usuImg")));
            return usuario;
        }
        return null;
    }

    /**
     * Funcion para actualizar un registro del combustible
     * @param combustible
     * @return String 1 en caso correcto, en caso contrario regresa error
     */
    public String updateCombustible(ObjCombustible combustible){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put("comPrecio", combustible.getComPrecio());
            db.update("Combustible", v, "comId = " + combustible.getComId(), null);
            return "1";
        }catch(Exception e){
            return e.toString();
        }
    }

    /**
     * Funcion que regresa una lista de objetos ObjCombustible
     * @return ArrayList<ObjCombustible>
     */
    public ArrayList<ObjCombustible> selectCombustibles(){
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query("Combustible", null, null, null, null, null, null);
            ArrayList<ObjCombustible> combustibles = new ArrayList<ObjCombustible>();
            if (cursor.moveToFirst()) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                do {
                    ObjCombustible combustible = new ObjCombustible(
                            cursor.getInt(cursor.getColumnIndex("comId")),
                            cursor.getString(cursor.getColumnIndex("comNombre")),
                            cursor.getFloat(cursor.getColumnIndex("comPrecio")),
                            sdf.parse(cursor.getString(cursor.getColumnIndex("comFecha"))),
                            cursor.getString(cursor.getColumnIndex("comColor")));
                    combustibles.add(combustible);
                } while (cursor.moveToNext());
            }
            return combustibles;
        }catch(Exception e){
            ArrayList<ObjCombustible> combustibles = new ArrayList<ObjCombustible>();
            return combustibles;
        }
    }

    /**
     * Funcion que regresa un objeto ObjCombustible por su Id
     * @param id
     * @return ObjCombustible
     */
    public ObjCombustible findById(int id){
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query("Combustible", null, "comId = " + id, null, null, null, null);
            ObjCombustible combustible = new ObjCombustible();
            if (cursor.moveToFirst()) {
                Locale loc = new Locale("es","MX");
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", loc);
                combustible.setComId(cursor.getInt(cursor.getColumnIndex("comId")));
                combustible.setComNombre(cursor.getString(cursor.getColumnIndex("comNombre")));
                combustible.setComPrecio(cursor.getFloat(cursor.getColumnIndex("comPrecio")));
                combustible.setComFecha(sdf.parse(cursor.getString(cursor.getColumnIndex("comFecha"))));
                combustible.setComColor(cursor.getString(cursor.getColumnIndex("comColor")));
            }
        return combustible;
    }catch(Exception e){
        ObjCombustible combustible = new ObjCombustible();
        return combustible;
    }
    }

    /**
     * Funcion que inserta un nuevo registro
     * @param registro
     * @return 1 en caso de que sea correcto, en caso contrario regresa el error
     */
    public String insertRegistro(ObjRegistro registro){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put("regFecha", registro.getRegFecha().toString());
            v.put("comId", registro.getObjCombustible().getComId());
            v.put("regKmRecorridos", registro.getRegKmRecorridos());
            v.put("regLitros", registro.getRegLitros());
            v.put("regDinero", registro.getRegDinero());
            db.insert("Registros", null, v);
            return "1";
        }catch(Exception e){
            return e.toString();
        }
    }

    /**
     * Funcion que actualiza un registro
     * @param registro
     * @return 1 en caso correcto, en caso contrario regresa el error
     */
    public String updateRegistro(ObjRegistro registro){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put("regFecha", Calendar.getInstance().getTime().toString());
            v.put("comId", registro.getObjCombustible().getComId());
            v.put("regKmRecorridos", registro.getRegKmRecorridos());
            v.put("regLitros", registro.getRegLitros());
            v.put("regDinero", registro.getRegDinero());
            db.update("Registros", v, "regFecha = " + registro.getRegFecha(), null);
            return "1";
        }catch(Exception e){
            return e.toString();
        }
    }

    /**
     * Funcion que regresa una lista de objetos ObjRegistro
     * @return ArrayList<ObjRegistro>
     */
    public ArrayList<ObjRegistro> selectRegistros(){
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query("Registros", null, null, null, null, null, null);
            ArrayList<ObjRegistro> registros = new ArrayList<ObjRegistro>();
            if (cursor.moveToFirst()) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                do {
                    ObjRegistro registro = new ObjRegistro(
                            sdf.parse(cursor.getString(cursor.getColumnIndex("regFecha"))),
                            findById(cursor.getInt(cursor.getColumnIndex("comNombre"))),
                            cursor.getFloat(cursor.getColumnIndex("regKmRecorridos")),
                            cursor.getFloat(cursor.getColumnIndex("regLitros")),
                            cursor.getFloat(cursor.getColumnIndex("regDinero")));
                    registros.add(registro);
                } while (cursor.moveToNext());
            }
            return registros;
        }catch(Exception e){
            ArrayList<ObjRegistro> registros = new ArrayList<ObjRegistro>();
            return registros;
        }
    }
}
