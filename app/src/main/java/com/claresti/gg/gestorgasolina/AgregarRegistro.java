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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AgregarRegistro extends AppCompatActivity {

    //Menu, Declaracion de variables del menu
    private DrawerLayout drawerLayout;
    final List<MenuItem> items = new ArrayList<>();
    private Menu menu;
    private ImageView btnMenu;
    private NavigationView nav;
    //Layout, Declaracion de variables del layout
    private DatePicker fecha;
    private Spinner gasolinas;
    private EditText kilometros;
    private EditText litros;
    private EditText dinero;
    //Base de Datos
    private AdmBD db;
    //Variable de combustibles
    private ArrayList<ObjCombustible> combustibles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Ocultar la AccionBar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_agregar_registro);
        //Cambiar el color en la barra de notificaciones (Solo funciona de lollipop hacia arriba)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.agregar_registro));
        }
        //Crear objeto base de datos
        db = new AdmBD(this);
        //Menu, Inicia las variables del menu y llama la funcion encargada de su manipulacion
        drawerLayout = (DrawerLayout) findViewById(R.id.dLayout);
        nav = (NavigationView)findViewById(R.id.navigation);
        menu = nav.getMenu();
        //Llamando metodo para dar funcionalidad al menu
        menuNav();
        //Asignacion de variables del layout
        fecha = (DatePicker)findViewById(R.id.fechaS);
        gasolinas = (Spinner)findViewById(R.id.tipoS);
        kilometros = (EditText)findViewById(R.id.kilometrosT);
        litros = (EditText)findViewById(R.id.litrosT);
        dinero = (EditText)findViewById(R.id.dineroT);
        //llenar el spinner
        cargarCombustibles();
        //Comprobacion de accion del activity(editar o crear)
        String accion = getIntent().getExtras().getString("accion");
        if(accion.equals("crear")){
            crearRegistro();
        }else{
            editarRegistro();
        }
    }

    /**
     * funcion para crear registros
     */
    private void crearRegistro(){

    }

    /**
     * funcion para editar un registro
     */
    private void editarRegistro(){

    }

    /**
     * funcion la cual llena el espinner de los combusibles
     */
    private void cargarCombustibles(){
        combustibles = db.selectCombustibles();
        Toast.makeText(getApplicationContext(), "" + combustibles.size(), Toast.LENGTH_SHORT).show();
        gasolinas.setAdapter(new AdapterSpinnerCombustibles(getApplicationContext(), combustibles));
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
                    Intent i = new Intent(AgregarRegistro.this, Estadisticas.class);
                    startActivity(i);
                    finish();
                }else if(pos == 2){

                }else if(pos == 3) {
                    Intent i = new Intent(AgregarRegistro.this, Precios.class);
                    startActivity(i);
                    finish();
                }else if(pos == 4){
                    Intent i = new Intent(AgregarRegistro.this, AcercaDe.class);
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
