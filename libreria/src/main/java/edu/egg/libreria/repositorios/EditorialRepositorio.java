
package edu.egg.libreria.repositorios;

import edu.egg.libreria.entidades.Editorial;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String>{

     @Query("SELECT c FROM Editorial c WHERE c.nombre = :nombre")
    public Editorial BuscarEditorialPorNombre(@Param("nombre")String nombre);  
  
    
}
