package ec.gob.senescyt.carga.visa;

import ec.gob.senescyt.carga.ConversorCsv;
import ec.gob.senescyt.titulos.core.TipoVisa;

import java.io.IOException;

public class TipoVisaCsv extends ConversorCsv {
    protected TipoVisaCsv() {
        super(TipoVisa.class);
    }

    @Override
    protected String entidadASql(Object entidad) {
        TipoVisa tipoVisa = (TipoVisa) entidad;

        return String.format("INSERT INTO %s VALUES (%s, %s);", nombreTabla,
                stringASql(tipoVisa.getId()),
                stringASql(tipoVisa.getNombre())
        );
    }

    public static void main(String... args) throws IOException {
        convertir(new TipoVisaCsv(), args);
    }
}
