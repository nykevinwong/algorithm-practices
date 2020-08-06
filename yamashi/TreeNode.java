import java.util.*;

// https://github.com/nykevinwong/TreeAlgorithm/blob/master/TreeTraversal.cs
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {  }
    TreeNode(int x) { val = x; }

    public static TreeNode createBinaryTreeFromArray(Integer[] arr)
    {
        return createBinaryTreeFromArray(arr, 0);
    }

    protected static TreeNode createBinaryTreeFromArray(Integer [] arr, int index)
    {
        if(arr.length > index)
        {
            if(arr[index]==null) return null;

            TreeNode node = new TreeNode(arr[index]);
            node.left = createBinaryTreeFromArray(arr, 2*index+1);
            node.right = createBinaryTreeFromArray(arr, 2*index+2);
            return node;
        }
        return null;
    }
}


class NaryTreeNode {
int val;
List<NaryTreeNode> nodes = new ArrayList<NaryTreeNode>();

public NaryTreeNode(int val) { this.val = val; }
public void addNode(NaryTreeNode node)
{
   if(!nodes.contains(node)) nodes.add(node);
}

public void addNodes(int[] arr)
{
   for(int val: arr) { nodes.add(new NaryTreeNode(val)); }
}
}