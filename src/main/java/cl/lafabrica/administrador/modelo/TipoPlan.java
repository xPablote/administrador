package cl.lafabrica.administrador.modelo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TipoPlan {
    private String idTipoPlan;
    private String nombrePlan;
    private Long valorPlan;
    private Timestamp fechaInicioPlan;
    private Timestamp fechaPagoPlan;
}
