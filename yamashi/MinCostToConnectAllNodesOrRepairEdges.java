import java.util.*;

class UF {
    private int[] parent;
    private int[] rank;
    private int count;

    public UF(int N)
    {
        count = N;
        parent = new int[N];
        rank = new int[N];
        for(int i=0;i<N;i++)
        {
            parent[i]=i;
            rank[i]=0;
        }
    }

    public int find(int x)
    {
        while(x!=parent[x])
        {
            parent[x] = parent[parent[x]]; // path compresion
            x = parent[x];
        }

        return x;
    }

    // union by rank
    public void union(int x, int y)
    {
        int rootX = find(x);
        int rootY = find(y);
        if(rootX==rootY) return;
        if(rank[rootX] < rank[rootY])
        {
            parent[rootX] = rootY;
        }
        else
        {
            parent[rootY] = rootX;
            if(rank[rootX] == rank[rootY]) rank[rootX]++;            
        }
        count--;
    }

    public int count() { return count; }
    public boolean connected(int x, int y) { return find(x)==find(y); }
}

class MinCostToConnectAllNodesOrRepairEdges implements IInterviewQuestion {

    public static int minCost(int n, int[][] edges, int[][] newEdges) {
        UF uf = new UF(n + 1); // + 1 because nodes are 1-based
        
        Queue<int[]> pq = new PriorityQueue<>(newEdges.length, (e1, e2) -> Integer.compare(e1[2], e2[2]));
        HashSet<String> added = new HashSet<>();
        
        for (int[] edge : newEdges) {
            pq.offer(edge);
            added.add(edge[0]+","+edge[1]);
        }
                
        for (int[] edge : edges) {
            if(!added.contains(edge[0]+","+edge[1])) // ensure the same broken edge is not added.
            {
                pq.offer(new int[]{edge[0],edge[1],0});
            }
        }
        
        int totalCost = 0;
        // 2 because nodes are 1-based and we have 1 unused component at index 0
        while (!pq.isEmpty() && uf.count() != 2) {
            int[] edge = pq.poll();
            if (!uf.connected(edge[0], edge[1])) {
                uf.union(edge[0], edge[1]);
                totalCost += edge[2];
            }
        }
        return totalCost;
    }

    public void performTest()
    {
        // below tests are for new edges, new roads and etc. new edge must be edges never in MST before.
        int n = 6;
        int[][] edges = {{1, 4}, {4, 5}, {2, 3}};
        int[][] newEdges = {{1, 2, 5}, {1, 3, 10}, {1, 6, 2}, {5, 6, 5}};
        System.out.println(minCost(n, edges, newEdges));
        Helper.equals( minCost(n, edges, newEdges), 7 , "new edges ->" );

        // below tests are for broken edges. broken edges must be edges already in MST
        n = 5;
        edges = new int[][] {{1, 2}, {2, 3}, {3, 4}, {4, 5}, {1, 5}};
        int[][] brokenEdges = new int[][] {{1, 2, 12}, {3, 4, 30}, {1, 5, 8}};
        Helper.equals( minCost(n, edges, brokenEdges), 20 , "broekn edges ->" );

        n = 6;
        edges = new int[][] {{1, 2}, {2, 3}, {4, 5}, {3, 5}, {1, 6}, {2, 4}};
        brokenEdges = new int[][] {{1, 6, 410}, {2, 4, 800}};
        Helper.equals( minCost(n, edges, brokenEdges), 410 , "broekn edges ->" );

        n = 6;
        edges = new int[][] { {1, 2}, {2, 3}, {4, 5}, {5, 6}, {1, 5}, {2, 4}, {3, 4} };
        brokenEdges = new int[][] { {1, 5, 110}, {2, 4, 84}, {3, 4, 79} };
        Helper.equals( minCost(n, edges, brokenEdges), 79 , "broekn edges ->" );

/*
Example 2:

Input: n = 6, edges = [[1, 2], [2, 3], [4, 5], [3, 5], [1, 6], [2, 4]], edgesToRepair = [[1, 6, 410], [2, 4, 800]]
Output: 410
Example 3:

Input: n = 6, edges = [[1, 2], [2, 3], [4, 5], [5, 6], [1, 5], [2, 4], [3, 4]], edgesToRepair = [[1, 5, 110], [2, 4, 84], [3, 4, 79]]
Output: 79
*/
        
    }

    public String toString() { 
        return "Min Cost to Connect All Nodes (a.k.a. Min Cost to Add New Roads) [https://leetcode.com/discuss/interview-question/356981]";
    }
}