package ec.gob.senescyt.usuario.dao;

import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.core.Permiso;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;
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
    }

    @Override
    protected String getTableName() {
        return "Perfil";
    }
}
