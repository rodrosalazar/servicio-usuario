package ec.gob.senescyt.integration.dao;

import ec.gob.senescyt.integration.AbstractIntegracionTest;
import ec.gob.senescyt.usuario.core.Funcion;
import ec.gob.senescyt.usuario.core.Modulo;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ModuloDAOIntegracionTest extends AbstractIntegracionTest {
    private String nombre;
    private Modulo modulo;

    @Before
    public void setUp() throws Exception {
        nombre = RandomStringUtils.random(10).toString();
    }

    @Test
    public void debeCrear() {
        modulo = new Modulo(nombre);
        moduloDAO.guardar(modulo);
        Modulo registroEncontrado = moduloDAO.encontrarPorId(modulo.getId());
        assertThat(registroEncontrado.getId(), is(modulo.getId()));
        assertThat(registroEncontrado.getNombre(), is(modulo.getNombre()));
    }

    @Test
    public void debeTenerFunciones() {
        modulo = new Modulo(nombre);
        Set<Funcion> funcionSet = new HashSet<>(1);
        Funcion funcion = new Funcion(nombre);
        funcionSet.add(funcion);
        modulo.setFunciones(funcionSet);
        moduloDAO.guardar(modulo);
        session.flush();
        Modulo registroEncontrado = moduloDAO.encontrarPorId(modulo.getId());
        assertThat(registroEncontrado.getFunciones().size(), is(1));
        assertThat(registroEncontrado.getFunciones().iterator().next().getId(), is(funcion.getId()));
    }
}
