package projectinsight.module.app.commons.validations;

import java.util.Collections;
import java.util.List;

public class ValidationException extends RuntimeException {

  private final List<ValidationError> errors;

  public ValidationException(List<ValidationError> errors) {
    this.errors = Collections.unmodifiableList(errors);
  }

  public List<ValidationError> getErrors() {
    return this.errors;
  }
}
