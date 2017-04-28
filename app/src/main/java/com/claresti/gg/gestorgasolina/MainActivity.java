package com.claresti.gg.gestorgasolina;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Menu, Declaracion de variables
    private DrawerLayout drawerLayout;
    final List<MenuItem> items = new ArrayList<>();
    private Menu menu;
    private ImageView btnMenu;
    private NavigationView nav;
    //variable de base de datos
    private AdmBD db;
    //Variebles del layout
    private RelativeLayout ventana;
    private ListView listaRegistros;
    //Variables de objetos
    private ArrayList<ObjRegistro> registros;
    private ObjUsuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        //Cambiar el color en la barra de notificaciones (Solo funciona de lollipop hacia arriba)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.main));
        }
        //Fin cambio de color de barra de notificaciones
        //Menu, Inicia las variables del menu y llama la funcion encargada de su manipulacion
        drawerLayout = (DrawerLayout) findViewById(R.id.dLayout);
        nav = (NavigationView)findViewById(R.id.navigation);
        menu = nav.getMenu();
        //Asignacion de variables del layout
        ventana = (RelativeLayout)findViewById(R.id.l_ventana);
        listaRegistros = (ListView)findViewById(R.id.listaRegistros);
        //Creacion objeto de la base de datos
        db = new AdmBD(this);
        //Creacion del objeto usuario y comprobacion de primera ves o no en el sistemaa
        //cambia el nombre de la base de datos y el valor de usuPrimera a 1 en caso de que sea la primera vez
        //en caso contrario solo agrega nombre del usuario al menu y carga las tareas
        usuario = db.selectUsuario();
        if(usuario.getUsuPrimera() == 0){
            menuNav();
            //Creacion de la ventana de inicio
            final RelativeLayout inicio = (RelativeLayout)findViewById(R.id.lPrimeraVez);
            inicio.setVisibility(View.VISIBLE);
            Button actualizar = (Button)findViewById(R.id.actualizarUsuario);
            actualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ObjUsuario usuarioActualizar = new ObjUsuario();
                    EditText usuNombre = (EditText)findViewById(R.id.nombreUsuario);
                    String nombre = usuNombre.getText().toString();
                    usuarioActualizar.setUsuNombre(nombre);
                    String respuesta = db.updateUsuario(usuarioActualizar);
                    if(respuesta.equals("1")){
                        msg("Se a registrado correctamente");
                        inicio.setVisibility(View.GONE);
                        View header = nav.getHeaderView(0);
                        TextView nombreUsuario = (TextView) header.findViewById(R.id.menuNombreUsuario);
                        nombreUsuario.setText(nombre);
                    }else{
                        msg("Ocurrio un error, intente nuevamente");
                    }
                }
            });
        }else {
            menuNav();
            //Codigo para poner en el Menu el nombre de usuario
            View header = nav.getHeaderView(0);
            TextView nombreUsuario = (TextView) header.findViewById(R.id.menuNombreUsuario);
            nombreUsuario.setText(usuario.getUsuNombre());
            //Fin Codigo para poner el nombre de usuario en el menu
        }
        //Llenar registros
        llenarRegistros();
        //Verificacion para mostrar mensajes
        Intent i = getIntent();
        Bundle extras = i.getExtras();
        if(extras != null){
            if (extras.containsKey("msg")) {
                msg(getIntent().getExtras().getString("msg"));
            }
        }

    }

    private void llenarRegistros() {
        registros = db.selectRegistros();
        listaRegistros.setAdapter(new AdapterListViewRegistros(getApplicationContext(), registros));
    }

    public void agregarRegistro(View v){
        Intent i = new Intent(MainActivity.this, AgregarRegistro.class);
        i.putExtra("accion","crear");
        startActivity(i);
    }

    /**
     * Funcion que da funcionalidad al menu
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

                }else if(pos == 1){
                    Intent i = new Intent(MainActivity.this, Estadisticas.class);
                    startActivity(i);
                }else if(pos == 2){
                    Intent i = new Intent(MainActivity.this, AgregarRegistro.class);
                    i.putExtra("accion","crear");
                    startActivity(i);
                }else if(pos == 3) {
                    Intent i = new Intent(MainActivity.this, Precios.class);
                    startActivity(i);
                }else if(pos == 4){
                    Intent i = new Intent(MainActivity.this, AcercaDe.class);
                    startActivity(i);
                }
                drawerLayout.closeDrawer(nav);
                item.setChecked(false);
                return false;
            }
        });
        //Bloque de codigo que da funcionalidad al boton de editar del header del menu
        View headerview = nav.getHeaderView(0);
        ImageView editar = (ImageView)headerview.findViewById(R.id.editar);
        RelativeLayout imgFondo = (RelativeLayout)headerview.findViewById(R.id.l_imgFondo);
        if(usuario.getUsuImg().equals("imgmenu")){
            imgFondo.setBackgroundResource(R.drawable.header_menu);
        }else{
            Uri path = Uri.fromFile(new File(usuario.getUsuImg()));
            Bitmap bitmap = BitmapFactory.decodeFile(usuario.getUsuImg());
            BitmapDrawable bdrawable = new BitmapDrawable(getApplicationContext().getResources(),bitmap);
            imgFondo.setBackground(bdrawable);
        }
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, editar_menu.class);
                startActivity(i);
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
        Snackbar.make(ventana, msg, Snackbar.LENGTH_LONG).setAction("Aceptar", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();
    }
}
