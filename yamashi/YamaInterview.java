
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.Queue;

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

public class YamaInterview
{
    public static void main(String[] args)
    {
        IInterviewQuestion[] questions = new IInterviewQuestion[] {
            new TopKFrequentlyMentionedKeywords(),
            new RottintOranges(),
            new CriticalRoutersOrConnections(),
            new SearchSuggestionSystem(),
            new NumberOfClusters() , 
            new ReorderDataInLogFile(),
            new PartitionLabel(),
            new FindPairWithGivenSum(),
            new SearchMatrix(),
            new FindUniquePairsWithGivenSum(),
            new FavoriteGenres(),
            new MostCommonWord(),
            new SubstringsOfSizeKwithKDistinctChars()
        }; 
        int count = 1;
        for(IInterviewQuestion q: questions) { 
            System.out.println("(" + count + ") " + q);
            q.performTest(); count++;
        }
    }
}