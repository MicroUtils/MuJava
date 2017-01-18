package mu.exceptions;

public class InterruptedError extends Error {
  public InterruptedError() {
  }

  public InterruptedError(String message) {
    super(message);
  }

  public InterruptedError(String message, Throwable cause) {
    super(message, cause);
  }

  public InterruptedError(Throwable cause) {
    super(cause);
  }

  public InterruptedError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
