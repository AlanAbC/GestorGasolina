package com.claresti.gg.gestorgasolina;

import java.util.Calendar;

public class Combustible {

    private int comId;
    private String comNombre;
    private float comPrecio;
    private Calendar comFecha;

    /**
     * Constructor
     * @param comId
     * @param comNombre
     * @param comPrecio
     * @param comFecha
     */
    public Combustible(int comId, String comNombre, float comPrecio, Calendar comFecha) {
        this.comId = comId;
        this.comNombre = comNombre;
        this.comPrecio = comPrecio;
        this.comFecha = comFecha;
    }

    /**
     * Constructor vacio
     */
    public Combustible(){

    }

    /**
     * regresa el id del combustible
     * @return comId
     */
    public int getComId() {
        return comId;
    }

    /**
     * inserta el id del combustible
     * @param comId
     */
    public void setComId(int comId) {
        this.comId = comId;
    }

    /**
     * regresa el nombre del combustible
     * @return comNombre
     */
    public String getComNombre() {
        return comNombre;
    }

    /**
     * inserta el nombre del combustible
     * @param comNombre
     */
    public void setComNombre(String comNombre) {
        this.comNombre = comNombre;
    }

    /**
     * regresa el precio del combustible
     * @return comPrecio
     */
    public float getComPrecio() {
        return comPrecio;
    }

    /**
     * inserta el precio del combustible
     * @param comPrecio
     */
    public void setComPrecio(float comPrecio) {
        this.comPrecio = comPrecio;
    }

    /**
     * regresa la fecha de cuando se registro el combustible
     * @return comFecha
     */
    public Calendar getComFecha() {
        return comFecha;
    }

    /**
     * inserta la fecha de creacion del combustible
     * @param comFecha
     */
    public void setComFecha(Calendar comFecha) {
        this.comFecha = comFecha;
    }
}
