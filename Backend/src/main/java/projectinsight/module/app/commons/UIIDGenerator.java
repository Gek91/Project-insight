package projectinsight.module.app.commons;

import java.util.UUID;

public class UIIDGenerator {

  private UIIDGenerator() { }

  public static String generate() {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
