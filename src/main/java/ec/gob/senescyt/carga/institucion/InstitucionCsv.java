package ec.gob.senescyt.carga.institucion;

import ec.gob.senescyt.carga.AbstractConversorCsv;
import ec.gob.senescyt.usuario.core.Institucion;

import java.io.IOException;

public class InstitucionCsv extends AbstractConversorCsv {

    public InstitucionCsv() {
        super(Institucion.class);
    }

    @Override
    protected String entidadASql(Object entidad) {
        Institucion institucion = (Institucion) entidad;

        return String.format("INSERT INTO %s VALUES (%s, %s, %s, %s, %s, %s, %s, %s);", nombreTabla,
                longASql(institucion.getId()), stringASql(institucion.getNombre()),
                longASql(institucion.getRegimenId()), stringASql(institucion.getRegimen()),
                longASql(institucion.getEstadoId()), stringASql(institucion.getEstado()),
                longASql(institucion.getCategoriaId()), stringASql(institucion.getCategoria())
        );
    }

    public static void main(String... args) throws IOException {
        convertir(new InstitucionCsv(), args);
    }
}
