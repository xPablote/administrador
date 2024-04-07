package cl.lafabrica.administrador.service.impl;

import cl.lafabrica.administrador.commons.GenericServiceImpl;
import cl.lafabrica.administrador.dto.UsuarioDto;
import cl.lafabrica.administrador.modelo.Usuario;
import cl.lafabrica.administrador.service.api.UsuarioServiceAPI;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl extends GenericServiceImpl<Usuario, UsuarioDto> implements UsuarioServiceAPI {

    @Autowired
    private Firestore firestore;
    public UsuarioServiceImpl() {
        super(UsuarioDto.class);
    }
    @Override
    public CollectionReference getCollection() {
        return firestore.collection("usuario");
    }
}
