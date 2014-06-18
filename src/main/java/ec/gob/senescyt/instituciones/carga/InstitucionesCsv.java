package ec.gob.senescyt.instituciones.carga;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import ec.gob.senescyt.usuario.core.Institucion;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class InstitucionesCsv {

    public static final String NOMBRE_TABLA = "instituciones";

    public String lineaASql(String lineaCsv) throws IOException {
        CsvMapper mapeador = new CsvMapper();
        mapeador.enable(CsvParser.Feature.TRIM_SPACES);

        Institucion institucion = mapeador.readerWithSchemaFor(Institucion.class).readValue(lineaCsv);

        return institucionASql(institucion);
    }

    public String archivoASql(File archivo) throws IOException {
        CsvMapper mapeador = new CsvMapper();
        mapeador.enable(CsvParser.Feature.TRIM_SPACES);
        CsvSchema esquema = mapeador.schemaFor(Institucion.class).withHeader();

        String resultado = "";

        MappingIterator<Object> iterador = mapeador.reader(Institucion.class).with(esquema).readValues(archivo);
        while (iterador.hasNext()) {
            resultado += institucionASql((Institucion) iterador.next()) + "\n";
        }

        return resultado;
    }

    private String institucionASql(Institucion institucion) {
        return String.format("INSERT INTO %s VALUES (%s, %s, %s, %s, %s, %s, %s, %s);", NOMBRE_TABLA,
                longASql(institucion.getId()), stringASql(institucion.getNombre()),
                longASql(institucion.getIdRegimen()), stringASql(institucion.getRegimen()),
                longASql(institucion.getIdEstado()), stringASql(institucion.getEstado()),
                longASql(institucion.getIdCategoria()), stringASql(institucion.getCategoria())
        );
    }

    private String longASql(long number) {
        if (number == 0) {
            return "NULL";
        }
        return String.valueOf(number);
    }

    private String stringASql(String string) {
        if (string == null) {
            return "NULL";
        }
        return "'" + string + "'";
    }

    public static void main(String... args) throws IOException {

        if (args.length == 0) {
            System.out.print("Uso correcto...");
            return;
        }
        String rutaOrigen = args[0];
        File archivoOrigen = new File(rutaOrigen);

        String rutaDestino = definirRutaDestino(archivoOrigen, args);

        File archivoDestino = new File(rutaDestino);
        BufferedWriter escritor = new BufferedWriter(new FileWriter(archivoDestino));

        escritor.write(new InstitucionesCsv().archivoASql(archivoOrigen));
        escritor.close();
    }

    private static String definirRutaDestino(File archivoOrigen, String[] args) {
        String nombreArchivoDestino = archivoOrigen.getName().replace(".csv", ".sql");

        if (args.length == 1) {
            return nombreArchivoDestino;
        }

        String argDestino = args[1];
        if (new File(argDestino).isDirectory()) {
            return argDestino + File.separator + nombreArchivoDestino;
        }

        return argDestino;
    }
}
