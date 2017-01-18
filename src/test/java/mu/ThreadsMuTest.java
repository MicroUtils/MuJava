package mu;

import mu.exceptions.InterruptedError;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class ThreadsMuTest {

  @Test(expected = InterruptedError.class)
  public void sleep() throws Exception {
    Thread.currentThread().interrupt();
    ThreadsMu.sleep(10, TimeUnit.MILLISECONDS);
  }

}
