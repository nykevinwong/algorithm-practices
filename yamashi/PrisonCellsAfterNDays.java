import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;

class PrisonCellsAfterNDays implements IInterviewQuestion {

    public int[] prisonAfterNDays(int[] cells, int N) {
        if(cells==null || cells.length == 0 || N <= 0) return cells;
            Set<String> set = new HashSet<>();
            
            while(0 < N)
            {
                int[] next = nextDayCell(cells);
                String curKey = Arrays.toString(next);
                
                if(set.contains(curKey))
                {                
                    N%=set.size();
                    while(0 < N--) cells  = nextDayCell(cells);
                    
                    return cells;
                }
                else
                {
                    set.add(curKey);
                }
                
                
                cells = next;   
                N-=1;
            }
            
            return cells;
        }
        
        public int[] nextDayCell(int[] cells)
        {
            int[] temp = new int[cells.length];
            
            for(int i=1;i<cells.length-1;i++)
            {
                temp[i] = (cells[i-1]==cells[i+1]) ? 1:0;        
            }
            return temp;
        }

    public void performTest()
    {
        Helper.equals(prisonAfterNDays(new int[]{1,0,0,1,0,0,1,0}, 1000000000) ,
            new int[]{0,0,1,1,1,1,1,0} , "prison After N Days:");

    }

    
    public String toString() { 
        return "prison After N Days [https://leetcode.com/problems/prison-cells-after-n-days/]";
    }
}