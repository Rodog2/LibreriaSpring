
package edu.egg.libreria.controladores;

import edu.egg.libreria.entidades.Usuario;
import edu.egg.libreria.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping("/dashboard")
    public String inicioAdmin(ModelMap model){
        List<Usuario> usuarios= usuarioServicio.listarUsuarios();
        model.put("usuarios", usuarios);
        return "index_1";
        
    }
    
}
