package mu;

import java.util.concurrent.TimeUnit;

public class ThreadsMu {

  public static void sleep (long duration, TimeUnit timeUnit) {
    try {
      Thread.sleep(timeUnit.toMillis(duration));
    } catch (InterruptedException e) {
      throw ExceptionsMu.handleInterruptedException(e);
    }
  }
}
