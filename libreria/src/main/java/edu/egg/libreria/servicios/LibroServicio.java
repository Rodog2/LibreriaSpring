
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
        Autor autor = autorServicio.verAutorRepetido(nombreAutor);
        Editorial editorial = editorialServicio.verEditorialRepetida(nombreEditorial);
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
    public void modificarLibro(String id, Long isbn, String titulo, Integer anio, Integer ejemplares) throws ErrorServicio{
        validarModificacion(isbn, titulo, anio, ejemplares);
        Libro libro = new Libro();
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if(respuesta.isPresent()){
            libro = respuesta.get();
        }
        libro.setTitulo(titulo);
        libro.setIsbn(isbn);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        
        libroRepositorio.save(libro);
        
    }
    
    
    public void borrarLibro(String titulo) throws ErrorServicio{
        if(titulo==null){
            throw new ErrorServicio("El titulo no puede estar vacio");
        }
        Libro libro= libroRepositorio.BuscarLibroPorNombre(titulo);
        if(libro.getTitulo().equalsIgnoreCase(titulo)){
            libroRepositorio.delete(libro);
        } else{
            throw new ErrorServicio("El titulo no coincide con ningun libro registrado");
        }
        
    }
    
    public List<Libro> listarLibros(){
        List<Libro> listaLibros = libroRepositorio.buscarTodosLosLibros();
        return listaLibros;
    }
    
    @Transactional
    public void bajaLibroId(String id) throws ErrorServicio{
        Libro libro= new Libro();
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if(respuesta.isPresent()){
            libro= respuesta.get();
        }
        libro.setAlta(false);
        libroRepositorio.save(libro);
    }
    @Transactional
      public void altaLibroId(String id) throws ErrorServicio{
      Libro libro= new Libro();
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if(respuesta.isPresent()){
            libro= respuesta.get();
        }
        libro.setAlta(true);
        libroRepositorio.save(libro);
    }
    
    public Libro buscarLibroPorId(String id){
      Libro libro= new Libro();
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if(respuesta.isPresent()){
            libro = respuesta.get();
        }
        return libro;
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
            throw new ErrorServicio("Los ejemplares no pueden ser 0");
        }
        if(nombreAutor==null){
            throw new ErrorServicio("Debe indicar un autor");
        }
        if(nombreEditorial==null){
            throw new ErrorServicio("Debe indicar una editorial");
        }
    }
    public void validarModificacion(Long isbn, String titulo, Integer anio, Integer ejemplares) throws ErrorServicio{
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
    }
    
    public Editorial verEditorialesAsignadas(String idEditorial) throws ErrorServicio{
        Editorial editorial = editorialServicio.buscarEditorialPorId(idEditorial);
        List<Libro> listaLibros = listarLibros();
        Integer cont= 0;
        for (Libro aux : listaLibros) {
            if(aux.getEditorial().getNombre().equals(editorial.getNombre())){
                cont ++;
            }
        }
        if(cont==0){
            return editorial;
        }else{
                throw new ErrorServicio("No puede borrar La editorial selecionada, al encontrase asignada a un libro");
            }
        }
    
     public Autor verAutoresAsignados(String idAutor) throws ErrorServicio{
        Autor autor = autorServicio.buscarAutorPorId(idAutor);
        List<Libro> listaLibros = listarLibros();
        Integer cont= 0;
        for (Libro aux : listaLibros) {
            if(aux.getAutor().equals(autor)){
                cont ++;
            }
        }
        if(cont==0){
            return autor;
        }else{
                throw new ErrorServicio("No puede borrar el autor al encontrase asignado a un libro");
            }
        }
}