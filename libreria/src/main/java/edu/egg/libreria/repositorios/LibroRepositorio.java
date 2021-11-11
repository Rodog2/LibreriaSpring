
package edu.egg.libreria.repositorios;

import edu.egg.libreria.entidades.Libro;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String>{

    @Query("SELECT c FROM Libro c WHERE c.titulo = :titulo")
    public Libro BuscarLibroPorNombre(@Param("titulo") String titulo);  
    
    @Query("SELECT c FROM Libro c")
    public List<Libro> buscarTodosLosLibros();
    
  
}
