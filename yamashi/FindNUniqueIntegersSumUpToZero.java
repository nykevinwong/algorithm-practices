import java.util.*;

class FindNUniqueIntegersSumUpToZero implements IInterviewQuestion {

    public int[] sumZero(int n) {
        
        int half = n/2;
        int[] arr = new int[n];
        for(int i=0;i< half;i++)
        {
            arr[i]=-(n-i);
            arr[n-i-1]= (n-i);
        }
                    
        return arr;
    }

    public void performTest()
    {
        for(int i=0;i<5;i++)
        {
            int n = (int)(Math.random()*20+1);
            int[] res = sumZero(n);
            
            int sum = Arrays.stream(res).reduce(0, (a,b) -> a + b);
            System.out.println("n:" + n  + ", " + Arrays.toString(res) + ", sum = 0 ? => " + (sum==0));            

        }

    }

    public String toString() { 
        return "Subarrays with K Different Integers (*) [https://leetcode.com/problems/subarrays-with-k-different-integers/]";
    }
}