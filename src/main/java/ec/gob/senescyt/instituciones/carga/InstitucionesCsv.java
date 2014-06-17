package ec.gob.senescyt.instituciones.carga;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import ec.gob.senescyt.usuario.core.Institucion;

import java.io.IOException;

public class InstitucionesCsv {

    public String filaASql(String filaCsv) throws IOException {
        CsvMapper mapper = new CsvMapper();

        Institucion institucion = mapper.readerWithSchemaFor(Institucion.class).readValue(filaCsv);

        return String.format("INSERT INTO instituciones VALUES (%s, '%s', %s, '%s', %s, '%s', %s, '%s');",
                institucion.getId(), institucion.getNombre(), institucion.getIdRegimen(), institucion.getRegimen(),
                institucion.getIdEstado(), institucion.getEstado(), institucion.getIdCategoria(), institucion.getCategoria());
    }
}
