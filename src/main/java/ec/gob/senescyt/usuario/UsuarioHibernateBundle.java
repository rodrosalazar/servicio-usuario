package ec.gob.senescyt.usuario;

import ec.gob.senescyt.UsuarioConfiguration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;

public class UsuarioHibernateBundle extends HibernateBundle<UsuarioConfiguration> {

    private UsuarioConfiguration configuration;

    public UsuarioHibernateBundle(Class<?> entity, Class<?>... entities) {
        super(entity, entities);
    }

    @Override
    public DataSourceFactory getDataSourceFactory(UsuarioConfiguration configuration) {
        this.configuration = configuration;
        return configuration.getDataSourceFactory();
    }

    public UsuarioConfiguration getConfiguration() {
        return configuration;
    }
}