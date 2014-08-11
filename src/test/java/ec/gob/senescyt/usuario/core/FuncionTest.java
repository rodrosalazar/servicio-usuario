package ec.gob.senescyt.usuario.core;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FuncionTest{
    private String nombre;
    private int id;

    @Before
    public void setUp() throws Exception {
        nombre = RandomStringUtils.random(9).toString();
        id = new Random().nextInt(999);
    }

    @Test
    public void debeInicializarConIdYNombre() {
        Funcion funcion = new Funcion(id, nombre);
        assertThat(funcion.getId(), is(id));
        assertThat(funcion.getNombre(), is(nombre));
    }
}