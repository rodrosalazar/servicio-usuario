package ec.gob.senescyt.usuario.core;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class NivelDeAccesoTest {
    private String nombre;
    private Funcion funcion;
    private NivelDeAcceso nivelDeAcceso;
    private FuncionNivelDeAcceso funcionNivelDeAcceso;

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

    @Test
    public void debeTenerRelacionConFunciones() {
        funcion = new Funcion(nombre);
        nivelDeAcceso = new NivelDeAcceso(nombre);
        funcionNivelDeAcceso = new FuncionNivelDeAcceso(funcion, nivelDeAcceso);
        Set<FuncionNivelDeAcceso> funcionNivelDeAccesoSet = new HashSet<>();
        funcionNivelDeAccesoSet.add(funcionNivelDeAcceso);

        nivelDeAcceso.setRelacionesConFunciones(funcionNivelDeAccesoSet);
        assertThat(nivelDeAcceso.getRelacionesConFunciones(), is(funcionNivelDeAccesoSet));
    }
}