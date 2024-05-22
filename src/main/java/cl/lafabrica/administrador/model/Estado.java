package cl.lafabrica.administrador.model;

import lombok.Getter;

@Getter
public enum Estado {
    ACTIVO("A", "Activo"),
    INACTIVO("I", "Inactivo"),
    PENDIENTE("P", "Pendiente");

    private String idEstado;
    private String tipoEstado;

    Estado(String idEstado, String tipoEstado) {
        this.idEstado = idEstado;
        this.tipoEstado = tipoEstado;
    }

}
