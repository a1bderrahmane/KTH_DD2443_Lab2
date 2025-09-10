public class SequentialSort implements Sorter {

        public SequentialSort() {

        }

        public void sort(int[] arr) {
                int n = arr.length;
                quickSort(arr, 0, n - 1);
        }

        public int getThreads() {
                return 1;
        }

        public static void quickSort(int[] arr, int low, int high) {
                if (low < high) {
                        int pivot = SortUtils.partition(arr, low, high);
                        quickSort(arr, low, pivot - 1);
                        quickSort(arr, pivot + 1, high);
                }
        }
}
