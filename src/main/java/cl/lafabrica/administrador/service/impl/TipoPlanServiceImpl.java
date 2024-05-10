package cl.lafabrica.administrador.service.impl;

import cl.lafabrica.administrador.modelo.TipoPlan;
import cl.lafabrica.administrador.pojo.response.ResponseFirestoreTipoPlan;
import cl.lafabrica.administrador.service.TipoPlanService;
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
public class TipoPlanServiceImpl implements TipoPlanService {
    private static final String FIRESTORE_COLLECTION = "tipoPlan";

    private static final Logger logger = LoggerFactory.getLogger(TipoPlanServiceImpl.class);

    @Autowired
    private Firestore firestore;

    private String fechaActual(){
        LocalDateTime requestDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return requestDateTime.format(formatter);
    }

    @Override
    public ResponseFirestoreTipoPlan createTipoPlan(TipoPlan tipoPlan) throws ExecutionException, InterruptedException {
        logger.info("[TipoPlanServiceImpl] ::: Iniciando el método createTipoPlan() ::: "+tipoPlan);
        firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference  = firestore.collection(FIRESTORE_COLLECTION).document(tipoPlan.getIdTipoPlan());
        ResponseFirestoreTipoPlan respuesta = new ResponseFirestoreTipoPlan();
        if (documentReference.get().get().exists()) {
            respuesta.setId(tipoPlan.getIdTipoPlan());
            respuesta.setFecha(fechaActual());
            respuesta.setMensaje("TipoPlan ID: " + tipoPlan.getIdTipoPlan() + " ya existe");
            logger.info("[TipoPlanServiceImpl] ::: Fin del método createTipoPlan() ::: TipoPlan existente "+tipoPlan.getIdTipoPlan());
            return respuesta;
        }
        ApiFuture<WriteResult> writeResultApiFuture = documentReference.set(tipoPlan);
        String id = documentReference.getId();
        respuesta.setId(id);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date fecha = writeResultApiFuture.get().getUpdateTime().toDate();
        String fechaCreacion = formatter.format(fecha);
        respuesta.setFecha(fechaCreacion);
        respuesta.setMensaje("TipoPlan ID: " + tipoPlan.getIdTipoPlan() + " agregado correctamente");
        logger.info("[TipoPlanServiceImpl] ::: Fin del método createTipoPlan() ::: TipoPlan creado exitosamente: "+tipoPlan.getIdTipoPlan());
        return respuesta;
    }

    @Override
    public TipoPlan getTipoPlan(String idTipoPlan) throws ExecutionException, InterruptedException, ParseException {
        logger.info("[TipoPlanServiceImpl] ::: Iniciando el método getTipoPlan() ::: "+idTipoPlan);
        firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference  = firestore.collection(FIRESTORE_COLLECTION).document(idTipoPlan);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot documentSnapshot = future.get();
        TipoPlan tipoPlan = new TipoPlan();
        if (documentSnapshot.exists()) {
            logger.info("[TipoPlanServiceImpl] ::: Fin del método getTipoPlan() ::: TipoPlan obtenido exitosamente: "+idTipoPlan);
            return this.getMapTipoPlan(tipoPlan, documentSnapshot);
        }
        logger.info("[TipoPlanServiceImpl] ::: Fin del método getTipoPlan() ::: TipoPlan inexistente: "+idTipoPlan);
        return tipoPlan;
    }

    @Override
    public List<TipoPlan> listTipoPlanes() throws ExecutionException, InterruptedException {
        logger.info("[TipoPlanServiceImpl] ::: Iniciando el método listTipoPlanes() ::: ");
        firestore = FirestoreClient.getFirestore();
        Iterable<DocumentReference> documentReference = firestore.collection(FIRESTORE_COLLECTION).listDocuments();
        Iterator<DocumentReference> iterator = documentReference.iterator();
        List<TipoPlan> tipoPlanes = new ArrayList<>();
        TipoPlan tipoPlan = new TipoPlan();
        while (iterator.hasNext()) {
            DocumentReference documentReference1 = iterator.next();
            ApiFuture<DocumentSnapshot> future = documentReference1.get();
            DocumentSnapshot snapshot = future.get();
            tipoPlanes.add(this.getMapTipoPlan(tipoPlan, snapshot));
        }
        logger.info("[TipoPlanServiceImpl] ::: Fin del método listTipoPlanes() ::: "+tipoPlanes);
        return tipoPlanes;
    }

    @Override
    public ResponseFirestoreTipoPlan updateTipoPlan(TipoPlan tipoPlan) throws ExecutionException, InterruptedException {
        logger.info("[TipoPlanServiceImpl] ::: Iniciando el método updateTipoPlan() ::: "+tipoPlan.getIdTipoPlan());
        firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference  = firestore.collection(FIRESTORE_COLLECTION).document(tipoPlan.getIdTipoPlan());
        ResponseFirestoreTipoPlan respuesta = new ResponseFirestoreTipoPlan();
        DocumentSnapshot documentSnapshot = documentReference.get().get();
        if (documentSnapshot.exists()){
            logger.info("[TipoPlanServiceImpl] ::: Fin del método updateTipoPlan() ::: TipoPlan modificado exitosamente ID: "+documentSnapshot.getId());
            String id = documentReference.getId();
            ApiFuture<WriteResult> writeResultApiFuture = documentReference.set(tipoPlan);
            respuesta.setId(id);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date fecha = writeResultApiFuture.get().getUpdateTime().toDate();
            String fechaCreacion = formatter.format(fecha);
            respuesta.setFecha(fechaCreacion);
            respuesta.setMensaje("Actualización TipoPlan: "+ tipoPlan.getIdTipoPlan() +", Exitosa");
        }else{
            logger.info("[TipoPlanServiceImpl] ::: Fin del método updateTipoPlan() ::: TipoPlan Inexistente ID: "+tipoPlan.getIdTipoPlan());
            respuesta.setFecha(fechaActual());
            respuesta.setMensaje("TipoPlan Id: "+ tipoPlan.getIdTipoPlan() +", Inexistente");
        }
        return  respuesta;
    }

    private TipoPlan getMapTipoPlan(TipoPlan tipoPlan, DocumentSnapshot documentSnapshot) {
        tipoPlan.setIdTipoPlan(documentSnapshot.getId());
        tipoPlan.setNombrePlan(documentSnapshot.getString("nombrePlan"));
        tipoPlan.setValorPlan(documentSnapshot.getLong("valorPlan"));
        tipoPlan.setFechaInicioPlan(documentSnapshot.getTimestamp("fechaInicioPlan").toSqlTimestamp());
        tipoPlan.setFechaPagoPlan(documentSnapshot.getTimestamp("fechaPagoPlan").toSqlTimestamp());
        return tipoPlan;
    }
}
