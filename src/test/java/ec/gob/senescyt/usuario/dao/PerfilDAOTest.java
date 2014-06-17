package ec.gob.senescyt.usuario.dao;

import ec.gob.senescyt.usuario.core.Perfil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PerfilDAOTest {

    @Test
    public void debePersistirElPerfil() {
        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        when(sessionFactory.getCurrentSession()).thenReturn(session);

        PerfilDAO perfilDAO = new PerfilDAO(sessionFactory);
        Perfil perfil = new Perfil();
        perfil.setNombre("indiferente");

        long actual = perfilDAO.guardar(perfil);

        assertThat(actual, is(0l));
    }

}
