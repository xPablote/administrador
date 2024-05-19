package cl.lafabrica.administrador.service;

import cl.lafabrica.administrador.modelo.Colaborador;
import cl.lafabrica.administrador.modelo.Estado;
import cl.lafabrica.administrador.pojo.response.ResponseFirestore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public interface ColaboradorService {

    ResponseFirestore createColaborador(Colaborador colaborador) throws ExecutionException, InterruptedException;

    Colaborador getColaborador(String username) throws ExecutionException, InterruptedException;

    List<Colaborador> listColaboradores() throws ExecutionException, InterruptedException;

    ResponseFirestore updateColaborador(Colaborador colaborador) throws ExecutionException, InterruptedException;

    ResponseFirestore changeStateColaborador(String username, Estado nuevoEstado) throws InterruptedException, ExecutionException;
}
