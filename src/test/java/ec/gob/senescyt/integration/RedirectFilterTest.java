package ec.gob.senescyt.integration;

import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.UsuarioApplication;
import ec.gob.senescyt.UsuarioConfiguration;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class RedirectFilterTest extends AbstractIntegracionTest {

    @ClassRule
    public static final DropwizardAppRule<UsuarioConfiguration> RULE = new DropwizardAppRule<>(UsuarioApplication.class, resourceFilePath(CONFIGURACION));

    @Override
    protected DropwizardAppRule<UsuarioConfiguration> getRule() {
        return RULE;
    }

    @Test
    public void debeDevolverRedireccionAHTTPSCuandoElRequestSeHaceConHTTP() {
        ClientResponse response = client.resource(
                String.format("http://localhost:%d/instituciones", RULE.getLocalPort()))
                .get(ClientResponse.class);

        assertThat(response.getStatus(), is(301));
    }
}