package ec.gob.senescyt.carga.arboles;

import ec.gob.senescyt.biblioteca.NivelArbol;
import ec.gob.senescyt.carga.ConversorCsv;

import java.io.IOException;

public class NivelArbolCsv extends ConversorCsv {
    public NivelArbolCsv() {
        super(NivelArbol.class);
    }

    @Override
    protected String entidadASql(Object entidad) {
        NivelArbol nivelArbol = (NivelArbol) entidad;

        return String.format("INSERT INTO %s VALUES (%s, %s, %s, %s);", nombreTabla,
                intASql(nivelArbol.getId()),
                stringASql(nivelArbol.getNombre()),
                intASql(nivelArbol.getArbolIdParaCsv()),
                intASql(nivelArbol.getNivelPadreIdParaCsv())
        );
    }

    public static void main(String... args) throws IOException {
        convertir(new NivelArbolCsv(), args);
    }
}

