
package edu.egg.libreria.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Libro {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private long isbn;
    private String titulo;
    private Integer anio;
    private Integer ejemplares;
    private Integer ejemplaresPrestados;
    private Integer ejemplearesRestantes;
    private boolean alta;
    @ManyToOne
    private Autor autor;
    @ManyToOne
    private Editorial editorial;
    @ManyToOne
    private Usuario usuario;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the isbn
     */
    public long getIsbn() {
        return isbn;
    }

    /**
     * @param isbn the isbn to set
     */
    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the anio
     */
    public Integer getAnio() {
        return anio;
    }

    /**
     * @param anio the anio to set
     */
    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    /**
     * @return the ejemplares
     */
    public Integer getEjemplares() {
        return ejemplares;
    }

    /**
     * @param ejemplares the ejemplares to set
     */
    public void setEjemplares(Integer ejemplares) {
        this.ejemplares = ejemplares;
    }

    /**
     * @return the ejemplaresPrestados
     */
    public Integer getEjemplaresPrestados() {
        return ejemplaresPrestados;
    }

    /**
     * @param ejemplaresPrestados the ejemplaresPrestados to set
     */
    public void setEjemplaresPrestados(Integer ejemplaresPrestados) {
        this.ejemplaresPrestados = ejemplaresPrestados;
    }

    /**
     * @return the ejemplearesRestantes
     */
    public Integer getEjemplearesRestantes() {
        return ejemplearesRestantes;
    }

    /**
     * @param ejemplearesRestantes the ejemplearesRestantes to set
     */
    public void setEjemplearesRestantes(Integer ejemplearesRestantes) {
        this.ejemplearesRestantes = ejemplearesRestantes;
    }

    /**
     * @return the alta
     */
    public boolean isAlta() {
        return alta;
    }

    /**
     * @param alta the alta to set
     */
    public void setAlta(boolean alta) {
        this.alta = alta;
    }

    /**
     * @return the autor
     */
    public Autor getAutor() {
        return autor;
    }

    /**
     * @param autor the autor to set
     */
    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    /**
     * @return the editorial
     */
    public Editorial getEditorial() {
        return editorial;
    }

    /**
     * @param editorial the editorial to set
     */
    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
}
