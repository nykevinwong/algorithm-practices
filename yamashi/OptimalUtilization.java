import java.util.*;

class OptimalUtilization implements IInterviewQuestion
{ // quite correct result.

    public int binarySearch(int[][] sorted, int target, boolean lowerBound)
    {
        int l = 0;
        int r = sorted.length -1;
        int mid = 0;
        int lastKeyPos = -1;
        while(l <= r)
        {
            mid = (l+r)/2;
            int x = sorted[mid][1];

            if(target < x) r= mid-1;
            else if(target > x) l= mid +1;
            else
            {
                lastKeyPos = mid;
                if(lowerBound) r= mid-1;
                else l = mid +1;                
            }
        }

        return (lastKeyPos==-1) ? -(l+1): lastKeyPos;
    }

    // get target sum or closet sum answer from two non-sorted array
    // if no target answer, we only pick the closet sum answer. 
    // for example, target = 20. if next sum answer is 19,18, we only pick all answers of 19.
    // better than Brute Force. 
    public List<List<Integer>> getOptimalUtilization(int[][] a, int[][] b, int target)
    {   
        Arrays.sort(a, (i,j) -> i[1]-j[1]);
        Arrays.sort(b, (i,j) -> i[1]-j[1]);

        int l = 0;
        int r = b.length-1;
        int minCloset = Integer.MAX_VALUE;
        List<List<Integer>> res = new ArrayList<>();

        while(l < a.length && r >= 0)
        {
            int sum = a[l][1] + b[r][1];
            int diff = sum - target ;

            if(sum > target ) r--;
            else {
                
                if(diff<= 0)
                {
                    diff = Math.abs(diff);
                    if(minCloset > diff)
                    {
                        minCloset = diff;
                        res.clear(); 
                        res.add(Arrays.asList( a[l][0], b[r][0]));                  
                    }
                    else if(minCloset == diff)
                    {
                        res.add(Arrays.asList( a[l][0], b[r][0]));                  
                    }
                }

                
                l++;
            }

        }

        return res;
    }

    // binary search approach
    public List<List<Integer>> binarySearch_getOptimalUtilization(int[][] a, int[][] b, int target)
    {   
        Arrays.sort(a, (i,j) -> i[1]-j[1] ); 
        Arrays.sort(b, (i,j) -> i[1]-j[1] ); 
        List<int[]> candidates = new ArrayList<>();
        List<List<Integer>> res = new ArrayList<>();
        int k =  b.length-1;
        
        while(k  >= 0) 
        {
            int complement = target - b[k][1];
            int index = binarySearch(a, complement, true); 
           // System.out.println("found at " + index + " for key = " + complement );
           if(index < 0) { 
            index = -index -1; // index of the least integer greater than key
            index = index -1; // index of the greatest integer less than key
           }

            while(index >= 0 && index < a.length) 
            {
            int diff = target - (a[index][1]+ b[k][1]);
            candidates.add(new int[]{a[index][0], b[k][0], diff }  );
            index--;
            }
           
            k--;
        }

        Collections.sort(candidates, (i , j) ->  i[2]-j[2]  );

        Integer[] lastDiff = { null};

        candidates.forEach( p -> {            
            if(lastDiff[0]==null || lastDiff[0]==p[2])
            {
  //              System.out.println("(" + Arrays.toString(p) + ")");
                res.add(Arrays.asList(p[0],p[1]) );
                lastDiff[0] = p[2];
            }
        });

        return res;
    }
    

    // same as binarsearch. TreeMap can be used for two dimension array source
    public List<List<Integer>> TreeMap_getOptimalUtilization(int[][] a, int[][] b, int target)
    {   
        TreeMap<Integer,Integer> pos = new TreeMap<>();
        List<int[]> candidates = new ArrayList<>();
        List<List<Integer>> res = new ArrayList<>();

        for(int i=0; i < a.length;i++)
        {
            pos.put(a[i][1],a[i][0]);
        }

        Arrays.sort(b, (i,j) -> i[1]-j[1] ); 

        int k = b.length -1;
        while(k>0)
        {
            int complement = target-b[k][1];
            Map<Integer,Integer> candidate = pos.headMap(complement, true);

            for(Map.Entry<Integer,Integer> e : candidate.entrySet())
            {
                int index = e.getValue();
                int value = e.getKey();
                int diff = target - (value+ b[k][1]);
                candidates.add(new int[]{ index , b[k][0], diff});
            }
            
            k--;
        }

        Collections.sort(candidates, (i , j) ->  i[2]-j[2]  );

        Integer[] lastDiff = { null};

        candidates.forEach( p -> {            
            if(lastDiff[0]==null || lastDiff[0]==p[2])
            {
//                System.out.println("(" + Arrays.toString(p) + ")");
                res.add(Arrays.asList(p[0],p[1]) );
                lastDiff[0] = p[2];
            }
        });

        return res;
    }

    public void performTest()
    {
        Helper.equalsTo(getOptimalUtilization(new int[][] { {1,2}, {2,4},{3,6}}, new int[][]{{1,2}}, 7 ), new Integer[][] {{2,1}});
        Helper.equalsTo(getOptimalUtilization(new int[][] { {1,3}, {2,5},{3,7},{4,10}}, new int[][]{{1,2},{2,3},{3,4},{4,5}}, 10 ), new Integer[][] {{2,4},{3,2}});
        Helper.equalsTo(getOptimalUtilization(new int[][] { {1,8}, {2,7},{3,14}}, new int[][]{{1,5},{2,10},{3,14}}, 20 ), new Integer[][] {{3,1}} );
        Helper.equalsTo(getOptimalUtilization(new int[][] { {1,8}, {2,15},{3,9}}, new int[][]{{1,8},{2,11},{3,12}}, 20 ), new Integer[][] {{1,3}, {3,2}});
        Helper.equalsTo(getOptimalUtilization(new int[][] { {1,0}, {2,0},{3,0}}, new int[][]{{1,0}}, 7 ), new Integer[][] {} );

        System.out.println("$$$$$$$$$$$ Binary search approach:"); 
        Helper.equalsTo(binarySearch_getOptimalUtilization(new int[][] { {1,2}, {2,4},{3,6}}, new int[][]{{1,2}}, 7 ), new Integer[][] {{2,1}});
        Helper.equalsTo(binarySearch_getOptimalUtilization(new int[][] { {1,3}, {2,5},{3,7},{4,10}}, new int[][]{{1,2},{2,3},{3,4},{4,5}}, 10 ), new Integer[][] {{2,4},{3,2}});
        Helper.equalsTo(binarySearch_getOptimalUtilization(new int[][] { {1,8}, {2,7},{3,14}}, new int[][]{{1,5},{2,10},{3,14}}, 20 ), new Integer[][] {{3,1}} );
        Helper.equalsTo(binarySearch_getOptimalUtilization(new int[][] { {1,8}, {2,15},{3,9}}, new int[][]{{1,8},{2,11},{3,12}}, 20 ), new Integer[][] {{1,3}, {3,2}});
        Helper.equalsTo(binarySearch_getOptimalUtilization(new int[][] { {1,0}, {2,0},{3,0}}, new int[][]{{1,0}}, 7 ), new Integer[][] {} );
    }

    public String toString() { 
        return "Optimal Utilization ([E]) [https://leetcode.com/discuss/interview-question/344677]";
    }
}