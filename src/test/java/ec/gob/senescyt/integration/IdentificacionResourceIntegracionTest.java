package ec.gob.senescyt.integration;

import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.commons.builders.PerfilBuilder;
import ec.gob.senescyt.commons.builders.UsuarioBuilder;
import ec.gob.senescyt.usuario.core.Credencial;
import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.dao.CredencialDAO;
import ec.gob.senescyt.usuario.dto.CredencialLogin;
import ec.gob.senescyt.usuario.exceptions.CifradoErroneoException;
import ec.gob.senescyt.usuario.utils.Hasher;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class IdentificacionResourceIntegracionTest extends AbstractIntegracionTest {

    private CredencialDAO credencialDAO;
    private String claveTest = "claveTest";
    private Usuario usuarioDePrueba;
    private Perfil perfilDePrueba;

    @Before
    public void setUp() throws CifradoErroneoException {
        credencialDAO = new CredencialDAO(sessionFactory);

        perfilDePrueba = hacerPost("perfiles",PerfilBuilder.nuevoPerfil().generar()).getEntity(Perfil.class);

        usuarioDePrueba = hacerPost("usuario", UsuarioBuilder.nuevoUsuario()
                .con(u -> u.perfiles = newArrayList(perfilDePrueba.getId()))
                .generar()).getEntity(Usuario.class);

        Credencial credencial = new Credencial(usuarioDePrueba.getNombreUsuario(),
                new Hasher().calcularHash(claveTest));
        
        credencialDAO.guardar(credencial);
        sessionFactory.getCurrentSession().flush();
    }

    @After
    public void tearDown() throws Exception {
        credencialDAO.eliminar(usuarioDePrueba.getNombreUsuario());
        sessionFactory.getCurrentSession().flush();
    }

    @Test
    public void debeArrojarErrorDeNoAutorizacionSiElNombreDeUsuarioNoExiste() {
        CredencialLogin credencialLogin = new CredencialLogin("username", "password");
        ClientResponse clientResponse = hacerPost("identificacion", credencialLogin);

        assertThat(clientResponse.getStatus(), is(401));
        assertThat(clientResponse.getEntity(String.class), CoreMatchers.containsString("Credenciales Incorrectas"));
    }

    @Test
    public void debeGenerarTokenParaCredencialesValidas() {
        CredencialLogin credencialLogin = new CredencialLogin(usuarioDePrueba.getNombreUsuario(), claveTest);
        ClientResponse clientResponse = hacerPost("identificacion", credencialLogin);

        assertThat(clientResponse.getStatus(), is(201));
        assertThat(clientResponse.getEntity(String.class), CoreMatchers.notNullValue());
    }
}
