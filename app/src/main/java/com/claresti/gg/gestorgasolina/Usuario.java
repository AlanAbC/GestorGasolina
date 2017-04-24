package com.claresti.gg.gestorgasolina;

public class Usuario {

    private int usuPrimera;
    private String usuNombre;
    private String usuImg;

    /**
     * Constructor
     * @param usuPrimera
     * @param usuNombre
     * @param usuImg
     */
    public Usuario(int usuPrimera, String usuNombre, String usuImg) {
        this.usuPrimera = usuPrimera;
        this.usuNombre = usuNombre;
        this.usuImg = usuImg;
    }

    /**
     * Constructor vacio
     */
    public Usuario(){

    }

    /**
     * regresa si el usuario ha usado o no lqa aplicacion
     * @return usuPrimera
     */
    public int getUsuPrimera() {
        return usuPrimera;
    }

    /**
     * inserta si el usuario ya uso la aplicacion
     * @param usuPrimera
     */
    public void setUsuPrimera(int usuPrimera) {
        this.usuPrimera = usuPrimera;
    }

    /**
     * regresa el nombre del usuario
     * @return usuNombre
     */
    public String getUsuNombre() {
        return usuNombre;
    }

    /**
     * inserta el nombre del usuario
     * @param usuNombre
     */
    public void setUsuNombre(String usuNombre) {
        this.usuNombre = usuNombre;
    }

    /**
     * regresa el nombre de la imagen del usuario
     * @return usuImg
     */
    public String getUsuImg() {
        return usuImg;
    }

    /**
     * inserta la imagen del usuario
     * @param usuImg
     */
    public void setUsuImg(String usuImg) {
        this.usuImg = usuImg;
    }
}
