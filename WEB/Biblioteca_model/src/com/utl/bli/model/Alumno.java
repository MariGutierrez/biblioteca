package com.utl.bli.model;

/**
 *
 * @author ximer
 */
public class Alumno {
    
    private int id_alumno;
    private Persona persona;
    private Usuario usuario;
    private String matricula;

    public Alumno() {
    }

    public Alumno(Persona persona, Usuario usuario, String matricula) {
        this.persona = persona;
        this.usuario = usuario;
        this.matricula = matricula;
    }

    public Alumno(int id_alumno, Persona persona, Usuario usuario, String matricula) {
        this.id_alumno = id_alumno;
        this.persona = persona;
        this.usuario = usuario;
        this.matricula = matricula;
    }

    public int getId_alumno() {
        return id_alumno;
    }

    public void setId_alumno(int id_alumno) {
        this.id_alumno = id_alumno;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    @Override
    public String toString() {
        return "Alumno{" + "id_alumno=" + id_alumno + ", persona=" + persona + ", usuario=" + usuario + ", matricula=" + matricula + '}';
    }
    
    
    


}