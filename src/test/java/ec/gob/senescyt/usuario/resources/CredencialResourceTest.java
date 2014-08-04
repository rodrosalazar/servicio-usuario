package ec.gob.senescyt.usuario.resources;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.commons.builders.CredencialBuilder;
import ec.gob.senescyt.usuario.core.Credencial;
import ec.gob.senescyt.usuario.dao.CredencialDAO;
import ec.gob.senescyt.usuario.exceptions.ValidacionExceptionMapper;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static ec.gob.senescyt.commons.helpers.ResourceTestHelper.assertErrorMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class CredencialResourceTest {

    public static final String CAMPO_EN_BLANCO = "";
    private static CredencialDAO credencialDAO = mock(CredencialDAO.class);
    private static CredencialResource credencialResource = new CredencialResource(credencialDAO);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(credencialResource)
            .addProvider(ValidacionExceptionMapper.class)
            .build();
    private Client client;

    @Before
    public void setUp() {
        client = resources.client();
    }

    @After
    public void tearDown() throws Exception {
        reset(credencialDAO);
    }

    @Test
    public void debeVerificarQueLaContraseniaNoEsteEnBlanco() throws Exception {
        Credencial credencial = CredencialBuilder.nuevaCredencial()
                .con(c -> c.contrasenia = CAMPO_EN_BLANCO)
                .generar();

        ClientResponse response = client.resource("/credenciales")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, credencial);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "El campo es obligatorio");
        verifyZeroInteractions(credencialDAO);
    }

    @Test
    public void debeVerificarQueLaContraseniaTengaAlMenos6Caracteres() {
        Credencial credencial = CredencialBuilder.nuevaCredencial()
                .con(c -> c.contrasenia = "corta")
                .generar();

        ClientResponse response = client.resource("/credenciales")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, credencial);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe contener entre 6 y 15 caracteres");
        verifyZeroInteractions(credencialDAO);
    }

    @Test
    public void debeVerificarQueLaContraseniaTengaMaximo15Caracteres() {
        Credencial credencial = CredencialBuilder.nuevaCredencial()
                .con(c -> c.contrasenia = "unaClaveDemasiadoDemasiadoLarga")
                .generar();

        ClientResponse response = client.resource("/credenciales")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, credencial);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe contener entre 6 y 15 caracteres");
        verifyZeroInteractions(credencialDAO);
    }

    @Test
    public void debeVerificarQueLaContraseniaTengaAlMenosUnaMayuscula() {
        Credencial credencial = CredencialBuilder.nuevaCredencial()
                .con(c -> c.contrasenia = "clave-minuscula")
                .generar();

        ClientResponse response = client.resource("/credenciales")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, credencial);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe contener al menos una mayúscula");
        verifyZeroInteractions(credencialDAO);
    }

    @Test
    public void debeVerificarQueLaContraseniaTengaAlMenosUnaMinuscula() {
        Credencial credencial = CredencialBuilder.nuevaCredencial()
                .con(c -> c.contrasenia = "A7654321")
                .generar();

        ClientResponse response = client.resource("/credenciales")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, credencial);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe contener al menos una minúscula");
        verifyZeroInteractions(credencialDAO);
    }

    @Test
    public void debeVerificarQueLaContraseniaTengaAlMenosUnNumero() {
        Credencial credencial = CredencialBuilder.nuevaCredencial()
                .con(c -> c.contrasenia = "SOLOLETRAS")
                .generar();

        ClientResponse response = client.resource("/credenciales")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, credencial);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe contener al menos un número");
        verifyZeroInteractions(credencialDAO);
    }

    @Test
    public void debeGuardarLaCredencialSiLaContraseniaEsValida() {
        Credencial credencial = CredencialBuilder.nuevaCredencial().generar();

        ClientResponse response = client.resource("/credenciales")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, credencial);

        System.err.println(response.getEntity(String.class));
        assertThat(response.getStatus(), is(201));
        verify(credencialDAO).guardar(any(Credencial.class));
    }
}