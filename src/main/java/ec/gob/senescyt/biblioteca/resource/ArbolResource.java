package ec.gob.senescyt.biblioteca.resource;

import ec.gob.senescyt.biblioteca.Arbol;
import ec.gob.senescyt.biblioteca.dao.ArbolDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/arboles")
@Produces(MediaType.APPLICATION_JSON)
public class ArbolResource {
    private ArbolDAO arbolDAO;

    public ArbolResource(ArbolDAO arbolDAO) {
        this.arbolDAO = arbolDAO;
    }

    @GET
    @Path("{id}")
    @UnitOfWork
    public Response obtenerArbol(@PathParam("id") int idArbol){
        Arbol arbolEncontrado = arbolDAO.obtenerPorId(idArbol);
        return Response.ok().entity(arbolEncontrado).build();
    }
}
