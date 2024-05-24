package cl.lafabrica.administrador.service;

import cl.lafabrica.administrador.model.Pago;
import cl.lafabrica.administrador.response.ResponseFirestore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public interface PagosService {

    ResponseFirestore createPago(Pago pago) throws ExecutionException, InterruptedException;

    Pago getPago(String run, String idPlan) throws ExecutionException, InterruptedException;

    List<Pago> listPagos() throws ExecutionException, InterruptedException;

    ResponseFirestore updatePago(Pago updatePago) throws ExecutionException, InterruptedException;
}
