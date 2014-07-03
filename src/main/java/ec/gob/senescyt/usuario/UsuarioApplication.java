package ec.gob.senescyt.usuario;

import com.google.common.annotations.VisibleForTesting;
import com.sun.jersey.api.core.ResourceConfig;
import ec.gob.senescyt.usuario.bundles.DBMigrationsBundle;
import ec.gob.senescyt.usuario.core.Institucion;
import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.core.Permiso;
import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.dao.InstitucionDAO;
import ec.gob.senescyt.usuario.dao.PerfilDAO;
import ec.gob.senescyt.usuario.dao.UsuarioDAO;
import ec.gob.senescyt.usuario.exceptions.ValidacionExceptionMapper;
import ec.gob.senescyt.usuario.lectores.LectorArchivoDePropiedades;
import ec.gob.senescyt.usuario.lectores.enums.ArchivosPropiedadesEnum;
import ec.gob.senescyt.usuario.resources.InstitucionResource;
import ec.gob.senescyt.usuario.resources.PerfilResource;
import ec.gob.senescyt.usuario.resources.UsuarioResource;
import ec.gob.senescyt.usuario.validators.CedulaValidator;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.hibernate.SessionFactory;

import javax.servlet.DispatcherType;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class UsuarioApplication extends Application<UsuarioConfiguration> {


    private final DBMigrationsBundle flywayBundle = new DBMigrationsBundle();

    private final HibernateBundle<UsuarioConfiguration> hibernate = new HibernateBundle<UsuarioConfiguration>(Perfil.class, Permiso.class,
            Usuario.class, Institucion.class) {

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
        UsuarioDAO usuarioDAO = new UsuarioDAO(getSessionFactory());
        InstitucionDAO institucionDAO = new InstitucionDAO(getSessionFactory());

        final PerfilResource perfilResource = new PerfilResource(perfilDAO);
        environment.jersey().register(perfilResource);

        CedulaValidator cedulaValidator = new CedulaValidator();
        LectorArchivoDePropiedades lectorArchivoDePropiedades = new LectorArchivoDePropiedades(ArchivosPropiedadesEnum.ARCHIVO_VALIDACIONES.getBaseName());

        final UsuarioResource usuarioResource = new UsuarioResource(usuarioDAO, cedulaValidator, lectorArchivoDePropiedades);
        environment.jersey().register(usuarioResource);

        final InstitucionResource institucionResource = new InstitucionResource(institucionDAO);
        environment.jersey().register(institucionResource);

        environment.servlets().addFilter("cors-filter", CrossOriginFilter.class)
                .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");

        registrarValidacionExceptionMapper(environment);
    }

    @VisibleForTesting
    public SessionFactory getSessionFactory() {
        return hibernate.getSessionFactory();
    }

    private void registrarValidacionExceptionMapper(Environment environment) {
        eliminarDefaultConstraintValidationMapper(environment);

        ValidacionExceptionMapper validacionExceptionMapper = new ValidacionExceptionMapper();
        environment.jersey().register(validacionExceptionMapper);

    }

    private void eliminarDefaultConstraintValidationMapper(Environment environment) {
        ResourceConfig jrConfig = environment.jersey().getResourceConfig();
        Set<Object> dwSingletons = jrConfig.getSingletons();
        List<Object> singletonsToRemove = new ArrayList<Object>();

        for (Object s : dwSingletons) {
            if (s instanceof ExceptionMapper && s.getClass().getName().equals("io.dropwizard.jersey.validation.ConstraintViolationExceptionMapper")) {
                singletonsToRemove.add(s);
            }
        }

        for (Object s : singletonsToRemove) {
            jrConfig.getSingletons().remove(s);
        }
    }
}
