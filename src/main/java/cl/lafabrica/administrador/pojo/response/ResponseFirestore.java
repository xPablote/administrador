package cl.lafabrica.administrador.pojo.response;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ResponseFirestore {

    private String id;
    private String fecha;
    private String mensaje;
}
