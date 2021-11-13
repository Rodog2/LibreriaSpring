
package edu.egg.libreria.servicios;

import edu.egg.libreria.entidades.Editorial;
import edu.egg.libreria.errores.ErrorServicio;
import edu.egg.libreria.repositorios.EditorialRepositorio;
import java.util.List;
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
    public void eliminarEditorial(String id) throws ErrorServicio{
        Optional<Editorial> respuesta = editRepos.findById(id);
        Editorial editorial;
        if(respuesta.isPresent()){
            editorial = respuesta.get();
        }else{
            throw new ErrorServicio("No existe la editorial a eliminar");
        }
        editRepos.delete(editorial);
    }
    
    public Editorial buscarEditorialPorNombre(String nombreEditorial) throws ErrorServicio{
        validar(nombreEditorial);
        Editorial editorial = editRepos.BuscarEditorialPorNombre(nombreEditorial);
        if(editorial ==null){
           throw new ErrorServicio("La editorial no puede ser nula");
        } else {
            return editorial;
        }
    }
     public List<Editorial> listarEditorial() throws ErrorServicio{
         
        return editRepos.listarEditorial();
    }
 
    @Transactional
    public void bajaEditorialId(String id) throws ErrorServicio{
        Editorial editorial= new Editorial();
        Optional<Editorial> respuesta = editRepos.findById(id);
        if(respuesta.isPresent()){
            editorial= respuesta.get();
        }
        editorial.setAlta(false);
        editRepos.save(editorial);
    }
    @Transactional
      public void altaEditorialId(String id) throws ErrorServicio{
         Editorial editorial= new Editorial();
        Optional<Editorial> respuesta = editRepos.findById(id);
        if(respuesta.isPresent()){
            editorial= respuesta.get();
        }
        editorial.setAlta(true);
        editRepos.save(editorial);
    }
    
    public Editorial buscarEditorialPorId(String id){
        Editorial editorial = new Editorial();
        Optional<Editorial> respuesta = editRepos.findById(id);
        if(respuesta.isPresent()){
            editorial = respuesta.get();
        }
        return editorial;
    }
    public void validar(String nombre) throws ErrorServicio{
        if(nombre ==null){
            throw new ErrorServicio("El nombre de la editorial no puede estar vacio");
        }
    }
    
     public Editorial verEditorialRepetida(String nombreEditorial) throws ErrorServicio{
         Editorial editorial;
        try {
             editorial = buscarEditorialPorNombre(nombreEditorial);
        } catch (ErrorServicio e) {
            agregarEditorial(nombreEditorial);
            editorial = buscarEditorialPorNombre(nombreEditorial);
    }
        return editorial;
    }
     
    
}
