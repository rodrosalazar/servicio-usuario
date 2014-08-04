package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.Credencial;
import ec.gob.senescyt.usuario.dao.CredencialDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.CREATED;

@Path("/credenciales")
@Produces(MediaType.APPLICATION_JSON)
public class CredencialResource {

    private CredencialDAO credencialDAO;

    public CredencialResource(CredencialDAO credencialDAO) {
        this.credencialDAO = credencialDAO;
    }

    @POST
    @UnitOfWork
    public Response crear(@Valid final Credencial credencial) {
        Credencial nuevaCredencial = credencialDAO.guardar(credencial);
        return Response.status(CREATED).entity(nuevaCredencial).build();
    }
}