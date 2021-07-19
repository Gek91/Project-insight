package projectTemplate.module.app.commons.validations.rest.data;

import projectTemplate.module.app.commons.validations.ValidationErrorTypeEnum;

public class ValidationErrorDTO {

  private String fieldName;
  private String errorType;

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getErrorType() {
    return errorType;
  }

  public void setErrorType(String errorType) {
    this.errorType = errorType;
  }
}
