package ec.gob.senescyt.carga.cine;

import ec.gob.senescyt.carga.ConversorCsv;
import ec.gob.senescyt.usuario.core.cine.Area;

import java.io.IOException;

public class AreaCsv extends ConversorCsv {

    public AreaCsv() {
        super(Area.class);
    }

    @Override
    protected String entidadASql(Object entidad) {
        Area area = (Area) entidad;

        return String.format("INSERT INTO %s VALUES (%s, %s, %s);", nombreTabla,
                stringASql(area.getId()),
                stringASql(area.getNombre()),
                stringASql(area.getClasificacionIdParaCsv())
        );
    }

    public static void main(String... args) throws IOException {
        convertir(new AreaCsv(), args);
    }
}
