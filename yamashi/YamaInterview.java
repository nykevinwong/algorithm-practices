
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

interface IInterviewQuestion
{
    public void performTest();
}

class TopKFrequentlyMentionedKeywords implements IInterviewQuestion
{
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
    
        //maintain a heap of size k. entry can provide two option. key/value
        
        for (Map.Entry<String, Integer> entry : m.entrySet()) {
            q.offer(entry.getKey());
            if (q.size() > k) { q.poll(); }            
        }
    
        //get all elements from the heap
        List<String> res = new ArrayList<>();        
        while (q.size() > 0) {
            String w = q.poll();
            System.out.print("["+ w + "=" + m.get(w) + "]," );
            res.add(w);
        }    
        System.out.println();
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

            System.out.println(
                "Top K Frequent ("+label +"): " +
            TopKFrequent(new String[] { "anacell", "cetracular", "betacellular" },
            new String[] { "Anacell provides the best services in the city", 
                "betacellular has awesome services",
                "Best services provided by anacell, everyone should use anacell" }, 2, nlogk)
            );

            System.out.println(
                "Top K Frequent (" + label + "): " +
            TopKFrequent(new String[] { "anacell", "betacellular", "cetracular", "deltacellular", "eurocell" },
            new String[] {  "I love anacell Best services; Best services provided by anacell",
                "betacellular has great services",
                "deltacellular provides much better services than betacellular",
                "cetracular is worse than anacell",
                "Betacellular is better than deltacellular." }, 2, nlogk) );
        }

    }
}


public class YamaInterview
{
    public static void main(String[] args)
    {
        IInterviewQuestion[] questions = new IInterviewQuestion[] {new TopKFrequentlyMentionedKeywords()}; 

        for(IInterviewQuestion q: questions) { q.performTest(); }
    }
}