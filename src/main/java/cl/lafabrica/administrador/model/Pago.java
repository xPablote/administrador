package cl.lafabrica.administrador.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pago {
    private String run;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String idPlan;
    private String nombrePlan;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Timestamp fechaPago;
    private Long monto;
    private String metodoPago;
    private Long descuento;
}
