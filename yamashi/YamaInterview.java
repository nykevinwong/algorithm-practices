
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeMap;
import java.util.SortedMap;
import java.util.Map.Entry;

import java.util.Iterator;





class GenerateParentheses implements IInterviewQuestion {
    
    public List<String> generateParentheses(int n) {
        List<String> res = new ArrayList<>();
        dfs("", n, n, res);   
        return res;
    }
    
    public void dfs(String s, int left, int right, List<String> res)
    {
        if(left > right) return;
        if(left <0 || right < 0) return;
        if(left==0 && right==0) res.add(s);
        
        dfs(s+"(", left-1, right, res);
        dfs(s+")", left, right-1, res);
    }

    public void performTest()
    {
        Helper.equals( generateParentheses(3), new String[] {"((()))","(()())","(())()","()(())","()()()"} , "Valid parentheses generation? ");
    }

   
    public String toString() { 
        return "Generate Parentheses ([I] *) [https://leetcode.com/problems/generate-parentheses/]";
    }
}

// https://github.com/nykevinwong/TreeAlgorithm/blob/master/TreeTraversal.cs
class TreeNode {
         int val;
         TreeNode left;
         TreeNode right;

         TreeNode() {  }
         TreeNode(int x) { val = x; }

         public static TreeNode createBinaryTreeFromArray(Integer[] arr)
         {
             return createBinaryTreeFromArray(arr, 0);
         }

         protected static TreeNode createBinaryTreeFromArray(Integer [] arr, int index)
         {
             if(arr.length > index)
             {
                 if(arr[index]==null) return null;

                 TreeNode node = new TreeNode(arr[index]);
                 node.left = createBinaryTreeFromArray(arr, 2*index+1);
                 node.right = createBinaryTreeFromArray(arr, 2*index+2);
                 return node;
             }
             return null;
         }
}

class SubtreeOfAnotherTree implements IInterviewQuestion
{
    public boolean isSubtree(TreeNode s, TreeNode t) {        
        return traverse(s, t);
    }
    
    public boolean traverse(TreeNode s, TreeNode t)
    {
        return s!=null && (
            sameStructure(s,t) ||
            traverse(s.left,t) ||
            traverse(s.right,t)
        );
    }
    
    public boolean sameStructure(TreeNode a, TreeNode b)
    {
        if(a==null && b ==null) // touch bottom of the tree
            return true;
        
        if(a!=null && b !=null &&
          a.val == b.val)
        {
            return sameStructure(a.left, b.left) && sameStructure(a.right, b.right);
        }   
        
        return false;
    }

    public void performTest()
    {
        TreeNode s = TreeNode.createBinaryTreeFromArray(new Integer[]{3,4,5,1,2});
        TreeNode t = TreeNode.createBinaryTreeFromArray(new Integer[]{4,1,2});
        System.out.println("is Subtree? " + traverse(s, t));
        TreeNode s0 = TreeNode.createBinaryTreeFromArray(new Integer[]{3,4,5,1,2,null,null,0});
        TreeNode t0= TreeNode.createBinaryTreeFromArray(new Integer[]{4,1,2});
        System.out.println("is Subtree? false = " + traverse(s0, t0));
        TreeNode s1 = TreeNode.createBinaryTreeFromArray(new Integer[]{1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,2});
        TreeNode t1 = TreeNode.createBinaryTreeFromArray(new Integer[]{1,null,1,null,1,null,1,null,1,null,1,2});
        System.out.println("is Subtree? " + traverse(s1, t1));
        TreeNode s2 = TreeNode.createBinaryTreeFromArray(new Integer[]{1,1});
        TreeNode t2 = TreeNode.createBinaryTreeFromArray(new Integer[]{1});
        System.out.println("is Subtree? " + traverse(s, t));
    }
   
    public String toString() { 
        return "Subtree of Another Tree ([I] *) [https://leetcode.com/problems/subtree-of-another-tree/]";
    }
}

class NaryTreeNode {
    int val;
    List<NaryTreeNode> nodes = new ArrayList<NaryTreeNode>();
    
    public NaryTreeNode(int val) { this.val = val; }
    public void addNode(NaryTreeNode node)
    {
        if(!nodes.contains(node)) nodes.add(node);
    }

    public void addNodes(int[] arr)
    {
        for(int val: arr) { nodes.add(new NaryTreeNode(val)); }
    }
}

class SubTreeWithMaximumAverage implements IInterviewQuestion
{
    class RefValue
    {
        public NaryTreeNode maxNode;
    }
    public NaryTreeNode getMaxAverageSubTree(NaryTreeNode root)
    {
        RefValue maxSubTree = new RefValue();
        float[] res = getSumAndAverage(root, maxSubTree);
        return maxSubTree.maxNode;
    }

    public float[] getSumAndAverage(NaryTreeNode root, RefValue maxSubTree)
    {
        if(root==null) return new float[]{0,0, 0};
 
        float sum = root.val;
        float maxAverage = 0;
        float childCount = 0;
        if(root.nodes.size() > 0)
        {
            for(NaryTreeNode child: root.nodes)
            {
                float[] res = getSumAndAverage(child, maxSubTree);     
                sum+= res[0];    
                childCount+=res[2];                      
                if(maxAverage < res[1])
                {                 
                    System.out.print(maxAverage +  " < " + res[1]); 
                    maxAverage = res[1];
                    maxSubTree.maxNode = child;
                    System.out.println(" [" + maxSubTree.maxNode.val +"]");
                }
            }

            float average = sum / (childCount+1);
            if(maxAverage < average)
            {                 
                System.out.print(maxAverage +  " < " + average); 
                maxAverage = average;
                maxSubTree.maxNode = root;
                System.out.println(" [" + maxSubTree.maxNode.val +"]");
            }
        }

        return new float[]{sum, maxAverage, childCount + 1};
    }

    public void performTest()
    {
        NaryTreeNode root = new NaryTreeNode(20);
        NaryTreeNode left = new NaryTreeNode(12);
        NaryTreeNode right = new NaryTreeNode(18);
        left.addNodes(new int[]{11,2,3});
        right.addNodes(new int[]{15,8});
        root.addNode(left);
        root.addNode(right);

        NaryTreeNode maxSubTree = getMaxAverageSubTree(root);
        System.out.println("18 = " + maxSubTree.val  );
    }

    public String toString() { 
        return "SubTree With Maximum Average [https://leetcode.com/discuss/interview-question/349617]";
    }

}



class MinimumCostToConnectRope implements IInterviewQuestion
{
    public int minCostToConnectRope(int[] ropes)
    {
        int res = 0;
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for(int len : ropes) { minHeap.add(len); }

        while(minHeap.size() > 1)
        {
            int combinedLen = minHeap.poll() + minHeap.poll();
            res += combinedLen;
            minHeap.offer(combinedLen);
        }

        return res;
    }

    public void performTest()
    {
        Helper.equals(minCostToConnectRope(new int[]{2, 4, 3}) , 14, "[2,4,3]: ");
        Helper.equals(minCostToConnectRope(new int[]{8, 4, 6, 12}) , 58, "[8,4,6,12]: ");
        Helper.equals(minCostToConnectRope(new int[]{2, 2, 3, 3}) , 20, "[2, 2, 3, 3]: ");
        Helper.equals(minCostToConnectRope(new int[]{1, 2, 5, 10, 35, 89}) , 224, "[1, 2, 5, 10, 35, 89]: ");

    }

    public String toString() { 
        return "Minimum Time to merge files/Minimum Cost to connect Ropes ([E]) [https://leetcode.com/discuss/interview-question/344677]";
    }
}


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



class MergeIntervals implements IInterviewQuestion
{
    public int[][] merge(int[][] intervals) {
		if (intervals== null || intervals.length <= 1) return intervals;

		// Sort by ascending starting point
		Arrays.sort(intervals, (i,j) -> i[0]==j[0] ? i[1]-j[1]:i[0]-j[0]);

		LinkedList<int[]> llRes = new LinkedList<>();
		llRes.add(intervals[0]);
        
		for (int i=1;i < intervals.length;i++) {
            int[] merged = llRes.getLast();                        
			if (intervals[i][0] <= merged[1]) // Overlapping intervals
            {
                llRes.removeLast();
				merged[1] = Math.max(merged[1], intervals[i][1]);
                llRes.addLast(merged);
            }
			else { // disjoint intervals                          
				llRes.add(intervals[i]);
			}
		}

		return llRes.toArray(new int[llRes.size()][]);
    }

    public void performTest()
    {
        int[][] res = merge(new int[][] { {1,3},{2,6},{8,10},{15,18} });
        Helper.arrayEquals(res, new int[][] { {1,6}, {8,10}, {15,18} } );
    }

    public String toString() { 
        return "Merge Intervals (*) [https://leetcode.com/problems/merge-intervals/]";
    }
}



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

class NthGeometricProgression implements IInterviewQuestion  {

    public char[] getNthGP(double secondTerm, double thirdTerm, int nth)
    {
        double r = thirdTerm/ secondTerm;
        double a = secondTerm/r;
        double result = a*Math.pow(r, nth-1);
        String str = String.valueOf(result);
        int pos = str.indexOf('.');

        if(pos > 0) // trim up to 
        {
           int decimalPlaces = 3;
           str = str.substring(0, Math.min(pos+decimalPlaces+1, str.length())); 
        }

    //    System.out.println("res => [" + str + "]");
        return str.toCharArray();
    }

    public void performTest()
    {
        Helper.equals(String.valueOf( getNthGP(1,2,4) ), "4.0", "seoncd Term:1, thrid Term: 2, Find 4th Term = ? ");
    }

    public String toString() { 
        return "Nth Geometric Progression ([I]*) [https://leetcode.com/discuss/interview-question/432213/]";
    }
}

class LongestPlaindromicSubstring implements IInterviewQuestion {

    public String longestPalindrome(String s) {
        if(s==null || s.length() < 2) return s;
        
        int left = 0, right = 0;
        int len = s.length();
        boolean[][] isPalindrome = new boolean[len][len];        
        // P(i,j) answers the question 'is the substring from index i to index j  is a paindrome?'.
        
        // j starts from 1. must compare at least two chars. one char from j, another char from i.
        
        for(int j=1; j < s.length();j++) 
            for(int i=0; i < j ; i++)
            {
                //j-i <=2 means current length doesnt have an inner string to check.
                boolean isInnerPalindrome = isPalindrome[i+1][j-1] || j-i <=2;
                
                if(isInnerPalindrome && s.charAt(i)==s.charAt(j) )
                {
                     isPalindrome[i][j] = true;
                    if(j-i > right-left) // update current max length
                    {
                        right = j;
                        left = i;
                    }
                }
            }
        return s.substring(left,right+1);
    }

    public String longestPalindrome_ExpandFromCenter(String s) {
        String max = "";
        
        for(int i=0;i< s.length();i++)
        {
            String s1 = expandFromCenter(s, i, i);
            String s2 = expandFromCenter(s, i, i+1);
            String curMax = (s1.length() > s2.length()) ? s1:s2;
            max = (curMax.length() > max.length()) ? curMax:max;            
        }
        
        return max;
    }
    
    public String expandFromCenter(String s, int l, int r)
    {
        while(l>=0 && r < s.length() && s.charAt(l) == s.charAt(r))
        { 
            l--; r++; 
        }
        
        int len = ((r-1)-(l+1));        
        return s.substring(l+1, r);
    }

    public void performTest()
    {
        Helper.equals(longestPalindrome("babad"),  "bab", " longest plaindrome ?" );
        Helper.equals(longestPalindrome("cbbd"),  "bab", " longest plaindrome ?" );

        Helper.equals(longestPalindrome_ExpandFromCenter("babad"),  "bab", "(EXPAND from Center) longest plaindrome ?" );
        Helper.equals(longestPalindrome_ExpandFromCenter("cbbd"),  "bab", "(EXPAND from Center)  longest plaindrome ?" );
    }

    public String toString() { 
        return "Longest Palindromic Substring ([I]**) [https://leetcode.com/problems/longest-palindromic-substring/]";
    }
}

class UF {
    private int[] parent;
    private int[] rank;
    private int count;

    public UF(int N)
    {
        count = N;
        parent = new int[N];
        rank = new int[N];
        for(int i=0;i<N;i++)
        {
            parent[i]=i;
            rank[i]=0;
        }
    }

    public int find(int x)
    {
        while(x!=parent[x])
        {
            parent[x] = parent[parent[x]]; // path compresion
            x = parent[x];
        }

        return x;
    }

    // union by rank
    public void union(int x, int y)
    {
        int rootX = find(x);
        int rootY = find(y);
        if(rootX==rootY) return;
        if(rank[rootX] < rank[rootY])
        {
            parent[rootX] = rootY;
        }
        else
        {
            parent[rootY] = rootX;
            if(rank[rootX] == rank[rootY]) rank[rootX]++;            
        }
        count--;
    }

    public int count() { return count; }
    public boolean connected(int x, int y) { return find(x)==find(y); }
}

class MinCostToConnectAllNodesOrRepairEdges implements IInterviewQuestion {

    public static int minCost(int n, int[][] edges, int[][] newEdges) {
        UF uf = new UF(n + 1); // + 1 because nodes are 1-based
        
        Queue<int[]> pq = new PriorityQueue<>(newEdges.length, (e1, e2) -> Integer.compare(e1[2], e2[2]));
        HashSet<String> added = new HashSet<>();
        
        for (int[] edge : newEdges) {
            pq.offer(edge);
            added.add(edge[0]+","+edge[1]);
        }
                
        for (int[] edge : edges) {
            if(!added.contains(edge[0]+","+edge[1])) // ensure the same broken edge is not added.
            {
                pq.offer(new int[]{edge[0],edge[1],0});
            }
        }
        
        int totalCost = 0;
        // 2 because nodes are 1-based and we have 1 unused component at index 0
        while (!pq.isEmpty() && uf.count() != 2) {
            int[] edge = pq.poll();
            if (!uf.connected(edge[0], edge[1])) {
                uf.union(edge[0], edge[1]);
                totalCost += edge[2];
            }
        }
        return totalCost;
    }

    public void performTest()
    {
        // below tests are for new edges, new roads and etc. new edge must be edges never in MST before.
        int n = 6;
        int[][] edges = {{1, 4}, {4, 5}, {2, 3}};
        int[][] newEdges = {{1, 2, 5}, {1, 3, 10}, {1, 6, 2}, {5, 6, 5}};
        System.out.println(minCost(n, edges, newEdges));
        Helper.equals( minCost(n, edges, newEdges), 7 , "new edges ->" );

        // below tests are for broken edges. broken edges must be edges already in MST
        n = 5;
        edges = new int[][] {{1, 2}, {2, 3}, {3, 4}, {4, 5}, {1, 5}};
        int[][] brokenEdges = new int[][] {{1, 2, 12}, {3, 4, 30}, {1, 5, 8}};
        Helper.equals( minCost(n, edges, brokenEdges), 20 , "broekn edges ->" );

        n = 6;
        edges = new int[][] {{1, 2}, {2, 3}, {4, 5}, {3, 5}, {1, 6}, {2, 4}};
        brokenEdges = new int[][] {{1, 6, 410}, {2, 4, 800}};
        Helper.equals( minCost(n, edges, brokenEdges), 410 , "broekn edges ->" );

        n = 6;
        edges = new int[][] { {1, 2}, {2, 3}, {4, 5}, {5, 6}, {1, 5}, {2, 4}, {3, 4} };
        brokenEdges = new int[][] { {1, 5, 110}, {2, 4, 84}, {3, 4, 79} };
        Helper.equals( minCost(n, edges, brokenEdges), 79 , "broekn edges ->" );

/*
Example 2:

Input: n = 6, edges = [[1, 2], [2, 3], [4, 5], [3, 5], [1, 6], [2, 4]], edgesToRepair = [[1, 6, 410], [2, 4, 800]]
Output: 410
Example 3:

Input: n = 6, edges = [[1, 2], [2, 3], [4, 5], [5, 6], [1, 5], [2, 4], [3, 4]], edgesToRepair = [[1, 5, 110], [2, 4, 84], [3, 4, 79]]
Output: 79
*/
        
    }

    public String toString() { 
        return "Min Cost to Connect All Nodes (a.k.a. Min Cost to Add New Roads) [https://leetcode.com/discuss/interview-question/356981]";
    }
}

public class YamaInterview
{
    public static void main(String[] args)
    {
        IInterviewQuestion[] questions = new IInterviewQuestion[] {
            new JavaCollections(),
            new BinarySearch(),
            new TopKFrequentlyMentionedKeywords(),
            new RottinOranges(), // Zombin in Matrix
            new CriticalRoutersOrConnections(),
            new SearchSuggestionSystem(), // Product Suggestions
            new NumberOfClusters() , 
            new ReorderDataInLogFile(),
            new PartitionLabel(),
            new OptimalUtilization(), 
            new MinimumCostToConnectRope(),
            new TreasureIsland(),
            new TreasureIsland2(),
            new FindPairWithGivenSum(),
            new CopyRandomLinkedList(),
            new MergeTwoSortedList(),
            new SubtreeOfAnotherTree(),
            new SearchMatrix(), // CORRECT 
            new FavoriteGenres(),
            new FindUniquePairsWithGivenSum(),
            new SubstringsOfExactlyKDistinctChars(), // MAYBE INCORRECT
            new SubarraysWithKDifferentIntegers(),
            new LongestStringWithThreeConsecutiveCharacters(), // longest numbers with K consecutive numbers. (MAYBE INCORRECT?)
            // Max of Min Altitudes
            new LongestPlaindromicSubstring(),
            new SubstringsOfSizeKwithKDistinctChars(),
            new MostCommonWord(),
            new KClosetPointstoOrigin(),
            new GenerateParentheses(),
            // below class can resolve two problems :(1) Min Cost to connect all nodes  (2) Min Cost to Repait Edges
            new MinCostToConnectAllNodesOrRepairEdges(),            
            new PrisonCellsAfterNDays(),
            new SubTreeWithMaximumAverage(),
            // Other
            new MergeIntervals(),

            new FindNUniqueIntegersSumUpToZero(),
            new NthGeometricProgression()
        }; 

        int count = 1;
        int technique = 1;
        for(IInterviewQuestion q: questions) { 
            if(q instanceof IImportTechnique)
            {
                System.out.println("*** TECHNIQUE " + technique + " *** " + q);
                q.performTest(); technique++;
                System.out.println("\n---------------------------\n");
            }
            else if(q instanceof IInterviewQuestion)
            {
                System.out.println("(" + count + ") " + q);
                q.performTest(); count++;
                System.out.println("\n---------------------------\n");
            }
        }
        for(IInterviewQuestion q: questions) { 
            if(q instanceof OptimalUtilization)
            {
                System.out.println("\n**********************************\n");
                q.performTest(); count=99999;
                System.out.println("\n**********************************\n");

            }
        }

    }
}
