
class SubTreeWithMaximumAverage implements IInterviewQuestion
{
    class RefValue
    {
        public NaryTreeNode maxNode;
    }
    public NaryTreeNode getMaxAverageSubTree(NaryTreeNode root)
    {
        RefValue maxSubTree = new RefValue();
        float[] res = getSumAndAverage(root, maxSubTree);
        return maxSubTree.maxNode;
    }

    public float[] getSumAndAverage(NaryTreeNode root, RefValue maxSubTree)
    {
        if(root==null) return new float[]{0,0, 0};
 
        float sum = root.val;
        float maxAverage = 0;
        float childCount = 0;
        if(root.nodes.size() > 0)
        {
            for(NaryTreeNode child: root.nodes)
            {
                float[] res = getSumAndAverage(child, maxSubTree);     
                sum+= res[0];    
                childCount+=res[2];                      
                if(maxAverage < res[1])
                {                 
                    System.out.print(maxAverage +  " < " + res[1]); 
                    maxAverage = res[1];
                    maxSubTree.maxNode = child;
                    System.out.println(" [" + maxSubTree.maxNode.val +"]");
                }
            }

            float average = sum / (childCount+1);
            if(maxAverage < average)
            {                 
                System.out.print(maxAverage +  " < " + average); 
                maxAverage = average;
                maxSubTree.maxNode = root;
                System.out.println(" [" + maxSubTree.maxNode.val +"]");
            }
        }

        return new float[]{sum, maxAverage, childCount + 1};
    }

    public void performTest()
    {
        NaryTreeNode root = new NaryTreeNode(20);
        NaryTreeNode left = new NaryTreeNode(12);
        NaryTreeNode right = new NaryTreeNode(18);
        left.addNodes(new int[]{11,2,3});
        right.addNodes(new int[]{15,8});
        root.addNode(left);
        root.addNode(right);

        NaryTreeNode maxSubTree = getMaxAverageSubTree(root);
        System.out.println("18 = " + maxSubTree.val  );
    }

    public String toString() { 
        return "SubTree With Maximum Average [https://leetcode.com/discuss/interview-question/349617]";
    }

}
