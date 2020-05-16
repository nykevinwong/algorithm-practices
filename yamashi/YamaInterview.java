
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

interface IInterviewQuestion
{
    public void performTest();
}

class Helper
{
    public static <T>  void equals(List<T> left, T[] right, String msg)
    {       
        if(msg!=null) System.out.println(msg + " =  " + Arrays.toString(right) + " => actual: " + Arrays.toString(left.toArray()) +" => " + left.equals(Arrays.asList(right)) );
    }

    public static void equals(int[] left, int[] right, String msg)
    {
        if(msg!=null) System.out.println(msg + " =  " + right + " => actual: " + Arrays.toString(left) + " => " + Arrays.equals(left, right) );
    }

    public static <T> void equals(T[] left, T[] right, String msg)
    {
        if(msg!=null) System.out.println(msg + " =  " + right + " => actual: " + Arrays.toString(left) + " => " + Arrays.equals(left, right) );
    }

    public static <T> void equals(T left, T right, String msg)
    {
        if(msg!=null) System.out.println(msg + "... =  " + right + " => " + (left.equals(right)) );
    }


    public static <T> void equals(List<List<T>> left, T[][] right, String msg)
    {
        if(msg!=null) System.out.println(msg + " =  " + right + " => " + equalsTo(left,right) );
    }


    public static <T> boolean equalsTo(List<List<T>> left, T[][] right)
    {
        if(left.size() != right.length) return false;

        for(int i=0;i<left.size();i++)
        {
            List<T> l2 = left.get(i);
            T[] r2 = right[i];
            for(int j=0;j<l2.size();j++)
            {
                T ls = l2.get(j);
                T rs = r2[j];
                if(!ls.equals(rs)) return false;
            }
        }
        return true;
    }

    public static boolean arrayEquals(int[][] left, int[][] right)
    {
        if(left.length != right.length) return false;
        System.out.println("\n * compare two arrays: ");

        for(int i=0;i<left.length;i++)
        {
            int[] l2 = left[i];
            int[] r2 = right[i];
            System.out.print(Arrays.toString(l2) + " == " + Arrays.toString(r2) + " ?");
            for(int j=0;j < l2.length;j++)
            {
                int ls = l2[j];
                int rs = r2[j];
                if(ls!=rs) 
                {
                    System.out.println(" **FALSE**");
                    return false; 
                }
            }
            System.out.println(" true");
        }
        return true;
    }

}

class TopKFrequentlyMentionedKeywords implements IInterviewQuestion
{   // related problems:
    // https://leetcode.com/problems/top-k-frequent-words/
    // https://leetcode.com/problems/top-k-frequent-elements/
    // solution from 35 - 79 = about 45 lines
    public List<String> nlogn_Sort(Map<String,Integer> m, int k)
    {   // sort in a descending order based on word frequency from large to small.
        List<String> res = new ArrayList(m.keySet());
        Collections.sort(res, (w1, w2) -> (m.get(w1) == m.get(w2) ? w1.compareTo(w2) : m.get(w2)-m.get(w1)) );
        return res;
    }

    public List<String> nlogk_Sort(Map<String,Integer> m, int k)
    {   
        // create a min heap to sort from small to large. add to sort. remove to get rid of smallest item.
        PriorityQueue<String> q = new PriorityQueue<>( (w1, w2) -> (m.get(w1) == m.get(w2) ? w2.compareTo(w1) : m.get(w1)-m.get(w2)));
        // if you use max heap, you won't be able to lock insert/delete into log(k) time complexity as we did below.
        for (Map.Entry<String, Integer> entry : m.entrySet()) {
            q.offer(entry.getKey());
            if (q.size() > k) { q.poll(); }  // ensure each insertion only uses log(k)       
        }
    
        //get all elements from the heap
        List<String> res = new ArrayList<>();        
        while (q.size() > 0) {
            String w = q.poll();
            res.add(w);
        }    
        Collections.reverse(res); //reverse the order        
        return res;
    }

    public List<String> TopKFrequent(String[] keywords, String[] reviews, int k, boolean nlogksort )
    {
        Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords)); // remove duplicates from keywrods
        Map<String, Integer> m = new HashMap<>();

        for(String r: reviews)
        {   // split based on non-character. characters:[a-zA-Z0-9_]
            String[] strs = r.split("\\W");
            Set<String> inReview = new HashSet<>();
            for(String w:strs)
            {  
                w = w.toLowerCase();  // comparision is CASE-INSENSITIVE
                if(keywordSet.contains(w) && // is it a keyword?
                   !inReview.contains(w) ) // does it appear more than once in a review string?
                {
                    m.put(w, m.getOrDefault(w,0)+1);
                    inReview.add(w);
                }
            }
        }

       List<String> res = nlogksort ? nlogk_Sort(m,k) : nlogn_Sort(m, k);
       return res.subList(0,k); // [0,K)
    }

    public void performTest()
    {
        boolean[] nlogkFlag = new boolean[] { false, true};

        for(int i=0; i < nlogkFlag.length;i++)
        {
            boolean nlogk = nlogkFlag[i];
            String label = nlogk ? "nlogk": "nlogn";

            Helper.equals(TopKFrequent(new String[] { "anacell", "cetracular", "betacellular" },
            new String[] { "Anacell provides the best services in the city", 
                "betacellular has awesome services",
                "Best services provided by anacell, everyone should use anacell" }, 2, nlogk) , 
                new String[] {"anacell", "betacellular"} , 
                "Top K Frequent ("+label +")");

            Helper.equals(TopKFrequent(new String[] { "anacell", "betacellular", "cetracular", "deltacellular", "eurocell" },
            new String[] {  "I love anacell Best services; Best services provided by anacell",
                "betacellular has great services",
                "deltacellular provides much better services than betacellular",
                "cetracular is worse than anacell",
                "Betacellular is better than deltacellular." }, 2, nlogk) ,
                new String[] {"betacellular", "anacell"} , 
                "Top K Frequent ("+label +")");
        }

    }

    public String toString() { return "Top K Frequently Mentioned Keywords ([E]***):";}
}

class RottintOranges  implements IInterviewQuestion {
    //https://leetcode.com/problems/rotting-oranges/
    // Zombie in Matrix: https://leetcode.com/discuss/interview-question/411357/
    // Minium hours to send file to all available servers
    public int orangesRotting(int[][] grid) {
        Queue<Integer[]> q = new LinkedList<>();
        int count =0;
        // push source noddes
        for(int i=0;i<grid.length;i++)
            for(int j=0;j<grid[0].length;j++)
            {
                if(grid[i][j]==2) q.offer(new Integer[] {i,j});
                if(grid[i][j]==1) count++;
            }
        
        if(count==0) return 0; // speicial case. no fresh orange at all from start.
        
        int[][] dir = new int[][]{{0,1},{1,0},{0,-1},{-1,0}};
        int time = -1;
        
        while(q.size() > 0)
        {
            int size = q.size();            
            for(int i=0;i<size;i++) // make sure to go through all nodes in current queue
            {
                Integer[] pos = q.poll(); // get position                        
                for(int j=0;j<dir.length;j++) // get adjacents of adjcent node.
                {
                    int nx= pos[0] + dir[j][0];
                    int ny =pos[1] + dir[j][1];

                    if(nx < 0 || ny < 0 || nx >= grid[0].length || ny >= grid.length) continue;
                    
                    if(grid[ny][nx]==1) // push an adjecent neighor which is a fresh orange in this case
                    { // in scoope
                        count--;
                        grid[ny][nx] = 2; // rotten the tomato, mark as visited
                        q.offer(new Integer[]{ny,nx}); 
                    }
                }      
            }
            
            time++;
        }
        
        return (count==0) ? time:-1; //-1 also means it's not possible to rotten every orange.
    }

    public void performTest()
    {
        Helper.equals(orangesRotting(new int[][] { {2,1,1},{1,1,0},{0,1,1} }), 4, "Min minutes to rotten all oranges: ");
        Helper.equals(orangesRotting(new int[][] { {2,1,1},{0,1,0},{1,0,1} }), -1, "Min minutes to rotten all oranges: ");
        Helper.equals(orangesRotting(new int[][] { {2,2,2},{0,2,0},{2,0,2} }), 0, "Min minutes to rotten all oranges: ");
    }

    public String toString() { return "Zombie in Matrix [Min hours to send file to all available servers] ([E]***):";}
}

class SearchSuggestionSystem implements IInterviewQuestion {
   // System Design interview for auto suggestions: https://www.youtube.com/watch?v=xrYTjaK5QVM
   // https://leetcode.com/problems/search-suggestions-system
    class Trie {
    Trie[] sub = new Trie[26];
    List<String> suggestion = new LinkedList<>();
    }

    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        Arrays.sort(products);
        
        List<List<String>> res = new ArrayList<>();
        Trie root = new Trie();
        for(String p: products) // pre-compute suggest list based on sorted order
        {
            Trie r = root;
            for(char c : p.toCharArray())
            {
                if(r.sub[c-'a']==null)
                    r.sub[c-'a'] = new Trie();
                r = r.sub[c-'a'];
                
                if(r.suggestion.size() < 3) r.suggestion.add(p);
            }
        }
        
        for(char c: searchWord.toCharArray())
        {
            if(root!=null) root = root.sub[c-'a'];
            res.add( (root==null) ? Arrays.asList() : root.suggestion);
        }
        
        return res;
    }

    public void performTest()
    {
        String[] products = new String[]{"mobile","mouse","moneypot","monitor","mousepad"};
        String searchWord = "mouse";
        Helper.equals(suggestedProducts(products,searchWord), 
        new String[][] {
            {"mobile","moneypot","monitor"},
            {"mobile","moneypot","monitor"},
            {"mouse","mousepad"},
            {"mouse","mousepad"},
            {"mouse","mousepad"}
        }, "AutoComplete/TypeAhead Suggestion: ");
    }

    public String toString() { return "Product Suggestions ([E]**):";}
}

class NumberOfClusters implements IInterviewQuestion {
    // questions: https://www.glassdoor.com/Interview/Problem-2d-grid-each-node-has-1-colors-find-number-of-clusters-of-a-given-color-red-blue-green-blue-gre-QTN_2930567.htm
    // https://leetcode.com/problems/number-of-islands/
    public int[] numberOfClusters(char[][] grid) {
        char[] colors = new char[] {'0','1','2'};
        int[] count = new int[] { 0,0,0};

        for(int i=0;i<grid.length;i++)
        {
            for(int j=0;j<grid[0].length;j++)
            {
                for(int k=0;k<colors.length;k++)
                {
                    if(grid[i][j]==colors[k]) 
                    {
                        dfs(grid, i, j, colors[k]);
                        count[k]++;
                        break; // break; no need to check other colors.
                    }
                }
            }
        }

      //  System.out.println("counts: " + Arrays.toString(count));
        return count;
    }
    
    public void dfs(char[][] grid, int y, int x, char target)
    {
        if(x < 0 || x >= grid[0].length || y < 0 || y >= grid.length ) return;        
        if(grid[y][x]!= target) return;
        
        grid[y][x]= 'v'; //marked as visited
        
        dfs(grid, y+1, x, target);
        dfs(grid, y-1, x, target);
        dfs(grid, y, x+1, target);
        dfs(grid, y, x-1, target);
    }

    public void performTest()
    {
        Helper.equals( numberOfClusters(new char[][] {
            {'1','1','0','1','0'},
            {'1','2','0','1','0'},
            {'1','2','2','2','2'},
            {'0','0','1','1','0'},
        }), new int[] {4,3,1} ,"Number of Culsters :");
        
    }

    public String toString() { return "Number Of Clusters ([E]**):";}
}

class CriticalRoutersOrConnections implements IInterviewQuestion 
{
    class Graph<T>
    {
        private Map<T, List<T>> adjList = new HashMap<>();
        private int time = 0;

        public void addEdges(T[][] edges)
        {
            for(int i=0;i<edges.length;i++)
            {
                addEdge(edges[i][0], edges[i][1]);
            }
        }

        public void clear()
        {
            adjList.clear();
        }

        public void addEdge(T u, T v)
        {
            addEdge(u, v, true);
        }

        public void addEdge(T u, T v, boolean biDir)
        {
            adjList.put(u, adjList.getOrDefault(u, new ArrayList<T>()));
            adjList.get(u).add(v);
            if(biDir)
            {
                adjList.put(v, adjList.getOrDefault(v, new ArrayList<T>()));
                adjList.get(v).add(u);
            }

        }        

        public void findCutPoints(T node, Map<T, Boolean> visited, Map<T, T> parent,  Map<T, Integer> disc, Map<T, Integer> low, Set<T> ap, Map<T, List<T>> bridges)
        {
            int children = 0;
            visited.put(node, true);
            time++;
            disc.put(node, time);
            low.put(node, time);
                        
            for(T adjNode: adjList.get(node))
            {
                if(visited.get(adjNode)==false)
                {
                    children++;
                    parent.put(adjNode,node);
                    findCutPoints(adjNode, visited, parent, disc, low, ap, bridges);
                    low.put(node, Math.min(low.get(node), low.get(adjNode) ) );
                    
                    // this piece of code is to determine cut points
                    if(ap!=null)
                    {
                        if(parent.get(node)==null && children > 1) ap.add(node);
                        if(parent.get(node) != null && low.get(adjNode) >= disc.get(node)) // > for bridges, >= for points
                            ap.add(node);
                    }

                    // this piece of code is to determine. > for bridges, >= for points
                    if(bridges != null)
                    {
                        if(low.get(adjNode) > disc.get(node)) {
                            bridges.put(node, bridges.getOrDefault(node, new ArrayList<T>()) );
                            bridges.get(node).add(adjNode);
                        }
                    }

                }
                else if(visited.get(adjNode)== true && adjNode != parent.get(node) )
                {
                    low.put(node, Math.min(low.get(node), disc.get(adjNode) ) );
                }

            }
        }

        public Set<T> findCutPoints()
        {
            Map<T, Boolean> visited = new HashMap<>();
            Map<T, T> parent= new HashMap<>();
            Map<T, Integer> low= new HashMap<>();
            Map<T, Integer> disc= new HashMap<>();
            Set<T> ap = new HashSet<T>();
            time = 0;
            for(T node: adjList.keySet())
            {
                visited.put(node, false);
                parent.put(node, null); // root by deafult
                low.put(node, 0);
                disc.put(node, 0);
            }
    
            for(T node: adjList.keySet())
            {
                if(visited.get(node)==false)
                findCutPoints(node, visited, parent, disc, low, ap, null);
            }

            return ap;
        }

        public Map<T, List<T>> findBridges()
        {
            Map<T, Boolean> visited = new HashMap<>();
            Map<T, T> parent= new HashMap<>();
            Map<T, Integer> low= new HashMap<>();
            Map<T, Integer> disc= new HashMap<>();
            Map<T, List<T>> bridges = new HashMap<>();

            time = 0;

            for(T node: adjList.keySet())
            {
                visited.put(node, false);
                parent.put(node, null); // root by deafult
                low.put(node, 0);
                disc.put(node, 0);
            }
    
            for(T node: adjList.keySet())
            {
                if(visited.get(node)==false)
                findCutPoints(node, visited, parent, disc, low, null, bridges);
            }

            return bridges;
        }
    }

    public void performTest()
    {
        Graph<Integer>  g = new Graph<>();

        // https://leetcode.com/discuss/interview-question/436073/
        g.addEdges(new Integer[][] { {0, 1}, {0, 2}, {1, 3}, {2, 3}, {2, 5}, {5, 6}, {3, 4} });
        System.out.println("Cut Points :" +  g.findCutPoints() );

        // https://leetcode.com/problems/critical-connections-in-a-network/
        g.clear();
        g.addEdges(new Integer[][] { {1, 2}, {1, 3}, {3, 4}, {1, 4}, {4, 5}} );
        System.out.println("Bridges (Cut Edges) :" + g.findBridges() );
        g.clear();
        g.addEdges(new Integer[][] { {1, 2}, {1, 3}, {2, 3}, {2, 4}, {2, 5}, {4, 6}, {5, 6} } );
        System.out.println("Bridges (Cut Edges) :" + g.findBridges() );
        g.clear();
        g.addEdges(new Integer[][] { {1, 2}, {1, 3}, {2, 3}, {3, 4}, {3, 6}, {4, 5}, {6, 7}, {6, 9}, {7, 8}, {8, 9} } );
        System.out.println("Bridges (Cut Edges) :" + g.findBridges() );
    }

    public String toString() { return "Critical Routers & Connections, Articulation Points/Cut Point, Bridges/Cut Edges ([E]**):";}

}

class PartitionLabel implements IInterviewQuestion
{
    public List<Integer> partitionLabels(String S) {
        int[] last = new int[26];
        List<Integer> res = new ArrayList<Integer>();
        
        for(int i=0;i<S.length();i++)
            last[S.charAt(i)-'a'] = i;           
        
        int lastMax = 0, start = 0;
        for(int i=0;i<S.length();i++)
        {
            lastMax = Math.max( last[S.charAt(i)-'a'], lastMax);           
            
            if(lastMax==i)
            {
                res.add(lastMax-start+1);                    
                start = i+1;
            }
        }
        
        return res;
    }

    public void performTest()
    {
        Helper.equals(partitionLabels("ababcbacadefegdehijhklij"), new Integer[] {9,7,8}, "Partition Label -> ababcbacadefegdehijhklij ");

    }

    public String toString() { return "Partition Labels([E]*) [https://leetcode.com/problems/partition-labels/]: ";}
}

class ReorderDataInLogFile implements IInterviewQuestion
{
    public String[] reorderLogFiles(String[] logs) {
        Arrays.sort(logs, (log1, log2) -> {
            String[] split1 = log1.split(" ", 2);
            String[] split2 = log2.split(" ", 2);
            boolean isDigit1 = Character.isDigit(split1[1].charAt(0));
            boolean isDigit2 = Character.isDigit(split2[1].charAt(0));
            if (!isDigit1 && !isDigit2) {
                int cmp = split1[1].compareTo(split2[1]);
                if (cmp != 0) return cmp;
                return split1[0].compareTo(split2[0]);
            }
            return isDigit1 ? (isDigit2 ? 0 : 1) : -1;
        });
        return logs;
    }

    public void performTest()
    {
        Helper.equals( reorderLogFiles(new String[] { "dig1 8 1 5 1","let1 art can","dig2 3 6","let2 own kit dig","let3 art zero"} ) ,
        new String[] {"let1 art can","let3 art zero","let2 own kit dig","dig1 8 1 5 1","dig2 3 6"}, "Reoder Log ");
    }

    public String toString() { return " Reorder Data in Log Files([E]*) [https://leetcode.com/problems/reorder-data-in-log-files/]: ";}
}

class FindPairWithGivenSum implements IInterviewQuestion
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

class FindUniquePairsWithGivenSum implements IInterviewQuestion
{
    public List<List<Integer>> findUniquePairsWithGivenSum(int[] nums, int target)
    {
        Map<Integer, List<Integer>> pos = new HashMap<>();
        List<List<Integer>> res = new ArrayList<>();
        for(int i=0;i<nums.length;i++) 
        { 
            List<Integer> ls = pos.get(target-nums[i]);

            if(ls!= null && ls.size() > 0)
            {  // to DO. get rid of duplication.
                    int x = nums[i];
                    int y = nums[ls.get(0)];
                    int left = x > y ? x:y;
                    int right = x > y ? y:x;
                    res.add(Arrays.asList(left, right));
                    ls.remove(0);
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
      Helper.equals(findUniquePairsWithGivenSum(new int[] {1, 1, 2, 45, 46, 46 } , 47),
      new Integer[][] { {45, 2}, {46, 1}, {46 ,1}}, "Find Unique Pairs ");

      Helper.equals(findUniquePairsWithGivenSum(new int[] {1, 5, 1, 5} , 6 ),
      new Integer[][] { {5, 1}, {5, 1}}, "Find Unique Pairs ");
    }

    public String toString() { return "Find Unqiue Pair With Given Sum ([E,I]**) [https://leetcode.com/discuss/interview-question/372434]: ";}
}

class SearchMatrix implements IInterviewQuestion
{
    public boolean searchMatrix(int[][] matrix, int target) {
        if(matrix==null || matrix.length==0 || matrix[0].length==0 ) return false;
        int col = matrix[0].length-1;
        int row = 0;
        
        while(row < matrix.length && col >= 0)
        {
            if(matrix[row][col]==target) return true;
            if(matrix[row][col] < target) row++;
            else col--;
        }
        
        return false;
    }

    public void performTest()
    {
        int[][] m = new int[][] {
            {1,   4,  7, 11, 15},
            {2,   5,  8, 12, 19},
            {3,   6,  9, 16, 22},
            {10, 13, 14, 17, 24},
            {18, 21, 23, 26, 30}
        };

        Helper.equals(searchMatrix(m, 16), true, "Search 16 ");
        Helper.equals(searchMatrix(m, 13), true, "Search 13 ");
        Helper.equals(searchMatrix(m, 100), false, "Search 100 ");
    }

    public String toString() { return "Seach a 2D Matrix ([N]**) [https://leetcode.com/problems/search-a-2d-matrix-ii]: ";}
}

class FavoriteGenres implements IInterviewQuestion
{
    public Map<String, List<String>> favoriteGenres(Map<String, List<String>> userSongs, Map<String, List<String>> songGenres)
    {
        Map<String, Integer> genreCount = new HashMap<>();
        Map<String, String> song2Genre = new HashMap<>();
        Map<String, List<String>> m = new HashMap<>();

        for(Map.Entry<String, List<String>> e : songGenres.entrySet())
        {
            for(String song: e.getValue())
            {
                song2Genre.put(song, e.getKey());
            }
        }
         
        System.out.println(song2Genre);

        for(Map.Entry<String, List<String>> e : userSongs.entrySet())
        {
            List<String> songs = e.getValue();
            String singer = e.getKey();
            genreCount.clear();

            int max = Integer.MIN_VALUE;
            for(String song: songs)
            {
                String genre = song2Genre.get(song);
                genreCount.put(genre, genreCount.getOrDefault(genre,0) +1 );
                max = Math.max( max, genreCount.get(genre));
            }

            for(Map.Entry<String,Integer> gc: genreCount.entrySet())
            {
                if(max==gc.getValue()) 
                {
                    m.put(singer, m.getOrDefault(singer, new ArrayList<String>()));
                    m.get(singer).add(gc.getKey());
                }
            }

            genreCount.clear();
        }

        return m;
    }

    public void performTest()
    {
        Map<String, List<String>> userSongs = new HashMap<>() ;
        Map<String, List<String>> songGenres = new HashMap<>();

        userSongs.put("David", Arrays.asList("song1", "song2", "song3", "song4", "song8"));
        userSongs.put("Emma", Arrays.asList("song5", "song6", "song7"));
        songGenres.put("Rock", Arrays.asList("song1", "song3"));
        songGenres.put("Dubstep", Arrays.asList("song7"));
        songGenres.put("Techno", Arrays.asList("song2", "song4"));
        songGenres.put("Pop", Arrays.asList("song5", "song6"));
        songGenres.put("Jazz", Arrays.asList("song8", "song9"));

        Map<String, List<String>> m = favoriteGenres(userSongs, songGenres);
        System.out.println(m);
    }

    public String toString() { return "Favorite Genres ([N]**) [    https://leetcode.com/discuss/interview-question/373006]: ";}

}

class MostCommonWord implements IInterviewQuestion
{
    public String mostCommonWord(String paragraph, String[] banned) {
        String[] words = paragraph.split("\\W+"); // split by all non-character. + means more than one non-character can be used. 
        HashSet<String> set = new HashSet<>();
        HashMap<String,Integer> m = new HashMap<>();
        String maxWord = null;
        int max = 0;
        for(String b : banned)
            set.add(b.toLowerCase());
        
        for(String w: words)
        {
            w = w.toLowerCase();
            if(!set.contains(w))
            {
                m.put(w, m.getOrDefault(w,0)+1);
                int count =m.get(w);
                if(count > max)
                {
                    maxWord = w;
                    max = count;
                }
            }
        }
        
        return maxWord;
    }

    public void performTest()
    {
        Helper.equals(mostCommonWord("Bob hit a ball, the hit BALL flew far after it was hit.", new String[] {"hit"}), 
        "ball" , "Most Command Word ");
        Helper.equals(mostCommonWord("Bob. hIt, baLl", new String[] {"bob", "hit"}), 
        "ball" , "Most Command Word ");
    }
    
    public String toString() { return "Most Common Word ([I]**) [https://leetcode.com/problems/most-common-word/]: ";}

}

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

    public List<String> substringswithExactlyKDistinctChars(String s, int k)
    {
        List<String> res = new ArrayList<>();
        HashMap<Character,Integer> m = new HashMap<>();

        for(int i=0, j = 0;i < s.length() && j < s.length(); )
        {
            char end = s.charAt(i);
            char start = s.charAt(j);

            if(m.getOrDefault(end, 0) == 0 && m.size() <= 2)
            {
                m.put(end, m.getOrDefault(end, 0)+1);
                i++;

            }
            else 
            {
                m.put(start, m.getOrDefault(start,0)-1);
                j++;
            }


         //   System.out.print(j + "," + i + " = " + m.get(end) + ",size=" + m.size() + "|");
            if(m.size()==2) res.add(s.substring(j,i));            

        }
        return res;
    }

    public void performTest()
    {
        Helper.equals( substringswithExactlyKDistinctChars("pqpqs",2), 
        new String[] {"pq", "pqp", "pqpq", "qp", "qpq", "pq", "qs"}, "SubString ");
    }
    
    public String toString() { return "Substrings with exactly K Distinct Chars ([I]**) [https://leetcode.com/discuss/interview-question/370157]: ";}
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

    public String LongestStringWithKConsecutiveCharacters(int k)
    {
        int maxRepeat = k- 1;
        HashMap<Character, Integer> m  =new HashMap<>(); 
        m.put('a',1);
        m.put('b',2);
        m.put('c',3);
        m.put('d',6);
        m.put('e',7);
        m.put('f',7);
        m.put('g',9);
        m.put('i',9);

        PriorityQueue<Character> pq = new PriorityQueue<>( (c1, c2) -> ( m.get(c2)-m.get(c1)));

        for(Character c : m.keySet()) { pq.add(c); }

        StringBuilder s = new StringBuilder();
        Character lastChar = null;

        while(pq.size() > 0)
        {            
            Character c = pq.poll(); // get max value

            if(lastChar == c)
            {
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
        System.out.println(LongestStringWithKConsecutiveCharacters(3));
    }

    
    public String toString() { 
        return "Copy Random Linked List ([N]**) [https://leetcode.com/problems/copy-list-with-random-pointer/] ";
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

class JavaCollections implements IInterviewQuestion  {
    public void performTest()
    {
        System.out.println("Priorty Queue:");
        PriorityQueue<Integer> pq=new PriorityQueue<>(); // min heap/priority queue by deafult
        int[] points = new int[] { 1,10,3,6,5,8,7,4,9,2}; int K = 3;

        for(int pValue: points) { pq.offer(pValue); }

        // print heap array content (in an array order)
        for(Integer p: pq) { System.out.print(p+ " "); } System.out.println();        

        // how to iterate through PriorityQueue without affectint pq heap content
        Iterator itr = pq.iterator(); 
        while (itr.hasNext()) { System.out.print(itr.next()+ " ");  } System.out.println();
       
        while(pq.size() > 0) { System.out.print(pq.poll()+ " "); } System.out.println();

        Queue<Integer> pq2=new PriorityQueue<>(Collections.reverseOrder()); // max heap/priority queue

        for(int pValue: points) { pq2.offer(pValue); }
        while(pq2.size() > 0) { System.out.print(pq2.poll()+ " "); } System.out.println();
   
        // a trick to stay N*log(k) time complexity for Max Priority Queue to get top k minimum in descending order (max-heap max first out)
        // without this trick, it's N*log(N) time complexity.
        // Min Priority Queue = N*log(N) since you need to put all numbers into the pq first.
        // Min Priority Queue with the trick won't work since you will get top k max in an ascending order. (min-heap min first out)
        for(int pValue: points) 
        {
            pq2.offer(pValue);
            if(pq2.size() > K)  pq2.poll();
        }

        // print the result        
        while(!pq2.isEmpty()) { System.out.print(pq2.poll() + " "); } System.out.println();
    
        // add() vs offer(), remove() vs poll(), element() vs peek().
        // add() from Collection can't return false and throw an exception if an element cannot be added.
        // offer() from Queue returns false if an element cannot be added.
        // when the queue is empty, element() and remove() from Collection throws NoSuchElementException, while poll() & peek() return null.
        /*
           add Throws:
            IllegalStateException - if the element cannot be added at this time due to capacity restrictions
            ClassCastException - if the class of the specified element prevents it from being added to this queue.
                for example, adding another type of object into a non-generic ArrayList.
            NullPointerException - if the specified element is null and this queue does not permit null elements
            IllegalArgumentException - if some property of this element prevents it from being added to this queue
        */

        System.out.println("HashMap:");

        HashMap<Integer, String> m = new HashMap<>();
        m.put(11, "AB");
        m.put(2, "CD");
        m.put(33, "EF");
        m.put(9, "GH");
        m.put(3, "IJ");    

        for(Map.Entry e: m.entrySet() ) { System.out.print("{" + e.getKey() + "=>" + e.getValue() + "},"); }  System.out.println();

        HashMap<Integer,String> m2 = (HashMap)m.clone();
        
        for(Map.Entry<Integer,String> e: m2.entrySet() ) { System.out.print("{" + e.getKey() + "=>" + e.getValue() + "},"); } System.out.println();   
        m2.clear();
        m2.putAll(m);        Iterator mItr = m2.entrySet().iterator();
        while(mItr.hasNext()) { Map.Entry e = (Map.Entry)mItr.next(); System.out.print("{" + e.getKey() + "=>" + e.getValue() + "},"); } System.out.println();

        /*  HashSet & HashMap doesn't maintain any kind of order of its elements.
            LinkedHashSet & LinkedHashMap maintains insertion order.
            TreeSet & TreeMap sort the entries in ascending order of keys and they don't allow null key and throw NullPointerException.
            Set -> contains
            Map -> containsKey, containsValue
        */        

        Set<Integer> set = m2.keySet();
        Iterator<Integer> ite2 = set.iterator();
        while(ite2.hasNext()) { System.out.print(ite2.next() + ","); } System.out.println();

        Collection<String> values = m2.values();
        for(String s: values) { System.out.print(s + ","); } System.out.println();
        Iterator<String> ite3 = values.iterator();
        while(ite3.hasNext()) { System.out.print(ite3.next() + ","); } System.out.println();

        System.out.println("hello");

    }
    
    public String toString() { 
        return "Mastering JavaCollectionsn [https://beginnersbook.com/2013/12/how-to-loop-hashmap-in-java/]";
    }
}

/*

- Use Comparator when you need more flexibilit
** The compareTo() method will return a positive number if one object is greater than the other, negative if it’s lower, and zero if they are the same.

import java.util.Comparator;

public class MyComparator implements Comparator<String>
{
   @Override // ascending order based on String length
   public int compare(String x, String y) { return x.length() - y.length();}
}

or you can replace everything before Comparator using new keyword as below.

Collections.sort(strArr, new Comparator<String>
{
   @Override // ascending order based on String length
   public int compare(String x, String y) { return x.length() - y.length();}
});

Using Comparator with lambda expressions as below:
Collections.sort(strArr, (x, y)  x.length() - y.length()  );

You always use compareTo for a String/object.
Collections.sort(strArr, (s1, s2) -> s1.compareTo(s2));

even shorter with
Collections.sort(strArr, Comparator.naturalOrder() );

    // ascending order means to arrange values from smallest to largest.
    // descending order means to arrange values from largest to smallest.

    // ascending order based on value
    public int compare(Integer x, Integer y) { return x - y;}
    // descending order based on value
    public int compare(Integer x, Integer y) { return y - x;}

    // sort by id in ascending order. when ids are the same, sort by their name in alphabetic order.
    public int compare(User x, User y) { return x.id == y.id ? x.name.compareTo(y)  : x.id-y.id  ;}
    (x,y) -> x.id==y.id ? x.name.compareTo(y) : x.id-y.id;

    // sort by map value in ascending order. When map value are the same, sort by the name in alphabetic order.
    public int compare(String s1, String s2) { return count.get(s1) == count.get(s2) ? s1.compareTo(s2)  : count.get(s1)-count.get(s2); }
    (s1,s2) -> count.get(s1)==count.get(s2) ? s1.compareTo(s2) : count.get(s1)-count.get(s2);

    Sorting a Map with TreeMap
    Map<String, Integer> m  = new TreeMap<>();
    m.put("DEF", 10);
    m.put("ABC", 20);
    System.out.println(m);

    Sorting a Set with TreeSet
    Set<String> s  = new TreeSet<>();
    s.put("DEF");
    s.put("ABC");
    System.out.println(m);
*/

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
{
    // get cloest target sum from two non-sorted array
    public List<int[]> getOptimalUtilization(int[][] a, int[][] b, int target)
    {
        Arrays.sort(a, (i,j) -> i[1]-j[1] );
        Arrays.sort(b, (i,j) -> i[1]-j[1] );
        int i = 0;
        int j = b.length-1;
        List<int[]> res = new ArrayList<>();

        while(i < a.length && j >= 0)
        {
            int sum = a[i][1] + b[j][1];
            if(sum > target)
            {
                j--;
            }
            else // less or equal
            {
                System.out.print("added  | ");
                res.add(new int[]{a[i][0], b[j][0]});
                
                int tmp = j; // get all duplicate values on b
                while(tmp > 0 && b[tmp][1] == b[tmp-1][1] ) res.add(new int[]{a[i][0], b[tmp--][0]});

                //deduplicate
                while(i < a.length && a[i][1]==a[i+1][1]) i++;
                i++;
            }
        }

        return res;
    }

    public void performTest()
    {
        Helper.equals(getOptimalUtilization(new int[][] { {1,2}, {2,4},{3,6}}, new int[][]{{1,2}}, 7 ), 
        new int[][] {{2,1}}, " ... " );
    }

    public String toString() { 
        return "Optimal Utilization ([E]) [https://leetcode.com/discuss/interview-question/344677]";
    }
}

public class YamaInterview
{
    public static void main(String[] args)
    {
        IInterviewQuestion[] questions = new IInterviewQuestion[] {
            new JavaCollections(),
            new TopKFrequentlyMentionedKeywords(),
            new RottintOranges(), // Zombin in Matrix
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
            new SearchMatrix(),
            new FavoriteGenres(),
            new FindUniquePairsWithGivenSum(),
            new SubstringsOfSizeKwithKDistinctChars(),
            // Max of Min Altitudes
            // Longest Palindromic substring
            new SubstringsOfExactlyKDistinctChars(),
            new LongestStringWithThreeConsecutiveCharacters(),
            new MostCommonWord(),
            new KClosetPointstoOrigin(),
            new GenerateParentheses(),
            // Min Cost to connect all nodes
            // Min Cost to Repait Edges
            new PrisonCellsAfterNDays(),
            new SubTreeWithMaximumAverage()
        }; 

        int count = 1;
        for(IInterviewQuestion q: questions) { 
            System.out.println("(" + count + ") " + q);
            q.performTest(); count++;
            System.out.println("\n---------------------------\n");
        }
    }
}
