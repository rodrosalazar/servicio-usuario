package ec.gob.senescyt.usuario.bundles;

import ec.gob.senescyt.UsuarioConfiguration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.flywaydb.core.Flyway;

public class DBMigrationsBundle implements ConfiguredBundle<UsuarioConfiguration> {
    @Override
    public void run(UsuarioConfiguration configuration, Environment environment) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(configuration.getDataSourceFactory().getUrl(), configuration.getDataSourceFactory().getUser(), configuration.getDataSourceFactory().getPassword());
        flyway.setSchemas(configuration.getDefaultSchema());
        flyway.migrate();
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
        // Do nothing.
    }
}
