package projectinsight.module.project.rest.customer;

import projectinsight.module.project.rest.customer.data.CreateCustomerRequestDTO;
import projectinsight.module.project.rest.customer.data.CustomerDetailDTO;
import projectinsight.module.project.rest.customer.data.CustomerListDTO;
import projectinsight.module.project.rest.customer.data.EditCustomerRequestDTO;

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

  @POST
  @Path("/")
  CustomerDetailDTO createCustomer(CreateCustomerRequestDTO request);

  @PUT
  @Path("/{id}")
  CustomerDetailDTO editCustomer(@PathParam("id") String id, EditCustomerRequestDTO request);
}
