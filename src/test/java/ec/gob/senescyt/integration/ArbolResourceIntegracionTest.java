package ec.gob.senescyt.integration;

import com.sun.jersey.api.client.ClientResponse;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ArbolResourceIntegracionTest extends AbstractIntegracionTest {

    @Test
    public void debeObtenerTodos() {
        ClientResponse response = hacerGet("arboles");

        assertThat(response.getStatus(), is(200));
    }
}