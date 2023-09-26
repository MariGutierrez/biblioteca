package com.prossofteam.oq.model;

/**
 *
 * @author ximer
 */
public class PresupuestoLC {
    
    private int idPresupuestoLC;
    private LenteContacto lenteContacto;
    private String clave;
    private Presupuesto presupuesto;
    private ExamenVista examenVista;

    public PresupuestoLC() {
    }

    public PresupuestoLC(LenteContacto lenteContacto, String clave, Presupuesto presupuesto, ExamenVista examenVista) {
        this.lenteContacto = lenteContacto;
        this.clave = clave;
        this.presupuesto = presupuesto;
        this.examenVista = examenVista;
    }

    public PresupuestoLC(int idPresupuestoLC, LenteContacto lenteContacto, String clave, Presupuesto presupuesto, ExamenVista examenVista) {
        this.idPresupuestoLC = idPresupuestoLC;
        this.lenteContacto = lenteContacto;
        this.clave = clave;
        this.presupuesto = presupuesto;
        this.examenVista = examenVista;
    }

    public int getIdPresupuestoLC() {
        return idPresupuestoLC;
    }

    public void setIdPresupuestoLC(int idPresupuestoLC) {
        this.idPresupuestoLC = idPresupuestoLC;
    }

    public LenteContacto getLenteContacto() {
        return lenteContacto;
    }

    public void setLenteContacto(LenteContacto lenteContacto) {
        this.lenteContacto = lenteContacto;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Presupuesto getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Presupuesto presupuesto) {
        this.presupuesto = presupuesto;
    }

    public ExamenVista getExamenVista() {
        return examenVista;
    }

    public void setExamenVista(ExamenVista examenVista) {
        this.examenVista = examenVista;
    }
    
    
    
    
    
    
    
    
    
}
