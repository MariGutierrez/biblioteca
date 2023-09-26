package com.prossofteam.oq.model;

/**
 *
 * @author ximer
 */

public class Tratamiento {

    int idTratamiento;
    String nombre;
    double precioCompra;
    double precioVenta;
    int estatus;

    public Tratamiento() {
    }

    public Tratamiento(int idTratamiento, String nombre, double precioCompra, double precioVenta, int estatus) {
        this.idTratamiento = idTratamiento;
        this.nombre = nombre;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.estatus = estatus;
    }

    public int getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(int idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    
    
}
