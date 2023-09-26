
package com.prossofteam.oq.model;

import java.util.List;

/**
 *
 * @author ximer
 */
public class DetalleVentaPre {
    private Venta venta;
    private List<VentaPresupuesto> ventaPresupuesto;

    public DetalleVentaPre() {
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public List<VentaPresupuesto> getVentaPresupuesto() {
        return ventaPresupuesto;
    }

    public void setVentaPresupuesto(List<VentaPresupuesto> ventaPresupuesto) {
        this.ventaPresupuesto = ventaPresupuesto;
    }

    @Override
    public String toString() {
        return "DetalleVentaPre{" + "venta=" + venta + ", ventaPresupuesto=" + ventaPresupuesto + '}';
    }
    
}
