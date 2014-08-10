package ec.gob.senescyt.titulos.core;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

public class DireccionTest {

    public static final String DIRECCION = "Indiference direcion";
    public static final String PROVINCIA = "Indiferente Provincia";
    public static final String CANTON = "Indiference Canton";
    public static final String PARROQUIA = "Indiferente Parroquia";
    private Direccion direccion1;

    @Before
    public void setUp() throws Exception {
        direccion1 = new Direccion(DIRECCION, PROVINCIA, CANTON, PARROQUIA);
    }

    @Test
    public void debeDevolverTrueCuandoDosDireccionesTienenLosMismoValores() {
        Direccion direccion2 = new Direccion(DIRECCION, PROVINCIA, CANTON, PARROQUIA);

        assertThat(direccion1, is(direccion2));
    }

    @Test
    public void debeDevolverFalsoCuandoUnaDeLasDireccionesEsNula() {
        Direccion direccion2 = null;

        assertThat(direccion1, is(not(direccion2)));
    }

    @Test
    public void debeDevolverFalsoCuandoLaDireccionCompletaEsDiferente() {
        Direccion direccion2 = new Direccion("Otra direccion", PROVINCIA, CANTON, PARROQUIA);

        assertThat(direccion1, is(not(direccion2)));
    }

    @Test
    public void debeDevolverFalsoCuandoLaProviciaEsDiferente() {
        Direccion direccion2 = new Direccion(DIRECCION, "Otra Provincia", CANTON, PARROQUIA);

        assertThat(direccion1, is(not(direccion2)));
    }

    @Test
    public void debeDevolverFalsoCuandoElCantonEsDiferente() {
        Direccion direccion2 = new Direccion(DIRECCION, PROVINCIA, "Otro Canton", PARROQUIA);

        assertThat(direccion1, is(not(direccion2)));
    }

    @Test
    public void debeDevolverFalsoCuandoLaParroquiaEsDiferente() {
        Direccion direccion2 = new Direccion(DIRECCION, PROVINCIA, CANTON, "Otra Parroquia");

        assertThat(direccion1, is(not(direccion2)));
    }
}