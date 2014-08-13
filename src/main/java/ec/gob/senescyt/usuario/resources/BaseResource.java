package ec.gob.senescyt.usuario.resources;


import ec.gob.senescyt.usuario.core.Entidad;
import ec.gob.senescyt.usuario.dao.AbstractServicioDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.List;

public class BaseResource<E extends Entidad, D extends AbstractServicioDAO> {
    protected final D dao;

    public BaseResource(D dao) {
        this.dao = dao;
    }

    @POST
    @UnitOfWork
    public Response crear(@Valid final E entidad){
        dao.guardar(entidad);
        return Response.status(Response.Status.CREATED).entity(entidad).build();
    }

    @PUT
    @UnitOfWork
    public Response modificar(@Valid final E entidad){
        dao.guardar(entidad);
        return Response.status(Response.Status.OK).entity(entidad).build();
    }

    @GET
    @UnitOfWork
    @Path("/{entidadId}")
    public Response obtener(@PathParam("entidadId") Long entidadId){
        Entidad entity = dao.encontrarPorId(entidadId);
        if (entity == null){
            return noEncotrado();
        }
        return Response.status(Response.Status.OK).entity((E) entity).build();
    }

    @GET
    @UnitOfWork
    public Response obtenerTodos(){
        List<E> entidades = dao.obtenerTodos();
        return Response.status(Response.Status.OK).entity(entidades).build();
    }

    @DELETE
    @UnitOfWork
    @Path("/{entidadId}")
    public Response eliminar(@PathParam("entidadId") Long entidadId){
        dao.eliminar(entidadId);
        return Response.status(Response.Status.OK).build();
    }

    protected Response noEncotrado() {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
