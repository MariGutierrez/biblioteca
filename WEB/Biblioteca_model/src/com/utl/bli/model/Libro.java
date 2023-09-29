
package com.utl.bli.model;
import com.utl.bli.model.Universidad;

/*@author Maria */
public class Libro {
    private int id_libro;
    private String titulo;
    private String autor;
    private String editorial;
    private String idioma;
    private String genero;
    private int no_paginas;
    private String libro;
    private boolean estatus;
    private boolean derecho_autor;
    private Universidad universidad;

    public Libro() {
    }

    public Libro(int id_libro, String titulo, String autor, String editorial, String idioma, String genero, int no_paginas, String libro, boolean estatus, boolean derecho_autor, Universidad universidad) {
        this.id_libro = id_libro;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.idioma = idioma;
        this.genero = genero;
        this.no_paginas = no_paginas;
        this.libro = libro;
        this.estatus = estatus;
        this.derecho_autor = derecho_autor;
        this.universidad = universidad;
    }

   
    public Universidad getUniversidad() {
        return universidad;
    }

    public void setUniversidad(Universidad universidad) {
        this.universidad = universidad;
    }

    public int getId_libro() {
        return id_libro;
    }

    public void setId_libro(int id_libro) {
        this.id_libro = id_libro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getNo_paginas() {
        return no_paginas;
    }

    public void setNo_paginas(int no_paginas) {
        this.no_paginas = no_paginas;
    }

    public String getLibro() {
        return libro;
    }

    public void setLibro(String libro) {
        this.libro = libro;
    }

    public boolean isEstatus() {
        return estatus;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    public boolean isDerecho_autor() {
        return derecho_autor;
    }

    public void setDerecho_autor(boolean derecho_autor) {
        this.derecho_autor = derecho_autor;
    }
    
    
    @Override
    public String toString() {
        return "Libro{" + "id=" + id_libro + ", titulo=" + titulo + ", autor=" + autor + ", editorial=" + editorial +
                ", idioma=" + idioma + ", genero=" + genero + ", num_pag=" + no_paginas + ", universidad=" + universidad + "}";

    }

    
}
