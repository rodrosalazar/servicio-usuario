package ec.gob.senescyt.integration;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import ec.gob.senescyt.commons.builders.MensajeErrorBuilder;
import ec.gob.senescyt.commons.builders.PerfilBuilder;
import ec.gob.senescyt.commons.builders.UsuarioBuilder;
import ec.gob.senescyt.commons.lectores.LectorArchivoDePropiedades;
import ec.gob.senescyt.commons.lectores.enums.ArchivosPropiedadesEnum;
import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.enums.TipoDocumento;
import org.apache.commons.lang.RandomStringUtils;
import org.hamcrest.CoreMatchers;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UsuarioResourceIntegracionTest extends AbstractIntegracionTest {

    private LectorArchivoDePropiedades lectorArchivoDePropiedades;
    private MensajeErrorBuilder mensajeErrorBuilder;
    private Perfil perfil;
    private Perfil perfilGuardado;

    @Before
    public void setUp() {
        perfil = PerfilBuilder.nuevoPerfil().generar();
        lectorArchivoDePropiedades = new LectorArchivoDePropiedades(ArchivosPropiedadesEnum.ARCHIVO_VALIDACIONES.getBaseName());
        mensajeErrorBuilder = new MensajeErrorBuilder(lectorArchivoDePropiedades);

        ClientResponse response = hacerPost("perfiles", perfil);

        assertThat(response.getStatus(), is(201));
        perfilGuardado = response.getEntity(Perfil.class);
    }

    @Test
    public void debeVerificarNumeroDeCedulaCorrecto() {
        MultivaluedMap<String, String> parametros = new MultivaluedMapImpl();
        parametros.add("cedula", "1111111116");

        ClientResponse response = hacerGet("usuario/validacion", parametros);

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void debeVerificarNumeroDeCedulaIncorrecto() {
        MultivaluedMap<String, String> parametros = new MultivaluedMapImpl();
        parametros.add("cedula", "1111111111");

        ClientResponse response = hacerGet("usuario/validacion", parametros);

        assertThat(response.getStatus(), is(400));
        assertThat(response.getEntity(String.class), is(mensajeErrorBuilder.mensajeNumeroIdentificacionInvalido()));
    }

    @Test
    public void debeVerificarQueLaFechaDeFinDeVigenciaNoPuedeSerMenorALaFechaActual() {
        Usuario usuarioconFechaDeVigenciaValido = UsuarioBuilder.nuevoUsuario()
                .con(u -> u.fechaDeVigencia = new DateTime().withZone(DateTimeZone.UTC).withTimeAtStartOfDay().minusMonths(1))
                .generar();

        ClientResponse response = hacerPost("usuario", usuarioconFechaDeVigenciaValido);

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void debeLanzarErrorCuandoEmailDeUsuarioEsInvalido() {
        Usuario usuarioConEmailInvalido = UsuarioBuilder.nuevoUsuario()
                .con(u -> u.emailInstitucional = "invalido")
                .generar();

        ClientResponse response = hacerPost("usuario", usuarioConEmailInvalido);

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void debeCrearUnNuevoUsuarioCuandoEsValido() {
        Usuario usuarioValido = UsuarioBuilder.nuevoUsuario()
                .con(u -> u.perfiles = newArrayList(perfilGuardado.getId()))
                .generar();

        ClientResponse response = hacerPost("usuario", usuarioValido);

        assertThat(response.getStatus(), is(201));
    }

    @Test
    public void debeIndicarQueUnNombreDeUsuarioYaHaSidoRegistrado() {
        Usuario usuarioValido = UsuarioBuilder.nuevoUsuario()
                .con(u -> u.perfiles = newArrayList(perfilGuardado.getId()))
                .generar();

        ClientResponse responseInsertUsuario = hacerPost("usuario", usuarioValido);

        assertThat(responseInsertUsuario.getStatus(), is(201));

        MultivaluedMap<String, String> parametros = new MultivaluedMapImpl();
        parametros.add("nombreUsuario", UsuarioBuilder.nuevoUsuario().generar().getNombreUsuario());

        ClientResponse responseValidacion = hacerGet("usuario/validacion", parametros);

        assertThat(responseValidacion.getStatus(), is(400));
        assertThat(responseValidacion.getEntity(String.class), is(mensajeErrorBuilder.mensajeNombreDeUsuarioYaHaSidoRegistrado()));
    }

    @Test
    public void debeIndicarQueUnNombreDeUsuarioNoSeEncuentraRegistrado() {
        Usuario usuarioValido = UsuarioBuilder.nuevoUsuario().generar();
        MultivaluedMap<String, String> parametros = new MultivaluedMapImpl();
        parametros.add("nombreUsuario", usuarioValido.getNombreUsuario());

        ClientResponse responseValidacion = hacerGet("usuario/validacion", parametros);

        assertThat(responseValidacion.getStatus(), is(200));
    }

    @Test
    public void debeIndicarQueUnNumeroDeIdentificacionYaHaSidoRegistrado() {
        Usuario usuario = UsuarioBuilder.nuevoUsuario()
                .con(u -> u.perfiles = newArrayList(perfilGuardado.getId()))
                .generar();

        ClientResponse responseInsertUsuario = hacerPost("usuario", usuario);

        assertThat(responseInsertUsuario.getStatus(), is(201));

        MultivaluedMap<String, String> parametros = new MultivaluedMapImpl();
        parametros.add("numeroIdentificacion", usuario.getIdentificacion().getNumeroIdentificacion());
        ClientResponse responseValidacion = hacerGet("usuario/validacion", parametros);

        assertThat(responseValidacion.getStatus(), is(400));
        assertThat(responseValidacion.getEntity(String.class), is(mensajeErrorBuilder.mensajeNumeroIdentificacionYaHaSidoRegistrado()));
    }

    @Test
    public void debeIndicarQueUnNumeroDeIdentificacionNoSeEncuentraRegistrado() {
        Usuario usuario = UsuarioBuilder.nuevoUsuario().generar();
        MultivaluedMap<String, String> parametros = new MultivaluedMapImpl();
        parametros.add("numeroIdentificacion", usuario.getIdentificacion().getNumeroIdentificacion());

        ClientResponse responseValidacion = hacerGet("usuario/validacion", parametros);

        assertThat(responseValidacion.getStatus(), is(200));
    }

    @Test
    public void debeValidarQueNombreDeUsuarioNoSeRepitaCuandoSeGuardaUsuario() {
        Usuario usuarioConNombreA = UsuarioBuilder.nuevoUsuario()
                .con(u -> u.perfiles = newArrayList(perfilGuardado.getId()))
                .con(u -> u.nombreUsuario = "A")
                .generar();

        ClientResponse responseInsertUsuario = hacerPost("usuario", usuarioConNombreA);

        assertThat(responseInsertUsuario.getStatus(), is(201));

        ClientResponse responseUsuarioRepetido = hacerPost("usuario", usuarioConNombreA);

        assertThat(responseUsuarioRepetido.getStatus(), is(400));
        assertThat(responseUsuarioRepetido.getEntity(String.class), is(mensajeErrorBuilder.mensajeNombreDeUsuarioYaHaSidoRegistrado()));
    }

    @Test
    public void debeValidarQueNumeroDeIdentificacionNoSeRepitaCuandoSeGuardaUsuario() {
        Usuario usuarioConNombreA = UsuarioBuilder.nuevoUsuario()
                .con(u -> u.perfiles = newArrayList(perfilGuardado.getId()))
                .con(u -> u.numeroIdentificacion = "1111111116")
                .generar();
        Usuario usuarioConElMismoId = UsuarioBuilder.nuevoUsuario().con(u -> u.perfiles = newArrayList(perfilGuardado.getId()))
                .con(u -> u.nombreUsuario = "otroDiferente")
                .con(u -> u.numeroIdentificacion = "1111111116")
                .generar();

        ClientResponse responseInsertUsuario = hacerPost("usuario", usuarioConNombreA);

        assertThat(responseInsertUsuario.getStatus(), is(201));

        ClientResponse responseUsuarioRepetido = hacerPost("usuario", usuarioConElMismoId);

        assertThat(responseUsuarioRepetido.getStatus(), is(400));
        assertThat(responseUsuarioRepetido.getEntity(String.class), is(mensajeErrorBuilder.mensajeNumeroIdentificacionYaHaSidoRegistrado()));
    }

    @Test
    public void debeDevolverUnMensajeDeErrorCuandoElNumeroDeIdentificacionEsMayorDe20() {
        Usuario usuarioCon21Digitos = UsuarioBuilder.nuevoUsuario()
                .con(u -> u.numeroIdentificacion = RandomStringUtils.random(21))
                .con(u -> u.tipoDocumento = TipoDocumento.PASAPORTE)
                .generar();

        ClientResponse response = hacerPost("usuario", usuarioCon21Digitos);

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void debeDevolverUnMensajeDeErrorCuandoElNumeroDePasaporteEstaVacio() {
        Usuario usuarioConPasaporteVacio = UsuarioBuilder.nuevoUsuario()
                .con(u -> u.tipoDocumento = TipoDocumento.PASAPORTE)
                .con(u -> u.numeroIdentificacion = "")
                .generar();

        ClientResponse response = hacerPost("usuario", usuarioConPasaporteVacio);

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void debeDevolverTodosLosUsuarios() {
        Usuario usuarioA = UsuarioBuilder.nuevoUsuario()
                .con(b -> b.nombreUsuario = "usuarioA")
                .con(b -> b.perfiles = newArrayList(perfilGuardado.getId()))
                .generar();
        Usuario usuarioB = UsuarioBuilder.nuevoUsuario()
                .con(b -> b.perfiles = newArrayList(perfilGuardado.getId()))
                .con(b -> b.tipoDocumento = TipoDocumento.PASAPORTE)
                .con(b -> b.numeroIdentificacion = "2222223")
                .con(b -> b.nombreUsuario = "usuarioB")
                .generar();

        ClientResponse responseUsuarioA = hacerPost("usuario", usuarioA);
        assertThat(responseUsuarioA.getStatus(), is(201));
        ClientResponse responseUsuarioB = hacerPost("usuario", usuarioB);
        System.out.println("ERROR " + responseUsuarioB.getEntity(String.class));
        assertThat(responseUsuarioB.getStatus(), is(201));

        ClientResponse response = hacerGet("usuario/todos");

        assertThat(response.getStatus(), is(200));
        List entity = response.getEntity(List.class);
        assertThat(entity, CoreMatchers.notNullValue());
        assertThat(entity.size(), is(2));
    }
}