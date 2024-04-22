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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class UsuarioServiceImpl  implements UsuarioService {
    private static final String FIRESTORE_COLLECTION = "usuarios";

    @Autowired
    private Firestore firestore;

    @Override
    public ResponseFirestore createUsuario(Usuario usuario) throws ExecutionException, InterruptedException, ParseException {
        firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference  = firestore.collection(FIRESTORE_COLLECTION).document(usuario.rut);
        String rut = documentReference.getId();
        ApiFuture<WriteResult> writeResultApiFuture = documentReference.set(usuario);
        ResponseFirestore respuesta = new ResponseFirestore();
        respuesta.setRut(rut);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date fecha = writeResultApiFuture.get().getUpdateTime().toDate();
        String fechaCreacion = formatter.format(fecha);
        respuesta.setFechaCreacion(fechaCreacion);
        return respuesta;
    }

    public Usuario getUsuario(String rut) throws ExecutionException, InterruptedException{
        firestore = FirestoreClient.getFirestore();

        DocumentReference documentReference  = firestore.collection(FIRESTORE_COLLECTION).document(rut);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot documentSnapshot = future.get();
        Usuario usuario = null;
        if (documentSnapshot.exists()) {
            return usuario = documentSnapshot.toObject(Usuario.class);
        }
        return usuario;
    }

    public List<Usuario> getUsuarios() throws ExecutionException, InterruptedException {
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
        return usuarios;
    }

    @Override
    public ResponseFirestore updateUsuario(Usuario usuario) throws ExecutionException, InterruptedException {
        firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference  = firestore.collection(FIRESTORE_COLLECTION).document(usuario.rut);
        String rut = documentReference.getId();
        ApiFuture<WriteResult> writeResultApiFuture = documentReference.set(usuario);
        ResponseFirestore respuesta = new ResponseFirestore();
        respuesta.setRut(rut);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date fecha = writeResultApiFuture.get().getUpdateTime().toDate();
        String fechaCreacion = formatter.format(fecha);
        respuesta.setFechaCreacion(fechaCreacion);
        return  respuesta;
    }

}
