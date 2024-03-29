package ec.gob.senescyt.carga.pais;

import ec.gob.senescyt.carga.AbstractConversorCsv;
import ec.gob.senescyt.titulos.core.Pais;

import java.io.IOException;

public class PaisCsv extends AbstractConversorCsv {

    protected PaisCsv() {
        super(Pais.class);
    }

    @Override
    protected String entidadASql(Object entidad) {
        Pais pais = (Pais) entidad;

        return String.format("INSERT INTO %s VALUES (%s, %s);", nombreTabla,
                stringASql(pais.getId()),
                stringASql(pais.getNombre())
        );
    }

    public static void main(String... args) throws IOException {
        convertir(new PaisCsv(), args);
    }
}
