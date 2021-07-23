package projectinsight.module.project.rest.project;

import projectinsight.module.project.rest.project.data.CreateProjectRequestDTO;
import projectinsight.module.project.rest.project.data.ProjectDetailDTO;
import projectinsight.module.project.rest.project.data.ProjectListDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/projects")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ProjectRestApi {

  @GET
  @Path("/")
  ProjectListDTO getProjectList();

  @GET
  @Path("/{id}")
  ProjectDetailDTO getProjectDetail(@PathParam("id") String id);

  @POST
  @Path("/")
  ProjectDetailDTO createProject(CreateProjectRequestDTO request);
}
