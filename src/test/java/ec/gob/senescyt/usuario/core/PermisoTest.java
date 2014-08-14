package ec.gob.senescyt.usuario.core;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

public class PermisoTest {
    private String modulo;
    private String funcion;
    private Acceso acceso;
    private Permiso permiso;

    @Before
    public void setUp() throws Exception {
        modulo = RandomStringUtils.random(10).toString();
        funcion = RandomStringUtils.random(10).toString();
        acceso = Acceso.CREAR;
    }

    @Test
    public void debeInicializarConElNombreYElAcceso() {
        permiso = new Permiso(modulo, funcion, acceso);
        assertThat(permiso.getModulo(), is(modulo));
        assertThat(permiso.getFuncion(), is(funcion));
        assertThat(permiso.getAcceso(), is(acceso));
    }

    @Test
    public void debeTenerIdAutoAsignado() {
        permiso = new Permiso(modulo, funcion, acceso);
        assertThat(permiso.getId(), is(nullValue()));
    }
}