package ec.gob.senescyt.carga;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import javax.persistence.Table;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public abstract class ConversorCsv {

    private final Class tipo;
    protected final String nombreTabla;

    protected ConversorCsv(Class tipo) {
        this.tipo = tipo;
        Table table = (Table) tipo.getAnnotation(Table.class);
        nombreTabla = table.name();
    }

    public String lineaASql(String lineaCsv) throws IOException {
        CsvMapper mapeador = new CsvMapper();
        mapeador.enable(CsvParser.Feature.TRIM_SPACES);

        Object entidad = mapeador.readerWithSchemaFor(tipo).readValue(lineaCsv);

        return entidadASql(entidad);
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

    protected abstract String entidadASql(Object entidad);

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

    protected String intASql(Integer number) {
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

    protected static void convertir(ConversorCsv conversorCsv, String... args) throws IOException {
        final String usoCorrecto = "\nUso correcto:\n" +
                "./gradlew runCsv -P params=\"paquete.Clase ruta-entrada.csv [ruta-salida.sql]\"\n\n" +
                "El archivo CSV de entrada esperado consiste en:\n" +
                "  - Una primera línea con los títulos de las columnas\n" +
                "  - Todas las líneas subsiguientes con los datos a importarse\n\n" +
                "Ejemplo:\n" +
                "CODIGO,NOMBRE,CODIGO_REGIMEN,REGIMEN,CODIGO_ESTADO,ESTADO,CODIGO_CATEGORIA,CATEGORIA\n" +
                "1001,ESCUELA POLITECNICA NACIONAL,01,PUBLICA,01,VIGENTE,01,A\n" +
                "1002,ESCUELA SUPERIOR POLITECNICA DE CHIMBORAZO,01,PUBLICA,01,VIGENTE,02,B\n" +
                "...";
        if (args.length == 0) {
            System.out.println(usoCorrecto);
            return;
        }
        String rutaOrigen = args[0];
        File archivoOrigen = new File(rutaOrigen);

        String rutaDestino = definirRutaDestino(archivoOrigen, args);

        File archivoDestino = new File(rutaDestino);
        OutputStreamWriter escritor = new OutputStreamWriter(new FileOutputStream((archivoDestino)), StandardCharsets.UTF_8);

        escritor.write(conversorCsv.archivoASql(archivoOrigen));
        escritor.close();
    }
}
