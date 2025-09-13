import java.util.stream.Stream;

/**
 * Sort using Java's ParallelStreams and Lambda functions.
 *
 * Hints:
 * - Do not take advice from StackOverflow.
 * - Think outside the box.
 *      - Stream of threads?
 *      - Stream of function invocations?
 *
 * By default, the number of threads in parallel stream is limited by the
 * number of cores in the system. You can limit the number of threads used by
 * parallel streams by wrapping it in a ForkJoinPool.
 *      ForkJoinPool myPool = new ForkJoinPool(threads);
 *      myPool.submit(() -> "my parallel stream method / function");
 */

class Interval {
    public final int low;
    public final int high;

    public Interval(int low, int high) {
        this.low = low;
        this.high = high;
    }
}

public class ParallelStreamSort implements Sorter {
    public final int threads;

    public ParallelStreamSort(int threads) {
        this.threads = threads;
    }

    public void sort(int[] arr) {
        parallelSort(arr, 0, arr.length - 1);
    }

    public int getThreads() {
        return threads;
    }

    private void parallelSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivot = SortUtils.partition(arr, low, high);
            Interval i1 = new Interval(low, pivot - 1);
            Interval i2 = new Interval(pivot + 1, high);
            Stream<Interval> s = Stream.of(i1, i2);
            s.parallel().forEach(interval -> parallelSort(arr, interval.low, interval.high));
        }
    }
}
