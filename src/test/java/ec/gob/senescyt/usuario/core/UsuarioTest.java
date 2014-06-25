package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.gob.senescyt.usuario.enums.TipoDocumentoEnum;
import io.dropwizard.jackson.Jackson;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by njumbo on 25/06/14.
 */
public class UsuarioTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private Usuario usuario;

    @Before
    public void setup() {
        long idInstitucion = 1l;

        usuario = new Usuario(new Identificacion(TipoDocumentoEnum.CEDULA, "1718642174"),
                new Nombre("Nelson", "Alberto", "Jumbo", "Hidalgo"),
                "testEmail@senescyt.gob.ec", "123456",
                new DateTime().withDate(2015, 1, 12).withZone(DateTimeZone.UTC).withTimeAtStartOfDay(),
                idInstitucion, "nombreUsuario");
    }

    @Test
    public void debeDeserializarUnUsuarioDesdeJSON() throws Exception {
        Usuario actual = MAPPER.readValue(fixture("fixtures/usuario.json"), Usuario.class);

        assertThat(actual.getIdentificacion(), is(usuario.getIdentificacion()));
        assertThat(actual.getNombre(), is(usuario.getNombre()));
        assertThat(actual.getEmailInstitucional(), is(usuario.getEmailInstitucional()));
        assertThat(actual.getNumeroAutorizacionQuipux(), is(usuario.getNumeroAutorizacionQuipux()));
        assertThat(actual.getFinDeVigencia(), is(usuario.getFinDeVigencia()));
    }

    @Test
    public void debeSerializarUnJSONDesdeUnUsuario() throws Exception {
        String actual = MAPPER.writeValueAsString(usuario);
        assertThat(actual, is(fixture("fixtures/usuario_con_id.json")));

    }

}
