package ec.gob.senescyt.usuario.core;

import java.util.List;

public class Permiso {

    private String moduloId;

    private List<Funcion> funciones;

    public Permiso(String moduloId, List<Funcion> funciones) {
        this.moduloId = moduloId;
        this.funciones = funciones;
    }

    public String getModuloId() {
        return moduloId;
    }

    public List<Funcion> getFunciones() {
        return funciones;
    }
}
