package cl.lafabrica.administrador.service.impl;

import cl.lafabrica.administrador.model.Colaborador;
import cl.lafabrica.administrador.model.Estado;
import cl.lafabrica.administrador.model.RolColaborador;
import cl.lafabrica.administrador.response.ResponseFirestore;
import cl.lafabrica.administrador.service.ColaboradorService;
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
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ColaboradorServiceImpl implements ColaboradorService {
    private static final String FIRESTORE_COLLECTION = "colaboradores";

    private static final Logger logger = LoggerFactory.getLogger(ColaboradorServiceImpl.class);

    @Autowired
    private Firestore firestore;

    private String fechaActual(){
        LocalDateTime requestDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return requestDateTime.format(formatter);
    }

    @Override
    public ResponseFirestore createColaborador(Colaborador colaborador) throws ExecutionException, InterruptedException {
        logger.info("[ColaboradorServiceImpl] ::: Iniciando el método createColaborador() ::: "+colaborador);
        firestore = FirestoreClient.getFirestore();
        ApiFuture<DocumentReference> documentReference  = firestore.collection(FIRESTORE_COLLECTION).add(colaborador);
        ResponseFirestore respuesta = new ResponseFirestore();
        if (documentReference.get().get().isCancelled()) {
            respuesta.setId(documentReference.get().getId());
            respuesta.setFecha(fechaActual());
            respuesta.setMensaje("Colaborador username: " + colaborador.getUsername() + " ya existe");
            logger.info("[ColaboradorServiceImpl] ::: Fin del método createColaborador() ::: Colaborador existente "+colaborador.getUsername());
            return respuesta;
        }
        ApiFuture<WriteResult> writeResultApiFuture = documentReference.get().set(colaborador);
        String id = documentReference.get().getId();
        respuesta.setId(id);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = writeResultApiFuture.get().getUpdateTime().toDate();
        String fechaCreacion = formatter.format(fecha);
        respuesta.setFecha(fechaCreacion);
        respuesta.setMensaje("Colaborador Username: " + colaborador.getUsername() + " agregado correctamente");
        logger.info("[ColaboradorServiceImpl] ::: Fin del método createColaborador() ::: Colaborador creado exitosamente: "+colaborador.getUsername());
        return respuesta;
    }

    @Override
    public Colaborador getColaborador(String username) throws ExecutionException, InterruptedException {
        logger.info("[ColaboradorServiceImpl] ::: Iniciando el método getColaborador() ::: "+username);
        firestore = FirestoreClient.getFirestore();
        Query query  = firestore.collection(FIRESTORE_COLLECTION).whereEqualTo("username", username);;
        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot querySnapshot = future.get();
        Colaborador colaborador = new Colaborador();
        if (!querySnapshot.isEmpty()) {
            QueryDocumentSnapshot document = querySnapshot.getDocuments().get(0);
            logger.info("[ColaboradorServiceImpl] ::: Fin del método getColaborador() ::: Colaborador obtenido exitosamente: "+username);
            return this.getMapColaborador(colaborador, document);
        } else {
            logger.info("[ColaboradorServiceImpl] ::: Fin del método getColaborador() ::: Colaborador inexistente: "+username);
            return colaborador;
        }
    }

    @Override
    public List<Colaborador> listColaboradores() throws ExecutionException, InterruptedException {
        logger.info("[ColaboradorServiceImpl] ::: Iniciando el método listColaboradores() ::: ");
        firestore = FirestoreClient.getFirestore();
        Iterable<DocumentReference> documentReference = firestore.collection(FIRESTORE_COLLECTION).listDocuments();
        Iterator<DocumentReference> iterator = documentReference.iterator();
        List<Colaborador> colaboradores = new ArrayList<>();
        Colaborador colaborador = new Colaborador();
        while (iterator.hasNext()) {
            DocumentReference documentReference1 = iterator.next();
            ApiFuture<DocumentSnapshot> future = documentReference1.get();
            DocumentSnapshot snapshot = future.get();
            colaboradores.add(this.getMapColaborador(colaborador, snapshot));
        }
        logger.info("[ColaboradorServiceImpl] ::: Fin del método listColaboradores() ::: "+colaboradores);
        return colaboradores;
    }

    @Override
    public ResponseFirestore updateColaborador(Colaborador updatedColaborador) throws ExecutionException, InterruptedException {
        logger.info("[ColaboradorServiceImpl] ::: Iniciando el método updateColaborador() ::: "+updatedColaborador.getUsername());
        firestore = FirestoreClient.getFirestore();
        Query  query  = firestore.collection(FIRESTORE_COLLECTION).whereEqualTo("username", updatedColaborador.getUsername()).limit(1);
        ResponseFirestore respuesta = new ResponseFirestore();
        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot querySnapshot = future.get();
        if (!querySnapshot.isEmpty()){
            QueryDocumentSnapshot document = querySnapshot.getDocuments().get(0);
            firestore.collection(FIRESTORE_COLLECTION).document(document.getId()).set(updatedColaborador);
            logger.info("[ColaboradorServiceImpl] ::: Fin del método updateColaborador() ::: Colaborador modificado exitosamente ID: "+document.getId());
            respuesta.setId(document.getId());
            String fechaCreacion = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            respuesta.setFecha(fechaCreacion);
            respuesta.setMensaje("Actualización Colaborador: "+ updatedColaborador.getUsername() +", Exitosa");
        }else{
            logger.info("[ColaboradorServiceImpl] ::: Fin del método updateColaborador() ::: Colaborador username : "+updatedColaborador.getUsername());
            respuesta.setFecha(fechaActual());
            respuesta.setMensaje("Colaborador username: "+ updatedColaborador.getUsername() +", Inexistente");
        }
        return  respuesta;
    }

    private Colaborador getMapColaborador(Colaborador colaborador, DocumentSnapshot documentSnapshot) {
        colaborador.setUsername(documentSnapshot.getString("username"));
        colaborador.setPassword(documentSnapshot.getString("password"));
        colaborador.setNombre(documentSnapshot.getString("nombre"));
        colaborador.setApellido(documentSnapshot.getString("apellido"));
        String rolColaboradorString = documentSnapshot.getString("rolColaborador");
        RolColaborador rolColaborador = RolColaborador.valueOf(rolColaboradorString);
        colaborador.setRolColaborador(rolColaborador);
        String estadoColaboradorString = documentSnapshot.getString("estado");
        Estado estadoColaborador = Estado.valueOf(estadoColaboradorString);
        colaborador.setEstado(estadoColaborador);
        return colaborador;
    }

    public ResponseFirestore changeStateColaborador(String username, Estado nuevoEstado) throws InterruptedException, ExecutionException {
        logger.info("[ColaboradorServiceImpl] ::: Iniciando el método changeStateColaborador() ::: "+username+", "+nuevoEstado);
        Firestore dbFirestore = FirestoreClient.getFirestore();
        Query query = dbFirestore.collection(FIRESTORE_COLLECTION).whereEqualTo("username", username).limit(1);
        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot querySnapshot = future.get();
        ResponseFirestore respuesta = new ResponseFirestore();
        if(!querySnapshot.isEmpty()) {
            logger.info("[ColaboradorServiceImpl] ::: Fin del método changeStateColaborador() ::: username: "+username+", nuevo Estado: "+nuevoEstado+", cambio de Estado exitoso: ");
            QueryDocumentSnapshot document = querySnapshot.getDocuments().get(0);
            dbFirestore.collection(FIRESTORE_COLLECTION).document(document.getId()).update("estado", nuevoEstado.name());
            String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            respuesta.setFecha(fecha);
            respuesta.setId(document.getId());
            respuesta.setMensaje("Estado del colaborador actualizado correctamente.");
        } else {
            logger.info("[ColaboradorServiceImpl] ::: Fin del método changeStateColaborador() ::: username: "+username);
            String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            respuesta.setFecha(fecha);
            respuesta.setId(username);
            respuesta.setMensaje("Colaborador "+username+" inexistente.");
        }
        return  respuesta;
    }
}
