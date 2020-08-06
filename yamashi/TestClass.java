/* IMPORTANT: Multiple classes and nested static classes are supported */



//imports for BufferedReader
import java.io.BufferedReader;
import java.io.InputStreamReader;

//import for Scanner and other utility classes
import java.util.*;


// Warning: Printing unwanted or ill-formatted data to output will cause the test cases to fail

class TestClass {

    public class NQueen 
    {
        int[][] board;
        int N;
        HashSet<int[]> queenPos = new HashSet<>();

        public NQueen(int N)
        {
            this.N = N;
            board = new int[N][N];
        }

        private boolean isValidSpot(int x, int y)
        {
            for(int i=0;i<board.length;i++)
                if(board[i][x]==1) return false;

            for(int j=0;j<board[0].length;j++)
                if(board[y][j]==1) return false;

            int[][] d = new int[][] {
                new int[] {1,1},
                new int[] {1,-1},
                new int[] {-1,1},
                new int[] {-1,-1}
            };

            int[][] pos = new int[][] {
                new int[] {x,y},
                new int[] {x,y},
                new int[] {x,y},
                new int[] {x,y}
            };

            HashSet<Integer> set = new HashSet<>();

            while(set.size() < 4)
            {
                for(int i=0;i<4;i++)
                {
                    pos[i][0] += d[i][0];
                    pos[i][1] += d[i][1];
                    int px = pos[i][0];
                    int py = pos[i][1];
                    if(px < 0 || px >= board[0].length || py <0 || py >= board.length) 
                    {
                        set.add(i);
                         continue;
                    }
                    if(board[py][px]==1) return false;
                }
            }

            return true;
        }

        public boolean solveNQueen()
        {
            return solveNQueen(N, 0,0);
        }

        private boolean solveNQueen(int count, int x1, int y1)
        {            
            if(count==0) return true; // this is the solution.

            for(int y=y1;y<board.length;y++)
            {
                for(int x=x1;x<board[0].length;x++)
                {
                    if(isValidSpot(x, y))
                    {
                        board[y][x]=1; 
                        if(solveNQueen(count-1, x1 +1, y1+1)) return true;
                        board[y][x]=0;
                    }
                }
            }

            return false;
        }

        @Override
        public String toString()
        {
            StringBuilder sb = new StringBuilder();
            for(int y=0;y<board.length;y++)
            {
                for(int x=0;x<board[0].length;x++)
                {
                    sb.append(board[y][x]+ " ");
                }                
                sb.append("\n");
            }

            return sb.toString();
        }

    }

    public static void main(String args[] ) throws Exception {
        //Scanner
        //Scanner s = new Scanner(System.in);
        int N = 4;//Integer.parseInt( s.nextLine() );                 // Reading input from STDIN

        TestClass test = new TestClass();

        test.Test(N);
        
    }

    public void Test(int N)
    {
        // Write your code here
        
        NQueen nq = new NQueen(N);

        if(!nq.solveNQueen())  System.out.println("NO");
        else
        {
             System.out.println("YES");
        }

             System.out.println(nq);

    }

}
