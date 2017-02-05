package mu;

import org.junit.Test;

import static org.junit.Assert.*;

public class ObjectsMuTest {

  @Test
  public void testEquals(){
    assertTrue(ObjectsMu.equals(1, 1));
    assertFalse(ObjectsMu.equals(1, 2));
    assertFalse(ObjectsMu.equals(null, 2));
    assertTrue(ObjectsMu.equals(null, null));
  }

}
