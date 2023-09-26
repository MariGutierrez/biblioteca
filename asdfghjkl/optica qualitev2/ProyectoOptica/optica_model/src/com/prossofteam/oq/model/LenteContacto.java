/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prossofteam.oq.model;

/**
 *
 * @author ruth1
 */
public class LenteContacto {
 
    Producto producto;
    private int idLenteContacto;
    private int keratometria;
    private String fotografia;

    public LenteContacto() {
    }

    public LenteContacto(Producto producto, int idLenteContacto, int keratometria, String fotografia) {
        this.producto = producto;
        this.idLenteContacto = idLenteContacto;
        this.keratometria = keratometria;
        this.fotografia = fotografia;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getIdLenteContacto() {
        return idLenteContacto;
    }

    public void setIdLenteContacto(int idLenteContacto) {
        this.idLenteContacto = idLenteContacto;
    }

    public int getKeratometria() {
        return keratometria;
    }

    public void setKeratometria(int keratometria) {
        this.keratometria = keratometria;
    }

    public String getFotografia() {
        return fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }

    @Override
    public String toString() {
        return "LenteContacto{" + "producto=" + producto + ", idLenteContacto=" + idLenteContacto + ", keratometria=" + keratometria + ", fotografia=" + fotografia + '}';
    }

    /**
     * @return the idLenteContacto
     */
   
}
