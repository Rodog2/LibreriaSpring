
package edu.egg.libreria.controladores;

import edu.egg.libreria.entidades.Autor;
import edu.egg.libreria.entidades.Editorial;
import edu.egg.libreria.errores.ErrorServicio;
import edu.egg.libreria.servicios.AutorServicio;
import edu.egg.libreria.servicios.EditorialServicio;
import edu.egg.libreria.servicios.LibroServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping("/ingresoLibro")
    public String ingresoLibro(ModelMap modelo){
        try {
            List<Autor> autores = autorServicio.listarAutores();
            List<Editorial> editoriales= editorialServicio.listarEditorial();
            modelo.put("autores", autores);
            modelo.put("editoriales", editoriales);
        } catch (ErrorServicio ex) {
            
        }
        return "ingresoLibros.html";
    }
    @PostMapping("/ingresarLibro")
    public String ingresarLibro(ModelMap modelo, @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam String nombreAutor, @RequestParam String nombreEditorial){
//        try {
//            libroServicio.cargarLibro(isbn, titulo, anio, ejemplares, nombreAutor, nombreEditorial);
//            modelo.put("titulo","Se ha cargado el libro correctamente");
//            return "exito.html";
//        } catch (Exception e) {
//            modelo.put("error",e.getMessage());
//            modelo.put("isbn",isbn);
//            modelo.put("titulo",titulo);
//            modelo.put("anio",anio);
//            modelo.put("ejemplares",ejemplares);
//            modelo.put("nombreAutor",nombreAutor);
//            modelo.put("nombreEditorial",nombreEditorial);
//            return "ingresoLibros.html";
//        }
//        
//    }
        System.out.println("isbn: "+ isbn);
        System.out.println("titulo "+titulo);
            System.out.println("anio "+ anio);
            System.out.println("ejemplares"+ ejemplares);
            System.out.println("nombreAutor "+nombreAutor);
            System.out.println("nombreEditorial "+nombreEditorial);
            return "ingresarLibro.html";
}
}