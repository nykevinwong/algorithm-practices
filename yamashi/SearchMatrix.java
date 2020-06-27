
public class SearchMatrix implements IInterviewQuestion
{
    public boolean searchMatrix(int[][] matrix, int target) {
        if(matrix==null || matrix.length==0 || matrix[0].length==0 ) return false;
        int col = matrix[0].length-1;
        int row = 0;
        
        while(row < matrix.length && col >= 0)
        {
            if(matrix[row][col]==target) return true;
            if(matrix[row][col] < target) row++;
            else col--;
        }
        
        return false;
    }

    public void performTest()
    {
        int[][] m = new int[][] {
            {1,   4,  7, 11, 15},
            {2,   5,  8, 12, 19},
            {3,   6,  9, 16, 22},
            {10, 13, 14, 17, 24},
            {18, 21, 23, 26, 30}
        };

        Helper.equals(searchMatrix(m, 16), true, "Search 16 ");
        Helper.equals(searchMatrix(m, 13), true, "Search 13 ");
        Helper.equals(searchMatrix(m, 100), false, "Search 100 ");
    }

    public String toString() { return "Seach a 2D Matrix ([N]**) [https://leetcode.com/problems/search-a-2d-matrix-ii]: ";}
}
