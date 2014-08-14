package ec.gob.senescyt;

import com.google.common.annotations.VisibleForTesting;
import com.sun.jersey.api.core.ResourceConfig;
import ec.gob.senescyt.biblioteca.Arbol;
import ec.gob.senescyt.biblioteca.NivelArbol;
import ec.gob.senescyt.biblioteca.dao.ArbolDAO;
import ec.gob.senescyt.biblioteca.resource.ArbolResource;
import ec.gob.senescyt.commons.builders.MensajeErrorBuilder;
import ec.gob.senescyt.commons.email.ConstructorContenidoEmail;
import ec.gob.senescyt.commons.email.DespachadorEmail;
import ec.gob.senescyt.commons.exceptions.DBConstraintViolationMapper;
import ec.gob.senescyt.commons.exceptions.HibernateConstraintViolationMapper;
import ec.gob.senescyt.commons.filters.HeaderResponseFilter;
import ec.gob.senescyt.commons.filters.RedirectFilter;
import ec.gob.senescyt.commons.lectores.LectorArchivoDePropiedades;
import ec.gob.senescyt.commons.lectores.enums.ArchivosPropiedadesEnum;
import ec.gob.senescyt.commons.resources.builders.ConstructorRespuestas;
import ec.gob.senescyt.titulos.core.Canton;
import ec.gob.senescyt.titulos.core.CategoriaVisa;
import ec.gob.senescyt.titulos.core.Cedula;
import ec.gob.senescyt.titulos.core.Direccion;
import ec.gob.senescyt.titulos.core.Etnia;
import ec.gob.senescyt.titulos.core.Identificacion;
import ec.gob.senescyt.titulos.core.Pais;
import ec.gob.senescyt.titulos.core.Parroquia;
import ec.gob.senescyt.titulos.core.Pasaporte;
import ec.gob.senescyt.titulos.core.PortadorTitulo;
import ec.gob.senescyt.titulos.core.Provincia;
import ec.gob.senescyt.titulos.core.TipoVisa;
import ec.gob.senescyt.titulos.core.UniversidadExtranjera;
import ec.gob.senescyt.titulos.core.UniversidadExtranjeraDAO;
import ec.gob.senescyt.titulos.dao.CantonDAO;
import ec.gob.senescyt.titulos.dao.CategoriaVisaDAO;
import ec.gob.senescyt.titulos.dao.EtniaDAO;
import ec.gob.senescyt.titulos.dao.PaisDAO;
import ec.gob.senescyt.titulos.dao.ParroquiaDAO;
import ec.gob.senescyt.titulos.dao.PortadorTituloDAO;
import ec.gob.senescyt.titulos.dao.ProvinciaDAO;
import ec.gob.senescyt.titulos.dao.TipoVisaDAO;
import ec.gob.senescyt.titulos.resources.CantonResource;
import ec.gob.senescyt.titulos.resources.CatalogosResource;
import ec.gob.senescyt.titulos.resources.ClasificacionResource;
import ec.gob.senescyt.titulos.resources.EtniaResource;
import ec.gob.senescyt.titulos.resources.ProvinciaResource;
import ec.gob.senescyt.titulos.resources.TipoDeVisaResource;
import ec.gob.senescyt.titulos.resources.TituloExtranjeroResource;
import ec.gob.senescyt.usuario.UsuarioHibernateBundle;
import ec.gob.senescyt.usuario.autenticacion.UsuarioAuthenticator;
import ec.gob.senescyt.usuario.bundles.DBMigrationsBundle;
import ec.gob.senescyt.usuario.core.*;
import ec.gob.senescyt.usuario.core.cine.Area;
import ec.gob.senescyt.usuario.core.cine.Clasificacion;
import ec.gob.senescyt.usuario.core.cine.Detalle;
import ec.gob.senescyt.usuario.core.cine.Subarea;
import ec.gob.senescyt.usuario.dao.ClasificacionDAO;
import ec.gob.senescyt.usuario.dao.CredencialDAO;
import ec.gob.senescyt.usuario.dao.InstitucionDAO;
import ec.gob.senescyt.usuario.dao.PerfilDAO;
import ec.gob.senescyt.usuario.dao.TokenDAO;
import ec.gob.senescyt.usuario.dao.UsuarioDAO;
import ec.gob.senescyt.usuario.exceptions.CifradoErroneoException;
import ec.gob.senescyt.usuario.exceptions.LoginIncorrectoMapper;
import ec.gob.senescyt.usuario.exceptions.ValidacionExceptionMapper;
import ec.gob.senescyt.usuario.resources.BusquedaResource;
import ec.gob.senescyt.usuario.resources.CredencialResource;
import ec.gob.senescyt.usuario.resources.IdentificacionResource;
import ec.gob.senescyt.usuario.resources.InstitucionResource;
import ec.gob.senescyt.usuario.resources.PaisResource;
import ec.gob.senescyt.usuario.resources.PerfilResource;
import ec.gob.senescyt.usuario.resources.UsuarioResource;
import ec.gob.senescyt.usuario.resources.management.LimpiezaResource;
import ec.gob.senescyt.usuario.utils.Hasher;
import ec.gob.senescyt.usuario.services.ServicioCedula;
import ec.gob.senescyt.usuario.services.ServicioCifrado;
import ec.gob.senescyt.usuario.services.ServicioCredencial;
import ec.gob.senescyt.usuario.validators.CedulaValidator;
import io.dropwizard.Application;
import io.dropwizard.auth.oauth.OAuthProvider;
import io.dropwizard.jersey.setup.JerseyEnvironment;
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


    private final UsuarioHibernateBundle hibernate = new UsuarioHibernateBundle(UsuarioConfiguration.class, Perfil.class, Permiso.class,
            Usuario.class, Institucion.class, Clasificacion.class, Area.class, Subarea.class, Detalle.class, Pais.class,
            Provincia.class, Canton.class, Parroquia.class, TipoVisa.class, CategoriaVisa.class, Etnia.class,
            PortadorTitulo.class, Direccion.class, Arbol.class, NivelArbol.class, UniversidadExtranjera.class,
            Token.class, Identificacion.class, Cedula.class, Pasaporte.class, Credencial.class, Permiso.class);
    private String defaultSchema;

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
        defaultSchema = hibernate.getConfiguration().getDefaultSchema();
        JerseyEnvironment jerseyEnvironment = environment.jersey();
        ConstructorRespuestas constructorRespuestas = new ConstructorRespuestas();

        configurarBiblioteca(jerseyEnvironment, constructorRespuestas);
        configurarCatalogos(jerseyEnvironment, constructorRespuestas);
        configurarCuenta(jerseyEnvironment, constructorRespuestas, configuration);
        configurarTitulos(jerseyEnvironment);
        configurarLimpieza(jerseyEnvironment);

        registrarFiltros(environment);

        registrarValidacionExceptionMapper(environment);

        jerseyEnvironment.register(new OAuthProvider<>(new UsuarioAuthenticator(), "SENESCYT"));
    }

    private void configurarLimpieza(JerseyEnvironment jerseyEnvironment) {
        PerfilDAO perfilDAO = new PerfilDAO(getSessionFactory(), defaultSchema);
        UsuarioDAO usuarioDAO = new UsuarioDAO(getSessionFactory(), defaultSchema);

        LimpiezaResource limpiezaResource = new LimpiezaResource(usuarioDAO, perfilDAO);
        jerseyEnvironment.register(limpiezaResource);
    }

    private void configurarTitulos(JerseyEnvironment jerseyEnvironment) {
        PortadorTituloDAO portadorTituloDAO = new PortadorTituloDAO(getSessionFactory());

        TituloExtranjeroResource tituloExtranjeroResource = new TituloExtranjeroResource(portadorTituloDAO);
        jerseyEnvironment.register(tituloExtranjeroResource);
    }

    private void configurarCuenta(JerseyEnvironment jerseyEnvironment, ConstructorRespuestas constructorRespuestas,
                                  UsuarioConfiguration configuration) throws CifradoErroneoException {

        CredencialDAO credencialesDAO = new CredencialDAO(getSessionFactory(), defaultSchema);
        UsuarioDAO usuarioDAO = new UsuarioDAO(getSessionFactory(), defaultSchema);
        CedulaValidator cedulaValidator = new CedulaValidator();
        ServicioCifrado servicioCifrado = new ServicioCifrado();
        Hasher hasher = new Hasher();
        LectorArchivoDePropiedades lectorPropiedadesValidacion = new LectorArchivoDePropiedades(ArchivosPropiedadesEnum.ARCHIVO_VALIDACIONES.getBaseName());
        TokenDAO tokenDAO = new TokenDAO(getSessionFactory(), defaultSchema);
        LectorArchivoDePropiedades lectorPropiedadesEmail = new LectorArchivoDePropiedades(ArchivosPropiedadesEnum.ARCHIVO_PROPIEDADES_EMAIL.getBaseName());
        ConstructorContenidoEmail constructorContenidoEmail = new ConstructorContenidoEmail();
        PerfilDAO perfilDAO = new PerfilDAO(getSessionFactory(), defaultSchema);
        MensajeErrorBuilder mensajeErrorBuilder = new MensajeErrorBuilder(lectorPropiedadesValidacion);
        ServicioCredencial servicioCredencial = new ServicioCredencial(credencialesDAO, servicioCifrado, hasher);
        ProvinciaDAO provinciaDAO = new ProvinciaDAO(getSessionFactory());
        ServicioCedula servicioCedula = new ServicioCedula(configuration.getConfiguracionBSG(), provinciaDAO);
        DespachadorEmail despachadorEmail = new DespachadorEmail(configuration.getConfiguracionEmail());

        UsuarioResource usuarioResource = new UsuarioResource(usuarioDAO, cedulaValidator, lectorPropiedadesValidacion,
                despachadorEmail, tokenDAO, lectorPropiedadesEmail, constructorContenidoEmail, constructorRespuestas);
        jerseyEnvironment.register(usuarioResource);

        PerfilResource perfilResource = new PerfilResource(perfilDAO);
        jerseyEnvironment.register(perfilResource);

        IdentificacionResource identificacionResource = new IdentificacionResource(servicioCredencial);
        jerseyEnvironment.register(identificacionResource);

        CredencialResource credencialResource = new CredencialResource(credencialesDAO, tokenDAO, mensajeErrorBuilder, servicioCredencial);
        jerseyEnvironment.register(credencialResource);

        BusquedaResource busquedaResource = new BusquedaResource(servicioCedula, tokenDAO, usuarioDAO, lectorPropiedadesValidacion);
        jerseyEnvironment.register(busquedaResource);
    }

    private void configurarCatalogos(JerseyEnvironment jerseyEnvironment, ConstructorRespuestas constructorRespuestas) {
        InstitucionDAO institucionDAO = new InstitucionDAO(getSessionFactory());
        ClasificacionDAO clasificacionDAO = new ClasificacionDAO(getSessionFactory());
        PaisDAO paisDAO = new PaisDAO(getSessionFactory());
        CantonDAO cantonDAO = new CantonDAO(getSessionFactory());
        ParroquiaDAO parroquiaDAO = new ParroquiaDAO(getSessionFactory());
        TipoVisaDAO tipoVisaDAO = new TipoVisaDAO(getSessionFactory());
        CategoriaVisaDAO categoriaVisaDAO = new CategoriaVisaDAO(getSessionFactory());
        EtniaDAO etniaDAO = new EtniaDAO(getSessionFactory());
        UniversidadExtranjeraDAO universidadExtranjeraDAO = new UniversidadExtranjeraDAO(getSessionFactory());
        ProvinciaDAO provinciaDAO = new ProvinciaDAO(getSessionFactory());

        PaisResource paisResource = new PaisResource(paisDAO, constructorRespuestas);
        jerseyEnvironment.register(paisResource);

        InstitucionResource institucionResource = new InstitucionResource(institucionDAO, constructorRespuestas, universidadExtranjeraDAO);
        jerseyEnvironment.register(institucionResource);

        ClasificacionResource clasificacionResource = new ClasificacionResource(clasificacionDAO);
        jerseyEnvironment.register(clasificacionResource);

        ProvinciaResource provinciaResource = new ProvinciaResource(provinciaDAO, cantonDAO, constructorRespuestas);
        jerseyEnvironment.register(provinciaResource);

        CantonResource cantonResource = new CantonResource(parroquiaDAO, constructorRespuestas);
        jerseyEnvironment.register(cantonResource);

        TipoDeVisaResource tipoDeVisaResource = new TipoDeVisaResource(tipoVisaDAO, categoriaVisaDAO, constructorRespuestas);
        jerseyEnvironment.register(tipoDeVisaResource);

        EtniaResource etniaResource = new EtniaResource(etniaDAO, constructorRespuestas);
        jerseyEnvironment.register(etniaResource);

        CatalogosResource catalagosResource = new CatalogosResource(constructorRespuestas);
        jerseyEnvironment.register(catalagosResource);
    }

    private void configurarBiblioteca(JerseyEnvironment jerseyEnvironment, ConstructorRespuestas constructorRespuestas) {
        ArbolDAO arbolDAO = new ArbolDAO(getSessionFactory());

        ArbolResource arbolResource = new ArbolResource(arbolDAO, constructorRespuestas);
        jerseyEnvironment.register(arbolResource);
    }

    private void registrarFiltros(Environment environment) {
        environment.jersey().getResourceConfig().getContainerResponseFilters().add(new HeaderResponseFilter(StandardCharsets.UTF_8.name()));

        environment.servlets().addFilter("cors-filter", CrossOriginFilter.class)
                .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");

        environment.servlets().addFilter("redirect-filter", RedirectFilter.class)
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
        environment.jersey().register(new HibernateConstraintViolationMapper());

        LoginIncorrectoMapper loginIncorrectoMapper = new LoginIncorrectoMapper();
        environment.jersey().register(loginIncorrectoMapper);

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
