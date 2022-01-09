import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
/***
 * CPE-593 Data Structure and Algorithm
 * HW3-Group-Sorting
 * Yinghao Wang: 10455443
 * Shuai Hao: 10432811
 * Fan Luo: 10442682
 * Sept 28, 2020
 */
public class GroupSorting {
    private static int bound;//the range of each number in the random-generated-array
    private static int times;//testing frequency
    private static int[] newArr;//the another new array we should test
    /**part 1-- read file
     * read from index.txt and get the bound, times(testing frequency) & another new Array
     * */
    public void readFile(String fileName){
        File file = new File(fileName);
        try(Scanner scanner = new Scanner(file)){
            bound = scanner.nextInt();
            if(bound <= 0)
                throw new IllegalArgumentException("bound must greater than 0");
            times = scanner.nextInt();
            if(times <= 0)
                throw new IllegalArgumentException("test times must be at least 1");

            int newLen = scanner.nextInt();
            if(newLen <= 0)
                throw new IllegalArgumentException("array length must be at least 1");
            newArr = new int[newLen];
            for(int i = 0; i < newLen; i ++){
                int temp = scanner.nextInt();
                newArr[i] = temp;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    /**
     * part 2 -- MergeSort
     * */
    public static void mergesort(int[] arr, int n){
        mergePartition(arr, 0,  n - 1);
    }
    private static void mergePartition(int[] arr, int l, int r){
        if(l >= r) return ;
        int mid = l + (r - l) / 2;
        mergePartition(arr, l, mid);
        mergePartition(arr, mid + 1, r);
        merge(arr, l, mid, r);
    }
    //Merge two ordered intervals : arr[l..mid] and arr[mid + 1, r]
    private static void merge(int[] arr, int l, int mid, int r){
        //create extra space to allocate the value
        int[] temp = Arrays.copyOfRange(arr, l, r + 1);

        int i = l, j = mid + 1;
        //Assign value to arr[k] in each cycle
        for(int k = l; k <= r; k ++){
            if(i > mid){
                arr[k] = temp[j - l];
                j ++;
            }
            else if(j > r){
                arr[k] = temp[i - l];
                i ++;
            }
            else if(temp[i - l] <= temp[j - l]){
                arr[k] = temp[i - l];
                i ++;
            }
            else{
                arr[k] = temp[j - l];
                j ++;
            }
        }
    }

    /**
     * part 3 QuickSort
     * Using Lomuto Method in the function partition()
     * */
    public static void quicksort(int[] arr, int l, int r){
        //create a random object in order to generate a random index in the partition function
        Random random = new Random();
        sort(arr, l, r, random);
    }
    public static void sort(int[] arr, int l, int r,Random random){
        if(l >= r) return ;
        int p = partition(arr, l, r, random);
        sort(arr, l, p - 1, random);
        sort(arr, p + 1, r, random);
    }
    // we use Lomuto method to implement the sort algorithm in the function of partition()
    private static int partition(int[] arr, int l, int r, Random random){
        //generate a random index in [l...r - l]
        int p = l + random.nextInt(r - l + 1);
        swap(arr, p, l);
        //arr[l + 1....j] < v ; arr[j + 1...i - i] >= v
        int j = l;
        //Lomuto method
        for(int i = l + 1; i <= r; i ++){
            if(arr[i] < arr[l]){
                j ++;
                swap(arr, i, j);
            }
        }
        swap(arr, l, j);
        return j;
    }
    /**
     * part 4 -- HeapSort
     * */
    public static void heapsort(int[] arr, int n){
        //find the last non-leaf node
        int start = (n - 1) / 2;
        for(int i = start; i >= 0; i --){
            makeHeap(arr, i, n);
        }
        //adjust the structure of the heap + swap the first with the last element
        for(int i = n - 1; i > 0; i --){
            swap(arr, 0, i);
            makeHeap(arr, 0, i);
        }
    }
    private static void makeHeap(int[] arr, int index, int size){
        int temp = arr[index];
        for(int k = index * 2 + 1; k < size; k = k * 2 + 1){//start with the left node of the index-th leaf
            if(k + 1 < size && arr[k] < arr[k + 1]){//left leaf < right leaf
                k ++;
            }
            if(arr[k] > temp){//the value in child > root
                arr[index] = arr[k];
                index = k;
            }
            else{
                break;
            }
        }
        arr[index] = temp;//swap the value
    }
    /**part 5
     * some additional functions to execute the sorting algorithm
     * */
    private static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    //generate a length of n random array, the range of each value in the array is [0...bound]
    public static int[] generateRandomArray(int bound, int n){
        int[] arr = new int[n];
        Random random = new Random();
        for(int i = 0; i < n; i ++){
            arr[i] = random.nextInt(bound + 1);
        }
        return arr;
    }
    //print the array
    private static void printArr(int[] arr){
        System.out.print("[");
        for(int i = 0; i < arr.length; i ++){
            System.out.print(arr[i]);
            if(i != arr.length - 1)
                System.out.print(',');
        }
        System.out.print("]");
        System.out.println();

    }
    //judge if the sorting is completed
    public static boolean isSorted(int[] arr){
        for(int i = 1; i < arr.length; i ++)
            if(arr[i - 1] > arr[i])
                return false;
        return true;
    }
    //compute the total running time for each sorting algorithm
    public static void runningTime(int[] arr, String sortName){
        long startTime = System.nanoTime();

        if(sortName.equals("mergesort"))
            mergesort(arr, arr.length);
        else if(sortName.equals("quicksort"))
            quicksort(arr, 0, arr.length - 1);
        else if(sortName.equals("heapsort"))
            heapsort(arr, arr.length);

        long endTime = System.nanoTime();
        double time  = (endTime - startTime) / 1000000000.0;

        //if the result is not a sorted array, it should throw the error!
        if(!isSorted(arr))
            throw new RuntimeException(sortName + " failed");
        System.out.println("time = " + time);
    }
    /**
     * sort each random array and print it out the result
     * */
    public static void sortAndresult(int bound, int len, int times){

        for(int i = 0; i < times; i ++){
            int[] arr = generateRandomArray(bound,len);
            int[] arr2 = Arrays.copyOf(arr, arr.length);
            int[] arr3 = Arrays.copyOf(arr, arr.length);
            //mergesort
            printArr(arr);
            System.out.println("mergesort");
            mergesort(arr, arr.length);
            printArr(arr);
            System.out.println();

            //quicksort
            printArr(arr2);
            System.out.println("quicksort");
            quicksort(arr2, 0,arr2.length - 1);
            printArr(arr2);
            System.out.println();

            //heapsort
            printArr(arr3);
            System.out.println("heapsort");
            heapsort(arr3, arr2.length);
            printArr(arr3);
            System.out.println();
        }
    }
    public static void main(String[] args){
        //read files and get the bound, length and another testing array
        GroupSorting gs = new GroupSorting();
        gs.readFile("input.txt");


        //print out all the sorted random-generated array using MergeSort, QuickSort & HeapSort
        sortAndresult(bound,bound,times);


        //test the new Array read from the file and print it out
        int[] testArray1 = Arrays.copyOf(newArr, newArr.length);
        int[] testArray2 = Arrays.copyOf(newArr, newArr.length);
        int[] testArray3 = Arrays.copyOf(newArr, newArr.length);

        //mergesort for new array
        System.out.println("mergesort");
        mergesort(testArray1, testArray1.length);
        printArr(testArray1);
        //quicksort for new array
        System.out.println("quicksort");
        quicksort(testArray2, 0, testArray1.length - 1);
        printArr(testArray2);
        //heapsort for new array
        System.out.println("heapksort");
        heapsort(testArray3, testArray1.length);
        printArr(testArray3);

        System.out.println("###############################################");

        int n = 1000000, n2 = 10000000, n3 = 100000000;

        //array 1 with the length of 10^6
        int[] arr1 = generateRandomArray(n,n);
        int[] arr12 = Arrays.copyOf(arr1, arr1.length);
        int[] arr13 = Arrays.copyOf(arr1, arr1.length);

        System.out.println("array 1 with the length of 10^6");
        System.out.print("Running time for mergesort: ");
        runningTime(arr1, "mergesort");
        System.out.print("Running time for quicksort: ");
        runningTime(arr12, "quicksort");
        System.out.print("Running time for heapsort: ");
        runningTime(arr13, "heapsort");

        System.out.println("###############################################");

        //array 2 with the length of 10^7
        int[] arr2 = generateRandomArray(n2,n2);
        int[] arr22 = Arrays.copyOf(arr2, arr2.length);
        int[] arr23 = Arrays.copyOf(arr2, arr2.length);

        System.out.println("array 2 with the length of 10^7");
        System.out.print("Running time for mergesort: ");
        runningTime(arr2, "mergesort");
        System.out.print("Running time for quicksort: ");
        runningTime(arr22, "quicksort");
        System.out.print("Running time for heapsort: ");
        runningTime(arr23, "heapsort");

        System.out.println("###############################################");

        //array 3 with the length of 10^8
        int[] arr3 = generateRandomArray(n3,n3);
        int[] arr32 = Arrays.copyOf(arr3, arr3.length);
        int[] arr33 = Arrays.copyOf(arr3, arr3.length);

        System.out.println("array 3 with the length of 10^8");
        System.out.print("Running time for mergesort: ");
        runningTime(arr3, "mergesort");
        System.out.print("Running time for quicksort: ");
        runningTime(arr32, "quicksort");
        System.out.print("Running time for heapsort: ");
        runningTime(arr33, "heapsort");

    }
}
