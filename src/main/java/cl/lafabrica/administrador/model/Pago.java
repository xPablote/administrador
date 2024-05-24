package cl.lafabrica.administrador.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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
    private Timestamp fechaPago;
    private Long monto;
    private String metodoPago;
    private Long descuento;
}
