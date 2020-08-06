import java.util.*;

class MergeIntervals implements IInterviewQuestion
{
    public int[][] merge(int[][] intervals) {
		if (intervals== null || intervals.length <= 1) return intervals;

		// Sort by ascending starting point
		Arrays.sort(intervals, (i,j) -> i[0]==j[0] ? i[1]-j[1]:i[0]-j[0]);

		LinkedList<int[]> llRes = new LinkedList<>();
		llRes.add(intervals[0]);
        
		for (int i=1;i < intervals.length;i++) {
            int[] merged = llRes.getLast();                        
			if (intervals[i][0] <= merged[1]) // Overlapping intervals
            {
                llRes.removeLast();
				merged[1] = Math.max(merged[1], intervals[i][1]);
                llRes.addLast(merged);
            }
			else { // disjoint intervals                          
				llRes.add(intervals[i]);
			}
		}

		return llRes.toArray(new int[llRes.size()][]);
    }

    public void performTest()
    {
        int[][] res = merge(new int[][] { {1,3},{2,6},{8,10},{15,18} });
        Helper.arrayEquals(res, new int[][] { {1,6}, {8,10}, {15,18} } );
    }

    public String toString() { 
        return "Merge Intervals (*) [https://leetcode.com/problems/merge-intervals/]";
    }
}
