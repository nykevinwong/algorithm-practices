
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Iterator;

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