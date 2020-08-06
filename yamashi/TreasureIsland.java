import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


class TreasureIsland implements IInterviewQuestion
{
    public List<int[]> minimumRoute(char[][] maze)
    {
        Queue<int[]> q = new LinkedList<>();
        int count = 0;
        int[][] dir = new int[][] { {0,1},{1,0},{ 0,-1},{-1,0}};
        q.offer(new int[]{0,0});
        maze[0][0]= (char)count++;

        while(q.size() > 0)
        {
            int size = q.size();
            for(int i=0;i<size;i++)
            {
                int[] pos = q.poll();
               // System.out.println(Arrays.toString(pos));
                for(int j=0;j<dir.length;j++)
                {
                    int nx = pos[0] + dir[j][0];
                    int ny = pos[1] + dir[j][1];
                    if(nx < 0 || nx >= maze[0].length || ny < 0 || ny >= maze.length) continue;
                    if(maze[ny][nx]=='O') // only O is walkable
                    {
                        q.offer(new int[] {nx,ny});
                        maze[ny][nx]= (char)count;
                    }
                    else if(maze[ny][nx]=='X')
                    { // found the target
                        System.out.println("Steps: " + (count));
                        List<int[]> res = createSolution(maze, pos[0], pos[1]);  
                        return res;
                    }
                }

            }
            count++;
        }

        return null;        
    }

    public List<int[]> createSolution(char[][] maze, int lastX, int lastY)
    {
        List<int[]> res = new ArrayList<>();
        Stack<int[]> s = new Stack<>();
        System.out.println("Creating the solution.");
        s.push(new int[] {lastX,lastY} );
        int count = (int)(maze[lastY][lastX]);
        count--;
        
        for(int i=0;i<maze.length;i++)
        {
            for(int j=0;j<maze[0].length;j++)
            {
                System.out.print(((char)(maze[i][j]+'0')) + "|");
            }
            System.out.println();
        }

        while(count >= 0)
        {
            int[][] dir = new int[][] { {0,1},{1,0},{ 0,-1},{-1,0}};
            for(int i=0;i<dir.length;i++)
            {
                int nx = lastX + dir[i][0];
                int ny = lastY + dir[i][1];
                if(nx < 0 || nx >= maze[0].length || ny < 0 || ny >= maze.length) continue;
                if((int)maze[ny][nx]==count) 
                {
                    s.push(new int[] {nx, ny});
                    lastX = nx; lastY = ny;
                    count--;
                    break;
                }
            }
        }

        while(!s.isEmpty()) { res.add(s.pop()); };
        
        return res;
    }

    public void performTest()
    {
        List<int[]> res = minimumRoute(new char[][] {
            {'O','O','O','O'},
            {'D','O','D','O'},
            {'O','O','O','O'},
            {'X','D','D','O'},
        });

        for(int[] pos: res)
        {
            System.out.print("(" + pos[1] + "," + pos[0] + ") ,");
        }
        System.out.println();
    }
    
    public String toString() { return "Treasure Island ([I]**) [https://leetcode.com/discuss/interview-question/347457]: ";}

}