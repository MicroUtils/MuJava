package mu;


import com.google.common.base.Preconditions;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * File handling helpers
 */
public class FilesMu {

  /**
   * write content to file with default charset, omitting the previous content if any
   *
   * @param contentToWrite
   * @param fileName
   */
  public static void writeFile(String contentToWrite, String fileName) {
    Preconditions.checkNotNull(contentToWrite);
    Preconditions.checkNotNull(fileName);
    try {
      Files.write(contentToWrite, new File(fileName), Charset.defaultCharset());
    } catch (IOException e) {
      throw ExceptionsMu.asUnchecked(e);
    }
  }

  /**
   * read file content with default charset
   *
   * @param fileName
   * @return list of lines of the content
   */
  public static List<String> readFile(String fileName) {
    Preconditions.checkNotNull(fileName);
    try {
      return Files.readLines(new File(fileName), Charset.defaultCharset());
    } catch (IOException e) {
      throw ExceptionsMu.asUnchecked(e);
    }
  }
}
