class SubarraysWithKDifferentIntegers implements IInterviewQuestion
{
    //Longest SubString With K Distinct Characters

    
    public int subarraysWithKDistinct(int[] A, int K) {        
        Window<Integer> w1 = new Window<Integer>();
        Window<Integer> w2 = new Window<Integer>();
        int left1=0, left2 =0;
        int count = 0;
        
        for(int i=0;i < A.length;++i)
        {
            int x = A[i];
            w1.add(x);
            w2.add(x);
            
            while(w1.kinds() > K )
                w1.remove(A[left1++]);
            
            while(w2.kinds() >= K )
                w2.remove(A[left2++]);
            
            count+= left2-left1;            
        }
        
        return count;
    }

    public void performTest()
    {
        Helper.equals( subarraysWithKDistinct(new int[]{1,2,1,2,3}, 2) , 7, "{1,2,1,2,3} = ");
        Helper.equals( subarraysWithKDistinct(new int[]{1,2,1,3,4}, 3) , 3, "{1,2,1,3,4} = ");

    }

    public String toString() { 
        return "Subarrays with K Different Integers (*) [https://leetcode.com/problems/subarrays-with-k-different-integers/]";
    }

 }
