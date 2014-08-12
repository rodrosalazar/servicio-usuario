package ec.gob.senescyt.usuario.core;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class NivelDeAccesoTest {
    private String nombre;

    @Before
    public void setUp() throws Exception {
        nombre = RandomStringUtils.random(10).toString();
    }

    @Test
    public void debeInicializarNombre() {
        NivelDeAcceso nivelDeAcceso = new NivelDeAcceso(nombre);
        assertThat(nivelDeAcceso.getNombre(), is(nombre));
        assertThat(nivelDeAcceso.getId(), is(notNullValue()));
    }

}