package ec.gob.senescyt.usuario.core;

public class FuncionNivelDeAcceso {
    private final Funcion funcion;
    private final NivelDeAcceso nivelDeAcceso;

    public FuncionNivelDeAcceso(Funcion funcion, NivelDeAcceso nivelDeAcceso) {
        this.funcion = funcion;
        this.nivelDeAcceso = nivelDeAcceso;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public NivelDeAcceso getNivelDeAcceso() {
        return nivelDeAcceso;
    }
}
