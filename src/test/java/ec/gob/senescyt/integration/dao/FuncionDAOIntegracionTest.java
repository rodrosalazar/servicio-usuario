package ec.gob.senescyt.integration.dao;

import ec.gob.senescyt.integration.AbstractIntegracionTest;
import ec.gob.senescyt.usuario.core.Funcion;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FuncionDAOIntegracionTest extends AbstractIntegracionTest {
    private String nombre;

    @Before
    public void setUp() throws Exception {
        nombre = RandomStringUtils.random(10).toString();
    }

    @Test
    public void debeCrear() {
        Funcion funcion = new Funcion(nombre);
        funcionDAO.guardar(funcion);
        Funcion registroEncontrado = funcionDAO.encontrarPorId(funcion.getId());
        assertThat(registroEncontrado.getId(), is(funcion.getId()));
    }
}
