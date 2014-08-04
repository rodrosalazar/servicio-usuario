package ec.gob.senescyt.commons.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/mu-5eb2c11b-070b9e3f-bd06cd4f-793c4d76")
@Produces(MediaType.APPLICATION_JSON)
public class BlitzResource {

    @GET
    public Response get(){
        return Response.ok("42").build();
    }

}
