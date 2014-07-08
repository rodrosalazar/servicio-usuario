package ec.gob.senescyt.usuario.core.cine;

public enum AnioClasificacion {
    CINE1997("001"),
    CINE2013("002");

    public final String id;

    private AnioClasificacion(String id) {
        this.id = id;
    }
}
