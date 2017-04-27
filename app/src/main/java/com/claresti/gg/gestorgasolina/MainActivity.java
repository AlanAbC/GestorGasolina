package com.claresti.gg.gestorgasolina;

import android.content.Intent;
import android.os.Build;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

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
        menuNav();
        //Asignacion de variables del layout
        ventana = (RelativeLayout)findViewById(R.id.l_ventana);
        listaRegistros = (ListView)findViewById(R.id.listaRegistros);
        //Creacion objeto de la base de datos
        db = new AdmBD(this);
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
