package ec.gob.senescyt.usuario.core;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.mock;

public class ModuloTest{
    private String nombre;
    private Modulo modulo;

    @Before
    public void setUp() throws Exception {
        nombre = RandomStringUtils.random(9).toString();
    }

    @Test
    public void debeInicializarConIdYNombre() {
        modulo = new Modulo(nombre);
        assertThat(modulo.getId(), is(notNullValue()));
        assertThat(modulo.getNombre(), is(nombre));
    }

    @Test
    public void debeTenerFunciones() {
        modulo = new Modulo(nombre);
        Set<Funcion> funcionSet = mock(Set.class);
        modulo.setFunciones(funcionSet);
        assertThat(modulo.getFunciones(), is(funcionSet));
    }
}