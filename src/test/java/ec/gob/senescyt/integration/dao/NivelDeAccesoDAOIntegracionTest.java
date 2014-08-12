package ec.gob.senescyt.integration.dao;

import ec.gob.senescyt.integration.AbstractIntegracionTest;
import ec.gob.senescyt.usuario.core.NivelDeAcceso;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class NivelDeAccesoDAOIntegracionTest extends AbstractIntegracionTest {
    private String nombre;

    @Before
    public void setUp() throws Exception {
        nombre = RandomStringUtils.random(10).toString();
    }

    @Test
    public void debeCrear() {
        NivelDeAcceso nivelDeAcceso = new NivelDeAcceso(nombre);
        nivelDeAccesoDAO.guardar(nivelDeAcceso);
        NivelDeAcceso registroEncontrado = nivelDeAccesoDAO.encontrarPorId(nivelDeAcceso.getId());
        assertThat(registroEncontrado.getId(), is(nivelDeAcceso.getId()));
    }
}
