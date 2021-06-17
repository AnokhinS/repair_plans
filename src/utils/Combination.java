package utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Combination {

    /* arr[]  ---> Input Array
    data[] ---> Temporary array to store current combination
    start & end ---> Staring and Ending indexes in arr[]
    index  ---> Current index in data[]
    r ---> Size of a combination to be printed */
    static void combinationUtil(int arr[], int data[], int start,
                                int end, int index, int r, List<int[]> combList)
    {
        // Current combination is ready to be printed, print it
        if (index == r)
        {
            combList.add(data.clone());
            return;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i=start; i<=end && end-i+1 >= r-index; i++)
        {
            data[index] = arr[i];
            combinationUtil(arr, data, i+1, end, index+1, r,combList);
        }
    }

    // The main function that prints all combinations of size r
    // in arr[] of size n. This function mainly uses combinationUtil()
    static void getCombinations(int arr[], int n, int r, List<int[]> combList)
    {
        // A temporary array to store all combination one by one
        int data[]=new int[r];

        // Print all combination using temporary array 'data[]'
        combinationUtil(arr, data, 0, n-1, 0, r, combList);
    }

    static void getCombinations(int arr[],List<int[]> combList){
        List<int[]> list = new ArrayList<>();
        int n = arr.length;
        for (int r = 1; r <= n; r++) {
            getCombinations(arr, n, r, list);
        }
        for (int[] a : list){
            permute(a, combList);
        }

    }

    public static void permute(int[] arr, List<int[]> comb){
        permuteHelper(arr, 0,comb);
    }
    private static BigInteger c = BigInteger.valueOf(0);
    private static void permuteHelper(int[] arr, int index, List<int[]> comb){
        if(index >= arr.length - 1){ //If we are at the last element - nothing left to permute
//            System.out.println(Arrays.toString(arr));
            comb.add(arr.clone());
            return;
        }

        for(int i = index; i < arr.length; i++){ //For each index in the sub array arr[index...end]

            //Swap the elements at indices index and i
            int t = arr[index];
            arr[index] = arr[i];
            arr[i] = t;

            //Recurse on the sub array arr[index+1...end]
            permuteHelper(arr, index+1,comb);

            //Swap the elements back
            t = arr[index];
            arr[index] = arr[i];
            arr[i] = t;
        }
    }

    public static List<int[]> getCombinations(int n){
        List<int[]> comb= new ArrayList<>();
        int[] arr = new int[n];
        for (int i = 0; i < arr.length; i++) {
            arr[i]=i;
        }
        getCombinations(arr, comb);
        return comb;
    }

}