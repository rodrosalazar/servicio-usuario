package ec.gob.senescyt.carga.cine;

import ec.gob.senescyt.carga.ConversorCsv;
import ec.gob.senescyt.usuario.core.cine.Subarea;

public class SubareaCsv extends ConversorCsv {

    public SubareaCsv() {
        super(Subarea.class);
    }

    @Override
    protected String entidadASql(Object entidad) {
        Subarea subarea = (Subarea) entidad;

        return String.format("INSERT INTO %s VALUES (%s, %s, %s);", nombreTabla,
                stringASql(subarea.getId()),
                stringASql(subarea.getNombre()),
                stringASql(subarea.getAreaId())
        );
    }
}
