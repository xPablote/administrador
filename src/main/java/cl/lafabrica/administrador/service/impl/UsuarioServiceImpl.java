package cl.lafabrica.administrador.service.impl;

import cl.lafabrica.administrador.modelo.Usuario;
import cl.lafabrica.administrador.pojo.response.ResponseFirestore;
import cl.lafabrica.administrador.service.UsuarioService;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class UsuarioServiceImpl  implements UsuarioService {
    private static final String FIRESTORE_COLLECTION = "usuarios";

    private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    @Autowired
    private Firestore firestore;

    @Override
    public ResponseFirestore createUsuario(Usuario usuario) throws ExecutionException, InterruptedException {
        logger.info("[UsuarioServiceImpl] ::: Iniciando el método createUsuario() ::: "+usuario);
        firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference  = firestore.collection(FIRESTORE_COLLECTION).document(usuario.run);
        ResponseFirestore respuesta = new ResponseFirestore();
        if (documentReference.get().get().exists()) {
            respuesta.setRun(usuario.run);
            respuesta.setFecha(fechaActual());
            respuesta.setMensaje("Usuario RUN: " + usuario.run + " ya existe");
            logger.info("[UsuarioServiceImpl] ::: Fin del método createUsuario() ::: Usuario existente "+usuario.run);
            return respuesta;
        }
        ApiFuture<WriteResult> writeResultApiFuture = documentReference.set(usuario);
        String run = documentReference.getId();
        respuesta.setRun(run);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date fecha = writeResultApiFuture.get().getUpdateTime().toDate();
        String fechaCreacion = formatter.format(fecha);
        respuesta.setFecha(fechaCreacion);
        respuesta.setMensaje("Usuario RUN: " + usuario.run + " agregado correctamente");
        logger.info("[UsuarioServiceImpl] ::: Fin del método createUsuario() ::: Usuario creado exitosamente: "+usuario.run);
        return respuesta;
    }

    public Usuario getUsuario(String run) throws ExecutionException, InterruptedException{
        logger.info("[UsuarioServiceImpl] ::: Iniciando el método getUsuario() ::: "+run);
        firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference  = firestore.collection(FIRESTORE_COLLECTION).document(run);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot documentSnapshot = future.get();
        Usuario usuario = null;
        if (documentSnapshot.exists()) {
            logger.info("[UsuarioServiceImpl] ::: Fin del método getUsuario() ::: Usuario obtenido exitosamente: "+run);
            return usuario = documentSnapshot.toObject(Usuario.class);
        }
        logger.info("[UsuarioServiceImpl] ::: Fin del método getUsuario() ::: Usuario inexistente: "+run);
        return usuario;
    }

    public List<Usuario> getUsuarios() throws ExecutionException, InterruptedException {
        logger.info("[UsuarioServiceImpl] ::: Iniciando el método getUsuarios() ::: ");
        firestore = FirestoreClient.getFirestore();
        Iterable<DocumentReference> documentReference = firestore.collection(FIRESTORE_COLLECTION).listDocuments();
        Iterator<DocumentReference> iterator = documentReference.iterator();
        List<Usuario> usuarios = new ArrayList<>();
        Usuario usuario = null;
        while (iterator.hasNext()) {
            DocumentReference documentReference1 = iterator.next();
            ApiFuture<DocumentSnapshot> future = documentReference1.get();
            DocumentSnapshot snapshot = future.get();
            usuario = snapshot.toObject(Usuario.class);
            usuarios.add(usuario);
        }
        logger.info("[UsuarioServiceImpl] ::: Fin del método getUsuarios() ::: "+usuarios);
        return usuarios;
    }

    @Override
    public ResponseFirestore updateUsuario(Usuario usuario) throws ExecutionException, InterruptedException {
        logger.info("[UsuarioServiceImpl] ::: Iniciando el método updateUsuario() ::: "+usuario.run);
        firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference  = firestore.collection(FIRESTORE_COLLECTION).document(usuario.run);
        ResponseFirestore respuesta = new ResponseFirestore();
        DocumentSnapshot documentSnapshot = documentReference.get().get();
        if (documentSnapshot.exists()){
            logger.info("[UsuarioServiceImpl] ::: Fin del método updateUsuario() ::: Usuario modificado exitosamente RUN: "+documentSnapshot.getId());
            String rut = documentReference.getId();
            ApiFuture<WriteResult> writeResultApiFuture = documentReference.set(usuario);
            respuesta.setRun(rut);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date fecha = writeResultApiFuture.get().getUpdateTime().toDate();
            String fechaCreacion = formatter.format(fecha);
            respuesta.setFecha(fechaCreacion);
            respuesta.setMensaje("Actualización RUN: "+ usuario.run +", Exitosa");
        }else{
            logger.info("[UsuarioServiceImpl] ::: Fin del método updateUsuario() ::: Usuario Inexistente RUN: "+usuario.run);
            respuesta.setFecha(fechaActual());
            respuesta.setMensaje("Usuario RUN: "+ usuario.run +", Inexistente");
        }
        return  respuesta;
    }

    private String fechaActual(){
        LocalDateTime requestDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return requestDateTime.format(formatter);
    }

}
