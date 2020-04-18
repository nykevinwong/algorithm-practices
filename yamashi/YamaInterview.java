
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
    public static void equals(List<String> left, String[] right, String msg)
    {
        if(msg!=null) System.out.println(msg + " =  " + Arrays.toString(right) + " => " + left.equals(Arrays.asList(right)) );
    }

    public static void equals(int left, int right, String msg)
    {
        if(msg!=null) System.out.println(msg + " =  " + right + " => " + (left == right) );
    }

    public static void equals(int[] left, int[] right, String msg)
    {
        if(msg!=null) System.out.println(msg + " =  " + right + " => actual: " + Arrays.toString(left) + " => " + Arrays.equals(left, right) );
    }

    public static void equals(List<List<String>> left, String[][] right, String msg)
    {
        if(msg!=null) System.out.println(msg + " =  " + right + " => " + equalsTo(left,right) );
    }

    public static boolean equalsTo(List<List<String>> left, String[][] right)
    {
        if(left.size() != right.length) return false;

        for(int i=0;i<left.size();i++)
        {
            List<String> l2 = left.get(i);
            String[] r2 = right[i];
            for(int j=0;j<l2.size();j++)
            {
                String ls = l2.get(j);
                String rs = r2[j];
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

class CriticalRouters implements IInterviewQuestion 
{
    // question: https://leetcode.com/discuss/interview-question/436073/
    class Graph<T>
    {
        private Map<T, List<T>> adjList = new HashMap<>();
        private int time = 0;

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

        public void findCutPoints(T node, Map<T, Boolean> visited, Map<T, T> parent,  Map<T, Integer> disc, Map<T, Integer> low, Set<T> ap)
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
                    findCutPoints(adjNode, visited, parent, disc, low, ap);
                    low.put(node, Math.min(low.get(node), low.get(adjNode) ) );

                    if(parent.get(node)==null && children > 1) ap.add(node);
                    else if(parent.get(node) != null && low.get(adjNode) >= disc.get(node)) 
                        ap.add(node);

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
                findCutPoints(node, visited, parent, disc, low, ap);
            }

            return ap;
        }
    }

    public void performTest()
    {
        int[][] edges = new int[][] { {0, 1}, {0, 2}, {1, 3}, {2, 3}, {2, 5}, {5, 6}, {3, 4} };
        Graph<Integer>  g = new Graph<>();

        for(int i=0;i<edges.length;i++)
        {
            g.addEdge(edges[i][0], edges[i][1]);
        }

        System.out.println("Cut Points :" +  g.findCutPoints() );
        
    }

    public String toString() { return "Critical Routers, Articulation Points ([E]**):";}

}

public class YamaInterview
{
    public static void main(String[] args)
    {
        IInterviewQuestion[] questions = new IInterviewQuestion[] {
            new TopKFrequentlyMentionedKeywords(),
            new RottintOranges(),
            new SearchSuggestionSystem(),
            new NumberOfClusters() , 
            new CriticalRouters()
        }; 
        int count = 1;
        for(IInterviewQuestion q: questions) { 
            System.out.println("(" + count + ") " + q);
            q.performTest(); count++;
        }
    }
}