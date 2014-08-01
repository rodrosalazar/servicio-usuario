package ec.gob.senescyt.integration;

import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.UsuarioApplication;
import ec.gob.senescyt.UsuarioConfiguration;
import ec.gob.senescyt.commons.enums.ElementosRaicesJSONEnum;
import ec.gob.senescyt.titulos.core.UniversidadExtranjera;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class InstitucionResourceIntegracionTest extends BaseIntegracionTest {

    @ClassRule
    public static final DropwizardAppRule<UsuarioConfiguration> RULE = new DropwizardAppRule<>(UsuarioApplication.class, resourceFilePath(CONFIGURACION));

    @Override
    protected DropwizardAppRule<UsuarioConfiguration> getRule() {
        return RULE;
    }

    @Test
    public void debeObtenerTodasLasInstituciones() throws Exception {
        ClientResponse response = hacerGet("instituciones");

        assertThat(response.getStatus(), is(200));
        assertThat(response.getEntity(List.class).size(), is(not(0)));
    }

    @Test
    public void debeObtenerUniversidadesDeConvenio() throws Exception {
        ClientResponse response = hacerGet("instituciones/convenio");

        assertThat(response.getStatus(), is(200));

        List<UniversidadExtranjera> universidadesConvenioEncontradas =
                (List) response.getEntity(HashMap.class).get(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_UNIVERSIDADES_CONVENIO.getNombre());

        assertThat(universidadesConvenioEncontradas.size(), is(not(0)));
    }

    @Test
    public void debeObtenerUniversidadesDeListado() throws Exception {
        ClientResponse response = hacerGet("instituciones/listado");

        assertThat(response.getStatus(), is(200));

        List<UniversidadExtranjera> universidadesListadoEncontradas =
                (List) response.getEntity(HashMap.class).get(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_UNIVERSIDADES_LISTADO.getNombre());

        assertThat(universidadesListadoEncontradas.size(), is(not(0)));
    }
}