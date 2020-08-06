import java.util.Arrays;
import java.util.PriorityQueue;

class KClosetPointstoOrigin implements IInterviewQuestion  {

    public int[][] KClosetPoints_Sort(int[][] points, int K) {
        Arrays.sort(points, (p1, p2) -> (p1[0]*p1[0] +p1[1]*p1[1]) - (p2[0]*p2[0] +p2[1]*p2[1]) );
        // int[][] temp = new int[K];
        // for(int i=0;i<K;i++) temp[i]=points[i];
        // return temp;
        return Arrays.copyOfRange(points, 0, K);
    }

    public int[][] KClosetPoints_PQ(int[][] points, int K) {
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>(  
            (p1, p2) -> (p2[0]*p2[0] +p2[1]*p2[1]) - (p1[0]*p1[0] +p1[1]*p1[1])  );
        // Max heap means get max value when polling.
        // polled values from the max heap are in an descending order

        // this trick maintains a size K of Max PriorityQueue (Max Heap) to stay N*log(k) time complexity.
        for(int[] p:points) 
        {
            pq.offer(p);
            if(pq.size() > K)  pq.poll();
        }
        // all first N-K items are polled. only last K items remained.
        int[][] res = new int[K][2];
        while(K > 0) res[--K] = pq.poll();
        return res;
    }

    public int[][] KClosetPoints_QSelect(int[][] points, int K) {
        int len =  points.length, l = 0, r = len - 1;
        while (l <= r) {
            int mid = helper(points, l, r);
            if (mid == K) break;
            if (mid < K) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return Arrays.copyOfRange(points, 0, K);
    }
    
    private int helper(int[][] arr, int l, int r) {
        int[] pivot = arr[l];

        while (l < r) {
            while (l < r && compare(arr[r], pivot) >= 0) r--;
            arr[l] = arr[r];
            while (l < r && compare(arr[l], pivot) <= 0) l++;
            arr[r] = arr[l];
        }

        arr[l] = pivot;
        return l;
    }
    
    private int compare(int[] p1, int[] p2) {
        return p1[0] * p1[0] + p1[1] * p1[1] - p2[0] * p2[0] - p2[1] * p2[1];
    }


    public void performTest()
    {
        System.out.println("KClosetPoints_Sort: " + Helper.arrayEquals(KClosetPoints_Sort(new int[][] { {-5,4}, {-6,-5}, {4,6} }, 2) , new int[][] { {-5, 4}, {4,6} } ) ) ;
        System.out.println("KClosetPoints_PQ: " + Helper.arrayEquals(KClosetPoints_PQ(new int[][] { {-5,4}, {-6,-5}, {4,6} }, 2) , new int[][] { {-5, 4}, {4,6} } ) );
        System.out.println("KClosetPoints_QSelect: " + Helper.arrayEquals(KClosetPoints_QSelect(new int[][] { {-5,4}, {-6,-5}, {4,6} }, 2) , new int[][] { {-5, 4}, {4,6} } ) );

    }

    
    public String toString() { 
        return "K Closest Points to Origin ([I] *)[https://leetcode.com/problems/k-closest-points-to-origin/discuss/220235/Java-Three-solutions-to-this-classical-K-th-problem.]";
    }
}
