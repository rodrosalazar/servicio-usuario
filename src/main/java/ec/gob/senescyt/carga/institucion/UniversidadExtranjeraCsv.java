package ec.gob.senescyt.carga.institucion;

import ec.gob.senescyt.carga.ConversorCsv;
import ec.gob.senescyt.titulos.core.UniversidadExtranjera;

import java.io.IOException;

public class UniversidadExtranjeraCsv extends ConversorCsv{
    protected UniversidadExtranjeraCsv() {
        super(UniversidadExtranjera.class);
    }

    @Override
    protected String entidadASql(Object entidad) {
        UniversidadExtranjera universidadExtranjera = (UniversidadExtranjera) entidad;

        return String.format("INSERT INTO %s VALUES (%s, %s, %s, %s);", nombreTabla,
                stringASql(universidadExtranjera.getId()),
                stringASql(universidadExtranjera.getNombre()),
                stringASql(universidadExtranjera.getCodigoTipo()),
                stringASql(universidadExtranjera.getCodigoPais())
        );
    }

    public static void main(String... args) throws IOException {
        convertir(new UniversidadExtranjeraCsv(), args);
    }
}
