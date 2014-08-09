package ec.gob.senescyt.usuario.resources;

import com.google.common.base.Optional;
import ec.gob.senescyt.commons.builders.MensajeErrorBuilder;
import ec.gob.senescyt.commons.email.ConstructorContenidoEmail;
import ec.gob.senescyt.commons.email.DespachadorEmail;
import ec.gob.senescyt.commons.lectores.LectorArchivoDePropiedades;
import ec.gob.senescyt.usuario.core.Token;
import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.dao.TokenDAO;
import ec.gob.senescyt.usuario.dao.UsuarioDAO;
import ec.gob.senescyt.usuario.enums.PropiedadesEmailEnum;
import ec.gob.senescyt.usuario.validators.CedulaValidator;
import io.dropwizard.hibernate.UnitOfWork;
import org.apache.commons.mail.EmailException;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/usuario")
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    private UsuarioDAO usuarioDAO;
    private TokenDAO tokenDAO;
    private CedulaValidator cedulaValidator;
    private LectorArchivoDePropiedades lectorPropiedadesEmail;
    private DespachadorEmail despachadorEmail;
    private ConstructorContenidoEmail constructorContenidoEmail;
    private MensajeErrorBuilder mensajeErrorBuilder;

    public UsuarioResource(UsuarioDAO usuarioDAO, CedulaValidator cedulaValidator,
                           LectorArchivoDePropiedades lectorPropiedadesValidacion, DespachadorEmail despachadorEmail,
                           TokenDAO tokenDAO, LectorArchivoDePropiedades lectorPropiedadesEmail,
                           ConstructorContenidoEmail constructorContenidoEmail) {
        this.usuarioDAO = usuarioDAO;
        this.tokenDAO = tokenDAO;
        this.cedulaValidator = cedulaValidator;
        this.lectorPropiedadesEmail = lectorPropiedadesEmail;
        this.despachadorEmail = despachadorEmail;
        this.constructorContenidoEmail = constructorContenidoEmail;
        this.mensajeErrorBuilder = new MensajeErrorBuilder(lectorPropiedadesValidacion);
    }

    @GET
    @Path("/validacion")
    @UnitOfWork
    public Response validar(@QueryParam("cedula") final Optional<String> cedula,
                            @QueryParam("nombreUsuario") final Optional<String> nombreUsuario,
                            @QueryParam("numeroIdentificacion") final Optional<String> numeroIdentificacion) {

        if (cedula.isPresent()) {
            if (cedulaValidator.isValidaCedula(cedula.get())) {
                return Response.ok().build();
            }
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(mensajeErrorBuilder.mensajeNumeroIdentificacionInvalido()).build();
        }

        if (nombreUsuario.isPresent()) {
            if (!usuarioDAO.isRegistradoNombreUsuario(nombreUsuario.get())) {
                return Response.ok().build();
            }
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(mensajeErrorBuilder.mensajeNombreDeUsuarioYaHaSidoRegistrado()).build();
        }

        if (numeroIdentificacion.isPresent()) {
            if (!usuarioDAO.isRegistradoNumeroIdentificacion(numeroIdentificacion.get())) {
                return Response.ok().build();
            }
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(mensajeErrorBuilder.mensajeNumeroIdentificacionYaHaSidoRegistrado()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @UnitOfWork
    public Response crear(@Valid final Usuario usuario) throws EmailException {
        if (usuario.getNombreUsuario() != null
                && usuarioDAO.isRegistradoNombreUsuario(usuario.getNombreUsuario())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(mensajeErrorBuilder.mensajeNombreDeUsuarioYaHaSidoRegistrado()).build();
        }

        if (usuario.getIdentificacion().getNumeroIdentificacion() != null
                && usuarioDAO.isRegistradoNumeroIdentificacion(usuario.getIdentificacion().getNumeroIdentificacion())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(mensajeErrorBuilder.mensajeNumeroIdentificacionYaHaSidoRegistrado()).build();
        }

        Usuario usuarioCreado = usuarioDAO.guardar(usuario);

        mandarConfirmacion(usuarioCreado);

        return Response.status(Response.Status.CREATED).entity(usuarioCreado).build();
    }

    private void mandarConfirmacion(Usuario usuarioCreado) throws EmailException {
        String idToken = generarToken(usuarioCreado);

        String nombreDestinatario = usuarioCreado.getNombre().toString();
        String nombreUsuario = usuarioCreado.getNombreUsuario();
        String emailDestinatario = usuarioCreado.getEmailInstitucional();

        String asunto = lectorPropiedadesEmail.leerPropiedad(PropiedadesEmailEnum.ASUNTO.getKey());
        String urlToken = lectorPropiedadesEmail.leerPropiedad(PropiedadesEmailEnum.URL.getKey()).concat(idToken) ;

        String mensaje = constructorContenidoEmail.construirEmailNotificacionUsuarioCreado(nombreDestinatario,
                nombreUsuario, urlToken);

        despachadorEmail.enviarEmail(emailDestinatario, nombreDestinatario, asunto, mensaje);
    }

    private String generarToken(Usuario usuarioCreado) {
        String idToken = UUID.randomUUID().toString();
        Token token = new Token(idToken, usuarioCreado);
        tokenDAO.guardar(token);
        return idToken;
    }
}