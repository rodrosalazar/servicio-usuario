package ec.gob.senescyt.carga.dpa;

import ec.gob.senescyt.carga.ConversorCsv;
import ec.gob.senescyt.titulos.core.Parroquia;

import java.io.IOException;

public class ParroquiaCsv extends ConversorCsv{
    protected ParroquiaCsv() {
        super(Parroquia.class);
    }
    @Override
    protected String entidadASql(Object entidad) {
        Parroquia parroquia = (Parroquia) entidad;

        return String.format("INSERT INTO %s VALUES (%s, %s, %s);", nombreTabla,
                stringASql(parroquia.getCantonIdParaCsv()),
                stringASql(parroquia.getId()),
                stringASql(parroquia.getNombre())
        );
    }

    public static void main(String... args) throws IOException {
        convertir(new ParroquiaCsv(), args);
    }
}
