package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import java.io.IOException;

import static com.google.common.collect.Lists.newArrayList;
import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PerfilTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void debeDeserializarUnPerfilDesdeJSON() throws IOException {
        Perfil esperado = new Perfil("Perfil1", newArrayList(new Permiso("modulo1", null), new Permiso("modulo2", null)));

        Perfil actual = MAPPER.readValue(fixture("fixtures/perfil.json"), Perfil.class);

        assertThat(actual.getNombre(), is(esperado.getNombre()));
        assertThat(actual.getPermisos().get(0).getNombre(), is(esperado.getPermisos().get(0).getNombre()));
        assertThat(actual.getPermisos().get(1).getNombre(), is(esperado.getPermisos().get(1).getNombre()));
    }

    @Test
    public void debeSerializarUnJSONDesdeUnPerfil() throws Exception {
        Perfil perfilOrigen = new Perfil("Perfil1", newArrayList(new Permiso("modulo1", null), new Permiso("modulo2", null)));

        String actual = MAPPER.writeValueAsString(perfilOrigen);

        assertThat(actual, is(fixture("fixtures/perfil_con_id.json")));
    }
}
