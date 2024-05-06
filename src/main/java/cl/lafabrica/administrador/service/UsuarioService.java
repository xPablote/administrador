package cl.lafabrica.administrador.service;

import cl.lafabrica.administrador.modelo.Estado;
import cl.lafabrica.administrador.modelo.Usuario;
import cl.lafabrica.administrador.pojo.response.ResponseFirestoreUsuario;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public interface UsuarioService {

    ResponseFirestoreUsuario createUsuario(Usuario usuario) throws ExecutionException, InterruptedException, ParseException;
    Usuario getUsuario(String rut) throws ExecutionException, InterruptedException, ParseException;
    List<Usuario> getUsuarios() throws ExecutionException, InterruptedException;
    ResponseFirestoreUsuario updateUsuario(Usuario usuario) throws ExecutionException, InterruptedException;
    ResponseFirestoreUsuario changeStateUsuario(String run, Estado nuevoEstado) throws InterruptedException, ExecutionException;
}
