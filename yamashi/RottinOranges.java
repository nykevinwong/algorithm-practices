import java.util.LinkedList;
import java.util.Queue;


public class RottinOranges  implements IInterviewQuestion {
    //https://leetcode.com/problems/rotting-oranges/
    // Zombie in Matrix: https://leetcode.com/discuss/interview-question/411357/
    // Minium hours to send file to all available servers
    public int orangesRotting(int[][] grid) {
        Queue<Integer[]> q = new LinkedList<>();
        int count =0;
        // push source noddes
        for(int i=0;i<grid.length;i++)
            for(int j=0;j<grid[0].length;j++)
            {
                if(grid[i][j]==2) q.offer(new Integer[] {i,j});
                if(grid[i][j]==1) count++;
            }
        
        if(count==0) return 0; // speicial case. no fresh orange at all from start.
        
        int[][] dir = new int[][]{{0,1},{1,0},{0,-1},{-1,0}};
        int time = -1;
        
        while(q.size() > 0)
        {
            int size = q.size();            
            for(int i=0;i<size;i++) // make sure to go through all nodes in current queue
            {
                Integer[] pos = q.poll(); // get position                        
                for(int j=0;j<dir.length;j++) // get adjacents of adjcent node.
                {
                    int nx= pos[0] + dir[j][0];
                    int ny =pos[1] + dir[j][1];

                    if(nx < 0 || ny < 0 || nx >= grid[0].length || ny >= grid.length) continue;
                    
                    if(grid[ny][nx]==1) // push an adjecent neighor which is a fresh orange in this case
                    { // in scoope
                        count--;
                        grid[ny][nx] = 2; // rotten the tomato, mark as visited
                        q.offer(new Integer[]{ny,nx}); 
                    }
                }      
            }
            
            time++;
        }
        
        return (count==0) ? time:-1; //-1 also means it's not possible to rotten every orange.
    }

    public void performTest()
    {
        Helper.equals(orangesRotting(new int[][] { {2,1,1},{1,1,0},{0,1,1} }), 4, "Min minutes to rotten all oranges: ");
        Helper.equals(orangesRotting(new int[][] { {2,1,1},{0,1,0},{1,0,1} }), -1, "Min minutes to rotten all oranges: ");
        Helper.equals(orangesRotting(new int[][] { {2,2,2},{0,2,0},{2,0,2} }), 0, "Min minutes to rotten all oranges: ");
    }

    public String toString() { return "Zombie in Matrix [Min hours to send file to all available servers] ([E]***):";}
}
