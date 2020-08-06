import java.util.HashMap;

class CopyRandomLinkedList  implements IInterviewQuestion {
    public Node copyRandomListWithDummyNode(Node head)   {
        if(head==null) return null;        
        HashMap<Node, Node> m = new HashMap<Node, Node>();
        Node dummy = new Node(0);
        
        for(Node cur = head, temp = dummy; cur!=null; cur=cur.next, temp=temp.next)
        {
            temp.next = new Node(cur.val);
            m.put(cur, temp.next);
        }
                
        for(Node cur = head, temp = dummy.next; cur!=null; cur=cur.next, temp=temp.next)
        {
            if(cur.random!=null) temp.random = m.get(cur.random);
        }
        
        return dummy.next;
    }

    public Node copyRandomListWithoutDummyNode(Node head)   {
        if(head==null) return null;        
        HashMap<Node, Node> m = new HashMap<Node, Node>();

        for(Node cur = head; cur!=null; cur=cur.next)
        {
            m.put(cur, new Node(cur.val));
        }
            
        for(Node cur = head; cur!=null; cur=cur.next)
        {
            m.get(cur).next = m.get(cur.next);
            m.get(cur).random = m.get(cur.random);
        }

        return m.get(head);
    }


    public void performTest()
    {
        Node root = new Node(new int[] {0,1,2,3,4,5,6,7,8,9}, new int[] {0 ,1,3,5,7,9,0,6, 8,0});
        Node copy = new Node(new int[] {0,1,2,3,4,5,6,7,8,9}, new int[] {0 ,1,3,5,7,9,0,6, 8,0});
        System.out.println("Same contents? " + copyRandomListWithDummyNode(root ).equalList(copy) );
        System.out.println("Same contents? " + copyRandomListWithoutDummyNode(root ).equalList(copy) );

    }
    
    public String toString() { return "Copy Random Linked List ([N]**) [https://leetcode.com/problems/reorganize-string/] ";}
}