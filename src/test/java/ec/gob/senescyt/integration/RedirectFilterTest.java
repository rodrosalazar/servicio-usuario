package ec.gob.senescyt.integration;

import com.sun.jersey.api.client.ClientResponse;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class RedirectFilterTest extends AbstractIntegracionTest {

    @Test
    public void debeDevolverRedireccionAHTTPSCuandoElRequestSeHaceConHTTP() {
        ClientResponse response = CLIENT.resource(
                String.format("http://localhost:%d/instituciones", RULE.getLocalPort()))
                .get(ClientResponse.class);

        assertThat(response.getStatus(), is(301));
    }
}