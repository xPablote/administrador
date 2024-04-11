package cl.lafabrica.administrador.service;

import cl.lafabrica.administrador.modelo.Usuario;
import cl.lafabrica.administrador.pojo.response.ResponseFirestore;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

@Service
public interface UsuarioService {

    ResponseFirestore saveUsuario(Usuario usuario) throws ExecutionException, InterruptedException, ParseException;

}
