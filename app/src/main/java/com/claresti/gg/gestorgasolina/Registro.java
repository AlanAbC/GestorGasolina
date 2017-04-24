package com.claresti.gg.gestorgasolina;

import java.util.Calendar;

public class Registro {

    private Calendar regFecha;
    private Combustible combustible;
    private float regKmRecorridos;
    private float regLitros;
    private float regDinero;

    /**
     * Constructor
     * @param regFecha
     * @param combustible
     * @param regKmRecorridos
     * @param regLitros
     * @param regDinero
     */
    public Registro(Calendar regFecha, Combustible combustible, float regKmRecorridos, float regLitros, float regDinero) {
        this.regFecha = regFecha;
        this.combustible = combustible;
        this.regKmRecorridos = regKmRecorridos;
        this.regLitros = regLitros;
        this.regDinero = regDinero;
    }

    /**
     * Constructor vacio
     */
    public Registro(){

    }

    /**
     * regresa la fecha de creacion del registro
     * @return regFecha
     */
    public Calendar getRegFecha() {
        return regFecha;
    }

    /**
     * inserta la fecha del registro
     * @param regFecha
     */
    public void setRegFecha(Calendar regFecha) {
        this.regFecha = regFecha;
    }

    /**
     * regresa un objeto de tipo combustible
     * @return combustible
     */
    public Combustible getCombustible() {
        return combustible;
    }

    /**
     * inserta un onjeto de tipó combustible
     * @param combustible
     */
    public void setCombustible(Combustible combustible) {
        this.combustible = combustible;
    }

    /**
     * regresa los kilometros recorridos
     * @return regKmRecorridos
     */
    public float getRegKmRecorridos() {
        return regKmRecorridos;
    }

    /**
     * inserta los kilometros recorridos
     * @param regKmRecorridos
     */
    public void setRegKmRecorridos(float regKmRecorridos) {
        this.regKmRecorridos = regKmRecorridos;
    }

    /**
     * inserta los litros comprados
     * @return regLitros
     */
    public float getRegLitros() {
        return regLitros;
    }

    /**
     * inserta los litros recorridos
     * @param regLitros
     */
    public void setRegLitros(float regLitros) {
        this.regLitros = regLitros;
    }

    /**
     * regresa la cantidad gastada
     * @return regDinero
     */
    public float getRegDinero() {
        return regDinero;
    }

    /**
     * inserta la cantidad gastada
     * @param regDinero
     */
    public void setRegDinero(float regDinero) {
        this.regDinero = regDinero;
    }
}
