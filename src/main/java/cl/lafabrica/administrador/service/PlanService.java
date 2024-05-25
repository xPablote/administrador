package cl.lafabrica.administrador.service;

import cl.lafabrica.administrador.model.Estado;
import cl.lafabrica.administrador.model.Plan;
import cl.lafabrica.administrador.response.ResponseFirestore;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public interface PlanService {

    ResponseFirestore createPlan(Plan plan) throws ExecutionException, InterruptedException, ParseException;
    Plan getPlan(String idPlan) throws ExecutionException, InterruptedException, ParseException;
    List<Plan> listPlanes() throws ExecutionException, InterruptedException;
    ResponseFirestore updatePlan(Plan plan) throws ExecutionException, InterruptedException;
    ResponseFirestore changeStatePlan(String idPlan, Estado nuevoEstado) throws InterruptedException, ExecutionException;
}
