package cl.lafabrica.administrador.service.impl;

import cl.lafabrica.administrador.modelo.Estado;
import cl.lafabrica.administrador.modelo.Usuario;
import cl.lafabrica.administrador.pojo.response.ResponseFirestoreUsuario;
import cl.lafabrica.administrador.service.UsuarioService;
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
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class UsuarioServiceImpl  implements UsuarioService {
    private static final String FIRESTORE_COLLECTION = "usuarios";

    private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    @Autowired
    private Firestore firestore;

    @Override
    public ResponseFirestoreUsuario createUsuario(Usuario usuario) throws ExecutionException, InterruptedException {
        logger.info("[UsuarioServiceImpl] ::: Iniciando el método createUsuario() ::: "+usuario);
        firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference  = firestore.collection(FIRESTORE_COLLECTION).document(usuario.getRun() );
        ResponseFirestoreUsuario respuesta = new ResponseFirestoreUsuario();
        if (documentReference.get().get().exists()) {
            respuesta.setRun(usuario.getRun());
            respuesta.setFecha(fechaActual());
            respuesta.setMensaje("Usuario RUN: " + usuario.getRun() + " ya existe");
            logger.info("[UsuarioServiceImpl] ::: Fin del método createUsuario() ::: Usuario existente "+usuario.getRun() );
            return respuesta;
        }
        ApiFuture<WriteResult> writeResultApiFuture = documentReference.set(usuario);
        String run = documentReference.getId();
        respuesta.setRun(run);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date fecha = writeResultApiFuture.get().getUpdateTime().toDate();
        String fechaCreacion = formatter.format(fecha);
        respuesta.setFecha(fechaCreacion);
        respuesta.setMensaje("Usuario RUN: " + usuario.getRun()  + " agregado correctamente");
        logger.info("[UsuarioServiceImpl] ::: Fin del método createUsuario() ::: Usuario creado exitosamente: "+usuario.getRun() );
        return respuesta;
    }

    public Usuario getUsuario(String run) throws ExecutionException, InterruptedException{
        logger.info("[UsuarioServiceImpl] ::: Iniciando el método getUsuario() ::: "+run);
        firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference  = firestore.collection(FIRESTORE_COLLECTION).document(run);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot documentSnapshot = future.get();
        Usuario usuario = new Usuario();
        if (documentSnapshot.exists()) {
            logger.info("[UsuarioServiceImpl] ::: Fin del método getUsuario() ::: Usuario obtenido exitosamente: "+run);
            usuario.setRun(documentSnapshot.getId());
            usuario.setDv(documentSnapshot.getString("dv"));
            usuario.setPrimerNombre(documentSnapshot.getString("primerNombre"));
            usuario.setSegundoNombre(documentSnapshot.getString("segundoNombre"));
            usuario.setPaternoApellido(documentSnapshot.getString("paternoApellido"));
            usuario.setMaternoApellido(documentSnapshot.getString("maternoApellido"));
            usuario.setEmail(documentSnapshot.getString("email"));
            usuario.setFono(documentSnapshot.getLong("fono"));
            usuario.setFechaNacimiento(documentSnapshot.getTimestamp("fechaNacimiento").toSqlTimestamp());
            usuario.setFechaRegistro(documentSnapshot.getTimestamp("fechaRegistro").toSqlTimestamp());
//            String rolUsuarioString = documentSnapshot.getString("rolUsuario");
//            RolUsuario rolUsuario = RolUsuario.valueOf(rolUsuarioString);
            String estadoString = documentSnapshot.getString("estado");
            Estado estado = Estado.valueOf(estadoString);
            usuario.setEstado(estado);
            usuario.setIdTipoPlan(documentSnapshot.getString("idTipoPlan"));
            return usuario;
        }
        logger.info("[UsuarioServiceImpl] ::: Fin del método getUsuario() ::: Usuario inexistente: "+run);
        return usuario;
    }

    public List<Usuario> getUsuarios() throws ExecutionException, InterruptedException {
        logger.info("[UsuarioServiceImpl] ::: Iniciando el método getUsuarios() ::: ");
        firestore = FirestoreClient.getFirestore();
        QuerySnapshot querySnapshot = firestore.collection(FIRESTORE_COLLECTION).get().get();
        List<Usuario> usuarios = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
            Usuario usuario = new Usuario();
            usuario.setRun(document.getId());
            usuario.setDv(document.getString("dv"));
            usuario.setPrimerNombre(document.getString("primerNombre"));
            usuario.setSegundoNombre(document.getString("segundoNombre"));
            usuario.setPaternoApellido(document.getString("paternoApellido"));
            usuario.setMaternoApellido(document.getString("maternoApellido"));
            usuario.setEmail(document.getString("email"));
            usuario.setFono(document.getLong("fono"));
            usuario.setFechaNacimiento(document.getTimestamp("fechaNacimiento").toSqlTimestamp());
            usuario.setFechaRegistro(document.getTimestamp("fechaRegistro").toSqlTimestamp());
//            String rolUsuarioString = document.getString("rolUsuario");
//            RolUsuario rolUsuario = RolUsuario.valueOf(rolUsuarioString);
            String estadoString = document.getString("estado");
            Estado estado = Estado.valueOf(estadoString);
            usuario.setEstado(estado);
            usuario.setIdTipoPlan(document.getString("idTipoPlan"));
            usuarios.add(usuario);
        }
        logger.info("[UsuarioServiceImpl] ::: Fin del método getUsuarios() ::: "+usuarios);
        return usuarios;
    }

    @Override
    public ResponseFirestoreUsuario updateUsuario(Usuario usuario) throws ExecutionException, InterruptedException {
        logger.info("[UsuarioServiceImpl] ::: Iniciando el método updateUsuario() ::: "+usuario.getRun() );
        firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference  = firestore.collection(FIRESTORE_COLLECTION).document(usuario.getRun() );
        ResponseFirestoreUsuario respuesta = new ResponseFirestoreUsuario();
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
            respuesta.setMensaje("Actualización RUN: "+ usuario.getRun()  +", Exitosa");
        }else{
            logger.info("[UsuarioServiceImpl] ::: Fin del método updateUsuario() ::: Usuario Inexistente RUN: "+usuario.getRun() );
            respuesta.setRun(usuario.getRun() );
            respuesta.setFecha(fechaActual());
            respuesta.setMensaje("Usuario RUN: "+ usuario.getRun()  +", Inexistente");
        }
        return  respuesta;
    }

    private String fechaActual(){
        LocalDateTime requestDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return requestDateTime.format(formatter);
    }

    public ResponseFirestoreUsuario changeStateUsuario(String run, Estado nuevoEstado) throws InterruptedException, ExecutionException {
        logger.info("[UsuarioServiceImpl] ::: Iniciando el método changeStateUsuario() ::: "+run);
        firestore = FirestoreClient.getFirestore();
        DocumentReference usuarioRef = firestore.collection("usuarios").document(run);
        ApiFuture<DocumentSnapshot> future = usuarioRef.get();
        DocumentSnapshot documento = future.get();
        ResponseFirestoreUsuario respuesta = new ResponseFirestoreUsuario();
        if (documento.exists()) {
            Map<String, Object> usuarioData = documento.getData();
            usuarioData.put("estado", nuevoEstado.getTipoEstado());
            usuarioRef.set(usuarioData, SetOptions.merge()).get();
            respuesta.setRun(run);
            respuesta.setFecha(fechaActual());
            respuesta.setMensaje("Usuario RUN: " + run + " cambio de estado: " + nuevoEstado.getTipoEstado());
            logger.info("[UsuarioServiceImpl] ::: Fin del método changeStateUsuario() ::: Usuario RUN: "+run+", nuevo Estado: "+nuevoEstado.getTipoEstado()+", cambio de Estado exitoso: ");
        } else {
            logger.info("[UsuarioServiceImpl] ::: Fin del método changeStateUsuario() ::: Usuario Inexistente RUN: "+run);
            respuesta.setRun(run);
            respuesta.setFecha(fechaActual());
            respuesta.setMensaje("Usuario RUN: "+ run +", Inexistente");
        }
        return  respuesta;
    }

}
