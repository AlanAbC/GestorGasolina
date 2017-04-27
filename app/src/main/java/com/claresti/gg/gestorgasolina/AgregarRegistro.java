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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private FloatingActionButton aceptar;
    private RelativeLayout ventana;
    //Base de Datos
    private AdmBD db;
    //Variable de combustibles
    private ArrayList<ObjCombustible> combustibles;
    //Variables de banderas
    private int comSeleccionada;

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
        //Menu, Inicia las variables del menu y llama la funcion encargada de su manipulacion
        drawerLayout = (DrawerLayout) findViewById(R.id.dLayout);
        nav = (NavigationView)findViewById(R.id.navigation);
        menu = nav.getMenu();
        //Llamando metodo para dar funcionalidad al menu
        menuNav();
        //Crear objeto base de datos
        db = new AdmBD(this);
        //Codigo para agregar el nombre de usuario al menu
        ObjUsuario usuario = db.selectUsuario();
        //Codigo para poner en el Menu el nombre de usuario
        View header = nav.getHeaderView(0);
        TextView nombreUsuario = (TextView) header.findViewById(R.id.menuNombreUsuario);
        nombreUsuario.setText(usuario.getUsuNombre());
        //Asignacion de variables del layout
        fecha = (DatePicker)findViewById(R.id.fechaS);
        gasolinas = (Spinner)findViewById(R.id.tipoS);
        kilometros = (EditText)findViewById(R.id.kilometrosT);
        litros = (EditText)findViewById(R.id.litrosT);
        dinero = (EditText)findViewById(R.id.dineroT);
        aceptar = (FloatingActionButton)findViewById(R.id.agregar);
        ventana = (RelativeLayout)findViewById(R.id.l_ventana);
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
        litros.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(!litros.getText().toString().equals("")){
                        float li = Float.parseFloat(litros.getText().toString());
                        float di = li * combustibles.get(comSeleccionada).getComPrecio();
                        String din = Float.toString(di);
                        dinero.setText(din);
                    }
                }
            }
        });
        dinero.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!dinero.getText().toString().equals("")){
                    float di = Float.parseFloat(dinero.getText().toString());
                    float li = di / combustibles.get(comSeleccionada).getComPrecio();
                    String lit = Float.toString(li);
                    litros.setText(lit);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String k = kilometros.getText().toString();
                String l = litros.getText().toString();
                String d = dinero.getText().toString();
                String f = fecha.getYear() + "/" + (fecha.getMonth() + 1) + "/" + fecha.getDayOfMonth() + " 00:00";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.US);
                Date fe = sdf.parse(f, new ParsePosition(0));
                if(k.equals("")){
                    msg("Falta ingresar los kilometros recorridos");
                }else if(l.equals("")){
                    msg("Falta ingresar los litros comprados");
                }else if(d.equals("")){
                    msg("Falta ingresar el monto total de la compra");
                }else{
                    ObjRegistro registro = new ObjRegistro();
                    registro.setObjCombustible(combustibles.get(comSeleccionada));
                    registro.setRegDinero(Float.parseFloat(d));
                    registro.setRegKmRecorridos(Float.parseFloat(k));
                    registro.setRegLitros(Float.parseFloat(l));
                    registro.setRegFecha(fe);
                    String respuesta = db.insertRegistro(registro);
                    if(respuesta.equals("1")){
                        Intent i = new Intent(AgregarRegistro.this, MainActivity.class);
                        i.putExtra("msg", "Se agrego correctamente el registro");
                        startActivity(i);
                        finish();
                    }else{
                        msg("Ocurrio un error, intente nuevamente");
                        Log.e("Error al crear registro", respuesta);
                    }
                }
            }
        });
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
        gasolinas.setAdapter(new AdapterSpinnerCombustibles(getApplicationContext(), combustibles));
        gasolinas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                float li;
                float di;
                if(!litros.getText().toString().equals("") && !dinero.getText().toString().equals("")){
                    comSeleccionada = position;
                    li = Float.parseFloat(litros.getText().toString());
                    di = li * combustibles.get(comSeleccionada).getComPrecio();
                    String din = Float.toString(di);
                    dinero.setText(din);
                }else{
                    comSeleccionada = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        //Bloque de codigo que da funcionalidad al boton de editar del header del menu
        View headerview = nav.getHeaderView(0);
        ImageView editar = (ImageView)headerview.findViewById(R.id.editar);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AgregarRegistro.this, editar_menu.class);
                startActivity(i);
                finish();
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
