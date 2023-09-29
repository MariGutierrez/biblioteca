package com.utl.bli.model;

/**
 *
 * @author ximer
 */
public class Alumno {
    
    private int id_alumno;
    private Persona idPersona;
    private Usuario idUsuario;
    private String matricula;
    private int estatus;

    public Alumno() {
    }

    public Alumno(int id_alumno, Persona idPersona, Usuario idUsuario, String matricula, int estatus) {
        this.id_alumno = id_alumno;
        this.idPersona = idPersona;
        this.idUsuario = idUsuario;
        this.matricula = matricula;
        this.estatus = estatus;
    }

    public int getId_alumno() {
        return id_alumno;
    }

    public void setId_alumno(int id_alumno) {
        this.id_alumno = id_alumno;
    }

    public Persona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Persona idPersona) {
        this.idPersona = idPersona;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        return "Alumno{" + "id_alumno=" + id_alumno + ", idPersona=" + idPersona + ", idUsuario=" + idUsuario + ", matricula=" + matricula + ", estatus=" + estatus + '}';
    }
    
    
    
}
