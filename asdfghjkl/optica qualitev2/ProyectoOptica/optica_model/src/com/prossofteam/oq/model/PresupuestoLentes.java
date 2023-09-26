package com.prossofteam.oq.model;

import java.util.List;

/**
 *
 * @author ximer
 */
public class PresupuestoLentes {
    int idPresupuestoLentes;
    Presupuesto idPresupuesto;
    int alturaOblea;
    TipoMica idTipoMica;
    Material idMaterial;
    Armazon idArmazon;
    List<Tratamiento> tratamientos;

    public PresupuestoLentes() {
    }

    public PresupuestoLentes(int idPresupuestoLentes, Presupuesto idPresupuesto, int alturaOblea, TipoMica idTipoMica, Material idMaterial, Armazon idArmazon, List<Tratamiento> tratamientos) {
        this.idPresupuestoLentes = idPresupuestoLentes;
        this.idPresupuesto = idPresupuesto;
        this.alturaOblea = alturaOblea;
        this.idTipoMica = idTipoMica;
        this.idMaterial = idMaterial;
        this.idArmazon = idArmazon;
        this.tratamientos = tratamientos;
    }

    public int getIdPresupuestoLentes() {
        return idPresupuestoLentes;
    }

    public void setIdPresupuestoLentes(int idPresupuestoLentes) {
        this.idPresupuestoLentes = idPresupuestoLentes;
    }

    public Presupuesto getIdPresupuesto() {
        return idPresupuesto;
    }

    public void setIdPresupuesto(Presupuesto idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
    }

    public int getAlturaOblea() {
        return alturaOblea;
    }

    public void setAlturaOblea(int alturaOblea) {
        this.alturaOblea = alturaOblea;
    }

    public TipoMica getIdTipoMica() {
        return idTipoMica;
    }

    public void setIdTipoMica(TipoMica idTipoMica) {
        this.idTipoMica = idTipoMica;
    }

    public Material getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Material idMaterial) {
        this.idMaterial = idMaterial;
    }

    public Armazon getIdArmazon() {
        return idArmazon;
    }

    public void setIdArmazon(Armazon idArmazon) {
        this.idArmazon = idArmazon;
    }

    public List<Tratamiento> getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(List<Tratamiento> tratamientos) {
        this.tratamientos = tratamientos;
    }

    @Override
    public String toString() {
        return "PresupuestoLentes{" + "idPresupuestoLentes=" + idPresupuestoLentes + ", idPresupuesto=" + idPresupuesto + ", alturaOblea=" + alturaOblea + ", idTipoMica=" + idTipoMica + ", idMaterial=" + idMaterial + ", idArmazon=" + idArmazon + ", tratamientos=" + tratamientos + '}';
    }

   
    
    
}
