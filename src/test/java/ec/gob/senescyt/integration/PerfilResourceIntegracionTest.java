package ec.gob.senescyt.integration;

import com.sun.jersey.api.client.ClientResponse;
import ec.gob.senescyt.usuario.core.Acceso;
import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.core.Permiso;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class PerfilResourceIntegracionTest extends AbstractIntegracionTest {
    public static final String PERFILES_URL = "perfiles";

    @Test
    public void debeSalvarElPerfilConPermisos() throws IOException {
        Permiso permiso = construirPermiso();
        Perfil perfil = ayudantePerfil.construirConPermisos(permiso);

        ClientResponse response = hacerPost(PERFILES_URL, perfil);

        assertThat(response.getStatus(), is(201));
        Perfil perfilRespuesta = response.getEntity(Perfil.class);
        assertThat(perfilDAO.obtenerTodos().get(0).getId(), is(perfilRespuesta.getId()));
        assertThat(perfilRespuesta.getNombre(), is(perfil.getNombre()));
        assertThat(perfilRespuesta.getPermisos().iterator().next().getId(), is(permiso.getId()));
    }

    @Test
    public void debeObtenerTodos() {
        Perfil perfil = guardarPerfilConPermiso();
        ClientResponse response = hacerGet(PERFILES_URL);

        List<LinkedHashMap> encontrarEntidad = response.getEntity(List.class);
        assertThat(encontrarEntidad.size(), is(1));
        assertThat(encontrarEntidad.get(0).get("nombre"), is(perfil.getNombre()));
    }

    @Test
    public void debeObtener() {
        Perfil perfil = guardarPerfilConPermiso();
        ClientResponse response = hacerGet(PERFILES_URL+ "/" + perfil.getId());

        Perfil encontrarEntidad = response.getEntity(Perfil.class);
        assertThat(encontrarEntidad.getId(), is(perfil.getId()));
        assertThat(encontrarEntidad.getNombre(), is(perfil.getNombre()));
    }

    @Test
    public void debeModificar() {
        Perfil perfil = guardarPerfilConPermiso();
        String modified = "MODIFIED";
        perfil.setNombre(modified);

        ClientResponse response = hacerPut(PERFILES_URL, perfil);
        Perfil encontrarEntidad = response.getEntity(Perfil.class);
        assertThat(encontrarEntidad.getId(), is(perfil.getId()));
        assertThat(encontrarEntidad.getNombre(), is(modified));
    }

    @Test
    public void debeEliminar() {
        Perfil perfil = guardarPerfilConPermiso();
        session.flush();
        ClientResponse response = hacerDelete(PERFILES_URL + "/" + perfil.getId());
        assertThat(response.getStatus(), is(200));
        session.clear();
        assertThat(perfilDAO.encontrarPorId(perfil.getId()), is(nullValue()));
    }

    private Perfil guardarPerfilConPermiso() {
        Permiso permiso = construirPermiso();
        Perfil perfil = ayudantePerfil.construirConPermisos(permiso);
        perfilDAO.guardar(perfil);
        return perfil;
    }

    private Permiso construirPermiso() {
        Permiso permiso = new Permiso(RandomStringUtils.random(10).toString(), Acceso.CREAR);
        permisoDAO.guardar(permiso);
        return permiso;
    }
}
