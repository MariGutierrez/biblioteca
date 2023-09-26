package com.prossofteam.oq.model;

public class Venta {

    private int idVenta;
    private Empleado empleado;
    private String clave;

    public Venta() {

    }

    public Venta(int idVenta, Empleado empleado, String clave) {
        this.idVenta = idVenta;
        this.empleado = empleado;
        this.clave = clave;
    }
    
    public Venta(Empleado empleado, String clave) {
        this.empleado = empleado;
        this.clave = clave;
    }
    
    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

}
