package ec.gob.senescyt.usuario.resources;

import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.dao.PerfilDAO;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class PerfilResourceTest {

    private PerfilDAO perfilDAO;
    private PerfilResource perfilResource;

    @Before
    public void setUp() throws Exception {
        perfilDAO = mock(PerfilDAO.class);
        perfilResource = new PerfilResource(perfilDAO);
    }

    @Test
    public void puedoIngresarUnNombreDePerfil() {
        Response response = perfilResource.crearPerfil("Perfil 1");
        Perfil nuevoPerfil = (Perfil) response.getEntity();

        verify(perfilDAO).guardar(any(Perfil.class));
        assertThat(response.getStatus()).isEqualTo(201);
        assertThat(nuevoPerfil.getNombre()).isEqualTo("Perfil 1");
    }

    @Test
    public void noPuedoCrearPerfilSinNombre() {
        Response response = perfilResource.crearPerfil("");

        verifyZeroInteractions(perfilDAO);
        assertThat(response.getStatus()).isEqualTo(400);
    }
}