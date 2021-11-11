
package edu.egg.libreria.servicios;

import edu.egg.libreria.entidades.Editorial;
import edu.egg.libreria.errores.ErrorServicio;
import edu.egg.libreria.repositorios.EditorialRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editRepos;
    @Transactional
    public void agregarEditorial(String nombre) throws ErrorServicio{
        validar(nombre);
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(true);
            editRepos.save(editorial);
    }
    @Transactional
    public void modificarEditorial(String idEditorial, String nombre) throws ErrorServicio{
        validar(nombre);
        Optional<Editorial> respuesta = editRepos.findById(idEditorial);
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            editRepos.save(editorial);
        }else{
            throw new ErrorServicio("No existe una editorial con el nombre ingresado");
        }
    }
    @Transactional
    public void eliminarEditorial(String idEditorial) throws ErrorServicio{
        Optional<Editorial> respuesta = editRepos.findById(idEditorial);
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setAlta(false);
        }else{
            throw new ErrorServicio("No existe la editorial a eliminar");
        }
    }
    
    public Editorial buscarEditorialPorNombre(String nombre) throws ErrorServicio{
        validar(nombre);
        Editorial editorial = editRepos.BuscarEditorialPorNombre(nombre);
        if(editorial ==null){
           return null;
        } else return editorial;
    }
    
    public void validar(String nombre) throws ErrorServicio{
        if(nombre ==null){
            throw new ErrorServicio("El nombre de la editorial no puede estar vacio");
        }
    }
    
}
