package mu;

import org.junit.Test;

import static org.junit.Assert.*;

public class FilesMuTest {

  @Test(expected = NullPointerException.class)
  public void testWriteFileThrowingNPEOnContent() throws Exception {
    FilesMu.writeFile(null, "fileName");
  }

  @Test(expected = NullPointerException.class)
  public void testWriteFileThrowingNPEOnFileName() throws Exception {
    FilesMu.writeFile("content", null);
  }

  @Test(expected = NullPointerException.class)
  public void testReadFile() throws Exception {
    FilesMu.readFile(null);
  }
}