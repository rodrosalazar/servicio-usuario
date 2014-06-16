package ec.gob.senescyt.usuario.resources;

import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.usuario.core.Perfil;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class PerfilResourceTest {

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new PerfilResource())
            .build();

    @Test
    public void puedoIngresarUnNombreDePerfil() {
        ClientResponse response = resources.client().resource("/perfil").post(ClientResponse.class, "Perfil 1");
        Perfil nuevoPerfil = response.getEntity(Perfil.class);

        assertThat(response.getStatus()).isEqualTo(201);
        assertThat(nuevoPerfil.getNombre()).isEqualTo("Perfil 1");
    }

    @Test
    public void noPuedoCrearPerfilSinNombre() {
        ClientResponse response = resources.client().resource("/perfil").post(ClientResponse.class, "");

        assertThat(response.getStatus()).isEqualTo(400);
    }

    @Test
    public void debePersistirElPerfil() {
        PerfilResource perfilResource = new PerfilResource();

        //verify(mock(servicioPerfil.guardar(perfil)));
    }

}