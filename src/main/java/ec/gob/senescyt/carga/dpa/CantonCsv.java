package ec.gob.senescyt.carga.dpa;

import ec.gob.senescyt.carga.ConversorCsv;
import ec.gob.senescyt.titulos.core.Canton;

import java.io.IOException;

public class CantonCsv extends ConversorCsv{
    public CantonCsv() {
        super(Canton.class);
    }

    @Override
    protected String entidadASql(Object entidad) {
        Canton canton = (Canton) entidad;

        return String.format("INSERT INTO %s VALUES (%s, %s, %s);", nombreTabla,
                stringASql(canton.getProvinciaIdParaCsv()),
                stringASql(canton.getId()),
                stringASql(canton.getNombre())
        );
    }

    public static void main(String... args) throws IOException {
        convertir(new CantonCsv(), args);
    }
}
