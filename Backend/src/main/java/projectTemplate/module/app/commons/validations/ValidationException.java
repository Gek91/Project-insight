package projectTemplate.module.app.commons.validations;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ValidationException extends RuntimeException {

  private final List<ValidationError> errors;

  public ValidationException(List<ValidationError> errors) {
    this.errors = Collections.unmodifiableList(errors);
  }

  public List<ValidationError> getErrors() {
    return this.errors;
  }
}
