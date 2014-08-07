package ec.gob.senescyt.carga.visa;

import ec.gob.senescyt.carga.AbstractConversorCsv;
import ec.gob.senescyt.titulos.core.CategoriaVisa;

import java.io.IOException;

public class CategoriaVisaCsv extends AbstractConversorCsv {
    protected CategoriaVisaCsv() {
        super(CategoriaVisa.class);
    }

    @Override
    protected String entidadASql(Object entidad) {
        CategoriaVisa tipoVisa = (CategoriaVisa) entidad;

        return String.format("INSERT INTO %s VALUES (%s, %s, %s);", nombreTabla,
                stringASql(tipoVisa.getId()),
                stringASql(tipoVisa.getTipoVisaIdParaCsv()),
                stringASql(tipoVisa.getNombre())
        );
    }

    public static void main(String... args) throws IOException {
        convertir(new CategoriaVisaCsv(), args);
    }
}
