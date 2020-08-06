



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