package cl.lafabrica.administrador.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TipoPlan {
    private String idTipoPlan;
    private String nombrePlan;
    private Long valorPlan;
    private String descripcionPlan;
    private Timestamp fechaInicioPlan;
    private Timestamp fechaPagoPlan;
}
