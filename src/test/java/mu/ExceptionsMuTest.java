package mu;

import mu.exceptions.GeneralThrowableError;
import mu.exceptions.InterruptedError;
import mu.exceptions.UncheckedException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.UncheckedIOException;

import static mu.ExceptionsMu.asUnchecked;
import static org.junit.Assert.*;

public class ExceptionsMuTest {

  @Test
  public void testAsUncheckedRuntimeException() throws Exception {
    final RuntimeException e = new RuntimeException();
    Assert.assertEquals(e, asUnchecked(e));
  }

  @Test(expected = GeneralThrowableError.class)
  public void testAsUncheckedThrowable() throws Exception {
    assertTrue(asUnchecked(new Throwable()) instanceof RuntimeException);
  }
  @Test(expected = InterruptedError.class)
  public void testAsUncheckedInterruptedException() throws Exception {
    asUnchecked(new InterruptedException());
  }

  @Test(expected = UncheckedException.class)
  public void testAsUncheckedException() throws Exception {
    asUnchecked(new Exception());
  }

  @Test
  public void testInterruptedExceptionHandling() throws Exception {
    Thread.currentThread().isInterrupted();
    assertTrue(ExceptionsMu.handleInterruptedException(new InterruptedException()) instanceof InterruptedError) ;
    assertTrue(Thread.currentThread().isInterrupted());
  }

  @Test(expected = GeneralThrowableError.class)
  public void testAsUncheckedFunctional() {
    asUnchecked(() -> {throw new Throwable();});
  }

  @Test
  public void testAsUncheckedIO() throws Exception {
    assertTrue(asUnchecked(new IOException()) instanceof UncheckedIOException);
  }

  @Test(expected = Error.class)
  public void testAsUncheckedError() throws Exception {
    final Error e = new Error();
    asUnchecked(e);
  }

  @Test
  public void testGetRootCause() throws Exception {
    final RuntimeException cause = new RuntimeException();
    Assert.assertEquals(cause, ExceptionsMu.getRootCause(new RuntimeException(cause)));
  }

  @Test
  public void testGetRootCauseWithNullCause() throws Exception {
    final RuntimeException e = new RuntimeException();
    Assert.assertEquals(e, ExceptionsMu.getRootCause(e));
  }

  @Test
  public void testGetStackTrace() throws Exception {
    Assert.assertNotNull(ExceptionsMu.getStackTrace(new RuntimeException()));
  }

  @Test
  public void testGetRootCauseMessage() throws Exception {
    String message = "message";
    final RuntimeException cause = new RuntimeException(message);
    Assert.assertEquals(message, ExceptionsMu.getRootCauseMessage(new RuntimeException(cause)));
  }

}
