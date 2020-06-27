


import java.util.HashSet;
import java.util.HashMap;


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