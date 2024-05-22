package cl.lafabrica.administrador.response;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ResponseFirestoreUsuario {

    private String run;
    private String fecha;
    private String mensaje;
}
