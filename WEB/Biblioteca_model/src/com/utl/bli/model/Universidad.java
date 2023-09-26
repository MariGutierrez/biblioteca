
package com.utl.bli.model;


public class Universidad {
    private int id_universidad;
    private String nombre_universidad;
    private String pais;
    private int estatus;

    public Universidad() {
    }

    public Universidad(int id_universidad, String nombre_universidad, String pais, char estatus) {
        this.id_universidad = id_universidad;
        this.nombre_universidad = nombre_universidad;
        this.pais = pais;
        this.estatus = estatus;
    }
    
    public int getId_universidad() {
        return id_universidad;
    }

    public void setId_universidad(int id_universidad) {
        this.id_universidad = id_universidad;
    }

    public String getNombre_universidad() {
        return nombre_universidad;
    }

    public void setNombre_universidad(String nombre_universidad) {
        this.nombre_universidad = nombre_universidad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        return "Universidad{" + "id_universidad=" + id_universidad + ", nombre_universidad=" + nombre_universidad + ", pais=" + pais + ", estatus=" + estatus + '}';
    }
    
    
}
