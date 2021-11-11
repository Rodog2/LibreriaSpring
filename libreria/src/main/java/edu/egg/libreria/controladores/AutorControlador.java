
package edu.egg.libreria.controladores;

import edu.egg.libreria.entidades.Autor;
import edu.egg.libreria.errores.ErrorServicio;
import edu.egg.libreria.repositorios.AutorRepositorio;
import edu.egg.libreria.servicios.AutorServicio;
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
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    
     @GetMapping("/ingresoAutores")
    public String ingresoAutor() {
        return "ingresoAutores.html";
    }
    
     @PostMapping("/ingresarAutores")
    public String ingresarAutor(ModelMap modelo, @RequestParam String nombre) {
        try {
            autorServicio.crearAutor(nombre);
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            return "ingresoAutores.html";
        }
        modelo.put("titulo","Felicitaciones");
        modelo.put("descripcion", "Se ha cargado el autor correctamente");
        return "/exito";
    }
    
     @GetMapping("/listarAutores")
    public String listaAutores(ModelMap modelo) {
        List<Autor> autores;
        try {
            autores = autorServicio.listarAutores();
            modelo.put("autores", autores);
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "listarAutores.html";
        }
      
        return "listarAutores.html";
    }
    @GetMapping("/modificarAutores/{id}")
    public String modificarAutor(ModelMap modelo,@PathVariable String id){
        modelo.put("autor", autorServicio.buscarAutorPorId(id));
        return "modificarAutores.html";
    }
    
    @PostMapping("/modificarAutores/{id}")
    public String modificarAutor(ModelMap modelo,@RequestParam String nombre,@PathVariable String id){
        try {
           autorServicio.modificarAutor(id, nombre);
           modelo.put("titulo", "Bien hecho");
           modelo.put("descripcion", "Se ha modificado el autor correctamente");
           return "/exito";
        } catch (Exception e) {
            modelo.put("error", "Lo siento, algo ha salido mal");
            return "modificarAutores.html";
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
//     @PostMapping("/bajaAutores")
//    public String baja(ModelMap modelo,@PathVariable String id){
//       
//        try {
//            autorServicio.bajaAutorId(id);
//            modelo.put("titulo","Se ha dado de baja el autor correctamente");
//            return "/exito";
//        } catch (ErrorServicio ex) {
//            modelo.put("error", ex.getMessage());
//            return "listarAutores.html";
//        }
//    }
//    
//      @PostMapping("/modificarAutor/{id}")
//    public String modificar(ModelMap modelo, @PathVariable String id, @RequestParam String nombre) {
//
//        try {
//            autorServicio.modificarAutor(id, nombre);
//            modelo.put("exito", "Modificacion exitosa");
//            List<Autor> autores = autorServicio.listarAutores();
//            modelo.put("autores", autores);
//            return "listaAutores.html";
//        } catch (ErrorServicio ex) {
//            modelo.put("error", "Falto algun dato");
//            List<Autor> autores = autorServicio.listarAutores();
//            modelo.put("autores", autores);
//            return "listaAutores.html";
//        }
//    }
}
