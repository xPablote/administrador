package cl.lafabrica.administrador.model;

import lombok.Getter;

@Getter
public enum RolColaborador {
    STAFF("S", "STAFF"),
    ADMIN("A", "ADMIN");

    private String idRol;
    private String tipoRol;

    RolColaborador(String idRol, String tipoRol) {
        this.idRol = idRol;
        this.tipoRol = tipoRol;
    }


}
