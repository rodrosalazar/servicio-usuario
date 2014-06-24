package ec.gob.senescyt.usuario;

import com.google.common.annotations.VisibleForTesting;
import ec.gob.senescyt.usuario.bundles.DBMigrationsBundle;
import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.core.Permiso;
import ec.gob.senescyt.usuario.dao.PerfilDAO;
import ec.gob.senescyt.usuario.resources.PerfilResource;
import ec.gob.senescyt.usuario.resources.UsuarioResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.hibernate.SessionFactory;

public class UsuarioApplication extends Application<UsuarioConfiguration> {


    private final DBMigrationsBundle flywayBundle = new DBMigrationsBundle();

    private final HibernateBundle<UsuarioConfiguration> hibernate = new HibernateBundle<UsuarioConfiguration>(Perfil.class, Permiso.class) {

        @Override
        public DataSourceFactory getDataSourceFactory(UsuarioConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    public static void main(String[] args) throws Exception {
        new UsuarioApplication().run(args);
    }

    @Override
    public String getName() {
        return "servicio-usuario";
    }

    @Override
    public void initialize(Bootstrap<UsuarioConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
        bootstrap.addBundle(flywayBundle);
    }


    @Override
    public void run(UsuarioConfiguration configuration, Environment environment) throws Exception {
        PerfilDAO perfilDAO = new PerfilDAO(getSessionFactory());
        final PerfilResource perfilResource = new PerfilResource(perfilDAO);
        environment.jersey().register(perfilResource);
        final UsuarioResource usuarioResource = new UsuarioResource();
        environment.jersey().register(usuarioResource);
    }

    @VisibleForTesting
    public SessionFactory getSessionFactory() {
        return hibernate.getSessionFactory();
    }
}
