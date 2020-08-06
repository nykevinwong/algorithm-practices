import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;


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
