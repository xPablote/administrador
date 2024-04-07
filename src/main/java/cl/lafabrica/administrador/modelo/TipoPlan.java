package cl.lafabrica.administrador.modelo;

import lombok.Data;

import java.util.Date;

@Data
public class TipoPlan {
    private String idTipoPlan;
    private String nombrePlan;
    private Long valorPlan;
    private Date fechaInicioPlan;
    private Date fechaPagoPlan;
}
