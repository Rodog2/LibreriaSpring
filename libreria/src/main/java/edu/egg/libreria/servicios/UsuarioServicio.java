
package edu.egg.libreria.servicios;

import edu.egg.libreria.entidades.Rol;
import edu.egg.libreria.entidades.Usuario;
import edu.egg.libreria.errores.ErrorServicio;
import edu.egg.libreria.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio implements UserDetailsService{

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
     @Transactional
    public void registrar(String nombre, String apellido, String mail, String clave1, String clave2) throws ErrorServicio{
         Usuario usuario = new Usuario();
         if(!clave1.equals(clave2)){
            throw new ErrorServicio("Las claves ingresadas deben ser iguales");
        }
        validar(nombre, apellido, mail, clave1);
        
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        String encriptada = new BCryptPasswordEncoder().encode(clave1);
        usuario.setClave(encriptada);
        usuario.setAlta(new Date());
        usuario.setRol(Rol.USER);
        usuarioRepositorio.save(usuario);
    }
    @Transactional
    public void modificar(String nombre, String apellido, String mail, String clave1, String clave2, String id) throws ErrorServicio {
         Usuario usuario;
         if(!clave1.equals(clave2)){
            throw new ErrorServicio("Las claves ingresadas deben ser iguales");
        }
        validar(nombre, apellido, mail, clave1);
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setMail(mail);
            String encriptada = new BCryptPasswordEncoder().encode(clave1);
            usuario.setClave(encriptada);
            usuarioRepositorio.save(usuario);
        } else {
            throw new ErrorServicio("No se encontró el usuario soliitado");
        }
    }
    @Transactional
    public void deshabilitarUsuario(String id) throws ErrorServicio{
        Usuario usuario;
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            usuario = respuesta.get();
            usuario.setBaja(new Date());
            usuarioRepositorio.save(usuario);
        } else {
            throw new ErrorServicio("No se encontró el usuario soliitado");
        }
    }
    @Transactional
     public void habilitarUsuario(String id) throws ErrorServicio{
        Usuario usuario;
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            usuario = respuesta.get();
            usuario.setBaja(null);
            usuarioRepositorio.save(usuario);
        } else {
            throw new ErrorServicio("No se encontró el usuario soliitado");
        }
    }
     
     public Usuario buscarPorID(String id) throws ErrorServicio{
         
         Usuario usuario;
         Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
         if(respuesta.isPresent()){
             return usuario=respuesta.get();
         }else {
             throw new ErrorServicio("El id del usuario no puede estar vacio");
         }
     }
     
     public List<Usuario> listarUsuarios(){
         return usuarioRepositorio.listarUsuarios();
     }

    public void validar(String nombre, String apellido, String mail, String clave1) throws ErrorServicio{
        if(nombre==null || nombre.isEmpty()){
            throw new ErrorServicio("El nombre no debe estar vacio");
        }
        if(apellido==null || apellido.isEmpty()){
            throw new ErrorServicio("El apellido no debe estar vacio");
        }
        if(mail==null || mail.isEmpty()){
            throw new ErrorServicio("El mail no debe estar vacio");
        }     
        if(clave1==null || clave1.isEmpty() || clave1.length()<=6){
            throw new ErrorServicio("La clave no debe estar vacia y tiene que tener mas de 6 dígitos");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
    Usuario usuario= usuarioRepositorio.buscarPorMail(mail);
    if(usuario!= null){
        List<GrantedAuthority> permisos= new ArrayList<>();
        GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_"+usuario.getRol());
        permisos.add(p1);
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        session.setAttribute("usuariosession", usuario);
        
        User user = new User(usuario.getMail(), usuario.getClave(), permisos);
        return user;
    }else{
        return null;
    }
    }
    
}
