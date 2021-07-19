package projectTemplate.module.app.commons.validations.rest.data;

public class ValidationErrorResponseDTO {

  private ValidationErrorsDTO validationErrors = new ValidationErrorsDTO();

  public ValidationErrorsDTO getValidationErrors() {
    return validationErrors;
  }

  public void setValidationErrors(ValidationErrorsDTO validationErrorsDTO) {
    this.validationErrors = validationErrorsDTO;
  }
}
