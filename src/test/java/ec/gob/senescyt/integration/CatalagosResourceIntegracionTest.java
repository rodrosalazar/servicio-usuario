package ec.gob.senescyt.integration;

import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.commons.enums.ElementosRaicesJSONEnum;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CatalagosResourceIntegracionTest extends AbstractIntegracionTest {

    @Test
    public void debeObtenerElListadoDeTiposDeInstituciones() {
        ClientResponse response = hacerGet("catalogos/tiposDeInstitucion");
        assertThat(response.getStatus(), is(200));
        Map entidad = response.getEntity(Map.class);
        List tiposInstituciones = (List) entidad.get(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_TIPOS_INSTITUCION.getNombre());
        assertThat(tiposInstituciones.size(), is(2));
    }
}
