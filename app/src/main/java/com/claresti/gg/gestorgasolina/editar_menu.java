package com.claresti.gg.gestorgasolina;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.media.audiofx.BassBoost;
import android.media.audiofx.EnvironmentalReverb;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.PersistableBundle;
import android.preference.DialogPreference;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.CAMERA;

public class editar_menu extends AppCompatActivity {

    //Menu, Declaracion de variables
    private DrawerLayout drawerLayout;
    final List<MenuItem> items = new ArrayList<>();
    private Menu menu;
    private ImageView btnMenu;
    private NavigationView nav;
    //Variable de base de datos
    private AdmBD db;
    //Variables del layout
    private EditText nombre;
    private ImageView img;
    private Button selectImg;
    private RelativeLayout ventana;
    //Variables de directorio para subir imegen
    private static String APP_DIRECTORY = "GestorGasolina/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "img";
    //Variables de permisos
    private final int MY_PERMISSION = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;
    //Variable para configurar el mPath
    private String mPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_editar_menu);
        //Cambiar el color en la barra de notificaciones (Solo funciona de lollipop hacia arriba)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.agregar_registro));
        }
        //Fin cambio de color de barra de notificaciones
        //Menu, Inicia las variables del menu y llama la funcion encargada de su manipulacion
        drawerLayout = (DrawerLayout) findViewById(R.id.dLayout);
        nav = (NavigationView)findViewById(R.id.navigation);
        menu = nav.getMenu();
        menuNav();
        // Fin menu
        //Codigo para crear el objeto de la base de datos y
        //agregar el nombre de usuario al menu
        db = new AdmBD(this);
        ObjUsuario usuario = db.selectUsuario();
        //Codigo para poner en el Menu el nombre de usuario
        View header = nav.getHeaderView(0);
        TextView nombreUsuario = (TextView) header.findViewById(R.id.menuNombreUsuario);
        nombreUsuario.setText(usuario.getUsuNombre());
        //Asignacion de variables del layout
        nombre = (EditText)findViewById(R.id.nombreT);
        img = (ImageView)findViewById(R.id.img);
        selectImg = (Button)findViewById(R.id.btn_img);
        ventana = (RelativeLayout)findViewById(R.id.principal);
        //Validacion de permisos
        if(permisos()){
            selectImg.setEnabled(true);
        }else{
            selectImg.setEnabled(false);
        }
        //lisenerBoton
        selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptions();
            }
        });
        //Insercion de nombre actual e imagen actual
        nombre.setHint(usuario.getUsuNombre());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * Funcion encargada de verificar y pedir los permisos
     * @return true si tiene permisos, false en caso contrario
     */
    private boolean permisos() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return true;
        }
        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED)){
            return true;
        }
        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))){
            Snackbar.make(ventana, "Los permisos son necesarios para poder usar la aplicación", Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(View v) {
                            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSION);
                        }
                    }).show();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSION);
        }
        return false;
    }

    /**
     * funcion que mostrara cuadro de dialogo para seleccionar la fuente de la imagen
     */
    private void showOptions() {
        final CharSequence[] opciones = {"Tomar foto", "Elegir de galería", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(editar_menu.this);
        builder.setTitle("Selecciona una opcion");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(opciones[which] == "Tomar foto"){
                    abrirCamara();
                }else if(opciones[which] == "Elegir de galería"){
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    i.setType("image/*");
                    startActivityForResult(i.createChooser(i, "Selecciona app de imagen"), SELECT_PICTURE);
                }else{
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void abrirCamara() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();
        if(isDirectoryCreated){
            isDirectoryCreated = file.mkdirs();
        }
        if(isDirectoryCreated){
            Long timestamp = System.currentTimeMillis() / 1000;
            String imgeName = timestamp.toString() + ".jpg";
            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY + File.separator + imgeName;
            File newFile = new File(mPath);
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(i, PHOTO_CODE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("file_path", mPath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPath = savedInstanceState.getString("file_path");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_OK){
            switch (requestCode){
                case PHOTO_CODE:
                    MediaScannerConnection.scanFile(this, new String[]{mPath}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> Uri = " + uri);
                        }
                    });
                    Bitmap bitmap = BitmapFactory.decodeFile(mPath);
                    img.setImageBitmap(bitmap);
                    break;
                case SELECT_PICTURE:
                    Uri path = data.getData();
                    img.setImageURI(path);
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_PERMISSION){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                msg("Permisos aceptados");
                selectImg.setEnabled(true);
            }
        }else{
            showExplanation();
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(editar_menu.this);
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para poder cambiar la imagen del menu necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent();
                i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                i.setData(uri);
                startActivity(i);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * funcion encargada de crear la actualizacion con los camvbios del usuario
     * @param v
     */
    public void actualizar(View v){

    }

    /**
     * funcion que da funcionalidad al menu
     */
    private void menuNav(){
        for(int i = 0; i < menu.size(); i++){
            items.add(menu.getItem(i));
        }
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                int pos = items.indexOf(item);
                if(pos == 0){
                    finish();
                }else if(pos == 1){
                    Intent i = new Intent(editar_menu.this, Estadisticas.class);
                    startActivity(i);
                    finish();
                }else if(pos == 2){
                    Intent i = new Intent(editar_menu.this, AgregarRegistro.class);
                    i.putExtra("accion","crear");
                    startActivity(i);
                    finish();
                }else if(pos == 3) {
                    Intent i = new Intent(editar_menu.this, Precios.class);
                    startActivity(i);
                    finish();
                }else if(pos == 4){
                    Intent i = new Intent(editar_menu.this, AcercaDe.class);
                    startActivity(i);
                    finish();
                }
                drawerLayout.closeDrawer(nav);
                item.setChecked(false);
                return false;
            }
        });
        btnMenu = (ImageView)findViewById(R.id.Btnmenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(nav);
            }
        });
    }

    /**
     * Funcion para crear un mensaje en pantalla
     * @param msg
     */
    public void msg(String msg){
        Snackbar.make(ventana, msg, Snackbar.LENGTH_SHORT).setAction("Aceptar", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();
    }
}
