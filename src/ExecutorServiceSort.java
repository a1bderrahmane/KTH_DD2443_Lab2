
/**
 * Sort using Java's ExecutorService.
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorServiceSort implements Sorter {
        public final int threads;
        ExecutorService executor;
        private AtomicInteger busyThreads;

        public ExecutorServiceSort(int threads) {
                this.threads = threads;
                busyThreads = new AtomicInteger(0);
        }

        public void sort(int[] arr) {
                int n = arr.length;
                this.executor = Executors.newFixedThreadPool(this.threads);

                try {
                        parallelQuickSort(arr, 0, n - 1);
                } finally {
                        executor.shutdown();
                        try {
                                // Wait for all tasks to complete
                                while (!executor.awaitTermination(1, java.util.concurrent.TimeUnit.SECONDS)) {
                                }
                        } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                        }
                }
        }

        public int getThreads() {
                return threads;
        }

        // private static class Worker implements Runnable {

        // Worker() {

        // }

        // public void run() {

        // }

        // }
        private boolean tryReserveThread() {
                while (true) {
                        int current = busyThreads.get();
                        if (current >= threads)
                                return false;
                        if (busyThreads.compareAndSet(current, current + 1))
                                return true;
                }
        }

        private void parallelQuickSort(int[] arr, int low, int high) {
                if (low >= high)
                        return;
                if (high - low <= 5000) {
                        // Just do it sequentially
                        // SequentialSort.quickSort(arr,low,high);
                        java.util.Arrays.sort(arr, low, high + 1);
                } else {

                        int pivot = SortUtils.partition(arr, low, high);
                        Future<?> leftFuture = null;
                        Future<?> rightFuture = null;

                        if (tryReserveThread()) {
                                leftFuture = executor.submit(() -> {
                                        try {
                                                parallelQuickSort(arr, low, pivot - 1);
                                        } finally {
                                                busyThreads.decrementAndGet();
                                        }
                                });
                        } else {
                                parallelQuickSort(arr, low, pivot - 1);
                        }
                        if (tryReserveThread()) {
                                rightFuture = executor.submit(() -> {
                                        try {
                                                parallelQuickSort(arr, pivot + 1, high);
                                        } finally {
                                                busyThreads.decrementAndGet();
                                        }
                                });
                        } else {
                                parallelQuickSort(arr, pivot + 1, high);
                        }
                        // System.out.println(busyThreads);
                        if (leftFuture != null) {
                                try {
                                        leftFuture.get();
                                } catch (Exception e) {
                                        throw new RuntimeException("Left sort failed", e);
                                }
                        }
                        if (rightFuture != null) {
                                try {
                                        rightFuture.get();
                                } catch (Exception e) {
                                        throw new RuntimeException("Right sort failed", e);
                                }
                        }
                }
        }
}
