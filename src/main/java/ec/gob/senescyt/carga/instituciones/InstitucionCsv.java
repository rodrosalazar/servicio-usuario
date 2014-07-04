package ec.gob.senescyt.carga.instituciones;

import ec.gob.senescyt.carga.ConversorCsv;
import ec.gob.senescyt.usuario.core.Institucion;

public class InstitucionCsv extends ConversorCsv {

    public static final String NOMBRE_TABLA = "instituciones";

    public InstitucionCsv() {
        super(Institucion.class);
    }

    @Override
    protected String entidadASql(Object object) {
        Institucion institucion = (Institucion) object;

        return String.format("INSERT INTO %s VALUES (%s, %s, %s, %s, %s, %s, %s, %s);", NOMBRE_TABLA,
                longASql(institucion.getId()), stringASql(institucion.getNombre()),
                longASql(institucion.getRegimenId()), stringASql(institucion.getRegimen()),
                longASql(institucion.getEstadoId()), stringASql(institucion.getEstado()),
                longASql(institucion.getCategoriaId()), stringASql(institucion.getCategoria())
        );
    }

}
