package cl.lafabrica.administrador.modelo;

import lombok.Getter;

@Getter
public enum RolUsuario {
    CLIENTE("C", "CLIENTE"),
    STAFF("S", "STAFF"),
    ADMIN("A", "ADMIN");

    private String idRol;
    private String tipoRol;

    RolUsuario(String idRol, String tipoRol) {
        this.idRol = idRol;
        this.tipoRol = tipoRol;
    }


}
