package ec.gob.senescyt.usuario;

import com.example.helloworld.health.TemplateHealthCheck;
import com.example.helloworld.resources.HelloWorldResource;
import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.dao.PerfilDAO;
import ec.gob.senescyt.usuario.resources.PerfilResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class UsuarioApplication extends Application<UsuarioConfiguration> {

    private final HibernateBundle<UsuarioConfiguration> hibernate = new HibernateBundle<UsuarioConfiguration>(Perfil.class) {
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
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<UsuarioConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(UsuarioConfiguration configuration, Environment environment) throws Exception {
        final HelloWorldResource resource = new HelloWorldResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );
        PerfilDAO perfilDAO = new PerfilDAO(hibernate.getSessionFactory());
        final PerfilResource perfilResource = new PerfilResource(perfilDAO);
        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);

        environment.jersey().register(resource);
        environment.jersey().register(perfilResource);
    }
}
