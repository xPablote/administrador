package cl.lafabrica.administrador.service;

import cl.lafabrica.administrador.modelo.TipoPlan;
import cl.lafabrica.administrador.pojo.response.ResponseFirestoreTipoPlan;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public interface TipoPlanService {

    ResponseFirestoreTipoPlan createTipoPlan(TipoPlan tipoPlan) throws ExecutionException, InterruptedException, ParseException;
    TipoPlan getTipoPlan(String idTipoPlan) throws ExecutionException, InterruptedException, ParseException;
    List<TipoPlan> listTipoPlanes() throws ExecutionException, InterruptedException;
    ResponseFirestoreTipoPlan updateTipoPlan(TipoPlan tipoPlan) throws ExecutionException, InterruptedException;
}
