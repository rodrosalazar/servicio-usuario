package ec.gob.senescyt.titulos.resources;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.commons.resources.builders.ConstructorRespuestas;
import ec.gob.senescyt.titulos.core.ModalidadEducacion;
import ec.gob.senescyt.titulos.core.NivelDeFormacion;
import ec.gob.senescyt.titulos.core.TipoDeInstitucion;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

public class CatalogosResourceTest {

    @ClassRule
    public static final ResourceTestRule RESOURCES = ResourceTestRule.builder()
            .addResource(new CatalogosResource(new ConstructorRespuestas()))
            .build();

    private Client client;

    @Before
    public void setUp() {
        client = RESOURCES.client();
    }

    @Test
    public void debeObtenerTodosLosTiposDeInstitucion() {
        ClientResponse response = client.resource("/catalogos/tiposDeInstitucion")
                .get(ClientResponse.class);

        assertThat(response.getStatus()).isEqualTo(200);
        Map<String, List<TipoDeInstitucion>> resultado = response.getEntity(Map.class);
        assertThat(resultado).isNotEmpty();
        List<TipoDeInstitucion> tiposDeInstitucion = resultado.get("tiposDeInstitucion");
        assertThat(tiposDeInstitucion.size()).isEqualTo(2);
    }

    @Test
    public void debeObtenerTodosLosNivelesDeFormacion() {
        ClientResponse response = client.resource("/catalogos/nivelesDeFormacion")
                .get(ClientResponse.class);

        assertThat(response.getStatus()).isEqualTo(200);
        Map<String, List<NivelDeFormacion>> resultado = response.getEntity(Map.class);
        assertThat(resultado).isNotEmpty();
        List<NivelDeFormacion> nivelesDeFormacion = resultado.get("nivelesDeFormacion");
        assertThat(nivelesDeFormacion.size()).isEqualTo(4);
    }

    @Test
    public void debeObtenerTodosLasModalidadesDeEducacion() {
        ClientResponse response = client.resource("/catalogos/modalidadesDeEducacion")
                .get(ClientResponse.class);

        assertThat(response.getStatus()).isEqualTo(200);
        Map<String, List<ModalidadEducacion>> resultado = response.getEntity(Map.class);
        assertThat(resultado).isNotEmpty();
        List<ModalidadEducacion> modalidadEducacion = resultado.get("modalidadesDeEducacion");
        assertThat(modalidadEducacion.size()).isEqualTo(2);
    }
}
