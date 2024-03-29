package ec.gob.senescyt.usuario.core;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class NombreTest {

    private String primerNombre = "PRIMER_NOMBRE";
    private String segundoNombre = "SEGUNDO_NOMBRE";
    private String primerApellido = "PRIMER_APELLIDO";
    private String segundoApellido = "SEGUNDO_APELLIDO";
    private String nombresCompletos = "PRIMER_NOMBRE SEGUNDO_NOMBRE PRIMER_APELLIDO SEGUNDO_APELLIDO";
    private String sinSegundoNombreNiSegundoApellido = "PRIMER_NOMBRE PRIMER_APELLIDO";

    @Test
    public void debeUnirCorrectamenteLasPartesDeUnNombre() {
       Nombre nombre = new Nombre(primerNombre, segundoNombre, primerApellido, segundoApellido);
       assertThat(nombresCompletos, is(nombre.toString()));
    }

    @Test
    public void debeImprimirCorrectamenteElNombreSiFaltaSegundoNombreOSegundoApellido() {
        Nombre nombre = new Nombre(primerNombre, null, primerApellido, null);
        assertThat(sinSegundoNombreNiSegundoApellido, is(nombre.toString()));
    }
}
