package com.prossofteam.oq.model;

/**
 *
 * @author ximer
 */
public class TipoMica {
    
    int idTipoMica;
    String nombre;
    double precioCompra;
    double precioVenta;

    public TipoMica() {
    }

    public TipoMica(int idTipoMica, String nombre, double precioCompra, double precioVenta) {
        this.idTipoMica = idTipoMica;
        this.nombre = nombre;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
    }
    
    public int getIdTipoMica() {
        return idTipoMica;
    }

    public void setIdTipoMica(int idTipoMica) {
        this.idTipoMica = idTipoMica;
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



    
}