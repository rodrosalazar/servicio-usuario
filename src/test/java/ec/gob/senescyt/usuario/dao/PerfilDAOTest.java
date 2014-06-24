package ec.gob.senescyt.usuario.dao;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.usuario.core.Perfil;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PerfilDAOTest extends BaseDAOTest {

    @Override
    protected String getTableName() {
        return "Perfil";
    }

    @Test
    public void debeSalvarElPerfilConPermisos() {
        Client client = new Client();

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/perfil", RULE.getLocalPort()))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, perfilAsJSON());

        assertThat(response.getStatus(), is(201));
        Perfil perfilRespuesta = response.getEntity(Perfil.class);
        assertThat(perfilRespuesta.getId(), is(not(0l)));
        assertThat(perfilRespuesta.getPermisos().get(0).getId(), is(not(0l)));
    }

    private String perfilAsJSON() {
        return "{\n" +
                "    \"nombre\": \"Perfil1\",\n" +
                "    \"permisos\": [\n" +
                "        {\n" +
                "            \"moduloId\":\"1\",\n" +
                "            \"funcionId\":\"2\",\n" +
                "            \"accesos\":[ \"CREAR\", \"LEER\" ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"moduloId\":\"2\",\n" +
                "            \"funcionId\":\"2\",\n" +
                "            \"accesos\":[ \"CREAR\", \"LEER\" ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }
}
