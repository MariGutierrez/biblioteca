package com.prossofteam.oq.model;

/**
 *
 * @author ximer
 */
public class VentaPresupuesto {

    private PresupuestoLC presupuestoLC;
    private int cantidad;
    private float precioUnitario;
    private float descuento;

    public VentaPresupuesto() {

    }

    public VentaPresupuesto(PresupuestoLC presupuestoLC, int cantidad, float precioUnitario, float descuento) {
        this.presupuestoLC = presupuestoLC;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.descuento = descuento;
    }

    public PresupuestoLC getPresupuestoLC() {
        return presupuestoLC;
    }

    public void setPresupuestoLC(PresupuestoLC presupuestoLC) {
        this.presupuestoLC = presupuestoLC;
    }

    

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

}
