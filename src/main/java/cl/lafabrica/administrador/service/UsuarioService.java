package cl.lafabrica.administrador.service;

import cl.lafabrica.administrador.model.Estado;
import cl.lafabrica.administrador.model.Usuario;
import cl.lafabrica.administrador.response.ResponseFirestoreUsuario;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public interface UsuarioService {

    ResponseFirestoreUsuario createUsuario(Usuario usuario) throws ExecutionException, InterruptedException, ParseException;
    Usuario getUsuario(String run) throws ExecutionException, InterruptedException, ParseException;
    List<Usuario> listUsuarios() throws ExecutionException, InterruptedException;
    ResponseFirestoreUsuario updateUsuario(Usuario usuario) throws ExecutionException, InterruptedException;
    ResponseFirestoreUsuario changeStateUsuario(String run, Estado nuevoEstado) throws InterruptedException, ExecutionException;
}
