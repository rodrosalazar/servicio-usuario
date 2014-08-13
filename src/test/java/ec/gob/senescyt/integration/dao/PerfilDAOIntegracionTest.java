package ec.gob.senescyt.integration.dao;

import ec.gob.senescyt.integration.AbstractIntegracionTest;
import ec.gob.senescyt.usuario.core.Acceso;
import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.core.Permiso;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

public class PerfilDAOIntegracionTest extends AbstractIntegracionTest {
    private List<Permiso> permisoSet = new ArrayList<>();
    private Perfil perfil;

    @Before
    public void setUp() throws Exception {
        String nombre = RandomStringUtils.random(10).toString();
        permisoSet.add(new Permiso(nombre, Acceso.CREAR));
        perfil = new Perfil(nombre, permisoSet);
    }

    @Test
    public void debeCrear() {
        perfilDAO.guardar(perfil);
        Perfil registroEncontrado = perfilDAO.encontrarPorId(perfil.getId());
        assertThat(registroEncontrado.getId(), is(perfil.getId()));
        assertThat(registroEncontrado.getNombre(), is(perfil.getNombre()));
        assertThat(registroEncontrado.getPermisos().size(), is(1));
        assertThat(registroEncontrado.getPermisos().get(0).getId(), is(permisoSet.get(0).getId()));
    }

    @Test
    public void debeObtenerTodos() {
        perfilDAO.guardar(perfil);
        List<Perfil> registroEncontrados = perfilDAO.obtenerTodos();
        assertThat(registroEncontrados.size(), is(1));
        assertThat(registroEncontrados.get(0).getId(), is(perfil.getId()));
    }

    @Test
    public void debeEliminar() {
        perfilDAO.guardar(perfil);
        perfilDAO.eliminar(perfil.getId());
        Perfil registroEncontrado = perfilDAO.encontrarPorId(perfil.getId());
        assertThat(registroEncontrado, is(nullValue()));
    }
}
