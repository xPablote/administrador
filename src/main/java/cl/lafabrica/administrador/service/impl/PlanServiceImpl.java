package cl.lafabrica.administrador.service.impl;

import cl.lafabrica.administrador.model.Estado;
import cl.lafabrica.administrador.model.Plan;
import cl.lafabrica.administrador.response.ResponseFirestore;
import cl.lafabrica.administrador.service.PlanService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class PlanServiceImpl implements PlanService {
    private static final String FIRESTORE_COLLECTION = "Planes";

    private static final Logger logger = LoggerFactory.getLogger(PlanServiceImpl.class);

    @Autowired
    private Firestore firestore;

    private String fechaActual(){
        LocalDateTime requestDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return requestDateTime.format(formatter);
    }

    @Override
    public ResponseFirestore createPlan(Plan plan) throws ExecutionException, InterruptedException {
        logger.info("[PlanServiceImpl] ::: Iniciando el método createPlan() ::: " + plan);
        firestore = FirestoreClient.getFirestore();
        CollectionReference collectionReference = firestore.collection(FIRESTORE_COLLECTION);
        ResponseFirestore respuesta = new ResponseFirestore();
        Query query = collectionReference.whereEqualTo("nombrePlan", plan.getNombrePlan());
        QuerySnapshot querySnapshot = query.get().get();
        if (!querySnapshot.isEmpty()) {
            respuesta.setId(null);
            respuesta.setFecha(fechaActual());
            respuesta.setMensaje("Ya existe un plan con el nombre " + plan.getNombrePlan());
            logger.info("[PlanServiceImpl] ::: Fin del método createPlan() ::: Plan existente con el nombre: " + plan.getNombrePlan());
            return respuesta;
        }
        int cantidadDocumentos = obtenerCantidadDocumentos(collectionReference);
        String idPlan = String.valueOf(cantidadDocumentos + 1);
        plan.setIdPlan(idPlan);
        DocumentReference documentReference = collectionReference.document(idPlan);
        ApiFuture<WriteResult> writeResultApiFuture = documentReference.set(plan);
        String id = documentReference.getId();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date fecha = writeResultApiFuture.get().getUpdateTime().toDate();
        String fechaCreacion = formatter.format(fecha);
        respuesta.setId(id);
        respuesta.setFecha(fechaCreacion);
        respuesta.setMensaje("Plan ID: " + id + " agregado correctamente");
        logger.info("[PlanServiceImpl] ::: Fin del método createPlan() ::: Plan creado exitosamente: " + id);
        return respuesta;
    }

    private int obtenerCantidadDocumentos(CollectionReference collectionReference) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = collectionReference.get();
        QuerySnapshot querySnapshot = querySnapshotApiFuture.get();
        return querySnapshot.size();
    }

    @Override
    public Plan getPlan(String nombrePlan) throws ExecutionException, InterruptedException, ParseException {
        logger.info("[PlanServiceImpl] ::: Iniciando el método getPlan() ::: "+ nombrePlan);
        firestore = FirestoreClient.getFirestore();
        CollectionReference collectionReference = firestore.collection(FIRESTORE_COLLECTION);
        Query query = collectionReference.whereEqualTo("nombrePlan", nombrePlan);
        QuerySnapshot querySnapshot = query.get().get();
        Plan plan = new Plan();
        if (querySnapshot.isEmpty()) {
            logger.info("[PlanServiceImpl] ::: Fin del método createPlan() ::: Plan existente con el nombre: " + nombrePlan);
            return plan;
        }
        QueryDocumentSnapshot document = querySnapshot.getDocuments().get(0);
        logger.info("[PlanServiceImpl] ::: Fin del método getPlan() ::: Plan obtenido exitosamente: "+ nombrePlan);
        return this.getMapPlan(plan, document);
    }

    @Override
    public List<Plan> listPlanes() throws ExecutionException, InterruptedException {
        logger.info("[PlanServiceImpl] ::: Iniciando el método listPlanes() ::: ");
        firestore = FirestoreClient.getFirestore();
        QuerySnapshot querySnapshot = firestore.collection(FIRESTORE_COLLECTION).get().get();
        List<Plan> planes = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
            Plan plan = new Plan();
            plan.setIdPlan(document.getId());
            plan.setNombrePlan(document.getString("nombrePlan"));
            plan.setDescripcionPlan(document.getString("descripcionPlan"));
            plan.setValorPlan(document.getLong("valorPlan"));
            String estadoString = document.getString("estado");
            Estado estado = Estado.valueOf(estadoString);
            plan.setEstado(estado);
            planes.add(plan);
        }
        logger.info("[PlanServiceImpl] ::: Fin del método listPlanes() ::: "+ planes);
        return planes;
    }

    @Override
    public ResponseFirestore updatePlan(Plan plan) throws ExecutionException, InterruptedException {
        logger.info("[PlanServiceImpl] ::: Iniciando el método updatePlan() ::: "+ plan.getNombrePlan());
        firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference  = firestore.collection(FIRESTORE_COLLECTION).document(plan.getNombrePlan());
        ResponseFirestore respuesta = new ResponseFirestore();
        DocumentSnapshot documentSnapshot = documentReference.get().get();
        if (documentSnapshot.exists()){
            logger.info("[PlanServiceImpl] ::: Fin del método updatePlan() ::: Plan modificado exitosamente ID: "+documentSnapshot.getId());
            String id = documentReference.getId();
            ApiFuture<WriteResult> writeResultApiFuture = documentReference.set(plan);
            respuesta.setId(id);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date fecha = writeResultApiFuture.get().getUpdateTime().toDate();
            String fechaCreacion = formatter.format(fecha);
            respuesta.setFecha(fechaCreacion);
            respuesta.setMensaje("Actualización Plan: "+ plan.getNombrePlan() +", Exitosa");
        }else{
            logger.info("[PlanServiceImpl] ::: Fin del método updatePlan() ::: Plan Inexistente ID: "+ plan.getNombrePlan());
            respuesta.setFecha(fechaActual());
            respuesta.setMensaje("Plan Id: "+ plan.getNombrePlan() +", Inexistente");
        }
        return  respuesta;
    }

    private Plan getMapPlan(Plan plan, QueryDocumentSnapshot documentSnapshot) {
        plan.setIdPlan(documentSnapshot.getId());
        plan.setNombrePlan(documentSnapshot.getString("nombrePlan"));
        plan.setValorPlan(documentSnapshot.getLong("valorPlan"));
        plan.setDescripcionPlan(documentSnapshot.getString("descripcionPlan"));
        String estadoString = documentSnapshot.getString("estado");
        Estado estado = Estado.valueOf(estadoString);
        plan.setEstado(estado);
        return plan;
    }
    public ResponseFirestore changeStatePlan(String nombrePlan, Estado nuevoEstado) throws InterruptedException, ExecutionException {
        logger.info("[PlanServiceImpl] ::: Iniciando el método changeStatePlan() ::: "+nombrePlan+", "+nuevoEstado);
        Firestore dbFirestore = FirestoreClient.getFirestore();
        Query query = dbFirestore.collection(FIRESTORE_COLLECTION).whereEqualTo("nombrePlan", nombrePlan).limit(1);
        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot querySnapshot = future.get();
        ResponseFirestore respuesta = new ResponseFirestore();
        if(!querySnapshot.isEmpty()) {
            logger.info("[PlanServiceImpl] ::: Fin del método changeStatePlan() ::: idPlan: "+nombrePlan+", nuevo Estado: "+nuevoEstado+", cambio de Estado exitoso: ");
            QueryDocumentSnapshot document = querySnapshot.getDocuments().get(0);
            dbFirestore.collection(FIRESTORE_COLLECTION).document(document.getId()).update("estado", nuevoEstado.name());
            String fecha = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            respuesta.setFecha(fecha);
            respuesta.setId(document.getId());
            respuesta.setMensaje("Estado del Plan actualizado correctamente.");
        } else {
            logger.info("[PlanServiceImpl] ::: Fin del método changeStatePlan() ::: idPlan: "+nombrePlan);
            String fecha = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            respuesta.setFecha(fecha);
            respuesta.setId(nombrePlan);
            respuesta.setMensaje("Plan "+nombrePlan+" inexistente.");
        }
        return  respuesta;
    }
}
