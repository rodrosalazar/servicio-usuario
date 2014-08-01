package ec.gob.senescyt.integration;

import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.UsuarioApplication;
import ec.gob.senescyt.UsuarioConfiguration;
import ec.gob.senescyt.usuario.core.cine.Area;
import ec.gob.senescyt.usuario.core.cine.Clasificacion;
import ec.gob.senescyt.usuario.core.cine.Detalle;
import ec.gob.senescyt.usuario.core.cine.Subarea;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ClasificacionResourceIntegracionTest extends BaseIntegracionTest {

    @ClassRule
    public static final DropwizardAppRule<UsuarioConfiguration> RULE = new DropwizardAppRule<>(UsuarioApplication.class, resourceFilePath(CONFIGURACION));

    @Override
    protected DropwizardAppRule<UsuarioConfiguration> getRule() {
        return RULE;
    }

    @Test
    public void debeObtenerElListadoDeCine1997() throws Exception {
        ClientResponse response = hacerGet("cine/1997");

        assertThat(response.getStatus(), is(200));
        List<Area> areas = response.getEntity(Clasificacion.class).getAreas();
        assertThat(areas.size(), is(not(0)));
        assertThat(areas.get(0).getSubareas().size(), is(not(0)));
    }

    @Test
    public void debeObtenerElListadoDeCine2013() throws Exception {
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