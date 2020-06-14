
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;



public class CriticalRoutersOrConnections implements IInterviewQuestion 
{
    class Graph<T>
    {
        private Map<T, List<T>> adjList = new HashMap<>();
        private int time = 0;

        public void addEdges(T[][] edges)
        {
            for(int i=0;i<edges.length;i++)
            {
                addEdge(edges[i][0], edges[i][1]);
            }
        }

        public void clear()
        {
            adjList.clear();
        }

        public void addEdge(T u, T v)
        {
            addEdge(u, v, true);
        }

        public void addEdge(T u, T v, boolean biDir)
        {
            adjList.put(u, adjList.getOrDefault(u, new ArrayList<T>()));
            adjList.get(u).add(v);
            if(biDir)
            {
                adjList.put(v, adjList.getOrDefault(v, new ArrayList<T>()));
                adjList.get(v).add(u);
            }
        }        

        public void findCutPoints(T node, Map<T, Boolean> visited, Map<T, T> parent,  Map<T, Integer> disc, Map<T, Integer> low, Set<T> ap, Map<T, List<T>> bridges)
        {
            int children = 0;
            visited.put(node, true);
            time++;
            disc.put(node, time);
            low.put(node, time);
                        
            for(T adjNode: adjList.get(node))
            {
                if(visited.get(adjNode)==false)
                {
                    children++;
                    parent.put(adjNode,node);
                    findCutPoints(adjNode, visited, parent, disc, low, ap, bridges);
                    low.put(node, Math.min(low.get(node), low.get(adjNode) ) );
                    
                    // this piece of code is to determine cut points
                    if(ap!=null)
                    {
                        if(parent.get(node)==null && children > 1) ap.add(node);
                        if(parent.get(node) != null && low.get(adjNode) >= disc.get(node)) // > for bridges, >= for points
                            ap.add(node);
                    }

                    // this piece of code is to determine. > for bridges, >= for points
                    if(bridges != null)
                    {
                        if(low.get(adjNode) > disc.get(node)) {
                            bridges.put(node, bridges.getOrDefault(node, new ArrayList<T>()) );
                            bridges.get(node).add(adjNode);
                        }
                    }

                }
                else if(visited.get(adjNode)== true && adjNode != parent.get(node) )
                {
                    low.put(node, Math.min(low.get(node), disc.get(adjNode) ) );
                }

            }
        }

        public Set<T> findCutPoints()
        {
            Map<T, Boolean> visited = new HashMap<>();
            Map<T, T> parent= new HashMap<>();
            Map<T, Integer> low= new HashMap<>();
            Map<T, Integer> disc= new HashMap<>();
            Set<T> ap = new HashSet<T>();
            time = 0;
            for(T node: adjList.keySet())
            {
                visited.put(node, false);
                parent.put(node, null); // root by deafult
                low.put(node, 0);
                disc.put(node, 0);
            }
    
            for(T node: adjList.keySet())
            {
                if(visited.get(node)==false)
                findCutPoints(node, visited, parent, disc, low, ap, null);
            }

            return ap;
        }

        public Map<T, List<T>> findBridges()
        {
            Map<T, Boolean> visited = new HashMap<>();
            Map<T, T> parent= new HashMap<>();
            Map<T, Integer> low= new HashMap<>();
            Map<T, Integer> disc= new HashMap<>();
            Map<T, List<T>> bridges = new HashMap<>();

            time = 0;

            for(T node: adjList.keySet())
            {
                visited.put(node, false);
                parent.put(node, null); // root by deafult
                low.put(node, 0);
                disc.put(node, 0);
            }
    
            for(T node: adjList.keySet())
            {
                if(visited.get(node)==false)
                findCutPoints(node, visited, parent, disc, low, null, bridges);
            }

            return bridges;
        }
    }

    public void performTest()
    {
        Graph<Integer>  g = new Graph<>();

        // https://leetcode.com/discuss/interview-question/436073/
        g.addEdges(new Integer[][] { {0, 1}, {0, 2}, {1, 3}, {2, 3}, {2, 5}, {5, 6}, {3, 4} });
        System.out.println("Cut Points :" +  g.findCutPoints() );

        // https://leetcode.com/problems/critical-connections-in-a-network/
        g.clear();
        g.addEdges(new Integer[][] { {1, 2}, {1, 3}, {3, 4}, {1, 4}, {4, 5}} );
        System.out.println("Bridges (Cut Edges) :" + g.findBridges() );
        g.clear();
        g.addEdges(new Integer[][] { {1, 2}, {1, 3}, {2, 3}, {2, 4}, {2, 5}, {4, 6}, {5, 6} } );
        System.out.println("Bridges (Cut Edges) :" + g.findBridges() );
        g.clear();
        g.addEdges(new Integer[][] { {1, 2}, {1, 3}, {2, 3}, {3, 4}, {3, 6}, {4, 5}, {6, 7}, {6, 9}, {7, 8}, {8, 9} } );
        System.out.println("Bridges (Cut Edges) :" + g.findBridges() );
    }

    public String toString() { return "Critical Routers & Connections, Articulation Points/Cut Point, Bridges/Cut Edges ([E]**):";}

}
