package ec.gob.senescyt.usuario.resources;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.biblioteca.dto.ContraseniaToken;
import ec.gob.senescyt.commons.builders.ContraseniaTokenBuilder;
import ec.gob.senescyt.commons.builders.MensajeErrorBuilder;
import ec.gob.senescyt.commons.builders.UsuarioBuilder;
import ec.gob.senescyt.commons.lectores.LectorArchivoDePropiedades;
import ec.gob.senescyt.commons.lectores.enums.ArchivosPropiedadesEnum;
import ec.gob.senescyt.usuario.core.Credencial;
import ec.gob.senescyt.usuario.core.Token;
import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.dao.CredencialDAO;
import ec.gob.senescyt.usuario.dao.TokenDAO;
import ec.gob.senescyt.usuario.exceptions.ValidacionExceptionMapper;
import ec.gob.senescyt.usuario.services.ServicioCredencial;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.util.Optional;

import static ec.gob.senescyt.commons.helpers.ResourceTestHelper.assertErrorMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class CredencialResourceTest {
    public static final String CAMPO_EN_BLANCO = "";
    public static final LectorArchivoDePropiedades LECTOR_ARCHIVO_DE_PROPIEDADES = new LectorArchivoDePropiedades(ArchivosPropiedadesEnum.ARCHIVO_VALIDACIONES.getBaseName());
    public static final MensajeErrorBuilder MENSAJE_ERROR_BUILDER = new MensajeErrorBuilder(LECTOR_ARCHIVO_DE_PROPIEDADES);
    public static final String TOKEN_VALIDO = "e590f1a6-517d-4c52-95ad-32c05504a2dc";

    private static CredencialDAO credencialDAO = mock(CredencialDAO.class);
    private static TokenDAO tokenDAO = mock(TokenDAO.class);
    private static ServicioCredencial servicioCredencial = mock(ServicioCredencial.class);
    private static CredencialResource credencialResource = new CredencialResource(credencialDAO, tokenDAO, MENSAJE_ERROR_BUILDER, servicioCredencial);

    @ClassRule
    public static final ResourceTestRule RESOURCES = ResourceTestRule.builder()
            .addResource(credencialResource)
            .addProvider(ValidacionExceptionMapper.class)
            .build();
    private Client client;

    @Before
    public void setUp() {
        client = RESOURCES.client();
    }

    @After
    public void tearDown() {
        reset(credencialDAO, tokenDAO, servicioCredencial);
    }

    @Test
    public void debeVerificarQueLaContraseniaNoEsteEnBlanco() {
        ContraseniaToken contraseniaToken = ContraseniaTokenBuilder.nuevaContraseniaToken()
                .con(c -> c.contrasenia = CAMPO_EN_BLANCO)
                .generar();

        ClientResponse response = client.resource("/credenciales")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, contraseniaToken);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "contrasenia El campo es obligatorio");
        verifyZeroInteractions(credencialDAO);
    }

    @Test
    public void debeVerificarQueLaContraseniaTengaAlMenos6Caracteres() {
        ContraseniaToken contraseniaToken = ContraseniaTokenBuilder.nuevaContraseniaToken()
                .con(c -> c.contrasenia = "corta")
                .generar();

        ClientResponse response = client.resource("/credenciales")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, contraseniaToken);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe contener entre 6 y 15 caracteres");
        verifyZeroInteractions(credencialDAO);
    }

    @Test
    public void debeVerificarQueLaContraseniaTengaMaximo15Caracteres() {
        ContraseniaToken contraseniaToken = ContraseniaTokenBuilder.nuevaContraseniaToken()
                .con(c -> c.contrasenia = "unaClaveDemasiadoDemasiadoLarga")
                .generar();

        ClientResponse response = client.resource("/credenciales")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, contraseniaToken);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe contener entre 6 y 15 caracteres");
        verifyZeroInteractions(credencialDAO);
    }

    @Test
    public void debeVerificarQueLaContraseniaTengaAlMenosUnaMayuscula() {
        ContraseniaToken contraseniaToken = ContraseniaTokenBuilder.nuevaContraseniaToken()
                .con(c -> c.contrasenia = "clave-minuscula")
                .generar();

        ClientResponse response = client.resource("/credenciales")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, contraseniaToken);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe contener al menos una mayúscula");
        verifyZeroInteractions(credencialDAO);
    }

    @Test
    public void debeVerificarQueLaContraseniaTengaAlMenosUnaMinuscula() {
        ContraseniaToken contraseniaToken = ContraseniaTokenBuilder.nuevaContraseniaToken()
                .con(c -> c.contrasenia = "A7654321")
                .generar();

        ClientResponse response = client.resource("/credenciales")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, contraseniaToken);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe contener al menos una minúscula");
        verifyZeroInteractions(credencialDAO);
    }

    @Test
    public void debeVerificarQueLaContraseniaTengaAlMenosUnNumero() {
        ContraseniaToken contraseniaToken = ContraseniaTokenBuilder.nuevaContraseniaToken()
                .con(c -> c.contrasenia = "SOLOLETRAS")
                .generar();

        ClientResponse response = client.resource("/credenciales")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, contraseniaToken);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "Debe contener al menos un número");
        verifyZeroInteractions(credencialDAO);
    }

    @Test
    public void debeVerificarQueElTokenNoEsteEnBlanco() {
        ContraseniaToken contraseniaToken = ContraseniaTokenBuilder.nuevaContraseniaToken()
                .con(c -> c.idToken = CAMPO_EN_BLANCO)
                .generar();

        ClientResponse response = client.resource("/credenciales")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, contraseniaToken);

        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "idToken El campo es obligatorio");
        verifyZeroInteractions(credencialDAO);
    }

    @Test
    public void debeVerificarQueElTokenSeaValido() {
        ContraseniaToken contraseniaToken = ContraseniaTokenBuilder.nuevaContraseniaToken().generar();
        when(tokenDAO.buscar("e590f1a6-517d-4c52-95ad-32c05504a2dc")).thenReturn(Optional.<Token>empty());

        ClientResponse response = client.resource("/credenciales")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, contraseniaToken);

        verify(tokenDAO).buscar("e590f1a6-517d-4c52-95ad-32c05504a2dc");
        verifyZeroInteractions(credencialDAO);
        assertThat(response.getStatus(), is(400));
        assertErrorMessage(response, "idToken Token inválido");
    }

    @Test
    public void debeBorrarElTokenUnaVezUtilizado() {
        Usuario usuario = UsuarioBuilder.nuevoUsuario().generar();
        Token token = new Token(TOKEN_VALIDO, usuario);
        ContraseniaToken contraseniaToken = ContraseniaTokenBuilder.nuevaContraseniaToken().generar();
        when(tokenDAO.buscar(anyString())).thenReturn(Optional.of(token));

        ClientResponse response = client.resource("/credenciales")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, contraseniaToken);

        assertThat(response.getStatus(), is(201));
        verify(tokenDAO).eliminar(token.getId());
    }

    @Test
    public void debeGuardarLaCredencialSiLaContraseniaEsValida() {
        String contrasenia = "Perez9";
        String hash = "hash de Perez9";
        String nombreUsuario = "loremPerez";
        ContraseniaToken contraseniaToken = ContraseniaTokenBuilder.nuevaContraseniaToken().con(c -> c.contrasenia = contrasenia).generar();
        Usuario usuario = UsuarioBuilder.nuevoUsuario().con(u -> u.nombreUsuario = nombreUsuario).generar();
        Token token = new Token(TOKEN_VALIDO, usuario);
        Credencial credencial = new Credencial(nombreUsuario, hash);
        when(tokenDAO.buscar(anyString())).thenReturn(Optional.of(token));
        when(servicioCredencial.convertirACredencial(contraseniaToken, token)).thenReturn(credencial);
        when(credencialDAO.guardar(any(Credencial.class))).thenReturn(credencial);

        ClientResponse response = client.resource("/credenciales")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, contraseniaToken);

        verify(credencialDAO).guardar(any(Credencial.class));
        assertThat(response.getStatus(), is(201));
    }
}