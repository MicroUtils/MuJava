package mu;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoggingMuTest {

  @Test
  public void getLogger() throws Exception {
    assertEquals("mu.LoggingMuTest", LoggingMu.getLogger().getName());
  }

}
