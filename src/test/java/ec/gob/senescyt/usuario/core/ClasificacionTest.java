package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.gob.senescyt.usuario.core.cine.Area;
import ec.gob.senescyt.usuario.core.cine.Clasificacion;
import ec.gob.senescyt.usuario.core.cine.Detalle;
import ec.gob.senescyt.usuario.core.cine.Subarea;
import io.dropwizard.jackson.Jackson;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.google.common.collect.Lists.newArrayList;
import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ClasificacionTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private Clasificacion clasificacion;
    private Clasificacion clasificacion2013;
    private Area area;
    private Area area2013;
    private Subarea subarea;
    private Subarea subarea2013;
    private Detalle detalle2013;

    public static final String ID_CLASIFICACION = "001";
    public static final String NOMBRE_CLASIFICACION = "CINE-UNESCO 1997";
    public static final String ID_AREA = "1";
    public static final String NOMBRE_AREA = "EDUCACION";
    public static final String ID_SUBAREA = "14";
    public static final String NOMBRE_SUBAREA = "FORMACION DE PERSONAL DOCENTE Y CIENCIAS DE LA EDUCACION";

    public static final String ID_CLASIFICACION_2013 = "002";
    public static final String NOMBRE_CLASIFICACION_2013 = "CINE-UNESCO 2013";
    public static final String ID_AREA_2013 = "02";
    public static final String NOMBRE_AREA_2013 = "ARTES Y HUMANIDADES";
    public static final String ID_SUBAREA_2013 = "021";
    public static final String NOMBRE_SUBAREA_2013 = "ARTES";
    public static final String ID_DETALLE_2013 = "0213";
    public static final String NOMBRE_DETALLE_2013 = "BELLAS ARTES";

    @Before
    public void setup() {
        subarea = new Subarea(ID_SUBAREA, NOMBRE_SUBAREA, null);
        area = new Area(ID_AREA, NOMBRE_AREA, newArrayList(subarea));
        clasificacion = new Clasificacion(ID_CLASIFICACION, NOMBRE_CLASIFICACION, newArrayList(area));

        detalle2013 = new Detalle(ID_DETALLE_2013, NOMBRE_DETALLE_2013);
        subarea2013 = new Subarea(ID_SUBAREA_2013, NOMBRE_SUBAREA_2013, newArrayList(detalle2013));
        area2013 = new Area(ID_AREA_2013, NOMBRE_AREA_2013, newArrayList(subarea2013));
        clasificacion2013 = new Clasificacion(ID_CLASIFICACION_2013, NOMBRE_CLASIFICACION_2013, newArrayList(area2013));
    }

    @Test
    public void debeDeserializarUnaClasificacionDesdeJSON() throws IOException {
        Clasificacion clasificacionDeserializada = MAPPER.readValue(fixture("fixtures/clasificacion.json"), Clasificacion.class);

        assertThat(clasificacionDeserializada.getId(), is(clasificacion.getId()));
        assertThat(clasificacionDeserializada.getNombre(), is(clasificacion.getNombre()));

        Area areaDeserializada = clasificacionDeserializada.getAreas().get(0);
        assertThat(areaDeserializada.getId(), is(area.getId()));

        Subarea subareaDeserializada = areaDeserializada.getSubareas().get(0);
        assertThat(subareaDeserializada.getId(), is(subarea.getId()));
    }

    @Test
    public void debeDeserializarUnaClasificacion2013DesdeJSON() throws IOException {
        Clasificacion clasificacionDeserializada = MAPPER.readValue(fixture("fixtures/clasificacion_cine_2013.json"), Clasificacion.class);

        assertThat(clasificacionDeserializada.getId(), is(clasificacion2013.getId()));
        assertThat(clasificacionDeserializada.getNombre(), is(clasificacion2013.getNombre()));

        Area areaDeserializada = clasificacionDeserializada.getAreas().get(0);
        assertThat(areaDeserializada.getId(), is(area2013.getId()));

        Subarea subareaDeserializada = areaDeserializada.getSubareas().get(0);
        assertThat(subareaDeserializada.getId(), is(subarea2013.getId()));

        Detalle detalleDeserializado = subareaDeserializada.getDetalles().get(0);
        assertThat(detalleDeserializado.getId(), is(detalle2013.getId()));

    }

    @Test
    public void debeSerializarUnaClasificacionAJSON() throws IOException {
        String clasificacionSerializada = MAPPER.writeValueAsString(clasificacion);
        assertThat(clasificacionSerializada, is(fixture("fixtures/clasificacion.json")));
    }

    @Test
    public void debeSerializarUnaClasificacion2013AJSON() throws IOException {
        String clasificacionSerializada = MAPPER.writeValueAsString(clasificacion2013);
        assertThat(clasificacionSerializada, is(fixture("fixtures/clasificacion_cine_2013.json")));
    }
}
