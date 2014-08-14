package ec.gob.senescyt.usuario.dto;

import ec.gob.senescyt.usuario.core.EstadoUsuario;

import javax.validation.constraints.NotNull;
import java.util.List;

public class EdicionBasicaUsuario {
    @NotNull
    private EstadoUsuario estado;
    @NotNull
    private List<Long> perfiles;

    private EdicionBasicaUsuario() {}

    public EdicionBasicaUsuario(EstadoUsuario estado, List<Long> perfiles) {
        this.estado = estado;
        this.perfiles = perfiles;
    }

    public List<Long> getPerfiles() {
        return perfiles;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }
}
