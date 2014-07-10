package ec.gob.senescyt.carga.pais;

import ec.gob.senescyt.carga.ConversorCsv;
import ec.gob.senescyt.usuario.core.Pais;

import java.io.IOException;

public class PaisCsv extends ConversorCsv {

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
