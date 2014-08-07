package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.dto.ContraseniaToken;
import ec.gob.senescyt.commons.builders.MensajeErrorBuilder;
import ec.gob.senescyt.usuario.core.Credencial;
import ec.gob.senescyt.usuario.core.Token;
import ec.gob.senescyt.usuario.dao.CredencialDAO;
import ec.gob.senescyt.usuario.dao.TokenDAO;
import ec.gob.senescyt.usuario.enums.MensajesErrorEnum;
import ec.gob.senescyt.usuario.services.ServicioCredencial;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.CREATED;

@Path("/credenciales")
@Produces(MediaType.APPLICATION_JSON)
public class CredencialResource {

    private CredencialDAO credencialDAO;
    private TokenDAO tokenDAO;
    private MensajeErrorBuilder mensajeErrorBuilder;
    private ServicioCredencial servicioCredencial;

    public CredencialResource(CredencialDAO credencialDAO, TokenDAO tokenDAO, MensajeErrorBuilder mensajeErrorBuilder, ServicioCredencial servicioCredencial) {
        this.credencialDAO = credencialDAO;
        this.tokenDAO = tokenDAO;
        this.mensajeErrorBuilder = mensajeErrorBuilder;
        this.servicioCredencial = servicioCredencial;
    }

    @POST
    @UnitOfWork
    public Response crear(@Valid final ContraseniaToken contraseniaToken) {
        Optional<Token> tokenOpcional = tokenDAO.buscar(contraseniaToken.getIdToken());

        if (!tokenOpcional.isPresent()) {
            Map<String, List<String>> entidadError = mensajeErrorBuilder.construirErrorCampo("idToken", MensajesErrorEnum.MENSAJE_ERROR_TOKEN);
            return Response.status(BAD_REQUEST).entity(entidadError).build();
        }

        Credencial credencial = servicioCredencial.convertirACredencial(contraseniaToken, tokenOpcional.get());
        credencialDAO.guardar(credencial);
        tokenDAO.eliminar(tokenOpcional.get().getId());

        return Response.status(CREATED).build();
    }
}