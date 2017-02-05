package mu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingMu {

  /**
   * @return slf4j logger with the name of the calling class
   */
  public static Logger getLogger() {
    return LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());
  }
}
