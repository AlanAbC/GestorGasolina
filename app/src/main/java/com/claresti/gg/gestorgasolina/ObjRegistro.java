package com.claresti.gg.gestorgasolina;

import java.util.Calendar;
import java.util.Date;

public class ObjRegistro {

    private Date regFecha;
    private ObjCombustible objCombustible;
    private float regKmRecorridos;
    private float regLitros;
    private float regDinero;

    /**
     * Constructor
     * @param regFecha
     * @param objCombustible
     * @param regKmRecorridos
     * @param regLitros
     * @param regDinero
     */
    public ObjRegistro(Date regFecha, ObjCombustible objCombustible, float regKmRecorridos, float regLitros, float regDinero) {
        this.regFecha = regFecha;
        this.objCombustible = objCombustible;
        this.regKmRecorridos = regKmRecorridos;
        this.regLitros = regLitros;
        this.regDinero = regDinero;
    }

    /**
     * Constructor vacio
     */
    public ObjRegistro(){

    }

    /**
     * regresa la fecha de creacion del registro
     * @return regFecha
     */
    public Date getRegFecha() {
        return regFecha;
    }

    /**
     * inserta la fecha del registro
     * @param regFecha
     */
    public void setRegFecha(Date regFecha) {
        this.regFecha = regFecha;
    }

    /**
     * regresa un objeto de tipo objCombustible
     * @return objCombustible
     */
    public ObjCombustible getObjCombustible() {
        return objCombustible;
    }

    /**
     * inserta un onjeto de tipó objCombustible
     * @param objCombustible
     */
    public void setObjCombustible(ObjCombustible objCombustible) {
        this.objCombustible = objCombustible;
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
