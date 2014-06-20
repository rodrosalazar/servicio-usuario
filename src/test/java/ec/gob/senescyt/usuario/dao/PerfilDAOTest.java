package ec.gob.senescyt.usuario.dao;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.core.Permiso;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PerfilDAOTest extends BaseDAOTest {

    private PerfilDAO perfilDAO;

    @Before
    public void setUp() {
        perfilDAO = new PerfilDAO(sessionFactory);
    }

    @Test
    public void debePersistirElPerfil() {
        Permiso permiso = new Permiso("modulo101", null);
        Perfil perfil = new Perfil("indiferente", newArrayList(permiso));

        Perfil nuevoPerfil = perfilDAO.guardar(perfil);

        assertThat(nuevoPerfil.getNombre(), is(perfil.getNombre()));
        System.out.println("ID " + perfil.getId());
        System.out.println("ID PERMISO " + perfil.getPermisos().get(0).getId());
        assertThat(nuevoPerfil.getPermisos().get(0).getNombre(), is(permiso.getNombre()));
    }

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
//        Perfil perfilRespuesta = response.getEntity(Perfil.class);
//        assertThat(perfilRespuesta.getId(), is(not(0l)));
//        assertThat(perfilRespuesta.getPermisos().get(0).getId(), is(not(0l)));
    }

    private String perfilAsJSON() {
        return "{\n" +
                "    \"nombre\": \"Perfil1\",\n" +
                "    \"permisos\": [\n" +
                "        {\n" +
                "            \"nombre\":\"modulo1\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"nombre\":\"modulo2\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }
}
