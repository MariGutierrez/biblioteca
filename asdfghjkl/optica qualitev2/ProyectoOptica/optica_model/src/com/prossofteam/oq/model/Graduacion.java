/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prossofteam.oq.model;

/**
 *
 * @author ximer
 */
public class Graduacion {
    int idGraduacion, cilindroOd, cilindroOi, ejeOd,ejeOi;
    Double esferaOd,esferaOi;
    String dip;

    public Graduacion() {
    }

    public Graduacion(int idGraduacion, int cilindroOd, int cilindroOi, int ejeOd, int ejeOi, Double esferaOd, Double esferaOi, String dip) {
        this.idGraduacion = idGraduacion;
        this.cilindroOd = cilindroOd;
        this.cilindroOi = cilindroOi;
        this.ejeOd = ejeOd;
        this.ejeOi = ejeOi;
        this.esferaOd = esferaOd;
        this.esferaOi = esferaOi;
        this.dip = dip;
    }

    public int getIdGraduacion() {
        return idGraduacion;
    }

    public void setIdGraduacion(int idGraduacion) {
        this.idGraduacion = idGraduacion;
    }

    public int getCilindroOd() {
        return cilindroOd;
    }

    public void setCilindroOd(int cilindroOd) {
        this.cilindroOd = cilindroOd;
    }

    public int getCilindroOi() {
        return cilindroOi;
    }

    public void setCilindroOi(int cilindroOi) {
        this.cilindroOi = cilindroOi;
    }

    public int getEjeOd() {
        return ejeOd;
    }

    public void setEjeOd(int ejeOd) {
        this.ejeOd = ejeOd;
    }

    public int getEjeOi() {
        return ejeOi;
    }

    public void setEjeOi(int ejeOi) {
        this.ejeOi = ejeOi;
    }

    public Double getEsferaOd() {
        return esferaOd;
    }

    public void setEsferaOd(Double esferaOd) {
        this.esferaOd = esferaOd;
    }

    public Double getEsferaOi() {
        return esferaOi;
    }

    public void setEsferaOi(Double esferaOi) {
        this.esferaOi = esferaOi;
    }

    public String getDip() {
        return dip;
    }

    public void setDip(String dip) {
        this.dip = dip;
    }
    
    
    
}
