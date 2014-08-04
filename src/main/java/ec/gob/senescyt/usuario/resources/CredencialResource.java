package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.biblioteca.dto.ContraseniaToken;
import ec.gob.senescyt.commons.builders.MensajeErrorBuilder;
import ec.gob.senescyt.usuario.core.Credencial;
import ec.gob.senescyt.usuario.core.Token;
import ec.gob.senescyt.usuario.dao.CredencialDAO;
import ec.gob.senescyt.usuario.dao.TokenDAO;
import ec.gob.senescyt.usuario.enums.MensajesErrorEnum;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.*;

import static com.google.common.collect.Lists.newArrayList;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.CREATED;

@Path("/credenciales")
@Produces(MediaType.APPLICATION_JSON)
public class CredencialResource {

    private CredencialDAO credencialDAO;
    private TokenDAO tokenDAO;
    private MensajeErrorBuilder mensajeErrorBuilder;

    public CredencialResource(CredencialDAO credencialDAO, TokenDAO tokenDAO, MensajeErrorBuilder mensajeErrorBuilder) {
        this.credencialDAO = credencialDAO;
        this.tokenDAO = tokenDAO;
        this.mensajeErrorBuilder = mensajeErrorBuilder;
    }

    @POST
    @UnitOfWork
    public Response crear(@Valid final ContraseniaToken contraseniaToken) {
        Optional<Token> token = tokenDAO.buscar(contraseniaToken.getIdToken());

        if (!token.isPresent()) {
            Map<String, List<String>> entidadError = mensajeErrorBuilder.construirErrorCampo("idToken", MensajesErrorEnum.MENSAJE_ERROR_TOKEN);
            return Response.status(BAD_REQUEST).entity(entidadError).build();
        }

        Credencial nuevaCredencial = credencialDAO.guardar(convertirACredencial(contraseniaToken, token.get()));
        return Response.status(CREATED).entity(nuevaCredencial).build();
    }

    private Credencial convertirACredencial(ContraseniaToken contraseniaToken, Token token) {
        String nombreUsuario = token.getUsuario().getNombreUsuario();
        String contrasenia = contraseniaToken.getContrasenia();

        return new Credencial(nombreUsuario, contrasenia);
    }
}