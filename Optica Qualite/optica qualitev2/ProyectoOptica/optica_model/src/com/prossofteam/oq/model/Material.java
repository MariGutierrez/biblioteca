
package com.prossofteam.oq.model;

/**
 *
 * @author ximer
 */
public class Material {
    
    int idMaterial;
    String nombre;
    double precioCompra;
    double precioVenta;
      public Material() {
    }

    public Material(int idMaterial, String nombre, double precioCompra, double precioVenta) {
        this.idMaterial = idMaterial;
        this.nombre = nombre;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
      
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
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
