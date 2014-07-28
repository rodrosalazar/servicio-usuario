package ec.gob.senescyt.usuario.resources;

import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.usuario.core.CedulaInfo;
import ec.gob.senescyt.usuario.exceptions.CedulaInvalidaException;
import ec.gob.senescyt.usuario.exceptions.CredencialesIncorrectasException;
import ec.gob.senescyt.usuario.exceptions.ServicioNoDisponibleException;
import ec.gob.senescyt.usuario.services.ServicioCedula;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static ec.gob.senescyt.commons.helpers.ResourceTestHelper.assertErrorMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class BusquedaResourceTest {

    private static ServicioCedula servicioCedula = mock(ServicioCedula.class);
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new BusquedaResource(servicioCedula))
            .build();

    @Before
    public void setUp() {
        reset(servicioCedula);
    }

    @Test
    public void debeDevolverServicioNoDisponibleCuandoRegistroCivilNoEstaDisponible() throws CedulaInvalidaException, ServicioNoDisponibleException, CredencialesIncorrectasException {
        String cedulaIndiferente = "1111";
        when(servicioCedula.buscar(cedulaIndiferente)).thenThrow(new ServicioNoDisponibleException("Servicio no disponible"));
        ClientResponse response = resources.client().resource("/busqueda")
                .queryParam("cedula", cedulaIndiferente)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        verify(servicioCedula).buscar(eq(cedulaIndiferente));
        assertThat(response.getStatus(), is(503));
        assertErrorMessage(response, "Servicio no disponible");
    }

    @Test
    public void debeDevolverBadRequestCuandoLaCedulaEsInvalida() throws CedulaInvalidaException, ServicioNoDisponibleException, CredencialesIncorrectasException {
        String cedulaInvalida = "1111";
        when(servicioCedula.buscar(cedulaInvalida)).thenThrow(new CedulaInvalidaException("Cedula enviada no corresponde a un usuario no existe o no esta registrado"));

        ClientResponse response = resources.client().resource("/busqueda")
                .queryParam("cedula", cedulaInvalida)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        verify(servicioCedula).buscar(eq(cedulaInvalida));
        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Cedula enviada no corresponde a un usuario no existe o no esta registrado");
    }

    @Test
    public void debeDevolverLosDatosDeLaCedulaCuandoEsValida() throws CedulaInvalidaException, ServicioNoDisponibleException, CredencialesIncorrectasException {
        String cedulaValida = "1111111116";
        String nombre = "Cedula correcta";
        String parroquia = "Parroquia Irrelevante";
        String direccion = "Direccion Irrelevante";
        String idProvincia = "02";
        String provincia = "Provincia Irrelevante";
        String canton = "Canton Irrelevante";
        String fechaNacimiento = "01/01/1980";
        String genero = "MASCULINO";
        String nacionalidad = "SUAZI";
        when(servicioCedula.buscar(cedulaValida)).thenReturn(new CedulaInfo(nombre, direccion, provincia, idProvincia, canton,
                parroquia, fechaNacimiento, genero, nacionalidad));

        ClientResponse response = resources.client().resource("/busqueda")
                .queryParam("cedula", cedulaValida)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        verify(servicioCedula).buscar(eq(cedulaValida));
        assertThat(response.getStatus(), is(200));
        CedulaInfo cedulaInfo = response.getEntity(CedulaInfo.class);
        assertThat(cedulaInfo.getNombre(), is(nombre));
        assertThat(cedulaInfo.getDireccionCompleta(), is(direccion));
        assertThat(cedulaInfo.getIdProvincia(), is(idProvincia));
        assertThat(cedulaInfo.getProvincia(), is(provincia));
        assertThat(cedulaInfo.getCanton(), is(canton));
        assertThat(cedulaInfo.getParroquia(), is(parroquia));
        assertThat(cedulaInfo.getFechaNacimiento(), is(fechaNacimiento));
        assertThat(cedulaInfo.getGenero(), is(genero));
        assertThat(cedulaInfo.getNacionalidad(), is(nacionalidad));
    }
}