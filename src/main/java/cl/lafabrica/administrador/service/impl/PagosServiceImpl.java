package cl.lafabrica.administrador.service.impl;

import cl.lafabrica.administrador.model.Pago;
import cl.lafabrica.administrador.model.PlanUsuario;
import cl.lafabrica.administrador.response.ResponseFirestore;
import cl.lafabrica.administrador.service.PagosService;
import cl.lafabrica.administrador.service.PlanUsuarioService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class PagosServiceImpl implements PagosService {
    private static final String FIRESTORE_COLLECTION = "Pagos";

    private static final Logger logger = LoggerFactory.getLogger(PagosServiceImpl.class);

    @Autowired
    private Firestore firestore;

    private String fechaActual(){
        LocalDateTime requestDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return requestDateTime.format(formatter);
    }

    @Override
    public ResponseFirestore createPago(Pago pago) throws ExecutionException, InterruptedException {
        logger.info("[PagosServiceImpl] ::: Iniciando el método createPago() ::: "+ pago);
        firestore = FirestoreClient.getFirestore();
        ApiFuture<DocumentReference> documentReference  = firestore.collection(FIRESTORE_COLLECTION).add(pago);
        ResponseFirestore respuesta = new ResponseFirestore();
        if (documentReference.get().get().isCancelled()) {
            respuesta.setId(documentReference.get().getId());
            respuesta.setFecha(fechaActual());
            respuesta.setMensaje("Pago ID: " + documentReference.get().getId() + " ya existe");
            logger.info("[PagosServiceImpl] ::: Fin del método createPago() ::: Pago existente "+ pago);
            return respuesta;
        }
        ApiFuture<WriteResult> writeResultApiFuture = documentReference.get().set(pago);
        String id = documentReference.get().getId();
        respuesta.setId(id);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = writeResultApiFuture.get().getUpdateTime().toDate();
        String fechaCreacion = formatter.format(fecha);
        respuesta.setFecha(fechaCreacion);
        respuesta.setMensaje("Pago ID: " + id + " agregado correctamente");
        logger.info("[PagosServiceImpl] ::: Fin del método createPago() ::: Pago creado exitosamente: "+ id);
        return respuesta;
    }

    @Override
    public Pago getPago(String run, String idPlan) throws ExecutionException, InterruptedException {
        logger.info("[PagosServiceImpl] ::: Iniciando el método getPago() ::: "+ idPlan);
        firestore = FirestoreClient.getFirestore();
        Query query  = firestore.collection(FIRESTORE_COLLECTION).whereEqualTo("run", run).whereEqualTo("idPlan", idPlan);
        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot querySnapshot = future.get();
        Pago pago = new Pago();
        if (!querySnapshot.isEmpty()) {
            QueryDocumentSnapshot document = querySnapshot.getDocuments().get(0);
            logger.info("[PagosServiceImpl] ::: Fin del método getPago() ::: PlanUsuario obtenido exitosamente: "+document);
            return this.getMapPago(pago, document);
        } else {
            logger.info("[PagosServiceImpl] ::: Fin del método getPago() ::: PlanUsuario inexistente: ");
            return pago;
        }
    }

    @Override
    public List<Pago> listPagos() throws ExecutionException, InterruptedException {
        logger.info("[PagosServiceImpl] ::: Iniciando el método listPagos() ::: ");
        firestore = FirestoreClient.getFirestore();
        QuerySnapshot querySnapshot = firestore.collection(FIRESTORE_COLLECTION).get().get();
        List<Pago> pagos = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
            Pago pago = new Pago();
            pago.setIdPlan(document.getString("idPlan"));
            pago.setRun(document.getString("run"));
            pago.setNombrePlan(document.getString("nombrePlan"));
            pago.setNombreUsuario(document.getString("nombreUsuario"));
            pago.setApellidoUsuario(document.getString("apellidoUsuario"));
            pago.setFechaPago(document.getTimestamp("fechaPago").toSqlTimestamp());
            pago.setMonto(document.getLong("monto"));
            pago.setMetodoPago(document.getString("metodoPago"));
            pago.setDescuento(document.getLong("descuento"));
            pagos.add(pago);
        }
        logger.info("[PagosServiceImpl] ::: Fin del método listPagos() ::: "+ pagos);
        return pagos;
    }

    @Override
    public ResponseFirestore updatePago(Pago updatePago) throws ExecutionException, InterruptedException {
        logger.info("[PagosServiceImpl] ::: Iniciando el método updatePago() ::: "+ updatePago);
        firestore = FirestoreClient.getFirestore();
        Query query  = firestore.collection(FIRESTORE_COLLECTION).whereEqualTo("run", updatePago.getRun()).limit(1).whereEqualTo("idPlan", updatePago.getIdPlan()).limit(1);
        ResponseFirestore respuesta = new ResponseFirestore();
        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot querySnapshot = future.get();
        if (!querySnapshot.isEmpty()){
            QueryDocumentSnapshot document = querySnapshot.getDocuments().get(0);
            firestore.collection(FIRESTORE_COLLECTION).document(document.getId()).set(updatePago);
            logger.info("[PagosServiceImpl] ::: Fin del método updatePago() ::: Pago modificado exitosamente ID: "+document.getId());
            respuesta.setId(document.getId());
            String fechaCreacion = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            respuesta.setFecha(fechaCreacion);
            respuesta.setMensaje("Actualización Pago: "+ document.getId() +", Exitosa");
        }else{
            logger.info("[PagosServiceImpl] ::: Fin del método updatePago() ::: Pago inexistente ");
            respuesta.setFecha(fechaActual());
            respuesta.setMensaje("Pago Inexistente");
        }
        return  respuesta;
    }

    private Pago getMapPago(Pago pago, DocumentSnapshot documentSnapshot) {
        pago.setRun(documentSnapshot.getString("run"));
        pago.setIdPlan(documentSnapshot.getString("idPlan"));
        pago.setNombrePlan(documentSnapshot.getString("nombrePlan"));
        pago.setNombreUsuario(documentSnapshot.getString("nombreUsuario"));
        pago.setApellidoUsuario(documentSnapshot.getString("apellidoUsuario"));
        pago.setFechaPago(documentSnapshot.getTimestamp("fechaPago").toSqlTimestamp());
        pago.setMonto(documentSnapshot.getLong("monto"));
        pago.setMetodoPago(documentSnapshot.getString("metodoPago"));
        pago.setDescuento(documentSnapshot.getLong("descuento"));
        return pago;
    }

}
