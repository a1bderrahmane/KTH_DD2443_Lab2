
/**
 * Sort using Java's ForkJoinPool.
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ForkJoinPoolSort implements Sorter {
        public final int threads;
        private static final int THRESHOLD = 2048;

        public ForkJoinPoolSort(int threads) {
                this.threads = threads;
        }

        public void sort(int[] arr) {
                ForkJoinPool pool =new ForkJoinPool(this.threads);
                Worker task = new Worker(arr,0,arr.length-1);
                pool.invoke(task);

        }

        public int getThreads() {
                return threads;
        }

        private static class Worker extends RecursiveAction {
                private int[] arr;
                private int low;
                private int high;

                Worker(int [] arr, int low , int high) {
                        this.high=high;
                        this.low=low;
                        this.arr=arr;
                }
                @Override
                protected void compute() {
                        if (low>=high)return ;
                        if(high -low <=THRESHOLD)
                        {
                                // Just do it sequentially
                                // SequentialSort.quickSort(arr,low,high);
                                java.util.Arrays.sort(arr, low, high + 1);
                        }
                        else
                        {
                                int pivot =SortUtils.partition(arr, low, high);
                                Worker left =new Worker(arr, low, pivot-1);
                                Worker right = new Worker(arr,pivot+1,high);
                                left.fork();
                                right.compute();
                                left.join();
                        }
                }
        }
}
