package ec.gob.senescyt.integration;

import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.UsuarioApplication;
import ec.gob.senescyt.UsuarioConfiguration;
import ec.gob.senescyt.commons.enums.ElementosRaicesJSONEnum;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class EtniaResourceIntegracionTest extends BaseIntegracionTest {

    @ClassRule
    public static final DropwizardAppRule<UsuarioConfiguration> RULE = new DropwizardAppRule<>(UsuarioApplication.class, resourceFilePath(CONFIGURACION));

    @Override
    protected DropwizardAppRule<UsuarioConfiguration> getRule() {
        return RULE;
    }

    @Test
    public void debeObtenerTodasLasEtnias() {
        ClientResponse response = hacerGet("etnias");

        assertThat(response.getStatus(), is(200));
        assertThat(((List)(response.getEntity(HashMap.class).get(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_ETNIAS.getNombre()))).size(), is(not(0)));
    }
}
