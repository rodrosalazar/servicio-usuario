package ec.gob.senescyt.usuario.core;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FuncionTest{
    private String nombre;
    private Funcion funcion;

    @Before
    public void setUp() throws Exception {
        nombre = RandomStringUtils.random(10).toString();
    }

    @Test
    public void debeInicializarNombre() {
        funcion = new Funcion(nombre);
        assertThat(funcion.getNombre(), is(nombre));
        assertThat(funcion.getId(), is(notNullValue()));
    }

    @Test
    public void debeTenerNivelDeAccesos() {
        funcion = new Funcion(nombre);
        NivelDeAcceso nivelDeAcceso = new NivelDeAcceso(nombre);
        Set<NivelDeAcceso> nivelDeAccesoSet = new HashSet<>(1);
        nivelDeAccesoSet.add(nivelDeAcceso);
        funcion.setNivelesDeAcceso(nivelDeAccesoSet);
        assertThat(funcion.getNivelesDeAcceso().size(), is(1));
        assertThat(funcion.getNivelesDeAcceso().iterator().next(), is(nivelDeAcceso));
    }
}