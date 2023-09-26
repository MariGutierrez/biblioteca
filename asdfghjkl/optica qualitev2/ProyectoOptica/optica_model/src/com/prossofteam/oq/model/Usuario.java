/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prossofteam.oq.model;

import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;
//import org.apache.commons.codec.digest.DigestUtils;


public class Usuario
{
    int idUsuario;
    String nombre;
    String contrasenia;
    String rol;    
    String lastToken;
    String dateLastToken;

    public Usuario(){}
    
    public Usuario(int idUsuario)
    {
        this.idUsuario = idUsuario;
    }
    
    public int getIdUsuario()
    {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario)
    {
        this.idUsuario = idUsuario;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getContrasenia()
    {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia)
    {
        this.contrasenia = contrasenia;
    }

    public String getRol()
    {
        return rol;
    }

    public void setRol(String rol)
    {
        this.rol = rol;
    }

    public String getLastToken()
    {
        return lastToken;
    }

    public void setLastToken(String lastToken)
    {
        this.lastToken = lastToken;
    }

    public String getDateLastToken()
    {
        return dateLastToken;
    }

    public void setDateLastToken(String dateLastToken)
    {
        this.dateLastToken = dateLastToken;
    }
    
    public void setLastToken(){
    String u = this.getNombre();
    String p = this.getContrasenia();
    String k = new Date().toString();
    String x = (DigestUtils.sha256Hex(u+";"+p+";"+k));
    this.lastToken = x;    
    }
    
    public void setDateLastToken(){
    String fecha = new Date().toString();
    this.dateLastToken = fecha;
    }
}
