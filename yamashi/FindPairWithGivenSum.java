
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;


public class FindPairWithGivenSum implements IInterviewQuestion
{
    public List<List<Integer>> findPairWithGivenSum(int[] nums, int target)
    {
        Map<Integer, List<Integer>> pos = new HashMap<>();
        List<List<Integer>> res = new ArrayList<>();
        int max = Integer.MIN_VALUE;

    
        for(int i=0;i<nums.length;i++) 
        { 
            List<Integer> ls = pos.get(target-nums[i]);

            if(ls!= null && ls.size() > 0)
            {
                    int idx1 = ls.get(0); // this one shall be smaller index.                    
                    int idx2 = i; // current one is always largest index since this index has not put into hash map yet.
                    int curMax = Math.max(nums[idx1],nums[idx2]);

                    ls.remove(0);
                    
                    if(curMax > max)
                    {
                        max = curMax;
                        res.clear();
                        res.add(Arrays.asList(idx1,idx2));
                    }

            }
            else
            {
                pos.put(nums[i], pos.getOrDefault(nums[i], new ArrayList<Integer>()) );
                pos.get(nums[i]).add(i);
            }
        }

        return res;
    }

    public void performTest()
    {
      Helper.equals(findPairWithGivenSum(new int[] {1, 10, 25, 35, 60 } , 90 - 30 ),
      new Integer[][] { {2, 3}}, "Find Pair ");
       
      Helper.equals(findPairWithGivenSum(new int[] {21,1,2,45,46,46} , 46 ),     
      new Integer[][] { {1, 3}}, "Find  Pairs ");

      Helper.equals(findPairWithGivenSum(new int[] {20, 50, 40, 25, 30, 10} , 90-30 ),     
      new Integer[][] { {1, 5}}, "Find  Pairs ");
      
    }

    public String toString() { return "Find Pair With Given Sum ([E,I]**) [https://leetcode.com/discuss/interview-question/356960]: ";}
}
