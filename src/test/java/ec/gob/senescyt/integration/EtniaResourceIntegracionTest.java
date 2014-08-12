package ec.gob.senescyt.integration;

import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.commons.enums.ElementosRaicesJSONEnum;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class EtniaResourceIntegracionTest extends AbstractIntegracionTest {

    @Test
    public void debeObtenerTodasLasEtnias() {
        ClientResponse response = hacerGet("etnias");

        assertThat(response.getStatus(), is(200));
        assertThat(((List) (response.getEntity(Map.class).get(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_ETNIAS.getNombre()))).size(), is(not(0)));
    }
}
