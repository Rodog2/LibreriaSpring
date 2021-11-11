
package edu.egg.libreria.servicios;

import edu.egg.libreria.entidades.Autor;
import edu.egg.libreria.entidades.Editorial;
import edu.egg.libreria.entidades.Libro;
import edu.egg.libreria.errores.ErrorServicio;
import edu.egg.libreria.repositorios.AutorRepositorio;
import edu.egg.libreria.repositorios.EditorialRepositorio;
import edu.egg.libreria.repositorios.LibroRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;
    
    @Transactional
    public void cargarLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, String nombreAutor, String nombreEditorial) throws ErrorServicio{
        validar(isbn, titulo, anio, ejemplares, nombreAutor, nombreEditorial);
        Autor autor = validarAutor(nombreAutor);
        Editorial editorial = validarEditorial(nombreEditorial);
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setAlta(true);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(0);
        libro.setEjemplearesRestantes(ejemplares-libro.getEjemplaresPrestados());
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libroRepositorio.save(libro);
    }
    @Transactional
    public void modificarLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, String nombreAutor, String nombreEditorial) throws ErrorServicio{
        validar(isbn, titulo, anio, ejemplares, nombreAutor, nombreEditorial);
        Libro libro = libroRepositorio.BuscarLibroPorNombre(titulo);
        libro.setIsbn(isbn);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        Editorial editorial = editorialServicio.buscarEditorialPorNombre(nombreEditorial);
        libro.setEditorial(editorial);
        Autor autor = autorServicio.buscarAutorPorNombre(nombreAutor);
        libro.setAutor(autor);
        libroRepositorio.save(libro);
        
    }
    
    
    public void borrarLibro(String titulo) throws ErrorServicio{
        if(titulo==null){
            throw new ErrorServicio("El titulo no puede estar vacio");
        }
        Libro libro= libroRepositorio.BuscarLibroPorNombre(titulo);
        libroRepositorio.delete(libro);
    }
    
    public List<Libro> listarLibros(){
        List<Libro> listaLibros = libroRepositorio.buscarTodosLosLibros();
        return listaLibros;
    }
    
    
    public void validar(Long isbn, String titulo, Integer anio, Integer ejemplares, String nombreAutor, String nombreEditorial) throws ErrorServicio{
    
        if(isbn==null){
            throw new ErrorServicio("El Isbn no puede estar vacío");
        }
        if(titulo==null){
            throw new ErrorServicio("El nombre no puede estar vacío");
        }
        if(anio==null){
            throw new ErrorServicio("El año no puede estar vacío");
        }
        if(ejemplares==null){
            throw new ErrorServicio("Los ejemplares no pueden cer 0");
        }
        if(nombreAutor==null){
            throw new ErrorServicio("Debe indicar un autor");
        }
        if(nombreEditorial==null){
            throw new ErrorServicio("Debe indicar una editorial");
        }
    }
    
    public Autor validarAutor(String nombreAutor) throws ErrorServicio{
        Autor autor;
        try {
             autor = autorServicio.buscarAutorPorNombre(nombreAutor);
        } catch (ErrorServicio e) {
            autorServicio.crearAutor(nombreAutor);
            autor = autorServicio.buscarAutorPorNombre(nombreAutor);
    }
        return autor;
}
    
    public Editorial validarEditorial(String nombreEditorial) throws ErrorServicio{
         Editorial editorial;
        try {
             editorial = editorialServicio.buscarEditorialPorNombre(nombreEditorial);
        } catch (ErrorServicio e) {
            editorialServicio.agregarEditorial(nombreEditorial);
            editorial = editorialServicio.buscarEditorialPorNombre(nombreEditorial);
    }
        return editorial;
    }
    
    
}