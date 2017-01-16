package mu;


import mu.exceptions.InterruptedError;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UncheckedIOException;

/**
 * Exception handling helpers
 * to convert checked exceptions to unchecked see <code>asUnchecked</code>
 */
public class ExceptionsMu {

  /**
   * this method wrap checked exception by unchecked exception
   * it is intended to use by client calling a code that throws checked exception in the case the client don't want to take care of the exception immediatly
   * and to prevent pollution of methods signature
   * example of usage:
   * <code>
   * try {
   * ...
   * } catch (IOException e) {
   * throw ExceptionsMu.asUnchecked(e);
   * }
   * </code>
   *
   * @param t the throwable
   * @return the <code>t</code> parameter if it is not a checked exception,
   * otherwise a <code>RuntimeException</code> with <code>t</code> as its cause
   */
  public static RuntimeException asUnchecked(@Nonnull Throwable t) {
    if (t instanceof RuntimeException) {
      return (RuntimeException) t;
    }
    if (t instanceof Error) {
      throw (Error) t;
    }

    //checked exception

    if (t instanceof IOException) {
      return new UncheckedIOException((IOException) t);
    }
    if (t instanceof InterruptedException) {
      Thread.currentThread().interrupt();
      throw new InterruptedError((InterruptedException)t);
    }
    return new RuntimeException(t);

  }

  /**
   * @param t the throwable
   * @return The root cause exception
   */
  public static Throwable getRootCause(@Nonnull Throwable t) {
    Throwable $ = t;
    while ($.getCause() != null && $.getCause() != $) {
      $ = $.getCause();
    }
    return $;
  }

  public static String getStackTrace(@Nonnull Throwable t) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    t.printStackTrace(pw);
    return sw.toString();
  }

  public static String getRootCauseMessage(@Nonnull Throwable t) {
    return getRootCause(t).getMessage();
  }
}
