package ec.gob.senescyt.carga.dpa;

import ec.gob.senescyt.carga.AbstractConversorCsv;
import ec.gob.senescyt.titulos.core.Provincia;

import java.io.IOException;

public class ProvinciaCsv extends AbstractConversorCsv {

    protected ProvinciaCsv() {
        super(Provincia.class);
    }

    @Override
    protected String entidadASql(Object entidad) {
        Provincia provincia = (Provincia) entidad;

        return String.format("INSERT INTO %s VALUES (%s, %s);", nombreTabla,
                stringASql(provincia.getId()),
                stringASql(provincia.getNombre())
        );
    }

    public static void main(String... args) throws IOException {
        convertir(new ProvinciaCsv(), args);
    }
}
