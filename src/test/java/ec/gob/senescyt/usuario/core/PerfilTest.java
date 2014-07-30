package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PerfilTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private Perfil perfil;

    @Before
    public void setUp() {
        long moduloId = 1l;
        long funcionId = 2l;
        List<Acceso> accesos = newArrayList(Acceso.CREAR, Acceso.LEER);
        long moduloId2 = 2l;

        perfil = new Perfil("Perfil1", newArrayList(new Permiso(moduloId, funcionId, accesos), new Permiso(moduloId2, funcionId, accesos)));
    }

    @Test
    public void debeDeserializarUnPerfilDesdeJSON() throws IOException {
        long id1 = 3l;
        long id2 = 4l;

        Perfil actual = MAPPER.readValue(fixture("fixtures/perfil.json"), Perfil.class);

        assertThat(actual.getNombre(), is(perfil.getNombre()));
        assertThat(actual.getPermisos().size(), is(2));
        assertThat(actual.getPermisos().get(0).getModuloId(), is(perfil.getPermisos().get(0).getModuloId()));
        assertThat(actual.getPermisos().get(0).getId(), is(id1));
        assertThat(actual.getPermisos().get(0).getAccesos(), is(perfil.getPermisos().get(0).getAccesos()));
        assertThat(actual.getPermisos().get(1).getId(), is(id2));
        assertThat(actual.getPermisos().get(1).getModuloId(), is(perfil.getPermisos().get(1).getModuloId()));
        assertThat(actual.getPermisos().get(1).getAccesos(), is(perfil.getPermisos().get(1).getAccesos()));
    }

    @Test
    public void debeSerializarUnPerfilAJSON() throws Exception {
        String actual = MAPPER.writeValueAsString(perfil);

        assertThat(actual, is(fixture("fixtures/perfil_con_id.json")));
    }
}
