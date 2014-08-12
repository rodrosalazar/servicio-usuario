package ec.gob.senescyt.integration;

import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.commons.builders.PerfilBuilder;
import ec.gob.senescyt.usuario.core.Perfil;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PerfilResourceIntegracionTest extends AbstractIntegracionTest {

    @Test
    public void debeSalvarElPerfilConPermisos() {
        ClientResponse response = hacerPost("perfiles",PerfilBuilder.nuevoPerfil().generar());

        assertThat(response.getStatus(), is(201));
        Perfil perfilRespuesta = response.getEntity(Perfil.class);
        assertThat(perfilRespuesta.getId(), is(not(0l)));
        assertThat(perfilRespuesta.getPermisos().get(0).getId(), is(not(0l)));
    }
}
