package ec.gob.senescyt.integration;

import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.commons.enums.ElementosRaicesJSONEnum;
import ec.gob.senescyt.titulos.core.UniversidadExtranjera;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class InstitucionResourceIntegracionTest extends AbstractIntegracionTest {

    @Test
    public void debeObtenerTodasLasInstituciones() {
        ClientResponse response = hacerGet("instituciones");

        assertThat(response.getStatus(), is(200));
        assertThat(response.getEntity(List.class).size(), is(not(0)));
    }

    @Test
    public void debeObtenerUniversidadesDeConvenio() {
        ClientResponse response = hacerGet("instituciones/convenio");

        assertThat(response.getStatus(), is(200));

        List<UniversidadExtranjera> universidadesConvenioEncontradas = (List<UniversidadExtranjera>)
                response.getEntity(Map.class).get(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_UNIVERSIDADES_CONVENIO.getNombre());

        assertThat(universidadesConvenioEncontradas.size(), is(not(0)));
    }

    @Test
    public void debeObtenerUniversidadesDeListado() {
        ClientResponse response = hacerGet("instituciones/listado");

        assertThat(response.getStatus(), is(200));

        List<UniversidadExtranjera> universidadesListadoEncontradas = (List<UniversidadExtranjera>)
                response.getEntity(Map.class).get(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_UNIVERSIDADES_LISTADO.getNombre());

        assertThat(universidadesListadoEncontradas.size(), is(not(0)));
    }
}