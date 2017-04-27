package com.claresti.gg.gestorgasolina;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    private RelativeLayout ventana;
    private TextView precioMag;
    private TextView precioPre;
    private TextView precioDie;
    private TextView precioGas;
    private ImageButton editarMag;
    private ImageButton editarPre;
    private ImageButton editarDie;
    private ImageButton editarGas;
    private EditText editMag;
    private EditText editPre;
    private EditText editDie;
    private EditText editGas;
    private ImageButton aceptarMag;
    private ImageButton aceptarPre;
    private ImageButton aceptarDie;
    private ImageButton aceptarGas;
    private ImageButton cancelarMag;
    private ImageButton cancelarPre;
    private ImageButton cancelarDie;
    private ImageButton cancelarGas;
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
        precioMag = (TextView)findViewById(R.id.precioMag);
        precioPre = (TextView)findViewById(R.id.precioPre);
        precioDie = (TextView)findViewById(R.id.precioDi);
        precioGas = (TextView)findViewById(R.id.precioGas);
        editarMag = (ImageButton)findViewById(R.id.editarMag);
        editarPre = (ImageButton)findViewById(R.id.editarPre);
        editarDie = (ImageButton)findViewById(R.id.editarDi);
        editarGas = (ImageButton)findViewById(R.id.editarGas);
        editMag = (EditText)findViewById(R.id.precioTMag);
        editPre = (EditText)findViewById(R.id.precioTPre);
        editDie = (EditText)findViewById(R.id.precioTDi);
        editGas = (EditText)findViewById(R.id.precioTGas);
        aceptarMag = (ImageButton)findViewById(R.id.aceptarMag);
        aceptarPre = (ImageButton)findViewById(R.id.aceptarPre);
        aceptarDie = (ImageButton)findViewById(R.id.aceptarDi);
        aceptarGas = (ImageButton)findViewById(R.id.aceptarGas);
        cancelarMag = (ImageButton)findViewById(R.id.cancelarMag);
        cancelarPre = (ImageButton)findViewById(R.id.cancelarPre);
        cancelarDie = (ImageButton)findViewById(R.id.cancelarDi);
        cancelarGas = (ImageButton)findViewById(R.id.cancelarGas);
        //Menu, Inicia las variables del menu y llama la funcion encargada de su manipulacion
        drawerLayout = (DrawerLayout) findViewById(R.id.dLayout);
        nav = (NavigationView)findViewById(R.id.navigation);
        menu = nav.getMenu();
        menuNav();
        //llenar el list view
        llenarPrecios();
        //Funciones a los botones
        listeners();
    }

    private void listeners() {
        editarMag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarMag.setVisibility(View.GONE);
                cancelarMag.setVisibility(View.VISIBLE);
                aceptarMag.setVisibility(View.VISIBLE);
                editMag.setVisibility(View.VISIBLE);
                editMag.setText(precioMag.getText());
                precioMag.setVisibility(View.GONE);
            }
        });
        editarPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarPre.setVisibility(View.GONE);
                cancelarPre.setVisibility(View.VISIBLE);
                aceptarPre.setVisibility(View.VISIBLE);
                editPre.setVisibility(View.VISIBLE);
                editPre.setText(precioPre.getText());
                precioPre.setVisibility(View.GONE);
            }
        });
        editarDie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarDie.setVisibility(View.GONE);
                cancelarDie.setVisibility(View.VISIBLE);
                aceptarDie.setVisibility(View.VISIBLE);
                editDie.setVisibility(View.VISIBLE);
                editDie.setText(precioDie.getText());
                precioDie.setVisibility(View.GONE);
            }
        });
        editarGas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarGas.setVisibility(View.GONE);
                cancelarGas.setVisibility(View.VISIBLE);
                aceptarGas.setVisibility(View.VISIBLE);
                editGas.setVisibility(View.VISIBLE);
                editGas.setText(precioGas.getText());
                precioGas.setVisibility(View.GONE);
            }
        });
        //Botones cancelar

        //Botones aceptar
    }

    /**
     * Funcion que llena el list view con los combustibles
     */
    private void llenarPrecios(){
        combustibles = db.selectCombustibles();
        precioMag.setText(Float.toString(combustibles.get(0).getComPrecio()));
        precioPre.setText(Float.toString(combustibles.get(1).getComPrecio()));
        precioDie.setText(Float.toString(combustibles.get(2).getComPrecio()));
        precioGas.setText(Float.toString(combustibles.get(3).getComPrecio()));
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
