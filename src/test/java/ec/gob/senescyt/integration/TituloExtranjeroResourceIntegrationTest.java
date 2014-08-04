package ec.gob.senescyt.integration;

import com.google.common.io.Resources;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.UsuarioApplication;
import ec.gob.senescyt.UsuarioConfiguration;
import ec.gob.senescyt.commons.builders.PortadorTituloBuilder;
import ec.gob.senescyt.titulos.core.Cedula;
import ec.gob.senescyt.titulos.core.PortadorTitulo;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.io.File;

import static ec.gob.senescyt.commons.helpers.ResourceTestHelper.assertErrorMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TituloExtranjeroResourceIntegrationTest extends BaseIntegracionTest {

    @ClassRule
    public static final DropwizardAppRule<UsuarioConfiguration> RULE = new DropwizardAppRule<>(UsuarioApplication.class, resourceFilePath(CONFIGURACION));

    private String numeroIdentificacionCedulaValida = "1111111116";

    public static String resourceFilePath(String resourceClassPathLocation) {
        try {
            return new File(Resources.getResource(resourceClassPathLocation).toURI()).getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected DropwizardAppRule<UsuarioConfiguration> getRule() {
        return RULE;
    }

    @Test
    public void debeAlmacenarUnNuevoExpedienteDeTituloExtranjero() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.identificacion = new Cedula(numeroIdentificacionCedulaValida))
                .generar();

        ClientResponse respuesta =  hacerPost("titulo/extranjero", portadorTitulo);

        assertThat(respuesta.getStatus(), is(201));
        assertThat(respuesta.getEntity(PortadorTitulo.class).getEmail(), is(portadorTitulo.getEmail()));
    }

    @Test
    public void debeHacerAlgoCuandoElCodigoDelPaisNoExiste() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.identificacion = new Cedula(numeroIdentificacionCedulaValida))
                .con(p -> p.idPaisNacionalidad = "invali")
                .generar();

        ClientResponse respuesta = hacerPost("titulo/extranjero",portadorTitulo);

        assertThat(respuesta.getStatus(), is(400));
        assertErrorMessage(respuesta, "idpaisnacionalidad no es un valor válido");
    }

    @Test
    public void debeHacerAlgoCuandoElCodigoDeEtniaNoExiste() {
        PortadorTitulo portadorTitulo = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p -> p.identificacion = new Cedula(numeroIdentificacionCedulaValida))
                .con(p -> p.idEtnia = "xx")
                .generar();

        ClientResponse respuesta = hacerPost("titulo/extranjero", portadorTitulo);

        assertThat(respuesta.getStatus(), is(400));
        assertErrorMessage(respuesta, "etnia_id no es un valor válido");
    }
}
