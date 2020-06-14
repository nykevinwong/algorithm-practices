
import java.util.ArrayList;
import java.util.List;

public class PartitionLabel implements IInterviewQuestion
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
