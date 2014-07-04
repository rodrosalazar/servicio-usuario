package ec.gob.senescyt.carga.cine;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import ec.gob.senescyt.usuario.core.cine.Clasificacion;

import java.io.File;
import java.io.IOException;

public class ClasificacionCsv {

    public static final String NOMBRE_TABLA = "cine_clasificaciones";

    public String lineaASql(String lineaCsv) throws IOException {
        CsvMapper mapeador = new CsvMapper();
        mapeador.enable(CsvParser.Feature.TRIM_SPACES);

        Clasificacion clasificacion = mapeador.readerWithSchemaFor(Clasificacion.class).readValue(lineaCsv);

        return entidadASql(clasificacion);
    }

    public String archivoASql(File archivo) throws IOException {
        CsvMapper mapeador = new CsvMapper();
        mapeador.enable(CsvParser.Feature.TRIM_SPACES);
        CsvSchema esquema = mapeador.schemaFor(Clasificacion.class).withHeader();

        String resultado = "";

        MappingIterator<Object> iterador = mapeador.reader(Clasificacion.class).with(esquema).readValues(archivo);
        while (iterador.hasNext()) {
            resultado += entidadASql((Clasificacion) iterador.next()) + "\n";
        }

        return resultado;
    }

    private String entidadASql(Clasificacion clasificacion) {
        return String.format("INSERT INTO %s VALUES (%s, %s);", NOMBRE_TABLA,
                stringASql(clasificacion.getId()),
                stringASql(clasificacion.getNombre())
        );
    }

    private String stringASql(String string) {
        if (string == null) {
            return "NULL";
        }
        return "'" + string + "'";
    }
}













