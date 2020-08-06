import java.util.LinkedList;
import java.util.Queue;

class TreasureIsland2 implements IInterviewQuestion
{
    public int minimumRoute(char[][] maze)
    {
        Queue<int[]> q = new LinkedList<>();
        int[][] dir = new int[][] { {0,1}, {0,-1}, {1,0}, {-1,0} };

        for(int i=0;i< maze.length;i++)
        {
            for(int j=0; j < maze[0].length;j++)
            {
                if(maze[i][j]=='S')
                {
                    q.add(new int[] {j, i});
                }
            }
        }
        
        int count = 0;
        while(q.size() > 0)
        {
            int size = q.size();

            for(int i=0;i<size;i++)
            {
                int[] pos = q.poll();
                for(int k=0;k<dir.length;k++)
                {
                    int nx = pos[0] + dir[k][0];
                    int ny = pos[1] + dir[k][1];
                    if(nx <0 || nx >= maze[0].length || ny <0 || ny >= maze.length) continue;

                    if(maze[ny][nx]=='O') 
                    {
                        maze[ny][nx] = 'D'; // visited
                        q.add(new int[] {nx, ny });
                    }
                    else if(maze[ny][nx]=='X')
                    {
                        return count; // found it
                    }
                }
            }
            count++;
        }

        return count;
    }

    public void performTest()
    {
        int minStep = minimumRoute(new char[][] {
            {'S','O','O','S','S'},
            {'D','O','D','O','D'},
            {'O','O','O','O','O'},
            {'X','D','D','O','O'},
            {'X','D','D','D','O'},
        });

        System.out.println("Min Steps: " + minStep);
    }
    
    public String toString() { return "Treasure Island 2 [https://leetcode.com/discuss/interview-question/356150]: ";}
}
