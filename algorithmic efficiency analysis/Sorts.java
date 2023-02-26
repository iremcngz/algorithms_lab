import java.util.Arrays;
import java.util.ArrayList;
public class Sorts {
    //Insertion Sort
    void InsertionSort(ArrayList<Integer> arr)
    {
        int n = arr.size();
        for (int j=1; j<n; ++j)
        {
            int key = arr.get(j);
            int i = j-1;

            while (i>=0 && arr.get(i) > key)
            {
                arr.set(i+1, arr.get(i));
                i = i-1;
            }
            arr.set(i+1,  key);
        }
    }


    //merge sort
    public void mergeSort(int startIndex, int endIndex,ArrayList <Integer> inputArray) {

        if (startIndex < endIndex && (endIndex - startIndex) >= 1) {
            int mid = startIndex + (endIndex - startIndex) / 2;
            mergeSort(startIndex, mid,inputArray);
            mergeSort(mid+1, endIndex,inputArray);
            merge(startIndex, endIndex,inputArray);
        }
    }

    //merge
    public void merge(int startIndex, int endIndex,ArrayList <Integer> inputArray) {
        ArrayList<Integer> mergedSortedArray = new ArrayList<Integer>();
        int midIndex = (endIndex + startIndex) / 2;
        int leftIndex = startIndex;
        int rightIndex = midIndex + 1;

        while (leftIndex <= midIndex && rightIndex <= endIndex) {
            if (inputArray.get(leftIndex) <= inputArray.get(rightIndex)) {
                mergedSortedArray.add(inputArray.get(leftIndex));
                leftIndex++;
            } else {
                mergedSortedArray.add(inputArray.get(rightIndex));
                rightIndex++;
            }
        }

        //Either of below while loop will execute
        while (leftIndex <= midIndex) {
            mergedSortedArray.add(inputArray.get(leftIndex));
            leftIndex++;
        }

        while (rightIndex <= endIndex) {
            mergedSortedArray.add(inputArray.get(rightIndex));
            rightIndex++;
        }

        int i = 0;
        int j = startIndex;
        //Setting sorted array to original one
        while (i < mergedSortedArray.size()) {
            inputArray.set(j, mergedSortedArray.get(i++));
            j++;
        }
    }


    //pigeonhole
    public void pigeonhole_sort(ArrayList<Integer> arr, int n)
    {
        int min = arr.get(0);
        int max = arr.get(0);
        int range, i, j, index;
        for(int a=0; a<n; a++)
        {
            if(arr.get(a) > max)
                max = arr.get(a);
            if(arr.get(a) < min)
                min = arr.get(a);
        }
        range = max - min + 1;
        int[] phole = new int[range];
        Arrays.fill(phole, 0);

        for(i = 0; i<n; i++)
            phole[arr.get(i) - min]++;
        index = 0;
        for(j = 0; j<range; j++)
            while(phole[j]-->0)
                arr.set(index++,j+min);
    }

    void counting_sort(ArrayList<Integer> nums) {
        int k=nums.get(0);
        for(int i=1;i<nums.size();i++){
            if(nums.get(i)>k)
                k=nums.get(i);
        }
        int s = nums.size(); //s = size of sorted array
        int sorted[] = new int[s]; //array to be sorted

        int count[] = new int[k+1];
        for (int i = 0; i < k+1; ++i) {
            count[i] = 0;
        }
        for (int i = 0; i < s; ++i) {
            ++count[nums.get(i)];
        }
        for (int i = 1; i <= k; ++i) {
            count[i] += count[i - 1];
        }
        //start sorting
        for (int i = s - 1; i >= 0; i--) {
            sorted[count[nums.get(i)] - 1] = nums.get(i);
            --count[nums.get(i)];
        }
        for (int i = 0; i < s; ++i) {
            nums.set(i, sorted[i]);
        }
    }



}
