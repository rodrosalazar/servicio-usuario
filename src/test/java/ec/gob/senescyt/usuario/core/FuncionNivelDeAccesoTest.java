package ec.gob.senescyt.usuario.core;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

public class FuncionNivelDeAccesoTest{
    private Funcion funcion = mock(Funcion.class);
    private NivelDeAcceso nivelDeAcceso = mock(NivelDeAcceso.class);
    private FuncionNivelDeAcceso funcionNivelDeAcceso;

    @Test
    public void debeInicializarConFuncionYNivelDeAcceso() {
        funcionNivelDeAcceso = new FuncionNivelDeAcceso(funcion, nivelDeAcceso);
        assertThat(funcionNivelDeAcceso.getFuncion(), is(funcion));
        assertThat(funcionNivelDeAcceso.getNivelDeAcceso(), is(nivelDeAcceso));
    }
}
