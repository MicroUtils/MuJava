package mu.exceptions;

public class GeneralThrowableError extends Error {
  public GeneralThrowableError() {
  }

  public GeneralThrowableError(String message) {
    super(message);
  }

  public GeneralThrowableError(String message, Throwable cause) {
    super(message, cause);
  }

  public GeneralThrowableError(Throwable cause) {
    super(cause);
  }

  public GeneralThrowableError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
