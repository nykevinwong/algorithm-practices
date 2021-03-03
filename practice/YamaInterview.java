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

interface IImportTechnique
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
        System.out.println("\n * compare list and array: ");

        for(int i=0;i<left.size();i++)
        {
            List<T> l2 = left.get(i);
            T[] r2 = right[i];
            System.out.print(Arrays.toString(l2.toArray()) + " == " + Arrays.toString(r2) + " ?");
            for(int j=0;j<l2.size();j++)
            {
                T ls = l2.get(j);
                T rs = r2[j];
                if(!ls.equals(rs)) 
                {
                    System.out.println(" **FALSE**");
                    return false; 
                }
            }
            System.out.println(" true");
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

class JavaCollections implements IInterviewQuestion, IImportTechnique  {
    public void performTest()
    {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int[] points = new int[] { 1,10,3,6,5,8,7,4,9,2}; 
        int K= 3;

        for(int pValue : points) pq.offer(pValue);

        for(Integer p: pq) { System.out.print(p + " "); } System.out.println();

        Iterator ite = pq.iterator();
        
        while(ite.hasNext()) {System.out.print(ite.next() + " "); } System.out.println();
        
        while(!pq.isEmpty()){ System.out.print(pq.poll() + " "); } System.out.println();

        PriorityQueue<Integer> maxPQ = new PriorityQueue<>((a,b) -> b-a);
        for(int pValue : points) maxPQ.offer(pValue);
        while(!maxPQ.isEmpty()){ System.out.print(maxPQ.poll() + " "); } System.out.println();

        // K*log(K) trick (top K minimum element using Max Heap)
        for(int pValue : points) 
        {
            maxPQ.offer(pValue);
            if(maxPQ.size() > K) maxPQ.poll();
        }

        System.out.println("K="+K);
        while(!maxPQ.isEmpty()){ System.out.print(maxPQ.poll() + " "); } System.out.println();

        HashMap<Integer, String> m = new HashMap<>();
        m.put(11, "AB");
        m.put(2, "CD");
        m.put(33, "EF");
        m.put(9, "GH");
        m.put(3, "IJ");    


    }

    public String toString() { 
        return "Mastering JavaCollectionsn [https://beginnersbook.com/2013/12/how-to-loop-hashmap-in-java/]";
    }
}

class TopKFrequentlyMentionedKeywords implements IInterviewQuestion
{   // related problems:
    // https://leetcode.com/problems/top-k-frequent-words/
    // https://leetcode.com/problems/top-k-frequent-elements/
    // solution from 35 - 79 = about 45 lines

    public List<String> TopKFrequent(String[] keywords, String[] reviews, int k, boolean nlogksort )
    {
        Set<String> uniqueKeywordSet = new HashSet(Arrays.asList(keywords));
        Map<String, Integer> wordCount = new HashMap<>();

        for(String r: reviews)
        {
            String[] words = r.toLowerCase().split("\\W");
            HashSet<String> inOneReview = new HashSet<>();
            for(String w: words)
            {                                
                if(!inOneReview.contains(w) && uniqueKeywordSet.contains(w)) // only count one word in one sentence once
                {
                    wordCount.put(w, wordCount.getOrDefault(w,0)+1);
                }
                inOneReview.add(w);
            }
        }

       if(nlogksort)
       {
           PriorityQueue<String> minHeap = new PriorityQueue<>( (a,b) -> (wordCount.get(a) == wordCount.get(b) ? b.compareTo(a) : wordCount.get(a)-wordCount.get(b)));

           for(Map.Entry<String,Integer> e : wordCount.entrySet())
           {
               minHeap.offer(e.getKey());
               if(minHeap.size() > k) minHeap.poll();
           }

           List<String> ans = new ArrayList<String>();
           while(!minHeap.isEmpty())
           {
               ans.add(minHeap.poll());
           }

           Collections.reverse(ans);
           return ans;
       }

       List<String> res = new ArrayList(wordCount.keySet());
       Collections.sort(res, (a,b) -> wordCount.get(a)== wordCount.get(b) ? a.compareTo(b) : wordCount.get(b)-wordCount.get(a) );

        return res.subList(0, k);
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
    // 0: no orange, 1: fresh oragne, 2: currently rotten orange.
    // return mininum hours or -1 (means not possible to rotten all oranges)
    public int orangesRotting(int[][] grid) {
        Queue<Integer[]> q = new LinkedList<>();
        int count = 0;

        for(int i=0;i<grid.length;i++)
        {
            for(int j=0;j<grid[i].length;j++)
            {
                if(grid[i][j]==2) q.offer(new Integer[]{j,i});
                if(grid[i][j]==1) count++;
            }
        }

        int time = -1;
        Integer[][] dirs = new Integer[][] { {0,1},{0,-1},{-1,0},{1,0} };
        while(!q.isEmpty())
        {
            int size = q.size();
            for(int k=0;k<size;k++) // processing all currently rotten oranges in current time;
            {
                Integer[] pos = q.poll();
                for(Integer[] d : dirs)
                {
                    int x = pos[0]+d[0];
                    int y = pos[1]+d[1];
                    if(x < 0 || x >= grid[0].length || y < 0 || y >= grid.length) continue;
                    if(grid[y][x]==1) 
                    {
                        count--; // remove this fresh orange
                        grid[y][x] = 2; // rotten it
                        q.offer(new Integer[]{y,x});
                    }
                }
            }
            time++;
        }
        // if all fresh oragnes are rotten or equals to zero,  retur time.
        return (count==0) ? time:-1;
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
     Trie[] nodes = new Trie[26];
     List<String> suggestion = new LinkedList<>();
     }
 
 
     // autocomplete/typehead list
     // suggest 3 items based on a searchWord currently typed.
     public List<List<String>> suggestedProducts(String[] products, String searchWord) {
         Arrays.sort(products);
 
         Trie root = new Trie();
         for(String p : products)
         {
             for(Character c: p.toCharArray())
             {
                 int pos = (int)c-'a';
                 if(root.nodes[pos]==null) root.nodes[pos] = new Trie();
                 root = root.nodes[pos];
                 
                 if(root.suggestion.size() < 3) root.suggestion.add(p);
             }            
         }
 
         List<List<String>> res = new ArrayList<>();
 
         for(Character c : searchWord.toCharArray())
         {
             if(root!=null) root = root.nodes[c];
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

public class YamaInterview
{
    public static void main(String[] args)
    {
        IInterviewQuestion[] questions = new IInterviewQuestion[] {
            new JavaCollections(),
            new TopKFrequentlyMentionedKeywords(),
            new RottintOranges(),
            new SearchSuggestionSystem()
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


    }
}



