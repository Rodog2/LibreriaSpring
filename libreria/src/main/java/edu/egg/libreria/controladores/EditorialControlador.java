
package edu.egg.libreria.controladores;

import edu.egg.libreria.entidades.Editorial;
import edu.egg.libreria.errores.ErrorServicio;

import edu.egg.libreria.servicios.EditorialServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/editorial")
public class EditorialControlador {

    @Autowired
    private EditorialServicio editorialServicio;
   
    
     @GetMapping("/ingresarEditorial")
    public String ingresoEditorial() {
        return "ingresarEditorial.html";
    }
    
     @PostMapping("/ingresarEditorial")
    public String ingresarEditorial(ModelMap modelo, @RequestParam String nombre) {
        try {
            editorialServicio.agregarEditorial(nombre);
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            return "ingresarEditorial.html";
        }
        modelo.put("titulo","Felicitaciones");
        modelo.put("descripcion", "Se ha cargado la editorial correctamente");
        return "/exito";
    }
    
     @GetMapping("/listarEditorial")
    public String listaEditorial(ModelMap modelo) {
       
        try {
            List<Editorial> editoriales = editorialServicio.listarEditorial();
            modelo.put("editoriales", editoriales);
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "index.html";
        }
      
        return "listarEditorial.html";
    }
    @GetMapping("/modificarEditorial/{id}")
    public String modificarEditorial(ModelMap modelo,@PathVariable String id){
        modelo.put("editorial", editorialServicio.buscarEditorialPorId(id));
        return "modificarEditorial.html";
    }
    
    @PostMapping("/modificarEditorial/{id}")
    public String modificarEditorial(ModelMap modelo,@RequestParam String nombre,@PathVariable String id){
        try {
           editorialServicio.modificarEditorial(id, nombre);
           modelo.put("titulo", "Bien hecho");
           modelo.put("descripcion", "Se ha modificado la editorial correctamente");
           return "/exito";
        } catch (Exception e) {
            modelo.put("error", "Lo siento, algo ha salido mal");
            return "modificarEditorial.html";
        }
    }
    @GetMapping("/bajaEditorial/{id}")
    public String baja(ModelMap modelo,@PathVariable String id){
       
        try {
            editorialServicio.bajaEditorialId(id);
            modelo.put("titulo","Se ha dado de baja la editorial correctamente");
            return "/exito";
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "listarEditorial.html";
        }
    }
       @GetMapping("/altaEditorial/{id}")
    public String alta(ModelMap modelo,@PathVariable String id){
       
        try {
            editorialServicio.altaEditorialId(id);
            modelo.put("titulo","Se ha dado de alta la editorial correctamente");
            return "/exito";
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "listarEditorial.html";
        }
    }
}