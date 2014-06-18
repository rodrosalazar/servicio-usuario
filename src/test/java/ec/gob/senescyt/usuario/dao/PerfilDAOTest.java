package ec.gob.senescyt.usuario.dao;

import ec.gob.senescyt.usuario.core.Perfil;
import org.junit.Before;
import org.junit.Test;

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
        Perfil perfil = new Perfil();
        perfil.setNombre("indiferente");

        long nuevoId = perfilDAO.guardar(perfil);
        Perfil nuevoPerfil = perfilDAO.buscar(nuevoId);
        sessionFactory.getCurrentSession().delete(nuevoPerfil);

        assertThat(nuevoPerfil.getNombre(), is(perfil.getNombre()));
    }

}
