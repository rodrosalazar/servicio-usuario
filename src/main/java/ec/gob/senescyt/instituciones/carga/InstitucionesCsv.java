package ec.gob.senescyt.instituciones.carga;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import ec.gob.senescyt.usuario.core.Institucion;

import java.io.IOException;

public class InstitucionesCsv {

    public static final String NOMBRE_TABLA = "instituciones";

    public String filaASql(String filaCsv) throws IOException {
        CsvMapper mapper = new CsvMapper();

        Institucion institucion = mapper.readerWithSchemaFor(Institucion.class).readValue(filaCsv);

        return String.format("INSERT INTO %s VALUES (%s, %s, %s, %s, %s, %s, %s, %s);", NOMBRE_TABLA,
                longToSql(institucion.getId()), stringToSql(institucion.getNombre()),
                longToSql(institucion.getIdRegimen()), stringToSql(institucion.getRegimen()),
                longToSql(institucion.getIdEstado()), stringToSql(institucion.getEstado()),
                longToSql(institucion.getIdCategoria()), stringToSql(institucion.getCategoria())
        );
    }

    private String longToSql(long number) {
        if (number == 0) {
            return "NULL";
        }
        return String.valueOf(number);
    }

    private String stringToSql(String string) {
        if (string == null) {
            return "NULL";
        }
        return "'" + string + "'";
    }
}
