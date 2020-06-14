

public class NumberOfClusters implements IInterviewQuestion {
    // questions: https://www.glassdoor.com/Interview/Problem-2d-grid-each-node-has-1-colors-find-number-of-clusters-of-a-given-color-red-blue-green-blue-gre-QTN_2930567.htm
    // https://leetcode.com/problems/number-of-islands/
    public int[] numberOfClusters(char[][] grid) {
        char[] colors = new char[] {'0','1','2'};
        int[] count = new int[] { 0,0,0};

        for(int i=0;i<grid.length;i++)
        {
            for(int j=0;j<grid[0].length;j++)
            {
                for(int k=0;k<colors.length;k++)
                {
                    if(grid[i][j]==colors[k]) 
                    {
                        dfs(grid, i, j, colors[k]);
                        count[k]++;
                        break; // break; no need to check other colors.
                    }
                }
            }
        }

      //  System.out.println("counts: " + Arrays.toString(count));
        return count;
    }
    
    public void dfs(char[][] grid, int y, int x, char target)
    {
        if(x < 0 || x >= grid[0].length || y < 0 || y >= grid.length ) return;        
        if(grid[y][x]!= target) return;
        
        grid[y][x]= 'v'; //marked as visited
        
        dfs(grid, y+1, x, target);
        dfs(grid, y-1, x, target);
        dfs(grid, y, x+1, target);
        dfs(grid, y, x-1, target);
    }

    public void performTest()
    {
        Helper.equals( numberOfClusters(new char[][] {
            {'1','1','0','1','0'},
            {'1','2','0','1','0'},
            {'1','2','2','2','2'},
            {'0','0','1','1','0'},
        }), new int[] {4,3,1} ,"Number of Culsters :");
        
    }

    public String toString() { return "Number Of Clusters ([E]**):";}
}
