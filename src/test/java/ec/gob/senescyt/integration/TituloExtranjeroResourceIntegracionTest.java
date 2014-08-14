package ec.gob.senescyt.integration;

import com.google.common.io.Resources;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.commons.builders.PortadorTituloBuilder;
import ec.gob.senescyt.titulos.core.Cedula;
import ec.gob.senescyt.titulos.core.Pasaporte;
import ec.gob.senescyt.titulos.core.PortadorTitulo;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import java.io.File;

import static ec.gob.senescyt.commons.helpers.ResourceTestHelper.assertErrorMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TituloExtranjeroResourceIntegracionTest extends AbstractIntegracionTest {

    private String numeroIdentificacionCedulaValida = "1111111116";
    private static final String RUTA_TITULO_EXTRANJERO = "titulo/extranjero";
    private static final String ID_CATEGORIA_VISA = "9";
    private static final String NUMERO_PASAPORTE = "ASE23";
    private static final DateTime FECHA_FIN_VIGENCIA_PASAPORTE_VALIDA = new DateTime(2015, 3, 16, 0, 0, DateTimeZone.UTC);

    public static String resourceFilePath(String resourceClassPathLocation) {
        try {
            return new File(Resources.getResource(resourceClassPathLocation).toURI()).getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void debeAlmacenarUnNuevoExpedienteDeTituloExtranjero() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.identificacion = new Cedula(numeroIdentificacionCedulaValida))
                .generar();

        ClientResponse respuesta =  hacerPost(RUTA_TITULO_EXTRANJERO, portadorTitulo);

        assertThat(respuesta.getStatus(), is(201));
        assertThat(respuesta.getEntity(PortadorTitulo.class).getEmail(), is(portadorTitulo.getEmail()));
    }

    @Test
    public void debeAlmacenarUnNuevoExpedienteDeTituloExtranjeroConPasaporte() {
        Pasaporte pasaporte = new Pasaporte(NUMERO_PASAPORTE, FECHA_FIN_VIGENCIA_PASAPORTE_VALIDA, null, ID_CATEGORIA_VISA, true);
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.identificacion = pasaporte)
                .generar();

        ClientResponse respuesta = hacerPost(RUTA_TITULO_EXTRANJERO, portadorTitulo);

        assertThat(respuesta.getStatus(), is(201));
        assertThat(respuesta.getEntity(PortadorTitulo.class).getEmail(), is(portadorTitulo.getEmail()));
    }

    @Test
    public void noDebeGuardarExpedienteCuandoElCodigoDelPaisNoExiste() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.identificacion = new Cedula(numeroIdentificacionCedulaValida))
                .con(p -> p.idPaisNacionalidad = "invali")
                .generar();

        ClientResponse respuesta = hacerPost(RUTA_TITULO_EXTRANJERO,portadorTitulo);

        assertThat(respuesta.getStatus(), is(400));
        assertErrorMessage(respuesta, "idpaisnacionalidad no es un valor válido");
    }

    @Test
    public void noDebeGuardarExpedienteElCodigoDeEtniaNoExiste() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.identificacion = new Cedula(numeroIdentificacionCedulaValida))
                .con(p -> p.idEtnia = "xx")
                .generar();

        ClientResponse respuesta = hacerPost(RUTA_TITULO_EXTRANJERO, portadorTitulo);

        assertThat(respuesta.getStatus(), is(400));
        assertErrorMessage(respuesta, "etnia_id no es un valor válido");
    }
}
