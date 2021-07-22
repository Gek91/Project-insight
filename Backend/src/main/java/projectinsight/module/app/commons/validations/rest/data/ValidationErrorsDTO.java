package projectinsight.module.app.commons.validations.rest.data;

import projectinsight.module.app.commons.validations.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorsDTO {

  private List<ValidationErrorDTO> errorList;

  public ValidationErrorsDTO() {
    this.errorList = new ArrayList<>();
  }

  public ValidationErrorsDTO(List<ValidationError> errors) {
    this();

    errors.stream().forEach(error -> {

      ValidationErrorDTO errorDTO = new ValidationErrorDTO();

      errorDTO.setErrorType(error.getErrorType() != null ? error.getErrorType().toString() : null);
      errorDTO.setFieldName(error.getFieldName());

      errorList.add(errorDTO);
    });
  }

  public List<ValidationErrorDTO> getErrorList() {
    return errorList;
  }

  public void setErrorList(List<ValidationErrorDTO> errorList) {
    this.errorList = errorList;
  }
}
