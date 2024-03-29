package ec.gob.senescyt.integration;

import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.usuario.core.cine.Area;
import ec.gob.senescyt.usuario.core.cine.Clasificacion;
import ec.gob.senescyt.usuario.core.cine.Detalle;
import ec.gob.senescyt.usuario.core.cine.Subarea;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ClasificacionResourceIntegracionTest extends AbstractIntegracionTest {

    @Test
    public void debeObtenerElListadoDeCine1997() {
        ClientResponse response = hacerGet("cine/1997");

        assertThat(response.getStatus(), is(200));
        List<Area> areas = response.getEntity(Clasificacion.class).getAreas();
        assertThat(areas.size(), is(not(0)));
        assertThat(areas.get(0).getSubareas().size(), is(not(0)));
    }

    @Test
    public void debeObtenerElListadoDeCine2013() {
        ClientResponse response = hacerGet("cine/2013");

        assertThat(response.getStatus(), is(200));

        List<Area> areas = response.getEntity(Clasificacion.class).getAreas();
        assertThat(areas.size(), is(not(0)));

        List<Subarea> subareas = areas.get(0).getSubareas();
        assertThat(subareas.size(), is(not(0)));

        List<Detalle> detalles = subareas.get(0).getDetalles();
        assertThat(detalles.size(), is(not(0)));
    }
}