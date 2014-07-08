package ec.gob.senescyt.carga.cine;

import ec.gob.senescyt.carga.ConversorCsv;
import ec.gob.senescyt.usuario.core.cine.Detalle;

import java.io.IOException;

public class DetalleCsv extends ConversorCsv {

    public DetalleCsv() {
        super(Detalle.class);
    }

    @Override
    protected String entidadASql(Object entidad) {

        Detalle detalle = (Detalle) entidad;

        return String.format("INSERT INTO %s VALUES (%s, %s, %s);", nombreTabla,
                stringASql(detalle.getId()),
                stringASql(detalle.getNombre()),
                stringASql(detalle.getSubareaIdParaCsv()));
    }

    public static void main(String... args) throws IOException {
        convertir(new DetalleCsv(), args);
    }
}
