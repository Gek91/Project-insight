package projectTemplate.module.project.rest.customer;

import projectTemplate.module.project.rest.customer.data.CustomerDetailDTO;
import projectTemplate.module.project.rest.customer.data.CustomerListDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface CustomerRestApi {

  @GET
  @Path("/")
  CustomerListDTO getCustomerList();

  @GET
  @Path("/{id}")
  CustomerDetailDTO getCustomerDetail(@PathParam("id") String id);
}
