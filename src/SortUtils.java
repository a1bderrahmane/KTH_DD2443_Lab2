// File: SortUtils.java
public class SortUtils {

    /**
     * Swaps two elements in an array.
     * @param arr The array containing the elements.
     * @param i The index of the first element.
     * @param j The index of the second element.
     */
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Partitions the subarray around a pivot (last element).
     * @param arr The array to be partitioned.
     * @param low The starting index of the subarray.
     * @param high The ending index of the subarray.
     * @return The index of the pivot element after partitioning.
     */
    public static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }
}