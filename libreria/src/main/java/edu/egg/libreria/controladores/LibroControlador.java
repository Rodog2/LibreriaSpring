
package edu.egg.libreria.controladores;

import edu.egg.libreria.entidades.Autor;
import edu.egg.libreria.entidades.Editorial;
import edu.egg.libreria.entidades.Libro;
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
import org.springframework.web.bind.annotation.PathVariable;
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
    @GetMapping
    public String listarLibros(ModelMap modelo){
        List<Libro> libros= libroServicio.listarLibros();
        modelo.put("libros", libros);
        return "listarLibros.html";
    }
    
    @GetMapping("/modificarLibro/{id}")
    public String modificarLibro(ModelMap modelo,@PathVariable String id){
        modelo.put("libro", libroServicio.buscarLibroPorId(id));
        return "modificarLibro.html";
    }
    
    @PostMapping("/modificarLibro/{id}")
    public String modificarLibro(ModelMap modelo,@RequestParam String titulo,@RequestParam Long isbn,@RequestParam Integer anio, 
            @RequestParam Integer ejemplares, @PathVariable String id){
        try {
           libroServicio.modificarLibro(id, isbn, titulo, anio, ejemplares);
           modelo.put("titulo", "Bien hecho");
           modelo.put("descripcion", "Se ha modificado el autor correctamente");
           return "/exito";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "modificarLibro.html";
        }
    }
    @GetMapping("/bajaAutores/{id}")
    public String baja(ModelMap modelo,@PathVariable String id){
       
        try {
            autorServicio.bajaAutorId(id);
            modelo.put("titulo","Se ha dado de baja el autor correctamente");
            return "/exito";
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "listarAutores.html";
        }
    }
       @GetMapping("/altaAutores/{id}")
    public String alta(ModelMap modelo,@PathVariable String id){
       
        try {
            autorServicio.altaAutorId(id);
            modelo.put("titulo","Se ha dado de baja el autor correctamente");
            return "/exito";
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "listarAutores.html";
        }
    }
    
}