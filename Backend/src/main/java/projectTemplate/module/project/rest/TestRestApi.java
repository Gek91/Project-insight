package projectTemplate.module.project.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface TestRestApi {

	@GET
	@Path("/test")
	public String echo();
	
	@GET
	@Path("/test-db")
	public String testDb();

}
