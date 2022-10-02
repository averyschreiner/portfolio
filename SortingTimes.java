/**
 * Class: 44-242 Data Structures
 * Author: Avery Schreiner
 * Description: lab 10 timing sorting
 * Due: 2 Dec 2021
 * I pledge that I have completed the programming assignment independently.
   I have not copied the code from a student or any source.
   I have not given my code to any other student.
   I have not given my code to any other student and will not share this code
   with anyone under any circumstances.
*/
package sort.timing;

import java.util.ArrayList;

/**
 *
 * @author nathan
 */
public class Main {

     private static void swapInds(ArrayList<Integer> L, int i, int j)
    {
        Integer t = L.get(i);
        L.set(i, L.get(j));
        L.set(j, t);
    }

    public static ArrayList<Integer> insertionSort(ArrayList<Integer> L)
    {   
        for (int i = 0; i < L.size(); i++)
        {
            int j = i;
            
            while (j > 0 && L.get(j-1) > L.get(j))
            {
                swapInds(L, j, j-1);
                j--;
            }
        }
        return L;
    }

    public static ArrayList<Integer> selectionSort(ArrayList<Integer> L)
    {
        for (int i = 0; i < L.size(); i++)
        {
            //get the index of the smallest number
            int smallest = i;
            
            for (int j = i; j < L.size(); j++)
            {
                if (L.get(j) < L.get(smallest))
                {
                    smallest = j;
                }
            }
            
            //swap elts at idx and i
            swapInds(L, smallest, i);
        }
        return L;
    }

    public static ArrayList<Integer> mergeSort(ArrayList<Integer> L)
    {
        //base case
        //if size of list is 0 or 1, must be sorted, just return list
        if (L.size() < 2)
        {
            return L;
        }
        
        //break L into two "equal" parts
        int x = L.size()/2;
        ArrayList<Integer> firstHalf = new ArrayList<>();
        ArrayList<Integer> lastHalf = new ArrayList<>();
        
        //add elts to first half
        for (int i = 0; i < x; i++)
        {
            firstHalf.add(L.get(i));
        }
        
        //add elts to last half
        for (int i = x; i < L.size(); i++)
        {
            lastHalf.add(L.get(i));
        }
        
        //recurse until lists are of size 1 or 0 (meaning they are sorted)
        ArrayList<Integer> A = mergeSort(firstHalf);
        ArrayList<Integer> B = mergeSort(lastHalf);
        
        //send to merge method to concatenate the two sorted lists
        return merge(A, B);
        
    }

    private static ArrayList<Integer> merge(ArrayList<Integer> A, ArrayList<Integer> B)
    {
        //create index trackers for each list
        int iA = 0;
        int iB = 0;
        
        //establish a new array to return
        ArrayList<Integer> C = new ArrayList<>();
        
        //run through both lists and add the smallest elt each time
        while (iA < A.size() && iB < B.size())
        {
            if (A.get(iA) < B.get(iB))
            {
                C.add(A.get(iA));
                iA++;
            }
            else
            {
                C.add(B.get(iB));
                iB++;
            }
        }
        
        //above loop will break if one list is finished, meaning we can
        //just add the rest of the other list since it is already sorted
        while (iA < A.size())
        {
            C.add(A.get(iA));
            iA++;
        }
        
        while (iB < B.size())
        {
            C.add(B.get(iB));
            iB++;
        }
        
        //return the concatenated list
        return C;
    }

    public static ArrayList<Integer> randoList(int size)
    {
        ArrayList<Integer> C = new ArrayList<>();
        for (int i=0; i<size; i++)
            C.add((int)(Math.random() * size * 2));
        return C;
    }

    public static double timeInsertion(int size)
    {
        double avg = 0;
        for (int i=0; i<30; i++)
        {
            ArrayList<Integer> l = randoList(size);
            long start = System.nanoTime();
            insertionSort(l);
            long end = System.nanoTime();
            avg += (end - start) / 1000000000.0;
        }
        return avg;
    }

    public static double timeSelection(int size)
    {
        double avg = 0;
        for (int i=0; i<30; i++)
        {
            ArrayList<Integer> l = randoList(size);
            long start = System.nanoTime();
            selectionSort(l);
            long end = System.nanoTime();
            avg += (end - start) / 1000000000.0;
        }
        return avg;
    }

    public static double timeMerge(int size)
    {
        double avg = 0;
        for (int i=0; i<30; i++)
        {
            ArrayList<Integer> l = randoList(size);
            long start = System.nanoTime();
            mergeSort(l);
            long end = System.nanoTime();
            avg += (end - start) / 1000000000.0;
        }
        return avg;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        int sizes[] = {1, 50, 100, 500, 1000, 5000};
        System.out.println("\tInsertion\tSelection\tMerge");
        for (int size:sizes)
        {
            System.out.println(size + "\t" + timeInsertion(size)
                    + "\t" + timeSelection(size)
                    + "\t" + timeMerge(size));
        }
    }
    
}
