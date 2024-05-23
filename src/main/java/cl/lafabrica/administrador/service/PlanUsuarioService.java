package cl.lafabrica.administrador.service;

import cl.lafabrica.administrador.model.PlanUsuario;
import cl.lafabrica.administrador.response.ResponseFirestore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public interface PlanUsuarioService {

    ResponseFirestore createPlanUsuario(PlanUsuario planUsuario) throws ExecutionException, InterruptedException;

    PlanUsuario getPlanUsuario(String run, String idPlan) throws ExecutionException, InterruptedException;

    List<PlanUsuario> listPlanesUsuarios() throws ExecutionException, InterruptedException;

    ResponseFirestore updatePlanUsuario(PlanUsuario planUsuario) throws ExecutionException, InterruptedException;
}
