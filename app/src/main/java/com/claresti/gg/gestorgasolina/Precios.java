package com.claresti.gg.gestorgasolina;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
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

public class Precios extends AppCompatActivity {

    //Menu, Declaracion de variables
    private DrawerLayout drawerLayout;
    final List<MenuItem> items = new ArrayList<>();
    private Menu menu;
    private ImageView btnMenu;
    private NavigationView nav;
    //Variables del layout
    private ListView precios;
    private RelativeLayout ventana;
    //Variable base de datos
    private AdmBD db;
    //Variable de combustibles
    private ArrayList<ObjCombustible> combustibles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_precios);
        //Cambiar el color en la barra de notificaciones (Solo funciona de lollipop hacia arriba)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.precios));
        }
        //Iniciacion de la base de datos
        db = new AdmBD(this);
        //Inicializacion de variables del layout
        ventana = (RelativeLayout)findViewById(R.id.contenedor);
        //Menu, Inicia las variables del menu y llama la funcion encargada de su manipulacion
        drawerLayout = (DrawerLayout) findViewById(R.id.dLayout);
        nav = (NavigationView)findViewById(R.id.navigation);
        menu = nav.getMenu();
        menuNav();
        //llenar el list view
        precios = (ListView)findViewById(R.id.listaPrecios);
        llenarPrecios();
    }

    /**
     * Funcion que llena el list view con los combustibles
     */
    private void llenarPrecios(){
        combustibles = db.selectCombustibles();
        precios.setAdapter(new AdapterListViewCombustibles(getApplicationContext(),combustibles, ventana));
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
                    Intent i = new Intent(Precios.this, Estadisticas.class);
                    startActivity(i);
                    finish();
                }else if(pos == 2){
                    Intent i = new Intent(Precios.this, AgregarRegistro.class);
                    i.putExtra("accion","crear");
                    startActivity(i);
                    finish();
                }else if(pos == 3) {

                }else if(pos == 4){
                    Intent i = new Intent(Precios.this, AcercaDe.class);
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
}
