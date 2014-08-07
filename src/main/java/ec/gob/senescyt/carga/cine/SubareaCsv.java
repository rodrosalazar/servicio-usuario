package ec.gob.senescyt.carga.cine;

import ec.gob.senescyt.carga.AbstractConversorCsv;
import ec.gob.senescyt.usuario.core.cine.Subarea;

import java.io.IOException;

public class SubareaCsv extends AbstractConversorCsv {

    public SubareaCsv() {
        super(Subarea.class);
    }

    @Override
    protected String entidadASql(Object entidad) {
        Subarea subarea = (Subarea) entidad;

        return String.format("INSERT INTO %s VALUES (%s, %s, %s);", nombreTabla,
                stringASql(subarea.getId()),
                stringASql(subarea.getNombre()),
                stringASql(subarea.getAreaIdParaCsv())
        );
    }

    public static void main(String... args) throws IOException {
        convertir(new SubareaCsv(), args);
    }
}
