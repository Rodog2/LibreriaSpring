
package edu.egg.libreria.controladores;

import edu.egg.libreria.entidades.Usuario;
import edu.egg.libreria.errores.ErrorServicio;
import edu.egg.libreria.servicios.UsuarioServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    
    @Autowired
    private UsuarioServicio usuarioServicio;
   
    
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/editar-perfil")
    public String editarPerfil(HttpSession session, @RequestParam (required = false) String id, ModelMap modelo ){
        
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if(login==null || !login.getId().equals(id)){
            return "redirect:/inicio";
        }
            try {
            Usuario usuario= usuarioServicio.buscarPorID(id);
            modelo.addAttribute("perfil",usuario);
        } catch (ErrorServicio e) {
            modelo.addAttribute("error",e.getMessage());
        }
        return "perfilUsuario.html";
    }
    
    
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/actualizar-perfil")
    public String actualizarPerfil(ModelMap modelo, HttpSession session, @RequestParam String id,@RequestParam String nombre,@RequestParam String apellido,@RequestParam String mail,@RequestParam String clave1,@RequestParam String clave2) {
        Usuario usuario = null;
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if(login==null || !login.getId().equals(id)){
            return "redirect:/inicio";
        }
        try {
            usuario = usuarioServicio.buscarPorID(id);
            usuarioServicio.modificar(nombre, apellido, mail, clave1, clave2, id);
            session.setAttribute("usuariosession", usuario);
            return "redirect:/inicio";
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            modelo.put("perfil", usuario);
            return "perfilUsuario.html";
        }
    }
    
}
