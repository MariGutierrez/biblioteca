/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utl.bli.model;

/*@author maria*/
public class Usuario {
    private int id_usuario;
    private String nombre_usuario;
    private String contrasenia;
    private String rol;
    private String last_token;
    private String date_last_token;
    private boolean estatus; 

    public Usuario() {
    }

    public Usuario(int id_usuario, String nombre_usuario, String contrasenia, String rol, String last_token, String date_last_token, boolean estatus) {
        this.id_usuario = id_usuario;
        this.nombre_usuario = nombre_usuario;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.last_token = last_token;
        this.date_last_token = date_last_token;
        this.estatus = estatus;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getLast_token() {
        return last_token;
    }

    public void setLast_token(String last_token) {
        this.last_token = last_token;
    }

    public String getDate_last_token() {
        return date_last_token;
    }

    public void setDate_last_token(String date_last_token) {
        this.date_last_token = date_last_token;
    }

    public boolean isEstatus() {
        return estatus;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }
    
    
}
