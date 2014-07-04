package ec.gob.senescyt.carga.cine;

import ec.gob.senescyt.carga.ConversorCsv;
import ec.gob.senescyt.usuario.core.cine.Clasificacion;

public class ClasificacionCsv extends ConversorCsv {

    public static final String NOMBRE_TABLA = "cine_clasificaciones";

    public ClasificacionCsv() {
        super(Clasificacion.class);
    }

    @Override
    protected String entidadASql(Object entidad) {
        Clasificacion clasificacion = (Clasificacion) entidad;

        return String.format("INSERT INTO %s VALUES (%s, %s);", ClasificacionCsv.NOMBRE_TABLA,
                stringASql(clasificacion.getId()),
                stringASql(clasificacion.getNombre())
        );
    }
}