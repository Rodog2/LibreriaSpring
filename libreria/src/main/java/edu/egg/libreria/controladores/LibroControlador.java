
package edu.egg.libreria.controladores;

import edu.egg.libreria.servicios.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libro")
public class LibroControlador {
    @Autowired
    private LibroServicio libroServicio;
    
    @GetMapping("/ingresoLibro")
    public String ingresoLibro(){
        return "ingresoLibros.html";
    }
    @PostMapping("/ingresarLibro")
    public String ingresarLibro(ModelMap modelo, @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam String nombreAutor, @RequestParam String nombreEditorial){
        try {
            libroServicio.cargarLibro(isbn, titulo, anio, ejemplares, nombreAutor, nombreEditorial);
            modelo.put("titulo","Se ha cargado el libro correctamente");
            return "exito.html";
        } catch (Exception e) {
            modelo.put("error",e.getMessage());
            modelo.put("isbn",isbn);
            modelo.put("titulo",titulo);
            modelo.put("anio",anio);
            modelo.put("ejemplares",ejemplares);
            modelo.put("nombreAutor",nombreAutor);
            modelo.put("nombreEditorial",nombreEditorial);
            return "ingresoLibros.html";
        }
        
    }
}
