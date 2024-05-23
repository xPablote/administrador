package cl.lafabrica.administrador.service.impl;

import cl.lafabrica.administrador.model.Plan;
import cl.lafabrica.administrador.response.ResponseFirestore;
import cl.lafabrica.administrador.service.PlanService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
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
import java.util.Iterator;
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
        logger.info("[PlanServiceImpl] ::: Iniciando el método createPlan() ::: "+ plan);
        firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference  = firestore.collection(FIRESTORE_COLLECTION).document(plan.getIdPlan());
        ResponseFirestore respuesta = new ResponseFirestore();
        if (documentReference.get().get().exists()) {
            respuesta.setId(plan.getIdPlan());
            respuesta.setFecha(fechaActual());
            respuesta.setMensaje("Plan ID: " + plan.getIdPlan() + " ya existe");
            logger.info("[PlanServiceImpl] ::: Fin del método createPlan() ::: Plan existente "+ plan.getIdPlan());
            return respuesta;
        }
        ApiFuture<WriteResult> writeResultApiFuture = documentReference.set(plan);
        String id = documentReference.getId();
        respuesta.setId(id);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date fecha = writeResultApiFuture.get().getUpdateTime().toDate();
        String fechaCreacion = formatter.format(fecha);
        respuesta.setFecha(fechaCreacion);
        respuesta.setMensaje("Plan ID: " + plan.getIdPlan() + " agregado correctamente");
        logger.info("[PlanServiceImpl] ::: Fin del método createPlan() ::: Plan creado exitosamente: "+ plan.getIdPlan());
        return respuesta;
    }

    @Override
    public Plan getPlan(String idPlan) throws ExecutionException, InterruptedException, ParseException {
        logger.info("[PlanServiceImpl] ::: Iniciando el método getPlan() ::: "+ idPlan);
        firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference  = firestore.collection(FIRESTORE_COLLECTION).document(idPlan);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot documentSnapshot = future.get();
        Plan plan = new Plan();
        if (documentSnapshot.exists()) {
            logger.info("[PlanServiceImpl] ::: Fin del método getPlan() ::: Plan obtenido exitosamente: "+ idPlan);
            return this.getMapPlan(plan, documentSnapshot);
        }
        logger.info("[PlanServiceImpl] ::: Fin del método getPlan() ::: Plan inexistente: "+ idPlan);
        return plan;
    }

    @Override
    public List<Plan> listPlanes() throws ExecutionException, InterruptedException {
        logger.info("[PlanServiceImpl] ::: Iniciando el método listPlanes() ::: ");
        firestore = FirestoreClient.getFirestore();
        Iterable<DocumentReference> documentReference = firestore.collection(FIRESTORE_COLLECTION).listDocuments();
        Iterator<DocumentReference> iterator = documentReference.iterator();
        List<Plan> planes = new ArrayList<>();
        Plan plan = new Plan();
        while (iterator.hasNext()) {
            DocumentReference documentReference1 = iterator.next();
            ApiFuture<DocumentSnapshot> future = documentReference1.get();
            DocumentSnapshot snapshot = future.get();
            planes.add(this.getMapPlan(plan, snapshot));
        }
        logger.info("[PlanServiceImpl] ::: Fin del método listPlanes() ::: "+ planes);
        return planes;
    }

    @Override
    public ResponseFirestore updatePlan(Plan plan) throws ExecutionException, InterruptedException {
        logger.info("[PlanServiceImpl] ::: Iniciando el método updatePlan() ::: "+ plan.getIdPlan());
        firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference  = firestore.collection(FIRESTORE_COLLECTION).document(plan.getIdPlan());
        ResponseFirestore respuesta = new ResponseFirestore();
        DocumentSnapshot documentSnapshot = documentReference.get().get();
        if (documentSnapshot.exists()){
            logger.info("[PlanServiceImpl] ::: Fin del método updatePlan() ::: Plan modificado exitosamente ID: "+documentSnapshot.getId());
            String id = documentReference.getId();
            ApiFuture<WriteResult> writeResultApiFuture = documentReference.set(plan);
            respuesta.setId(id);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date fecha = writeResultApiFuture.get().getUpdateTime().toDate();
            String fechaCreacion = formatter.format(fecha);
            respuesta.setFecha(fechaCreacion);
            respuesta.setMensaje("Actualización Plan: "+ plan.getIdPlan() +", Exitosa");
        }else{
            logger.info("[PlanServiceImpl] ::: Fin del método updatePlan() ::: Plan Inexistente ID: "+ plan.getIdPlan());
            respuesta.setFecha(fechaActual());
            respuesta.setMensaje("Plan Id: "+ plan.getIdPlan() +", Inexistente");
        }
        return  respuesta;
    }

    private Plan getMapPlan(Plan plan, DocumentSnapshot documentSnapshot) {
        plan.setIdPlan(documentSnapshot.getId());
        plan.setNombrePlan(documentSnapshot.getString("nombrePlan"));
        plan.setValorPlan(documentSnapshot.getLong("valorPlan"));
        return plan;
    }
}
