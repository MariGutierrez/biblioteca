
package com.prossofteam.oq.model;

import java.util.List;

public class DetalleVentaProducto {
    private Venta venta;
    private List<VentaProducto> listaProductos;

    public DetalleVentaProducto() {
    }

    public DetalleVentaProducto(Venta venta, List<VentaProducto> listaProductos) {
        this.venta = venta;
        this.listaProductos = listaProductos;
    }

    
    
    
    public Venta getVenta() {
        return venta;
    }

    /**
     * @param venta the venta to set
     */
    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    /**
     * @return the listaProductos
     */
    public List<VentaProducto> getListaProductos() {
        return listaProductos;
    }

    /**
     * @param listaProductos the listaProductos to set
     */
    public void setListaProductos(List<VentaProducto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    @Override
    public String toString() {
        String mensaje = "";
        for(VentaProducto listaProducto : listaProductos){
            mensaje+=listaProducto.toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("DetalleVP(");
        sb.append("venta=").append(venta);
        sb.append(", listaProductos=").append(mensaje);
        sb.append('}');
        
        
        return sb.toString();
    }
    
    
}
