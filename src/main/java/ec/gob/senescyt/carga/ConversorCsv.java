package ec.gob.senescyt.carga;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import ec.gob.senescyt.carga.instituciones.InstitucionCsv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class ConversorCsv {

    private final Class tipo;

    protected ConversorCsv(Class tipo) {
        this.tipo = tipo;
    }

    public String lineaASql(String lineaCsv) throws IOException {
        CsvMapper mapeador = new CsvMapper();
        mapeador.enable(CsvParser.Feature.TRIM_SPACES);

        Object objeto = mapeador.readerWithSchemaFor(tipo).readValue(lineaCsv);

        return entidadASql(objeto);
    }

    public String archivoASql(File archivo) throws IOException {
        CsvMapper mapeador = new CsvMapper();
        mapeador.enable(CsvParser.Feature.TRIM_SPACES);
        CsvSchema esquema = mapeador.schemaFor(tipo).withHeader();

        String resultado = "";

        MappingIterator<Object> iterador = mapeador.reader(tipo).with(esquema).readValues(archivo);
        while (iterador.hasNext()) {
            resultado += entidadASql(iterador.next()) + "\n";
        }

        return resultado;
    }

    protected abstract String entidadASql(Object object) ;

    protected String stringASql(String string) {
        if (string == null) {
            return "NULL";
        }
        return "'" + string + "'";
    }

    protected String longASql(Long number) {
        if (number == null || number == 0) {
            return "NULL";
        }
        return String.valueOf(number);
    }

    private static String definirRutaDestino(File archivoOrigen, String... args) {
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

    public static void main(String... args) throws IOException {

        if (args.length == 0) {
            System.out.println("\nUso correcto:\n" +
                    "./gradlew runCsv -P params=\"ruta-entrada.csv [ruta-salida.sql]\"\n\n" +
                    "El archivo CSV de entrada esperado consiste en:\n" +
                    "  - Una primera línea con los títulos de las columnas\n" +
                    "  - Todas las líneas subsiguientes con los datos a importarse\n\n" +
                    "Ejemplo:\n" +
                    "CODIGO,NOMBRE,CODIGO_REGIMEN,REGIMEN,CODIGO_ESTADO,ESTADO,CODIGO_CATEGORIA,CATEGORIA\n" +
                    "1001,ESCUELA POLITECNICA NACIONAL,01,PUBLICA,01,VIGENTE,01,A\n" +
                    "1002,ESCUELA SUPERIOR POLITECNICA DE CHIMBORAZO,01,PUBLICA,01,VIGENTE,02,B\n" +
                    "...");
            return;
        }
        String rutaOrigen = args[0];
        File archivoOrigen = new File(rutaOrigen);

        String rutaDestino = definirRutaDestino(archivoOrigen, args);

        File archivoDestino = new File(rutaDestino);
        BufferedWriter escritor = new BufferedWriter(new FileWriter(archivoDestino));

        escritor.write(new InstitucionCsv().archivoASql(archivoOrigen));
        escritor.close();
    }
}
