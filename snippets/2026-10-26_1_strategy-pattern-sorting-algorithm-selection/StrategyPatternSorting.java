// Title: Strategy Pattern - Sorting Algorithm Selection

// The Strategy pattern defines a family of algorithms, encapsulates each one,
// and makes them interchangeable. Use it when you have multiple ways to perform
// a task and want to select the algorithm at runtime based on conditions.

import java.util.Arrays;

interface SortStrategy {
    void sort(int[] array);
}

class BubbleSort implements SortStrategy {
    @Override
    public void sort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }
}

class QuickSort implements SortStrategy {
    @Override
    public void sort(int[] array) {
        if (array.length == 0) return;
        quickSort(array, 0, array.length - 1);
    }

    private void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pi = partition(array, low, high);
            quickSort(array, low, pi - 1);
            quickSort(array, pi + 1, high);
        }
    }

    private int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        return i + 1;
    }
}

class Sorter {
    private SortStrategy strategy;

    public Sorter(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void execute(int[] array) {
        strategy.sort(array);
    }
}

public class StrategyPatternSorting {
    public static void main(String[] args) {
        int[] data1 = {64, 34, 25, 12, 22, 11, 90};
        int[] data2 = {64, 34, 25, 12, 22, 11, 90};

        Sorter sorter = new Sorter(new BubbleSort());
        sorter.execute(data1);
        System.out.println("Bubble Sort: " + Arrays.toString(data1));

        sorter.setStrategy(new QuickSort());
        sorter.execute(data2);
        System.out.println("Quick Sort:  " + Arrays.toString(data2));
    }
}
