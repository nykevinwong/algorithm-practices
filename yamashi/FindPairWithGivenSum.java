
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
        int leftMax = Integer.MIN_VALUE;
        int rightMax = Integer.MIN_VALUE;

    
        for(int i=0;i<nums.length;i++) 
        { 
            List<Integer> ls = pos.get(target-nums[i]);

            if(ls!= null && ls.size() > 0)
            {
                    int left = ls.get(0) > i ? i: ls.get(0);
                    int right = ls.get(0) > i ? ls.get(0):i;
                    if(left >= leftMax) //when left =leftMax, right must be equal to rightMax.
                    {
                        leftMax = left;
                        rightMax = right;
                        res.clear();
                        res.add(Arrays.asList(leftMax, rightMax));
                    }
                    ls.remove(0);
            }
            else
            {
                pos.put(nums[i], pos.getOrDefault(nums[i], new ArrayList<Integer>()) );
                pos.get(nums[i]).add(i);
            }
        }
        

    //    Collections.sort(res, (arr1, arr2) -> ( arr1[0]==arr2[0] ? arr2[1]-arr1[1] : arr2[0]-arr1[0] )   );


        return res;
    }

    public void performTest()
    {
      Helper.equals(findPairWithGivenSum(new int[] {1, 10, 25, 35, 60 } , 90 - 30 ),
      new Integer[][] { {2, 3}}, "Find Pair ");
       
      Helper.equals(findPairWithGivenSum(new int[] {21,1,2,45,46,46} , 46 ),
      new Integer[][] { {1, 3}}, "Find  Pairs ");

    }

    public String toString() { return "Find Pair With Given Sum ([E,I]**) [https://leetcode.com/discuss/interview-question/356960]: ";}
}
