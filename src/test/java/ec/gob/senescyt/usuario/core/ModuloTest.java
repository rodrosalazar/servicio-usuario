package ec.gob.senescyt.usuario.core;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class ModuloTest{
    private String nombre;

    @Before
    public void setUp() throws Exception {
        nombre = RandomStringUtils.random(9).toString();
    }

    @Test
    public void debeInicializarConIdYNombre() {
        Modulo modulo = new Modulo(nombre);
        assertThat(modulo.getId(), is(notNullValue()));
        assertThat(modulo.getNombre(), is(nombre));
    }

}