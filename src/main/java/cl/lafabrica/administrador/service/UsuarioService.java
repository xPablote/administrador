package cl.lafabrica.administrador.service;

import cl.lafabrica.administrador.modelo.Usuario;
import cl.lafabrica.administrador.pojo.response.ResponseFirestore;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public interface UsuarioService {

    ResponseFirestore createUsuario(Usuario usuario) throws ExecutionException, InterruptedException, ParseException;
    Usuario getUsuario(String rut) throws ExecutionException, InterruptedException, ParseException;
    List<Usuario> getUsuarios() throws ExecutionException, InterruptedException;
    ResponseFirestore updateUsuario(Usuario usuario) throws ExecutionException, InterruptedException;
}
