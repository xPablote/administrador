package cl.lafabrica.administrador.modelo;

import lombok.Data;

import java.sql.Timestamp;


@Data
public class Usuario {

    public String run;
    public String dv;
    public String primerNombre;
    public String segundoNombre;
    public String paternoApellido;
    public String maternoApellido;
    public String email;
    public Integer fono;
    public Timestamp fechaNacimiento;
    public String tipoUsuario;
    public Timestamp fechaRegistro;

}
