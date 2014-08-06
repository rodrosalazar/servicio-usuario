package ec.gob.senescyt.integration;

import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.UsuarioApplication;
import ec.gob.senescyt.UsuarioConfiguration;
import ec.gob.senescyt.commons.builders.PerfilBuilder;
import ec.gob.senescyt.usuario.core.Perfil;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PerfilResourceIntegracionTest extends BaseIntegracionTest {

    @ClassRule
    public static final DropwizardAppRule<UsuarioConfiguration> RULE = new DropwizardAppRule<>(UsuarioApplication.class, resourceFilePath(CONFIGURACION));

    @Override
    protected DropwizardAppRule<UsuarioConfiguration> getRule() {
        return RULE;
    }

    @Test
    public void debeSalvarElPerfilConPermisos() {
        ClientResponse response = hacerPost("perfiles",PerfilBuilder.nuevoPerfil().generar());

        assertThat(response.getStatus(), is(201));
        Perfil perfilRespuesta = response.getEntity(Perfil.class);
        assertThat(perfilRespuesta.getId(), is(not(0l)));
        assertThat(perfilRespuesta.getPermisos().get(0).getId(), is(not(0l)));
    }
}
