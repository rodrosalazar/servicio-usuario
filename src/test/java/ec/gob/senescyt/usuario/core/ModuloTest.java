package ec.gob.senescyt.usuario.core;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ModuloTest{
    private String nombre;
    private int id;

    @Before
    public void setUp() throws Exception {
        nombre = RandomStringUtils.random(9).toString();
        id = new Random().nextInt(999);
    }

    @Test
    public void debeInicializarConIdYNombre() {
        Modulo modulo = new Modulo(id, nombre);
        assertThat(modulo.getId(), is(id));
        assertThat(modulo.getNombre(), is(nombre));
    }

}