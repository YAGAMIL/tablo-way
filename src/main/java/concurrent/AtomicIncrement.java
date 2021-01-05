package concurrent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Description: 原子递增 Program:tablo-way concurrent Created on 2020/8/23 16:04
 *
 * @author <a href="mailto: Tablo_Jhin1996@outlook.com">Tablo</a>
 * @version 1.0
 */
public class AtomicIncrement {

  static long lockCount = 0L;

  static LongAdder segmentedLockCount = new LongAdder();

  static AtomicLong count = new AtomicLong(0L);

  private void longAdderAdd() {
    segmentedLockCount.increment();
  }

  private void syncAdd() {
    IntStream.range(0, 100000)
        .forEach(
            i -> {
              synchronized (this) {
                lockCount++;
              }
            });
  }

  private void jucAdd() {
    IntStream.range(0, 100000).forEach(i -> count.incrementAndGet());
  }

  public static void main(String[] args) throws InterruptedException {
    AtomicIncrement increment = new AtomicIncrement();
    List<Thread> threads =
        IntStream.range(0, 10)
            .mapToObj(i -> new Thread(increment::jucAdd))
            .collect(Collectors.toList());
    long start = System.currentTimeMillis();
    threads.forEach(Thread::start);
    for (Thread thread : threads) {
      thread.join();
    }
    System.out.println("juc-result" + count + "time--" + (System.currentTimeMillis() - start));

    List<Thread> syncThreads;
    syncThreads = IntStream.range(0, 10).mapToObj(i -> new Thread(increment::syncAdd)).collect(Collectors.toList());
    start = System.currentTimeMillis();
    syncThreads.forEach(Thread::start);
    for (Thread thread : syncThreads) {
      thread.join();
    }
    System.out.println(
        "sync-result:" + "LockCount:" + lockCount + "time " + (System.currentTimeMillis() - start));
  }
}
