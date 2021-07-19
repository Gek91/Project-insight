package projectTemplate.module.project.rest.project;

import projectTemplate.module.project.rest.project.data.ProjectDetailDTO;
import projectTemplate.module.project.rest.project.data.ProjectListDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/projects")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ProjectRestApi {

  @GET
  @Path("/")
  public ProjectListDTO getProjectList();

  @GET
  @Path("/{id}")
  public ProjectDetailDTO getProjectDetail(@PathParam("id") String id);

}
