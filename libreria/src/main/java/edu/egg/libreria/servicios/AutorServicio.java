
package edu.egg.libreria.servicios;

import edu.egg.libreria.entidades.Autor;
import edu.egg.libreria.errores.ErrorServicio;
import edu.egg.libreria.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;
    @Transactional
    public void crearAutor(String nombre) throws ErrorServicio{
        validar(nombre);
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(true);
        autorRepositorio.save(autor);
    }
    @Transactional
    public void modificarAutor(String idAutor, String nombre)throws ErrorServicio{
        Optional<Autor> respuesta = autorRepositorio.findById(idAutor);
        if(respuesta.isPresent()){
           Autor autor = respuesta.get();
            autor.setNombre(nombre);
            autorRepositorio.save(autor);
        }else {
            throw new ErrorServicio("No existe el Autor que quiere modificar");
        }
    }
    
    public Autor buscarAutorPorNombre(String nombre) throws ErrorServicio{
        validar(nombre);
        Autor autor = autorRepositorio.BuscarAutorPorNombre(nombre);
        if(autor == null){
            throw new ErrorServicio("No existe el autor buscado");
        }else {
         return autor;   
        }
    }
    public List<Autor> listarAutores() throws ErrorServicio{
        return autorRepositorio.listarAutores();
    }
    @Transactional
    public void bajaAutorNombre(String nombre) throws ErrorServicio{
        validar(nombre);
        Autor autor = autorRepositorio.BuscarAutorPorNombre(nombre);
        if(autor == null){
            throw new ErrorServicio("No existe el autor buscado");
        }else {
            autor.setAlta(false);
        }
    }
    
    public void bajaAutorId(String id) throws ErrorServicio{
        Autor autor= new Autor();
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if(respuesta.isPresent()){
            autor= respuesta.get();
        }
        autor.setAlta(false);
        autorRepositorio.save(autor);
    }
      public void altaAutorId(String id) throws ErrorServicio{
        Autor autor= new Autor();
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if(respuesta.isPresent()){
            autor= respuesta.get();
        }
        autor.setAlta(true);
        autorRepositorio.save(autor);
    }
    
    public Autor buscarAutorPorId(String id){
        Autor autor = new Autor();
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if(respuesta.isPresent()){
            autor = respuesta.get();
        }
        return autor;
    }
    
    public void validar(String nombre) throws ErrorServicio{
        if(nombre ==null){
            throw new ErrorServicio("El nombre del autor no puede estar vacío");
        }
       
        
    }
    
}
