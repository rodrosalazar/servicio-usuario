package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.gob.senescyt.usuario.core.cine.Area;
import ec.gob.senescyt.usuario.core.cine.Clasificacion;
import ec.gob.senescyt.usuario.core.cine.Subarea;
import io.dropwizard.jackson.Jackson;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;
import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class Cine1997Test {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private Clasificacion clasificacion;
    private Area area;
    private Subarea subarea;

    public static final String ID_CLASIFICACION = "001";
    public static final String NOMBRE_CLASIFICACION = "CINE-UNESCO 1997";
    public static final String ID_AREA = "1";
    public static final String NOMBRE_AREA = "EDUCACION";
    public static final String ID_SUBAREA = "14";
    public static final String NOMBRE_SUBAREA = "FORMACION DE PERSONAL DOCENTE Y CIENCIAS DE LA EDUCACION";

    @Before
    public void setup() {
        subarea = new Subarea(ID_SUBAREA, NOMBRE_SUBAREA);
        area = new Area(ID_AREA, NOMBRE_AREA, newArrayList(subarea));
        clasificacion = new Clasificacion(ID_CLASIFICACION, NOMBRE_CLASIFICACION, newArrayList(area));
    }

    @Test
    public void debeDeserializarUnaClasificacionDesdeJSON() throws Exception {
        Clasificacion clasificacionDeserializada = MAPPER.readValue(fixture("fixtures/clasificacion.json"), Clasificacion.class);

        assertThat(clasificacionDeserializada.getId(), is(clasificacion.getId()));
        assertThat(clasificacionDeserializada.getNombre(), is(clasificacion.getNombre()));

        Area areaDeserializada = clasificacionDeserializada.getAreas().get(0);
        assertThat(areaDeserializada.getId(), is(area.getId()));

        Subarea subareaDeserializada = areaDeserializada.getSubareas().get(0);
        assertThat(subareaDeserializada.getId(), is(subarea.getId()));
    }


    @Test
    public void debeSerializarUnaClasificacionAJSON() throws Exception {
        String clasificacionSerializada = MAPPER.writeValueAsString(clasificacion);
        assertThat(clasificacionSerializada, is(fixture("fixtures/clasificacion.json")));
    }
}
