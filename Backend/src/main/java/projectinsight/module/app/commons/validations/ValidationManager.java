package projectinsight.module.app.commons.validations;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ValidationManager {
  private List<ValidationError> validationErrors;

  private ValidationManager() {
    this.validationErrors = new ArrayList<>();
  }

  public static ValidationManager build() {
    return new ValidationManager();
  }

  public ValidationManager validate(boolean condition, String field, ValidationErrorTypeEnum errorType) {

    if(!condition) {

      ValidationError error = new ValidationError(field, errorType);

      validationErrors.add(error);
    }

    return this;
  }

  public ValidationManager required(Object fieldValue, String field) {
    return validate(fieldValue != null, field, ValidationErrorTypeEnum.REQUIRED);
  }

  public ValidationManager requiredNotBlank(String fieldValue, String field) {
    return validate(StringUtils.isNotBlank(fieldValue), field, ValidationErrorTypeEnum.REQUIRED);
  }

  public ValidationManager requiredNotEmpty(String fieldValue, String field) {
    return validate(StringUtils.isNotEmpty(fieldValue), field, ValidationErrorTypeEnum.REQUIRED);
  }

  public boolean hasErrors() {
    return !this.validationErrors.isEmpty();
  }

  public void throwValidationExceptionIfHasErrors() {
    if(this.hasErrors()){
      throw new ValidationException(this.validationErrors);
    }
  }
}
