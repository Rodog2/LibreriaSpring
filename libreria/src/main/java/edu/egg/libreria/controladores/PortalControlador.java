    
package edu.egg.libreria.controladores;

import edu.egg.libreria.errores.ErrorServicio;
import edu.egg.libreria.servicios.UsuarioServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalControlador {

    
    @Autowired
    UsuarioServicio usuarioServicio;
    
    @GetMapping("/")
    public String index() {
        return "index.html";
    }
    @GetMapping("/exito")
    public String exito() {
        return "exito.html";
    }
    @GetMapping("/login")
    public String login(ModelMap model, @RequestParam(required = false) String error){
        if(error!= null){
          model.put("error", "El usuario o la contraseña son incorrectos");  
        }
        return "login.html"; 
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(){
        return "inicio.html";
    }
    
    @GetMapping("/registro")
    public String registro(){
        return "registroUsuario.html";
    }
    @PostMapping("/registrar")
    public String registrar(ModelMap model,
                            @RequestParam String nombre,
                            @RequestParam String apellido,
                            @RequestParam String mail,
                            @RequestParam String clave1,
                            @RequestParam String clave2){
        
        try {
            usuarioServicio.registrar(nombre, apellido, mail, clave1, clave2);
            model.put("titulo", "Se ha registrado el Usuario exitosamente");
            return "exito.html";
        } catch (ErrorServicio ex) {
            Logger.getLogger(PortalControlador.class.getName()).log(Level.SEVERE, null, ex);
            model.put("error", ex.getMessage());
            model.put("nombre", nombre);
            model.put("apellido", apellido);
            model.put("mail", mail);
            return "registroUsuario.html";
        }
        
    }
}
