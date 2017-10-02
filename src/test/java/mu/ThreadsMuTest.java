package mu;

import mu.exceptions.InterruptedError;
import org.junit.Test;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class ThreadsMuTest {

  @Test(expected = InterruptedError.class)
  public void sleep() throws Exception {
    Thread.currentThread().interrupt();
    ThreadsMu.sleep(10, TimeUnit.MILLISECONDS);
  }

  @Test
  public void test_createThreadPoolExecutor() throws Exception {
    int workQueueSize = 1_000_000;
    int threadPoolSize = 10;
    ThreadPoolExecutor pool = ThreadsMu.createThreadPoolExecutor(threadPoolSize, workQueueSize, "myPool");
    assertEquals(0, pool.getPoolSize());
    assertEquals(threadPoolSize, pool.getCorePoolSize());
    assertEquals(threadPoolSize, pool.getMaximumPoolSize());
    assertEquals(0, pool.getCompletedTaskCount());
    assertEquals(0, pool.getQueue().size());
    assertEquals(workQueueSize, pool.getQueue().remainingCapacity());
    assertEquals(false, pool.isShutdown());
    pool.shutdown();
  }

}
