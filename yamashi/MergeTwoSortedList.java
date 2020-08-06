

class MergeTwoSortedList implements IInterviewQuestion
{
    public Node mergeTwoLists(Node l1, Node l2) {
        Node dummy = new Node(0);
        Node temp = dummy;
        
        while(l1 !=null && l2 != null)
        {
            Node smaller = (l1.val < l2.val) ? l1:l2;
            temp.next = smaller;             
            temp = temp.next;
            
            if(l1.val < l2.val) l1=l1.next;
            else l2 = l2.next;
        }
        
        while(l1!=null)
        {
            temp.next = l1;
            temp = temp.next;
            l1 = l1.next;
        }
        
        while(l2!=null)
        {
            temp.next = l2;
            temp = temp.next;
            l2 = l2.next;
        }
        
        return dummy.next;
    }

    public void performTest()
    {        
         System.out.println("Same contents? " + mergeTwoLists(new Node(new int[] {1,3,6,8}), new Node(new int[]{2,4,5,7}) )
         .equalList(new Node(new int[] {1,2,3,4,5,6,7,8} ) ) );

    }
    
    public String toString() { return "Merge Two Sorted Lists ([N]**) [https://leetcode.com/problems/merge-two-sorted-lists/] ";}

}