package cl.lafabrica.administrador.service;

import cl.lafabrica.administrador.modelo.TipoPlan;
import cl.lafabrica.administrador.pojo.response.ResponseFirestore;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public interface TipoPlanService {

    ResponseFirestore createTipoPlan(TipoPlan tipoPlan) throws ExecutionException, InterruptedException, ParseException;
    TipoPlan getTipoPlan(String idTipoPlan) throws ExecutionException, InterruptedException, ParseException;
    List<TipoPlan> listTipoPlanes() throws ExecutionException, InterruptedException;
    ResponseFirestore updateTipoPlan(TipoPlan tipoPlan) throws ExecutionException, InterruptedException;
}
