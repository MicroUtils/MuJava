package mu;


import mu.exceptions.GeneralThrowableError;
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
  public static RuntimeException asUnchecked(Throwable t) {
    if (t instanceof RuntimeException) {
      return (RuntimeException) t;
    }
    if (t instanceof Error) {
      throw (Error) t;
    }
    if (t instanceof Exception) {//checked exception
      if (t instanceof IOException) {
        return new UncheckedIOException((IOException) t);
      }
      if (t instanceof InterruptedException) {
        throw handleInterruptedException((InterruptedException) t);
      }
    }
    throw new GeneralThrowableError(t);
  }

  public interface NonCheckedExecutable<T> {
    T execute() throws Exception;
  }

  /**
   * wrap a method invocation with a try/catch clause wrapping checked exception with unchecked
   * example of usage:
   * <code>asUnchecked(() -&gt; {doSomething();});</code>
   *
   * @param executable the method to execute
   * @param <T> type of return value
   * @return value returned from NonCheckedExecutable.execute()
   */
  public static <T> T asUnchecked(NonCheckedExecutable<T> executable) {
    try {
      return executable.execute();
    } catch (Exception e) {
      throw asUnchecked(e);
    }
  }

  /**
   * <code>InterruptedException</code> signals the thread that it should stop
   * so the best approach is to treat it like an <code>Error</code> and try to shutdown the thread gracefully
   * The methods sets back interrupted flag and return an error wrapping the original <code>InterruptedException</code> that should be thrown
   * @return InterruptedError wrapping the checked InterruptedException
   */
  public static InterruptedError handleInterruptedException(InterruptedException e) {
    Thread.currentThread().interrupt();
    return new InterruptedError(e);
  }

  /**
   * @param t the throwable
   * @return The root cause exception
   */
  public static Throwable getRootCause(Throwable t) {
    Throwable $ = t;
    while ($.getCause() != null && $.getCause() != $) {
      $ = $.getCause();
    }
    return $;
  }

  public static String getStackTrace(Throwable t) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    t.printStackTrace(pw);
    return sw.toString();
  }

  public static String getRootCauseMessage(Throwable t) {
    return getRootCause(t).getMessage();
  }


}
