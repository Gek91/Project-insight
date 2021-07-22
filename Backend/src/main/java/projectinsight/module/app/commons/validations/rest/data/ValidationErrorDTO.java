package projectinsight.module.app.commons.validations.rest.data;

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
