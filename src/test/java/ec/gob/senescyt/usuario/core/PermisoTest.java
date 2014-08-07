package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.fest.util.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PermisoTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private Permiso perfil;

    @Before
    public void setUp() {
        perfil = new Permiso(1l, 2l, newArrayList(Acceso.CREAR, Acceso.LEER));
    }

    @Test
    public void debeDeserializarUnPermisoDesdeJSON() throws IOException {
        Permiso actual = MAPPER.readValue(fixture("fixtures/permiso.json"), Permiso.class);

        assertThat(actual.getModuloId(), is(perfil.getModuloId()));
        assertThat(actual.getFuncionId(), is(perfil.getFuncionId()));
//        assertThat(actual.getAccesos(), is(perfil.getAccesos()));
    }

    @Test
    public void debeSerializarUnJSONDesdeUnPermiso() throws IOException {
        String permisoDestino = MAPPER.writeValueAsString(perfil);

        assertThat(permisoDestino, is(fixture("fixtures/permiso_con_id.json")));
    }
}