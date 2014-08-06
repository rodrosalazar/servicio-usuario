package ec.gob.senescyt.titulos.resources;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.commons.resources.builders.ConstructorRespuestas;
import ec.gob.senescyt.titulos.core.TipoDeInstitucion;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

public class TipoDeInstitucionResourceTest {

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new TipoDeInstitucionResource(new ConstructorRespuestas()))
            .build();

    private Client client;

    @Before
    public void setUp() {
        client = resources.client();
    }

    @Test
    public void debeObtenerTodosLosTiposDeInstitucion() {
        ClientResponse response = client.resource("/tiposDeInstitucion").get(ClientResponse.class);

        assertThat(response.getStatus()).isEqualTo(200);
        Map<String, List<TipoDeInstitucion>> resultado = response.getEntity(Map.class);
        assertThat(resultado).isNotEmpty();
        List<TipoDeInstitucion> tiposDeInstitucion = resultado.get("tiposDeInstitucion");
        assertThat(tiposDeInstitucion.size()).isEqualTo(2);
    }
}
