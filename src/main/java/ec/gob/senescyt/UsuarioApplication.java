package ec.gob.senescyt;

import com.google.common.annotations.VisibleForTesting;
import com.sun.jersey.api.core.ResourceConfig;
import ec.gob.senescyt.biblioteca.Arbol;
import ec.gob.senescyt.biblioteca.NivelArbol;
import ec.gob.senescyt.biblioteca.dao.ArbolDAO;
import ec.gob.senescyt.biblioteca.resource.ArbolResource;
import ec.gob.senescyt.commons.email.DespachadorEmail;
import ec.gob.senescyt.commons.exceptions.DBConstraintViolationMapper;
import ec.gob.senescyt.commons.filters.HeaderResponseFilter;
import ec.gob.senescyt.commons.lectores.LectorArchivoDePropiedades;
import ec.gob.senescyt.commons.lectores.enums.ArchivosPropiedadesEnum;
import ec.gob.senescyt.commons.resources.builders.ConstructorRespuestas;
import ec.gob.senescyt.titulos.core.*;
import ec.gob.senescyt.titulos.core.Identificacion;
import ec.gob.senescyt.titulos.dao.*;
import ec.gob.senescyt.titulos.resources.TituloExtranjeroResource;
import ec.gob.senescyt.usuario.bundles.DBMigrationsBundle;
import ec.gob.senescyt.usuario.core.*;
import ec.gob.senescyt.usuario.core.cine.Area;
import ec.gob.senescyt.usuario.core.cine.Clasificacion;
import ec.gob.senescyt.usuario.core.cine.Detalle;
import ec.gob.senescyt.usuario.core.cine.Subarea;
import ec.gob.senescyt.usuario.dao.*;
import ec.gob.senescyt.usuario.exceptions.ValidacionExceptionMapper;
import ec.gob.senescyt.usuario.resources.*;
import ec.gob.senescyt.usuario.resources.management.LimpiezaResource;
import ec.gob.senescyt.usuario.services.ServicioCedula;
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
import java.nio.charset.StandardCharsets;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UsuarioApplication extends Application<UsuarioConfiguration> {

    private final DBMigrationsBundle flywayBundle = new DBMigrationsBundle();

    private final HibernateBundle<UsuarioConfiguration> hibernate = new HibernateBundle<UsuarioConfiguration>(Perfil.class, Permiso.class,
            Usuario.class, Institucion.class, Clasificacion.class, Area.class, Subarea.class, Detalle.class, Pais.class,
            Provincia.class, Canton.class, Parroquia.class, TipoVisa.class, CategoriaVisa.class, Etnia.class,
            PortadorTitulo.class, Direccion.class, Arbol.class, NivelArbol.class, UniversidadExtranjera.class,
            Token.class, Identificacion.class, Cedula.class, Pasaporte.class) {

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
        ClasificacionDAO clasificacionDAO = new ClasificacionDAO(getSessionFactory());
        PaisDAO paisDAO = new PaisDAO(getSessionFactory());
        ProvinciaDAO provinciaDAO = new ProvinciaDAO(getSessionFactory());
        CantonDAO cantonDAO = new CantonDAO(getSessionFactory());
        ParroquiaDAO parroquiaDAO = new ParroquiaDAO(getSessionFactory());
        TipoVisaDAO tipoVisaDAO = new TipoVisaDAO(getSessionFactory());
        CategoriaVisaDAO categoriaVisaDAO = new CategoriaVisaDAO(getSessionFactory());
        EtniaDAO etniaDAO = new EtniaDAO(getSessionFactory());
        ConstructorRespuestas constructorRespuestas = new ConstructorRespuestas();
        PortadorTituloDAO portadorTituloDAO = new PortadorTituloDAO(getSessionFactory());
        ArbolDAO arbolDAO = new ArbolDAO(getSessionFactory());
        DespachadorEmail despachadorEmail = new DespachadorEmail(configuration.getConfiguracionEmail());
        ServicioCedula servicioCedula = new ServicioCedula(configuration.getConfiguracionBSG(), provinciaDAO);
        UniversidadExtranjeraDAO universidadExtranjeraDAO = new UniversidadExtranjeraDAO(getSessionFactory());
        TokenDAO tokenDAO = new TokenDAO(getSessionFactory());

        final PerfilResource perfilResource = new PerfilResource(perfilDAO, constructorRespuestas );
        environment.jersey().register(perfilResource);

        CedulaValidator cedulaValidator = new CedulaValidator();
        LectorArchivoDePropiedades lectorPropiedadesValidacion = new LectorArchivoDePropiedades(ArchivosPropiedadesEnum.ARCHIVO_VALIDACIONES.getBaseName());
        LectorArchivoDePropiedades lectorPropiedadesEmail = new LectorArchivoDePropiedades(ArchivosPropiedadesEnum.ARCHIVO_PROPIEDADES_EMAIL.getBaseName());

        final UsuarioResource usuarioResource = new UsuarioResource(usuarioDAO, cedulaValidator, lectorPropiedadesValidacion, despachadorEmail, tokenDAO, lectorPropiedadesEmail);
        environment.jersey().register(usuarioResource);

        final InstitucionResource institucionResource = new InstitucionResource(institucionDAO,constructorRespuestas,universidadExtranjeraDAO);
        environment.jersey().register(institucionResource);

        final LimpiezaResource limpiezaResource = new LimpiezaResource(usuarioDAO);
        environment.jersey().register(limpiezaResource);

        final ClasificacionResource clasificacionResource = new ClasificacionResource(clasificacionDAO);
        environment.jersey().register(clasificacionResource);

        final PaisResource paisResource = new PaisResource(paisDAO, constructorRespuestas);
        environment.jersey().register(paisResource);

        final ProvinciaResource provinciaResource = new ProvinciaResource(provinciaDAO, cantonDAO, constructorRespuestas);
        environment.jersey().register(provinciaResource);

        final CantonResource cantonResource = new CantonResource(cantonDAO, parroquiaDAO, constructorRespuestas);
        environment.jersey().register(cantonResource);

        final TipoDeVisaResource tipoDeVisaResource = new TipoDeVisaResource(tipoVisaDAO, categoriaVisaDAO, constructorRespuestas);
        environment.jersey().register(tipoDeVisaResource);

        final EtniaResource etniaResource = new EtniaResource(etniaDAO, constructorRespuestas);
        environment.jersey().register(etniaResource);

        final TituloExtranjeroResource tituloExtranjeroResource = new TituloExtranjeroResource(portadorTituloDAO);
        environment.jersey().register(tituloExtranjeroResource);

        final ArbolResource arbolResource = new ArbolResource(arbolDAO, constructorRespuestas);
        environment.jersey().register(arbolResource);

        final BusquedaResource busquedaResource = new BusquedaResource(servicioCedula);
        environment.jersey().register(busquedaResource);

        final ContraseniaResource contraseniaResource = new ContraseniaResource(tokenDAO);
        environment.jersey().register(contraseniaResource);

        registrarFiltros(environment);

        registrarValidacionExceptionMapper(environment);
    }

    private void registrarFiltros(Environment environment) {
        environment.jersey().getResourceConfig().getContainerResponseFilters().add(new HeaderResponseFilter(StandardCharsets.UTF_8.name()));

        environment.servlets().addFilter("cors-filter", CrossOriginFilter.class)
                .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
    }

    @VisibleForTesting
    public SessionFactory getSessionFactory() {
        return hibernate.getSessionFactory();
    }

    private void registrarValidacionExceptionMapper(Environment environment) {
        eliminarDefaultConstraintValidationMapper(environment);

        ValidacionExceptionMapper validacionExceptionMapper = new ValidacionExceptionMapper();
        environment.jersey().register(validacionExceptionMapper);
        environment.jersey().register(new DBConstraintViolationMapper());

    }

    private void eliminarDefaultConstraintValidationMapper(Environment environment) {
        ResourceConfig jrConfig = environment.jersey().getResourceConfig();
        Set<Object> dwSingletons = jrConfig.getSingletons();

        List<Object> singletonsToRemove = dwSingletons
                .stream()
                .filter(s -> s instanceof ExceptionMapper &&
                        s.getClass().getName().equals("io.dropwizard.jersey.validation.ConstraintViolationExceptionMapper"))
                .collect(Collectors.toList());

        for (Object s : singletonsToRemove) {
            jrConfig.getSingletons().remove(s);
        }
    }
}
