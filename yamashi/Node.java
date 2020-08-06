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


public class Node {
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