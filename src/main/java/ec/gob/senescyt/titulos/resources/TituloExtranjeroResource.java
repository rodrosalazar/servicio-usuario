package ec.gob.senescyt.titulos.resources;

import ec.gob.senescyt.titulos.core.PortadorTitulo;
import ec.gob.senescyt.titulos.dao.PortadorTituloDAO;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.CREATED;

@Path("/titulo/extranjero")
@Produces(MediaType.APPLICATION_JSON)
public class TituloExtranjeroResource {
    private PortadorTituloDAO portadorTituloDAO;

    public TituloExtranjeroResource(PortadorTituloDAO portadorTituloDAO) {
        this.portadorTituloDAO = portadorTituloDAO;
    }

    @POST
    public Response crear(@Valid PortadorTitulo portadorTitulo) {
        PortadorTitulo nuevoTitulo = portadorTituloDAO.guardar(portadorTitulo);
        return Response.status(CREATED).entity(nuevoTitulo).build();
    }


}
