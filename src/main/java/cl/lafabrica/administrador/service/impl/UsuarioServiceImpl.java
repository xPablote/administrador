package cl.lafabrica.administrador.service.impl;

import cl.lafabrica.administrador.modelo.Usuario;
import cl.lafabrica.administrador.pojo.response.ResponseFirestore;
import cl.lafabrica.administrador.service.UsuarioService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

@Service
public class UsuarioServiceImpl  implements UsuarioService {
    private static final String FIRESTORE_COLLECTION = "usuarios";

    @Autowired
    private Firestore firestore;

    @Override
    public ResponseFirestore saveUsuario(Usuario usuario) throws ExecutionException, InterruptedException, ParseException {
        firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference  = firestore.collection(FIRESTORE_COLLECTION).document();
        String id = documentReference.getId();
        ApiFuture<WriteResult> writeResultApiFuture = documentReference.set(usuario);
        ResponseFirestore respuesta = new ResponseFirestore();
        respuesta.setId(id);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date fecha = writeResultApiFuture.get().getUpdateTime().toDate();
        String fechaCreacion = formatter.format(fecha);
        respuesta.setFechaCreacion(fechaCreacion);
        return respuesta;
    }


}
