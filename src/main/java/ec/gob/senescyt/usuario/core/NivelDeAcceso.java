package ec.gob.senescyt.usuario.core;

public class NivelDeAcceso {
    private final String nombre;
    private final int id;

    public NivelDeAcceso(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }
}
