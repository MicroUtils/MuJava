package mu;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadsMu {

  public static void sleep (long duration, TimeUnit timeUnit) {
    try {
      Thread.sleep(timeUnit.toMillis(duration));
    } catch (InterruptedException e) {
      throw ExceptionsMu.handleInterruptedException(e);
    }
  }

  public static ThreadPoolExecutor createThreadPoolExecutor(int threadPoolSize, int workQueueSize, String poolName) {
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(workQueueSize);
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
      threadPoolSize,
      threadPoolSize,
      1,
      TimeUnit.MINUTES,
      workQueue,
      new ThreadFactoryBuilder().setNameFormat(poolName + "-%d").build());
    threadPoolExecutor.allowCoreThreadTimeOut(true);
    return threadPoolExecutor;
  }
}
