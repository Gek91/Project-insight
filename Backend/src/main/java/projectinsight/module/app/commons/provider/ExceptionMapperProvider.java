package projectinsight.module.app.commons.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import projectinsight.module.app.commons.exception.EntityNotExistException;
import projectinsight.module.app.commons.validations.ValidationException;
import projectinsight.module.app.commons.validations.rest.data.ValidationErrorResponseDTO;
import projectinsight.module.app.commons.validations.rest.data.ValidationErrorsDTO;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionMapperProvider implements ExceptionMapper<Throwable> {

  private transient Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public Response toResponse(Throwable exception) {


    Response response = null;

    if (exception instanceof ValidationException) {

      ValidationErrorResponseDTO responseEntity = new ValidationErrorResponseDTO();
      responseEntity.setValidationErrors(new ValidationErrorsDTO(((ValidationException) exception).getErrors()));
      response = Response.status(Response.Status.BAD_REQUEST).entity(responseEntity).build();

    /*} else if (exception instanceof PermissionException) {
      response = Response.status(Status.FORBIDDEN).build();
    } else if (exception instanceof DocumentInconsistentException) {
      response = Response.status(DOCUMENT_INCONSISTENT_STATUS).build(); */
    } else if (exception instanceof EntityNotExistException) {
      response = Response.status(Response.Status.NOT_FOUND).build();
    /*} else if (exception instanceof ConcurrentModifyException) {

      ValidationErrorResponseDTO responseEntity = new ValidationErrorResponseDTO();
      responseEntity.setValidationErrors(new ValidationErrorsDTO());
      responseEntity.getValidationErrors().getList().add(new ValidationErrorDTO("CONCURRENCY_ERROR", null, null, null));
      response = Response.status(Status.CONFLICT).entity(responseEntity).build();
*/
    } else {
      logger.error("Thrown Exception", exception);
      response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    return response;
  }

}
