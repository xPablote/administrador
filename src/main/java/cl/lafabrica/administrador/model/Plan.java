package cl.lafabrica.administrador.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Plan {
    private String idPlan;
    private String nombrePlan;
    private Long valorPlan;
    private String descripcionPlan;
}
