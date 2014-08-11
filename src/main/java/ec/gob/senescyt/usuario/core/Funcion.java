package ec.gob.senescyt.usuario.core;

public class Funcion {
    private final int id;
    private final String nombre;

    public Funcion(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
