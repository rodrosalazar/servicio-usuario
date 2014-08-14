package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.gob.senescyt.commons.builders.UsuarioBuilder;
import io.dropwizard.jackson.Jackson;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class UsuarioTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private Usuario usuario;

    @Before
    public void setUp() {
        Institucion institucion = new Institucion(1L, "PUCE", 1L, "R", 1L, "A", 1L, "ACADEMICO");

        usuario = UsuarioBuilder.nuevoUsuario()
                .con(u -> u.numeroIdentificacion = "1718642174")
                .con(u -> u.primerNombre = "Nelson")
                .con(u -> u.segundoNombre = "Alberto")
                .con(u -> u.primerApellido = "Jumbo")
                .con(u -> u.segundoApellido = "Hidalgo")
                .con(u -> u.emailInstitucional = "testEmail@senescyt.gob.ec")
                .con(u -> u.numeroAutorizacionQuipux = "SENESCYT-DFAPO-2014-65946-MI")
                .con(u -> u.fechaDeVigencia = new DateTime(2016, 7, 29, 0, 0, DateTimeZone.UTC))
                .con(u -> u.institucion = institucion)
                .con(u -> u.nombreUsuario = "nombreUsuario")
                .con(u -> u.perfiles = asList(1234l, 5678l, 9630l))
                .generar();
    }

    @Test
    public void debeDeserializarUnUsuarioDesdeJSON() throws IOException {
        Usuario actual = MAPPER.readValue(fixture("fixtures/usuario.json"), Usuario.class);

        MatcherAssert.assertThat(actual.getIdentificacion(), is(usuario.getIdentificacion()));
        MatcherAssert.assertThat(actual.getNombre(), is(usuario.getNombre()));
        MatcherAssert.assertThat(actual.getEmailInstitucional(), is(usuario.getEmailInstitucional()));
        MatcherAssert.assertThat(actual.getNumeroAutorizacionQuipux(), is(usuario.getNumeroAutorizacionQuipux()));
        MatcherAssert.assertThat(actual.getFinDeVigencia(), is(usuario.getFinDeVigencia()));
        MatcherAssert.assertThat(actual.getPerfiles().get(0), is(usuario.getPerfiles().get(0)));
        MatcherAssert.assertThat(actual.getInstitucion(), CoreMatchers.is(notNullValue()));
        MatcherAssert.assertThat(actual.getInstitucion().getId(), is(usuario.getInstitucion().getId()));
    }

    @Test
    public void debeSerializarUnJSONDesdeUnUsuario() throws IOException {
        String actual = MAPPER.writeValueAsString(usuario);
        MatcherAssert.assertThat(actual, is(fixture("fixtures/usuario_con_id.json")));
    }

}
