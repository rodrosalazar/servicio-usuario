package ec.gob.senescyt.usuario.core;

import javax.persistence.Id;
import java.util.List;

public class Funcion {

    @Id
    private String id;

    private List<Acceso> accesos;

    public Funcion(String id, List<Acceso> accesos) {
        this.id = id;
        this.accesos = accesos;
    }

    public String getId() {
        return id;
    }

    public List<Acceso> getAccesos() {
        return accesos;
    }
}
