package ec.gob.senescyt.usuario.resources;

import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.commons.builders.UsuarioBuilder;
import ec.gob.senescyt.commons.lectores.LectorArchivoDePropiedades;
import ec.gob.senescyt.commons.lectores.enums.ArchivosPropiedadesEnum;
import ec.gob.senescyt.usuario.core.CedulaInfo;
import ec.gob.senescyt.usuario.core.Token;
import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.dao.TokenDAO;
import ec.gob.senescyt.usuario.dao.UsuarioDAO;
import ec.gob.senescyt.usuario.exceptions.CedulaInvalidaException;
import ec.gob.senescyt.usuario.exceptions.CredencialesIncorrectasException;
import ec.gob.senescyt.usuario.exceptions.ServicioNoDisponibleException;
import ec.gob.senescyt.usuario.services.ServicioCedula;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.UUID;

import static ec.gob.senescyt.commons.helpers.ResourceTestHelper.assertErrorMessage;
import static org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400;
import static org.eclipse.jetty.http.HttpStatus.OK_200;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class BusquedaResourceTest {

    private static final int OK_STATUS_CODE = Response.Status.OK.getStatusCode();
    public static final String RUTA_BUSQUEDA = "/busqueda";
    private static ServicioCedula servicioCedula = mock(ServicioCedula.class);
    private static TokenDAO tokenDAO = mock(TokenDAO.class);
    private static UsuarioDAO usuarioDAO = mock(UsuarioDAO.class);
    private static LectorArchivoDePropiedades lectorPropiedadesValidacion = new LectorArchivoDePropiedades(ArchivosPropiedadesEnum.ARCHIVO_VALIDACIONES.getBaseName());

    @ClassRule
    public static final ResourceTestRule RESOURCES = ResourceTestRule.builder()
            .addResource(new BusquedaResource(servicioCedula, tokenDAO, usuarioDAO,lectorPropiedadesValidacion))
            .build();

    private String tokenInvalido;
    private String tokenValido;

    @Before
    public void setUp() {
        Mockito.reset(servicioCedula, tokenDAO);
        tokenInvalido = UUID.randomUUID().toString();
        tokenValido = UUID.randomUUID().toString();
    }

    @Test
    public void debeDevolverServicioNoDisponibleCuandoRegistroCivilNoEstaDisponible() throws CedulaInvalidaException, ServicioNoDisponibleException, CredencialesIncorrectasException {
        String cedulaIndiferente = "1111";
        when(servicioCedula.buscar(cedulaIndiferente)).thenThrow(new ServicioNoDisponibleException("Servicio no disponible", null));
        ClientResponse response = RESOURCES.client().resource(RUTA_BUSQUEDA)
                .queryParam("cedula", cedulaIndiferente)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        Mockito.verify(servicioCedula).buscar(eq(cedulaIndiferente));
        assertThat(response.getStatus(), is(503));
        assertErrorMessage(response, "Servicio no disponible");
    }

    @Test
    public void debeDevolverBadRequestCuandoLaCedulaEsInvalida() throws CedulaInvalidaException, ServicioNoDisponibleException, CredencialesIncorrectasException {
        String cedulaInvalida = "1111";
        when(servicioCedula.buscar(cedulaInvalida)).thenThrow(new CedulaInvalidaException("Cedula enviada no corresponde a un usuario no existe o no esta registrado"));

        ClientResponse response = RESOURCES.client().resource(RUTA_BUSQUEDA)
                .queryParam("cedula", cedulaInvalida)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        Mockito.verify(servicioCedula).buscar(eq(cedulaInvalida));
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

        ClientResponse response = RESOURCES.client().resource(RUTA_BUSQUEDA)
                .queryParam("cedula", cedulaValida)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        Mockito.verify(servicioCedula).buscar(eq(cedulaValida));
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


    @Test
    public void debeDevolverRecursoNoEncontradoCuandoTokenNoEsValido() {
        when(tokenDAO.buscar(tokenInvalido)).thenReturn(Optional.empty());

        ClientResponse response = RESOURCES.client().resource(RUTA_BUSQUEDA)
                .queryParam("token", tokenInvalido)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        assertThat(response.getStatus(), is(Response.Status.NOT_FOUND.getStatusCode()));
    }

    @Test
    public void debeDevolverElIdDelUsuarioCuandoElTokenEsValido() {
        Token tokenTest = new Token(tokenValido, UsuarioBuilder.nuevoUsuario().generar());
        when(tokenDAO.buscar(tokenValido)).thenReturn(Optional.of(tokenTest));
        ClientResponse response = RESOURCES.client().resource(RUTA_BUSQUEDA)
                .queryParam("token", tokenValido)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        long idUsuarioEsperado = 0;
        assertThat(response.getStatus(), is(OK_STATUS_CODE));
        Token token = response.getEntity(Token.class);
        assertThat(token.getUsuario().getId(), is(idUsuarioEsperado));
    }

    @Test
    public void debeDevolverElNombreDeUsuarioCuandoElTokenEsValido() {
        Token tokenTest = new Token(tokenValido, UsuarioBuilder.nuevoUsuario().generar());
        when(tokenDAO.buscar(tokenValido)).thenReturn(Optional.of(tokenTest));
        ClientResponse response = RESOURCES.client().resource(RUTA_BUSQUEDA)
                .queryParam("token", tokenValido)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        String nombreUsuarioEsperado = "usuarioSenescyt";
        assertThat(response.getStatus(), is(OK_STATUS_CODE));
        Token token = response.getEntity(Token.class);
        assertThat(token.getUsuario().getNombreUsuario(), is(nombreUsuarioEsperado));
    }

    @Test
    public void debeDevolverMensajeDeErrorNoEncontradoCuandoLaIdentificacionEsInvalida() {
        String identificacionInvalida = " invalido";
        when(usuarioDAO.buscarPorIdentificacion(eq(identificacionInvalida))).thenReturn(com.google.common.base.Optional.<Usuario>absent());

        ClientResponse response = RESOURCES.client().resource(RUTA_BUSQUEDA)
                .queryParam("identificacion", identificacionInvalida)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        assertThat(response.getStatus(), is(BAD_REQUEST_400));
        assertErrorMessage(response, "Usuario no encontrado");
    }

    @Test
    public void debeDevolverLaIdentificacionDelUsuarioCuandoLaIdentifcacionEsValida(){
        Usuario usuario = UsuarioBuilder.nuevoUsuario().con(u -> u.numeroIdentificacion = "1111").generar();
        String identificacionValida = "1111";
        when(usuarioDAO.buscarPorIdentificacion(eq(identificacionValida))).thenReturn(com.google.common.base.Optional.of(usuario));

        ClientResponse response = RESOURCES.client().resource(RUTA_BUSQUEDA)
                .queryParam("identificacion",identificacionValida)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        assertThat(response.getStatus(),is(OK_200));
        assertThat(response.getEntity(Usuario.class).getIdentificacion().getNumeroIdentificacion(), is(usuario.getIdentificacion().getNumeroIdentificacion()));
    }

}