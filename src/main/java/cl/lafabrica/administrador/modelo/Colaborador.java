package cl.lafabrica.administrador.modelo;

import com.google.cloud.firestore.annotation.PropertyName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Colaborador {
    private String username;
    private String password;
    private String nombre;
    private String apellido;
    @PropertyName("rolColaborador")
    public RolColaborador rolColaborador;
    @PropertyName("estado")
    private Estado estado;

}
