package cl.lafabrica.administrador.service.impl;

import cl.lafabrica.administrador.model.PlanUsuario;
import cl.lafabrica.administrador.response.ResponseFirestore;
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
public class PlanUsuarioServiceImpl implements PlanUsuarioService {
    private static final String FIRESTORE_COLLECTION = "PlanesUsuarios";

    private static final Logger logger = LoggerFactory.getLogger(PlanUsuarioServiceImpl.class);

    @Autowired
    private Firestore firestore;

    private String fechaActual(){
        LocalDateTime requestDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return requestDateTime.format(formatter);
    }

    @Override
    public ResponseFirestore createPlanUsuario(PlanUsuario planUsuario) throws ExecutionException, InterruptedException {
        logger.info("[PlanUsuarioServiceImpl] ::: Iniciando el método createPlanUsuario() ::: "+ planUsuario);
        firestore = FirestoreClient.getFirestore();
        ApiFuture<DocumentReference> documentReference  = firestore.collection(FIRESTORE_COLLECTION).add(planUsuario);
        ResponseFirestore respuesta = new ResponseFirestore();
        if (documentReference.get().get().isCancelled()) {
            respuesta.setId(documentReference.get().getId());
            respuesta.setFecha(fechaActual());
            respuesta.setMensaje("PlanUsuario ID: " + documentReference.get().getId() + " ya existe");
            logger.info("[PlanUsuarioServiceImpl] ::: Fin del método createPlanUsuario() ::: PlanUsuario existente "+ planUsuario);
            return respuesta;
        }
        ApiFuture<WriteResult> writeResultApiFuture = documentReference.get().set(planUsuario);
        String id = documentReference.get().getId();
        respuesta.setId(id);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = writeResultApiFuture.get().getUpdateTime().toDate();
        String fechaCreacion = formatter.format(fecha);
        respuesta.setFecha(fechaCreacion);
        respuesta.setMensaje("PlanUsuario ID: " + id + " agregado correctamente");
        logger.info("[PlanUsuarioServiceImpl] ::: Fin del método createPlanUsuario() ::: Plan creado exitosamente: "+ id);
        return respuesta;
    }

    @Override
    public PlanUsuario getPlanUsuario(String run, String idPlan) throws ExecutionException, InterruptedException {
        logger.info("[PlanUsuarioServiceImpl] ::: Iniciando el método getPlanUsuario() ::: "+ idPlan);
        firestore = FirestoreClient.getFirestore();
        Query query  = firestore.collection(FIRESTORE_COLLECTION).whereEqualTo("run", run).whereEqualTo("idPlan", idPlan);
        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot querySnapshot = future.get();
        PlanUsuario planUsuario = new PlanUsuario();
        if (!querySnapshot.isEmpty()) {
            QueryDocumentSnapshot document = querySnapshot.getDocuments().get(0);
            logger.info("[ColaboradorServiceImpl] ::: Fin del método getPlanUsuario() ::: PlanUsuario obtenido exitosamente: "+document);
            return this.getMapPlanUsuario(planUsuario, document);
        } else {
            logger.info("[PlanUsuarioServiceImpl] ::: Fin del método getPlanUsuario() ::: PlanUsuario inexistente: ");
            return planUsuario;
        }
    }

    @Override
    public List<PlanUsuario> listPlanesUsuarios() throws ExecutionException, InterruptedException {
        logger.info("[PlanUsuarioServiceImpl] ::: Iniciando el método listPlanesUsuarios() ::: ");
        firestore = FirestoreClient.getFirestore();
        QuerySnapshot querySnapshot = firestore.collection(FIRESTORE_COLLECTION).get().get();
        List<PlanUsuario> planesUsuarios = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
            PlanUsuario planUsuario = new PlanUsuario();
            planUsuario.setIdPlan(document.getString("idPlan"));
            planUsuario.setRun(document.getString("run"));
            planUsuario.setNombrePlan(document.getString("nombrePlan"));
            planUsuario.setNombreUsuario(document.getString("nombreUsuario"));
            planUsuario.setApellidoUsuario(document.getString("apellidoUsuario"));
            planUsuario.setFechaRegistroPlan(document.getTimestamp("fechaRegistroPlan").toSqlTimestamp());
            planUsuario.setFechaInicio(document.getTimestamp("fechaInicio").toSqlTimestamp());
            planUsuario.setFechaFin(document.getTimestamp("fechaFin").toSqlTimestamp());
            planUsuario.setMonto(document.getLong("monto"));
            planUsuario.setMetodoPago(document.getString("metodoPago"));
            planUsuario.setDescuento(document.getLong("descuento"));
            planesUsuarios.add(planUsuario);
        }
        logger.info("[PlanUsuarioServiceImpl] ::: Fin del método listPlanesUsuarios() ::: "+ planesUsuarios);
        return planesUsuarios;
    }

    @Override
    public ResponseFirestore updatePlanUsuario(PlanUsuario updatePlanUsuario) throws ExecutionException, InterruptedException {
        logger.info("[PlanUsuarioServiceImpl] ::: Iniciando el método updatePlanUsuario() ::: "+ updatePlanUsuario);
        firestore = FirestoreClient.getFirestore();
        Query query  = firestore.collection(FIRESTORE_COLLECTION).whereEqualTo("run", updatePlanUsuario.getRun()).limit(1).whereEqualTo("idPlan", updatePlanUsuario.getIdPlan()).limit(1);
        ResponseFirestore respuesta = new ResponseFirestore();
        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot querySnapshot = future.get();
        if (!querySnapshot.isEmpty()){
            QueryDocumentSnapshot document = querySnapshot.getDocuments().get(0);
            firestore.collection(FIRESTORE_COLLECTION).document(document.getId()).set(updatePlanUsuario);
            logger.info("[PlanUsuarioServiceImpl] ::: Fin del método updatePlanUsuario() ::: PlanUsuario modificado exitosamente ID: "+document.getId());
            respuesta.setId(document.getId());
            String fechaCreacion = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            respuesta.setFecha(fechaCreacion);
            respuesta.setMensaje("Actualización PlanUsuario: "+ document.getId() +", Exitosa");
        }else{
            logger.info("[PlanUsuarioServiceImpl] ::: Fin del método updatePlanUsuario() ::: PlanUsuario inexistente ");
            respuesta.setFecha(fechaActual());
            respuesta.setMensaje("PlanUsuario Inexistente");
        }
        return  respuesta;
    }

    private PlanUsuario getMapPlanUsuario(PlanUsuario planUsuario, DocumentSnapshot documentSnapshot) {
        planUsuario.setRun(documentSnapshot.getString("run"));
        planUsuario.setIdPlan(documentSnapshot.getString("idPlan"));
        planUsuario.setNombrePlan(documentSnapshot.getString("nombrePlan"));
        planUsuario.setNombreUsuario(documentSnapshot.getString("nombreUsuario"));
        planUsuario.setApellidoUsuario(documentSnapshot.getString("apellidoUsuario"));
        planUsuario.setFechaRegistroPlan(documentSnapshot.getTimestamp("fechaRegistroPlan").toSqlTimestamp());
        planUsuario.setFechaInicio(documentSnapshot.getTimestamp("fechaInicio").toSqlTimestamp());
        planUsuario.setFechaFin(documentSnapshot.getTimestamp("fechaFin").toSqlTimestamp());
        planUsuario.setMonto(documentSnapshot.getLong("monto"));
        planUsuario.setMetodoPago(documentSnapshot.getString("metodoPago"));
        planUsuario.setDescuento(documentSnapshot.getLong("descuento"));
        return planUsuario;
    }

}
