package ec.gob.senescyt.usuario.core;

public enum EstadoUsuario {
    ACTIVO("Activo"), INACTIVO("Inactivo");
    private String descripcion;
    private EstadoUsuario(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
