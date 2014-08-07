package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class InstitucionTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private Institucion institucion;

    @Before
    public void setup() {
        long idInstitucion = 1l;
        String nombreInstitucion = "ESPE";
        long regimenId = 1L;
        String nombreRegimen = "REGIMENTEST";
        long estadoId = 1L;
        String nombreEstado = "ESTADO";
        long categoriaId = 1L;
        String nombreCategoria = "CATEGORIA";

        institucion = new Institucion(idInstitucion, nombreInstitucion, regimenId, nombreRegimen, estadoId, nombreEstado, categoriaId, nombreCategoria);
    }

    @Test
    public void debeDeserializarUnUsuarioDesdeJSON() throws IOException {
        Institucion institucionDeserializada = MAPPER.readValue(fixture("fixtures/institucion_con_id.json"), Institucion.class);

        assertThat(institucionDeserializada.getId(),is(institucion.getId()));
        assertThat(institucionDeserializada.getNombre(),is(institucion.getNombre()));
        assertThat(institucionDeserializada.getRegimenId(),is(institucion.getRegimenId()));
        assertThat(institucionDeserializada.getRegimen(),is(institucion.getRegimen()));
        assertThat(institucionDeserializada.getEstadoId(),is(institucion.getEstadoId()));
        assertThat(institucionDeserializada.getEstado(),is(institucion.getEstado()));
        assertThat(institucionDeserializada.getCategoriaId(),is(institucion.getCategoriaId()));
        assertThat(institucionDeserializada.getCategoria(),is(institucion.getCategoria()));
    }

    @Test
    public void debeSerializarUnUsuarioAJSON() throws IOException {
        String actual = MAPPER.writeValueAsString(institucion);
        assertThat(actual, is(fixture("fixtures/institucion_con_id.json")));
    }
}