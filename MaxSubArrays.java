/**
 * Class: Survey of Alg
 * Author: Avery Schreiner
 * Description: max sub array project
 * Due: 25 Feb 2022
 * I pledge that I have completed the programming assignment independently.
   I have not copied the code from a student or any source.
   I have not given my code to any other student.
   I have not given my code to any other student and will not share this code
   with anyone under any circumstances.
*/

import java.util.Random;

public class MaxSubArrayProject {
    public static void main(String[] args) 
    {
        //vars, and test arrays
        long start, end, time;
        int[] testArr = {-2,1,-3,4,-1,2,1,-5,4,-3}; //sum should be 6
        int[] testArr1 = {7,1,-3,4,-1,2,1,-5,4,-3,-1,4,-9,8,-9,1,-5,-4,3,1}; //sum should be 11
        
        //hand picked examples
        System.out.println("Hand Picked Examples:");
        System.out.println("Method, Array(Length): Result, Timing(ns)");
        
        //brute 1
        start = System.nanoTime();
        int result = brute(testArr);
        end = System.nanoTime();
        time = end - start;
        System.out.println("Brute, Array1(" + testArr.length + "): " + result + ", " + time);
        
        //kadane 1
        start = System.nanoTime();
        result = kadane(testArr);
        end = System.nanoTime();
        time = end - start;
        System.out.println("Kadane, Array1(" + testArr.length + "): " + result + ", " + time);
        
        //brute 2
        start = System.nanoTime();
        result = brute(testArr1);
        end = System.nanoTime();
        time = end - start;
        System.out.println("Brute, Array2(" + testArr1.length + "): " + result + ", " + time);
        
        //kadane 2
        start = System.nanoTime();
        result = kadane(testArr1);
        end = System.nanoTime();
        time = end - start;
        System.out.println("Kadane, Array2(" + testArr1.length + "): " + result + ", " + time);
        System.out.println();

        
        //testing arrays of various lengths
        int[] lengths = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000};
        long[] btimings = new long[lengths.length];
        long[] ktimings = new long[lengths.length];
        long bsum = 0;
        long ksum = 0;
        
        //for each length of array
        for (int i = 0; i < 10; i++)
        {
            //we will take the average of these 100 iterations
            for (int j = 0; j < 100; j++)
            {
                //fill array with random ints
                int[] arr = fill(lengths[i]);
            
                //test brute
                start = System.nanoTime();
                result = brute(arr);
                end = System.nanoTime();
                time = end - start;
                bsum += time;
            
                //test kadane
                start = System.nanoTime();
                int result1 = kadane(arr);
                end = System.nanoTime();
                time = end - start;
                ksum += time;
                
                //check to see if brute and kadane got the same result
                if (result != result1)
                {
                    System.out.println("Differing results for arrays of lengths " + lengths[i]);
                    System.out.println("on the " + j + " iteration.");
                    System.out.println("Brute got: " + result);
                    System.out.println("Kadane got: " + result1);
                }
            }
            
            //add average run time to the array
            btimings[i] = bsum / 100;
            ktimings[i] = ksum / 100;
            bsum = 0;
            ksum = 0;
        }
        
        //output timings
        System.out.print("Lengths:");
        for (int i = 0; i < 10; i++)
        {
            System.out.printf("%-12d ", lengths[i]);
        }
        System.out.println();
        
        System.out.print("Brute:  ");
        for (int i = 0; i < 10; i++)
        {
            System.out.printf("%-12d", btimings[i]);
        }
        System.out.println();
        
        System.out.print("Kadane: ");
        for (int i = 0; i < 10; i++)
        {
            System.out.printf("%-12d ", ktimings[i]);
        }
        System.out.println();
    }
    
    public static int brute(int[] vals)
    {
        //first set return var to val in index 0
        int maxSum = vals[0];
        
        //iterate over the whole list, "start" index value
        for (int i=0; i<vals.length; i++)
        {
            //"go through" index value
            for (int j=i; j<vals.length; j++)
            {
                //reset
                int total = 0;
                
                //iterate from i to j
                for (int k=i; k<j+1; k++)
                {
                    //add up sub array
                    total += vals[k];
                }
                
                //compare to current max, and change if necessary
                if (maxSum < total)
                {
                    maxSum = total;
                }
            }
        }
        return maxSum;
    }
    
    public static int kadane(int[] vals)
    {
        int best = 0;
        int current = 0;
        
        //run through vals
        for (int x : vals)
        {
            current = max(0, current + x);
            best = max(best, current);
        }
        return best;
    }
    
    //simple comparison method
    public static int max(int a, int b)
    {
        if (a > b)
            return a;
        return b;
    }
    
    //method that fills arrays with random ints into array of size n
    public static int[] fill(int n)
    {
        int[] arr = new int[n];
        Random rand = new Random();
        int x,y;
        
        //add 100 ints to the array (-9 through 9)
        for (int i=0; i<arr.length; i++)
        {
            //only single digits, no zeros
            x = rand.nextInt(9) + 1;
            
            //50/50ish chance of num being negative
            y = rand.nextInt(2);
            if (y == 1)
            {
                x *= -1;
            }
            
            //add to array
            arr[i] = x;
        }
        return arr;
    }
}
