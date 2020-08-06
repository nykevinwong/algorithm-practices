

class SubtreeOfAnotherTree implements IInterviewQuestion
{
    public boolean isSubtree(TreeNode s, TreeNode t) {        
        return traverse(s, t);
    }
    
    public boolean traverse(TreeNode s, TreeNode t)
    {
        return s!=null && (
            sameStructure(s,t) ||
            traverse(s.left,t) ||
            traverse(s.right,t)
        );
    }
    
    public boolean sameStructure(TreeNode a, TreeNode b)
    {
        if(a==null && b ==null) // touch bottom of the tree
            return true;
        
        if(a!=null && b !=null &&
          a.val == b.val)
        {
            return sameStructure(a.left, b.left) && sameStructure(a.right, b.right);
        }   
        
        return false;
    }

    public void performTest()
    {
        TreeNode s = TreeNode.createBinaryTreeFromArray(new Integer[]{3,4,5,1,2});
        TreeNode t = TreeNode.createBinaryTreeFromArray(new Integer[]{4,1,2});
        System.out.println("is Subtree? " + traverse(s, t));
        TreeNode s0 = TreeNode.createBinaryTreeFromArray(new Integer[]{3,4,5,1,2,null,null,0});
        TreeNode t0= TreeNode.createBinaryTreeFromArray(new Integer[]{4,1,2});
        System.out.println("is Subtree? false = " + traverse(s0, t0));
        TreeNode s1 = TreeNode.createBinaryTreeFromArray(new Integer[]{1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,2});
        TreeNode t1 = TreeNode.createBinaryTreeFromArray(new Integer[]{1,null,1,null,1,null,1,null,1,null,1,2});
        System.out.println("is Subtree? " + traverse(s1, t1));
        TreeNode s2 = TreeNode.createBinaryTreeFromArray(new Integer[]{1,1});
        TreeNode t2 = TreeNode.createBinaryTreeFromArray(new Integer[]{1});
        System.out.println("is Subtree? " + traverse(s, t));
    }
   
    public String toString() { 
        return "Subtree of Another Tree ([I] *) [https://leetcode.com/problems/subtree-of-another-tree/]";
    }
}
