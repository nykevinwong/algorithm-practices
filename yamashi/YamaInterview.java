
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

class SubstringsOfSizeKwithKDistinctChars implements IInterviewQuestion
{
    public List<String> substringsOfSizeKwithKDistinctChars(String s, int k)
    {
        List<String> res = new ArrayList<>();
        Set<String> set = new HashSet<>();
        int[] m = new int[256];

        for(int i=0, j = 0;i < s.length() && j < s.length(); )
        {
            if(m[s.charAt(i)]==0)
            {
                m[s.charAt(i++)]++;

            }
            else 
            {
                m[s.charAt(j++)]--;
            }
            if(i-j==k) { 
                String sub = s.substring(j,i);
                // use both hashSet and list to remove duplicate and maintain the original order 
                if(!set.contains(sub)) { res.add(sub); }
                set.add(sub);
                m[s.charAt(j++)]--;
            }
            

        }
        return res;
    }

    public void performTest()
    {
        Helper.equals( substringsOfSizeKwithKDistinctChars("awaglknagawunagwkwagl",4), 
        new String[] {"wagl", "aglk", "glkn", "lkna", "knag", "gawu", "awun", "wuna", "unag", "nagw", "agwk", "kwag"}, "SubString");
    }
    
    public String toString() { return "Substrings Of Size K with K Distinct Chars ([I]**) [https://leetcode.com/discuss/interview-question/370112]: ";}
}


class SubstringsOfExactlyKDistinctChars implements IInterviewQuestion
{

    public int substringswithExactlyKDistinctChars(String s, int K)
    {
        Window<Character> w1 = new Window<Character>();
        Window<Character> w2 = new Window<Character>();
        int count = 0, left1=0, left2 = 0;
        for(int i=0;i<s.length();i++)
        {
            Character c = s.charAt(i);
            w1.add(c);
            w2.add(c);
            while(w1.kinds() > K) w1.remove(s.charAt(left1++));
            while(w2.kinds() >= K) w2.remove(s.charAt(left2++));
            count += left2-left1;
        }
        return count;
    }

    public void performTest()
    {
        Helper.equals( substringswithExactlyKDistinctChars("pqpqs",2), 7, "pqpqs has 7 number of substring stasified the ans. ");
//        new String[] {"pq", "pqp", "pqpq", "qp", "qpq", "pq", "qs"}, "SubString ");
    }
    
    public String toString() { return "Substrings with exactly K Distinct Chars ([I]**) [https://leetcode.com/discuss/interview-question/370157  https://leetcode.com/problems/subarrays-with-k-different-integers/]: ";}
}

class TreasureIsland implements IInterviewQuestion
{
    public List<int[]> minimumRoute(char[][] maze)
    {
        Queue<int[]> q = new LinkedList<>();
        int count = 0;
        int[][] dir = new int[][] { {0,1},{1,0},{ 0,-1},{-1,0}};
        q.offer(new int[]{0,0});
        maze[0][0]= (char)count++;

        while(q.size() > 0)
        {
            int size = q.size();
            for(int i=0;i<size;i++)
            {
                int[] pos = q.poll();
               // System.out.println(Arrays.toString(pos));
                for(int j=0;j<dir.length;j++)
                {
                    int nx = pos[0] + dir[j][0];
                    int ny = pos[1] + dir[j][1];
                    if(nx < 0 || nx >= maze[0].length || ny < 0 || ny >= maze.length) continue;
                    if(maze[ny][nx]=='O') // only O is walkable
                    {
                        q.offer(new int[] {nx,ny});
                        maze[ny][nx]= (char)count;
                    }
                    else if(maze[ny][nx]=='X')
                    { // found the target
                        System.out.println("Steps: " + (count));
                        List<int[]> res = createSolution(maze, pos[0], pos[1]);  
                        return res;
                    }
                }

            }
            count++;
        }

        return null;        
    }

    public List<int[]> createSolution(char[][] maze, int lastX, int lastY)
    {
        List<int[]> res = new ArrayList<>();
        Stack<int[]> s = new Stack<>();
        System.out.println("Creating the solution.");
        s.push(new int[] {lastX,lastY} );
        int count = (int)(maze[lastY][lastX]);
        count--;
        
        for(int i=0;i<maze.length;i++)
        {
            for(int j=0;j<maze[0].length;j++)
            {
                System.out.print(((char)(maze[i][j]+'0')) + "|");
            }
            System.out.println();
        }

        while(count >= 0)
        {
            int[][] dir = new int[][] { {0,1},{1,0},{ 0,-1},{-1,0}};
            for(int i=0;i<dir.length;i++)
            {
                int nx = lastX + dir[i][0];
                int ny = lastY + dir[i][1];
                if(nx < 0 || nx >= maze[0].length || ny < 0 || ny >= maze.length) continue;
                if((int)maze[ny][nx]==count) 
                {
                    s.push(new int[] {nx, ny});
                    lastX = nx; lastY = ny;
                    count--;
                    break;
                }
            }
        }

        while(!s.isEmpty()) { res.add(s.pop()); };
        
        return res;
    }

    public void performTest()
    {
        List<int[]> res = minimumRoute(new char[][] {
            {'O','O','O','O'},
            {'D','O','D','O'},
            {'O','O','O','O'},
            {'X','D','D','O'},
        });

        for(int[] pos: res)
        {
            System.out.print("(" + pos[1] + "," + pos[0] + ") ,");
        }
        System.out.println();
    }
    
    public String toString() { return "Treasure Island ([I]**) [https://leetcode.com/discuss/interview-question/347457]: ";}

}

class Node {
    public int val;
    public Node next;
    public Node random;

    public Node(int x) { val = x;} 

    public Node(int x, Node random) { val = x; this.random = random; } 

    public Node(int[] arr)
    {
        this(arr, new int[] {});
    }

    public Node(int[] arr, int[] randomPos)
    {
        Node cur = this;   
        HashMap<Integer,Node> m = new HashMap<>();

        for(int i=0;i<arr.length;i++)
        {
            if(i==0)
            {
                cur.val = arr[i];
            }
            else
            {
                cur.next = new Node(arr[i], null);
                cur = cur.next;
            }

            m.put(i, cur);
        }

        
        for(int j=0;j < randomPos.length;j++)
        {
            Node node =  m.get(j);
            if(randomPos[j]!=0)
                node.random = m.get(randomPos[j]);
        }
    }

    public boolean equalList(Node other) { 
        Node cur=this;

        for(; cur!=null && other !=null ; cur=cur.next, other=other.next)
        {
           // System.out.print("["+ cur.val + "," + other.val + "](  ");
           // System.out.print( cur.random + "," + other.random + " )  |");

            if(cur.val != other.val) return false;

            if(cur.random== null && other.random== null ) continue;

            if(cur.random!= null && other.random!= null && 
            cur.random.val != other.random.val) return false;

            if(cur.random== null || other.random== null ) return false;

        }

      
        return (cur==null && other==null); 
    }
}



class MergeTwoSortedList implements IInterviewQuestion
{
    public Node mergeTwoLists(Node l1, Node l2) {
        Node dummy = new Node(0);
        Node temp = dummy;
        
        while(l1 !=null && l2 != null)
        {
            Node smaller = (l1.val < l2.val) ? l1:l2;
            temp.next = smaller;             
            temp = temp.next;
            
            if(l1.val < l2.val) l1=l1.next;
            else l2 = l2.next;
        }
        
        while(l1!=null)
        {
            temp.next = l1;
            temp = temp.next;
            l1 = l1.next;
        }
        
        while(l2!=null)
        {
            temp.next = l2;
            temp = temp.next;
            l2 = l2.next;
        }
        
        return dummy.next;
    }

    public void performTest()
    {        
         System.out.println("Same contents? " + mergeTwoLists(new Node(new int[] {1,3,6,8}), new Node(new int[]{2,4,5,7}) )
         .equalList(new Node(new int[] {1,2,3,4,5,6,7,8} ) ) );

    }
    
    public String toString() { return "Merge Two Sorted Lists ([N]**) [https://leetcode.com/problems/merge-two-sorted-lists/] ";}

}


class TreasureIsland2 implements IInterviewQuestion
{
    public int minimumRoute(char[][] maze)
    {
        Queue<int[]> q = new LinkedList<>();
        int[][] dir = new int[][] { {0,1}, {0,-1}, {1,0}, {-1,0} };

        for(int i=0;i< maze.length;i++)
        {
            for(int j=0; j < maze[0].length;j++)
            {
                if(maze[i][j]=='S')
                {
                    q.add(new int[] {j, i});
                }
            }
        }
        
        int count = 0;
        while(q.size() > 0)
        {
            int size = q.size();

            for(int i=0;i<size;i++)
            {
                int[] pos = q.poll();
                for(int k=0;k<dir.length;k++)
                {
                    int nx = pos[0] + dir[k][0];
                    int ny = pos[1] + dir[k][1];
                    if(nx <0 || nx >= maze[0].length || ny <0 || ny >= maze.length) continue;

                    if(maze[ny][nx]=='O') 
                    {
                        maze[ny][nx] = 'D'; // visited
                        q.add(new int[] {nx, ny });
                    }
                    else if(maze[ny][nx]=='X')
                    {
                        return count; // found it
                    }
                }
            }
            count++;
        }

        return count;
    }

    public void performTest()
    {
        int minStep = minimumRoute(new char[][] {
            {'S','O','O','S','S'},
            {'D','O','D','O','D'},
            {'O','O','O','O','O'},
            {'X','D','D','O','O'},
            {'X','D','D','D','O'},
        });

        System.out.println("Min Steps: " + minStep);
    }
    
    public String toString() { return "Treasure Island 2 [https://leetcode.com/discuss/interview-question/356150]: ";}
}

class CopyRandomLinkedList  implements IInterviewQuestion {
    public Node copyRandomListWithDummyNode(Node head)   {
        if(head==null) return null;        
        HashMap<Node, Node> m = new HashMap<Node, Node>();
        Node dummy = new Node(0);
        
        for(Node cur = head, temp = dummy; cur!=null; cur=cur.next, temp=temp.next)
        {
            temp.next = new Node(cur.val);
            m.put(cur, temp.next);
        }
                
        for(Node cur = head, temp = dummy.next; cur!=null; cur=cur.next, temp=temp.next)
        {
            if(cur.random!=null) temp.random = m.get(cur.random);
        }
        
        return dummy.next;
    }

    public Node copyRandomListWithoutDummyNode(Node head)   {
        if(head==null) return null;        
        HashMap<Node, Node> m = new HashMap<Node, Node>();

        for(Node cur = head; cur!=null; cur=cur.next)
        {
            m.put(cur, new Node(cur.val));
        }
            
        for(Node cur = head; cur!=null; cur=cur.next)
        {
            m.get(cur).next = m.get(cur.next);
            m.get(cur).random = m.get(cur.random);
        }

        return m.get(head);
    }


    public void performTest()
    {
        Node root = new Node(new int[] {0,1,2,3,4,5,6,7,8,9}, new int[] {0 ,1,3,5,7,9,0,6, 8,0});
        Node copy = new Node(new int[] {0,1,2,3,4,5,6,7,8,9}, new int[] {0 ,1,3,5,7,9,0,6, 8,0});
        System.out.println("Same contents? " + copyRandomListWithDummyNode(root ).equalList(copy) );
        System.out.println("Same contents? " + copyRandomListWithoutDummyNode(root ).equalList(copy) );

    }
    
    public String toString() { return "Copy Random Linked List ([N]**) [https://leetcode.com/problems/reorganize-string/] ";}
}

class LongestStringWithThreeConsecutiveCharacters  implements IInterviewQuestion {
    
    // only work for leetcode 1405. Longest Happy String. at most 3 characters. not a general solution
    public void LongestStringWithAtMostKChar(Map<Character, int[]> map, int K, StringBuilder result) {
        PriorityQueue<Map.Entry<Character, int[]>> q = new PriorityQueue<>((a, b) -> (b.getValue()[0] - a.getValue()[0]));
        for(Map.Entry<Character, int[]> e : map.entrySet()) {
            q.add(e);
        }
        boolean f = false;
        while(!q.isEmpty()) {
            Map.Entry<Character, int[]> current = q.poll();
            if(current.getValue()[0] > 0 && current.getValue()[1] < K && !f) {
                result.append(current.getKey());
                map.put(current.getKey(), new int[]{current.getValue()[0] - 1, current.getValue()[1] + 1});
                f = true;
            } else {
                map.put(current.getKey(), new int[]{current.getValue()[0], 0});   
            }
        }
        if(f) {
            LongestStringWithAtMostKChar(map, K, result);
        }
    }

    // INCORRECT. Find the right solution online.
    public String LongestStringWithKConsecutiveCharacters(HashMap<Character, Integer> m, int k)
    {
        System.out.print("K = " + k + " | ");
        int maxRepeat = k;
        PriorityQueue<Character> pq = new PriorityQueue<>( (c1, c2) -> ( m.get(c1)==m.get(c2) ? c1-c2 :m.get(c2)-m.get(c1) ) );
        
        Iterator<Map.Entry<Character,Integer>> ite = m.entrySet().iterator();

        while(ite.hasNext())
        {
            Map.Entry<Character,Integer> e = ite.next();
            if(e.getValue()<=0) ite.remove();
        }
        
        for(Character c : m.keySet()) { pq.add(c); }

        StringBuilder s = new StringBuilder();
        Character lastChar = null;

        while(pq.size() > 0)
        {            
            Character c = pq.poll(); // get max value

            if(lastChar == c)
            {
               if(pq.isEmpty()) return s.toString();// + ".... (unable to finish) : NOT POSSIBLE ";
                Character next = pq.poll();
                pq.add(c); // add back;
                c = next;
            }
            

            int count = m.get(c);
        //    System.out.print(" pq:" + pq + " , c="+ c + " | ");

            if(count > 0)
            {
                for(int i=0;  i < Math.min(maxRepeat,count) ; i++)
                {
                    s.append(c);
                    m.put(c,m.get(c)-1);
                }

                if(m.get(c)>0) pq.add(c); // add back 
            }
            lastChar = c;
        }

        return s.toString();
    }

    public void performTest()
    {
        HashMap<Character, Integer> m  =new HashMap<>(); 
/*
        m.put('a',1);
        m.put('b',2);
        m.put('c',3);
        m.put('d',6);
        m.put('e',7);
        m.put('f',7);
        m.put('g',9);
        m.put('i',9);

        System.out.println(m + " => "+  LongestStringWithKConsecutiveCharacters(m,3));

        m.clear();*/
        m.put('a',1);
        m.put('b',1);
        m.put('c',7);
        System.out.println(m + " => "+  LongestStringWithKConsecutiveCharacters(m,3));
        
        m.clear();
        m.put('a',2);
        m.put('b',2);
        m.put('c',1);
        System.out.println(m + " => "+  LongestStringWithKConsecutiveCharacters(m,2));

        m.clear();
        m.put('a',7);
        m.put('b',1);
        m.put('c',0);
        System.out.println(m + " => "+  LongestStringWithKConsecutiveCharacters(m,2));

        StringBuilder s = new StringBuilder();
        HashMap<Character, int[]> m2  =new HashMap<>(); 
        m2.put('a', new int[]{1, 0});
        m2.put('b', new int[]{1, 0});
        m2.put('c', new int[]{7, 0});

        LongestStringWithAtMostKChar(m2, 3, s);
        System.out.println(m2 + " => " + s);

        m2.clear();
        m2.put('a', new int[]{2, 0});
        m2.put('b', new int[]{2, 0});
        m2.put('c', new int[]{1, 0});
        
        LongestStringWithAtMostKChar(m2, 2, s);
        System.out.println(m2 + " => " + s);

        m2.clear();
        m2.put('a', new int[]{7, 0});
        m2.put('b', new int[]{1, 0});
        m2.put('c', new int[]{0, 0});        
        LongestStringWithAtMostKChar(m2, 2, s);
        System.out.println(m2 + " => " + s);
    }

    
    public String toString() { 
        return "Longest String With 3 or K Consecutive Characters ([N]**) [https://leetcode.com/problems/reorganize-string/  https://leetcode.com/problems/longest-happy-string/ ] ";
    }

}

class PrisonCellsAfterNDays implements IInterviewQuestion {

    public int[] prisonAfterNDays(int[] cells, int N) {
        if(cells==null || cells.length == 0 || N <= 0) return cells;
            
        HashSet<String> set = new HashSet<>();
        int cycle=0;
        for(int i=0;i < N;i++)
        {
            int[] next = nextDay(cells);

            String s =Arrays.toString(next);
            if(!set.contains(s))
            {
                set.add(s);
                cycle++;
            }
            else 
            {
                N%=cycle;
                while(0 < N--) { cells = nextDay(cells); }
                break;
            }
            cells = next;
        }

        return cells;
    }

    private int[] nextDay(int[] cells)
    {
        int[] temp = new int[cells.length];

        for(int i=1;i<cells.length-1;i++)
        {
            temp[i] = (cells[i-1] == cells[i+1] ) ? 1:0;            
        }
        return temp;
    }

    public void performTest()
    {
        Helper.equals(prisonAfterNDays(new int[]{1,0,0,1,0,0,1,0}, 1000000000) ,
            new int[]{0,0,1,1,1,1,1,0} , "prison After N Days:");

    }

    
    public String toString() { 
        return "prison After N Days [https://leetcode.com/problems/prison-cells-after-n-days/]";
    }
}

class KClosetPointstoOrigin implements IInterviewQuestion  {

    public int[][] KClosetPoints_Sort(int[][] points, int K) {
        Arrays.sort(points, (p1, p2) -> (p1[0]*p1[0] +p1[1]*p1[1]) - (p2[0]*p2[0] +p2[1]*p2[1]) );
        // int[][] temp = new int[K];
        // for(int i=0;i<K;i++) temp[i]=points[i];
        // return temp;
        return Arrays.copyOfRange(points, 0, K);
    }

    public int[][] KClosetPoints_PQ(int[][] points, int K) {
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>(  
            (p1, p2) -> (p2[0]*p2[0] +p2[1]*p2[1]) - (p1[0]*p1[0] +p1[1]*p1[1])  );
        // Max heap means get max value when polling.
        // polled values from the max heap are in an descending order

        // this trick maintains a size K of Max PriorityQueue (Max Heap) to stay N*log(k) time complexity.
        for(int[] p:points) 
        {
            pq.offer(p);
            if(pq.size() > K)  pq.poll();
        }
        // all first N-K items are polled. only last K items remained.
        int[][] res = new int[K][2];
        while(K > 0) res[--K] = pq.poll();
        return res;
    }

    public int[][] KClosetPoints_QSelect(int[][] points, int K) {
        int len =  points.length, l = 0, r = len - 1;
        while (l <= r) {
            int mid = helper(points, l, r);
            if (mid == K) break;
            if (mid < K) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return Arrays.copyOfRange(points, 0, K);
    }
    
    private int helper(int[][] arr, int l, int r) {
        int[] pivot = arr[l];

        while (l < r) {
            while (l < r && compare(arr[r], pivot) >= 0) r--;
            arr[l] = arr[r];
            while (l < r && compare(arr[l], pivot) <= 0) l++;
            arr[r] = arr[l];
        }

        arr[l] = pivot;
        return l;
    }
    
    private int compare(int[] p1, int[] p2) {
        return p1[0] * p1[0] + p1[1] * p1[1] - p2[0] * p2[0] - p2[1] * p2[1];
    }


    public void performTest()
    {
        System.out.println("KClosetPoints_Sort: " + Helper.arrayEquals(KClosetPoints_Sort(new int[][] { {-5,4}, {-6,-5}, {4,6} }, 2) , new int[][] { {-5, 4}, {4,6} } ) ) ;
        System.out.println("KClosetPoints_PQ: " + Helper.arrayEquals(KClosetPoints_PQ(new int[][] { {-5,4}, {-6,-5}, {4,6} }, 2) , new int[][] { {-5, 4}, {4,6} } ) );
        System.out.println("KClosetPoints_QSelect: " + Helper.arrayEquals(KClosetPoints_QSelect(new int[][] { {-5,4}, {-6,-5}, {4,6} }, 2) , new int[][] { {-5, 4}, {4,6} } ) );

    }

    
    public String toString() { 
        return "K Closest Points to Origin ([I] *)[https://leetcode.com/problems/k-closest-points-to-origin/discuss/220235/Java-Three-solutions-to-this-classical-K-th-problem.]";
    }
}



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

class Window<E>
{
    Map<E,Integer> m = new HashMap<>();
    int kinds = 0; // the total number of types available in the map
    
    public Window() {}
    
    public void add(E x)
    {
        m.put(x, m.getOrDefault(x,0)+1);
        if(m.get(x)==1) kinds++;
    }
    
    public void remove(E x)
    {
        m.put(x, m.get(x)-1);
        if(m.get(x)==0) kinds--;
    }
    
    public int kinds() { return kinds; }
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
