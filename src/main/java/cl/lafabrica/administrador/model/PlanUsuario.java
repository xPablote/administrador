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
public class PlanUsuario {
    private String run;
    private String idPlan;
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    private Long monto;
    private String metodoPago;
    private Long descuento;

}
