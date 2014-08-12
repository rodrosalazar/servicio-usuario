package ec.gob.senescyt.integration.dao;

import ec.gob.senescyt.integration.AbstractIntegracionTest;
import ec.gob.senescyt.usuario.core.Modulo;
import ec.gob.senescyt.usuario.core.UsuarioPerfil;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UsuarioPerfilDAOIntegracionTest extends AbstractIntegracionTest {
    private String nombre;
    private UsuarioPerfil usuarioPerfil;

    @Before
    public void setUp() throws Exception {
        nombre = RandomStringUtils.random(10).toString();
    }

    @Test
    public void debeCrear() {
        usuarioPerfil = new UsuarioPerfil(nombre);
        usuarioPerfilDAO.guardar(usuarioPerfil);
        UsuarioPerfil registroEncontrado = usuarioPerfilDAO.encontrarPorId(usuarioPerfil.getId());
        assertThat(registroEncontrado.getId(), is(usuarioPerfil.getId()));
    }

    @Test
    public void debeTenerModulos() {
        usuarioPerfil = new UsuarioPerfil(nombre);
        Set<Modulo> moduloSet = new HashSet<>(1);
        Modulo modulo = new Modulo(nombre);
        moduloSet.add(modulo);
        usuarioPerfil.setModulos(moduloSet);
        usuarioPerfilDAO.guardar(usuarioPerfil);
        session.flush();
        UsuarioPerfil registroEncontrado = usuarioPerfilDAO.encontrarPorId(usuarioPerfil.getId());
        assertThat(registroEncontrado.getModulos().size(), is(1));
        assertThat(registroEncontrado.getModulos().iterator().next().getId(), is(modulo.getId()));
    }
}
