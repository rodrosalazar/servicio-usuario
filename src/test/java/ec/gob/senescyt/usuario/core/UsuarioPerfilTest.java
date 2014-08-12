package ec.gob.senescyt.usuario.core;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

public class UsuarioPerfilTest {
    private String nombre;
    private UsuarioPerfil usuarioPerfil;

    @Before
    public void setUp() throws Exception {
        nombre = RandomStringUtils.random(10).toString();
    }

    @Test
    public void debeInicializarNombre() {
        usuarioPerfil = new UsuarioPerfil(nombre);
        assertThat(usuarioPerfil.getNombre(), is(nombre));
        assertThat(usuarioPerfil.getId(), is(notNullValue()));
    }

    @Test
    public void debeTenerModulos() {
        usuarioPerfil = new UsuarioPerfil(nombre);
        Set<Modulo> moduloSet = mock(HashSet.class);
        usuarioPerfil.setModulos(moduloSet);
        assertThat(usuarioPerfil.getModulos(), is(moduloSet));
    }
}