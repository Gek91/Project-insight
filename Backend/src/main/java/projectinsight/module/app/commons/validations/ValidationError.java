package projectinsight.module.app.commons.validations;

public class ValidationError {

  private String fieldName;
  private ValidationErrorTypeEnum errorType;

  public ValidationError(String fieldName, ValidationErrorTypeEnum errorType) {
    this.fieldName = fieldName;
    this.errorType = errorType;
  }

  public String getFieldName() {
    return fieldName;
  }

  public ValidationErrorTypeEnum getErrorType() {
    return errorType;
  }
}
