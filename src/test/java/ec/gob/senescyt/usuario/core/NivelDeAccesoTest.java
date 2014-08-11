package ec.gob.senescyt.usuario.core;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class NivelDeAccesoTest {
    private String nombre;
    private int id;

    @Before
    public void setUp() throws Exception {
        nombre = RandomStringUtils.random(10).toString();
        id = new Random().nextInt(999);
    }

    @Test
    public void debeInicializarConIdYNombre() {
        NivelDeAcceso nivelDeAcceso = new NivelDeAcceso(id, nombre);
        assertThat(nivelDeAcceso.getNombre(), is(nombre));
        assertThat(nivelDeAcceso.getId(), is(id));
    }
}