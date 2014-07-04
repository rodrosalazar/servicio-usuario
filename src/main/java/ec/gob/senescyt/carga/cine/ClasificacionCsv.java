package ec.gob.senescyt.carga.cine;

import ec.gob.senescyt.carga.ConversorCsv;
import ec.gob.senescyt.usuario.core.cine.Clasificacion;

public class ClasificacionCsv extends ConversorCsv {

    public ClasificacionCsv() {
        super(Clasificacion.class);
    }

    @Override
    protected String entidadASql(Object entidad) {
        Clasificacion clasificacion = (Clasificacion) entidad;

        return String.format("INSERT INTO %s VALUES (%s, %s);", nombreTabla,
                stringASql(clasificacion.getId()),
                stringASql(clasificacion.getNombre())
        );
    }
}