#include <iostream>
#include <list>
#include <map>
#include <queue>
#include <string>
#include <set>
#include <stack>

using namespace std;

template <typename T>
class Graph
{
private:
	map<T, list<T>> adjList;
	bool undirected = false;
public:

	void addEdge(T u, T v, bool biDir = true)
	{
		adjList[u].push_back(v);
		if (biDir) { adjList[v].push_back(u); }
		undirected = undirected | biDir;
	}

	void print()
	{
		for (auto pair : adjList)
		{
			int node = pair.first;
			cout << "node " << node << " => ";
			for (auto adjNode : adjList[node])
			{
				cout << adjNode << ", ";
			}
			cout << endl;
		}
	}

	void bfs(T src)
	{
		cout << "BFS: ";
		queue<T> q;
		map<T, bool> visited;

		q.push(src);
		visited[src] = true;

		while (!q.empty())
		{
			T node = q.front();
			cout << node<< " ";
			q.pop();
			for (auto adjNode : adjList[node])
			{
				if (!visited[adjNode])
				{
					q.push(adjNode);
					visited[adjNode] = true;
				}
			}
		}

		cout << endl;
	}

	void sssp(T src, T dest)
	{
		cout << "Single Source Shortest Path: " << endl;
		queue<T> q;
		map<T, int> dist;
		map<T, T> parent;

		for (auto pair : adjList)
		{
			int node = pair.first;
			dist[node] = INT_MAX; // set to infitiy by deault since some node might be in another connected component.
		}

		q.push(src);
		dist[src] = 0;
		parent[src] = src;

		while (!q.empty())
		{
			T node = q.front();
			q.pop();
			for (auto adjNode : adjList[node])
			{
				if (dist[adjNode]== INT_MAX)
				{
					q.push(adjNode);
					dist[adjNode] = dist[node] + 1;
					parent[adjNode] = node;
				}
			}
		}

		for (auto pair : adjList)
		{
			T node = pair.first;
			cout << "The minimum distance from " << src << " to " << node << " = " << dist[node] << endl;
		}

		while (dest != src)
		{
			cout << dest << " <= ";
			dest = parent[dest];
		}
		cout << src << endl;

	}

	void dfs(T src)
	{
		map<T, bool> visited;
		cout << "DFS: ";
		dfsHelper(src, visited);
		cout << endl;
	}

	void dfsHelper(T node, map<T,bool> &visited, list<T>* ordering=NULL)
	{
		visited[node] = true;
		if (ordering == NULL) { 
			cout << node << " "; 
		}

		for (auto adjNode : adjList[node])
		{
			if (!visited[adjNode])
			{
				dfsHelper(adjNode, visited, ordering);
			}
		}

		if (ordering != NULL) // if topological sort is applied, put node in the correct order.
			ordering->push_front(node);
	}

	void listConnectedComponents()
	{
		map<T, bool> visited;
		int count = 0;

		cout << "Connected Components: " << endl;
		for (auto pair : adjList)
		{
			T node = pair.first;
			if (!visited[node])
			{
				count++;
				cout << "Component " << count << ": ";
				dfsHelper(node, visited);
				cout << endl;
			}
		}
	
		cout << "Total Connected Components in Graph = " << count << endl;
	}

	void dfs_topologicalSort()
	{
		if (undirected ==true)
		{
			cout << "DFS TopologicSort won't work in a undirected/bi-directed graph." << endl; 
			return;
		}

		cout << "DFS Topological Sort:";

		map<T, bool> visited;
		list<T> ordering;

		for (auto pair : adjList)
		{
			T node = pair.first;
			if (!visited[node])
			{
				dfsHelper(node, visited, &ordering);
			}
		}
		
		for (auto e : ordering)
		{
			cout << e << " => ";
		}
		cout << endl;
	}

	void bfs_topologicalSort()
	{
		map<T, bool> visited;
		map<T, int> indegree;
		queue<T> q;
		
		if (undirected == true)
		{
			cout << "BFS TopologicSort won't work in a undirected/bi-directed graph." << endl;
			return;
		}

		for (auto pair : adjList)
		{
			T node = pair.first;
			indegree[node] = 0;
			visited[node] = false;
		}

		for (auto pair : adjList)
		{
			T node = pair.first;
			for (auto adjNode : adjList[node])
			{
				indegree[adjNode]++;
			}
		}

		for (auto pair : adjList)
		{
			T node = pair.first;
			if (indegree[node] == 0)
				q.push(node);
		}

		while (!q.empty())
		{
			T node = q.front();
			q.pop();
			cout << node << " => ";
			for (auto adjNode : adjList[node])
			{
					indegree[adjNode]--;
				if (indegree[adjNode] == 0)
				{
					q.push(adjNode);
				}
			}

		}

		cout << endl;
	}

	bool BFS_detectCycle_In_UndirectedGraph(T src)
	{
		queue<T> q;
		map<T, bool> visited;
		map<T, T> parent;

		q.push(src);
		parent[src] = src;

		while (!q.empty())		
		{
			T node = q.front();
			q.pop();
			for (auto adjNode : adjList[node])
			{
				if (!visited[adjNode])
				{
					visited[adjNode] = true;
					parent[adjNode] = node;
					q.push(adjNode);
				}
				else if (visited[adjNode] && parent[node] != adjNode)
				{
					return true;
				}
			}
		}

		return false;
	}

	bool isCyclicHelper(T node, map<T, bool> &visited, map<T, bool> inStack) {
		visited[node] = true;
		inStack[node] = true; // it is now in the visiting path.

		for (T neighbour : adjList[node]) {
			if (!visited[neighbour] && isCyclicHelper(neighbour, visited, inStack) || inStack[neighbour])
			{
				return true;
			}
		}

		inStack[node] = false;
		return false;
	}

	bool DFS_detectCycle_In_directedGraph(T src)
	{
		map<T, bool> visited;
		map<T, bool> inStack;

		for (auto pair : adjList)
		{
			T node = pair.first;
			if (!visited[node] && isCyclicHelper(node, visited, inStack)) {
				return true;
			}
		}
		return false;
	}

	void DFS_AllPathsHelper(T node, T dest, map<T, bool> visited, map<T, bool> inStack, list<T> &path )
	{
		visited[node] = true;
		inStack[node] = true;
		path.push_back(node);

		if (node == dest)
		{
			for (auto e : path)
			{
				cout << e << " => ";
			}
			cout << endl;
		}

		for (T adjNode : adjList[node])
		{
			if (!visited[adjNode] )
			{ 
				DFS_AllPathsHelper(adjNode, dest, visited, inStack, path);
			}
		}

		path.remove(node);
	}

	void DFS_AllPaths(T src, T dest)
	{
		cout << "All possible paths from " << src << " to " << dest << ":" << endl;
		map<T, bool> visited;
		map<T, bool> inStack;
		list<T> path;

		for (auto pair : adjList)
		{
			T node = pair.first;
			if (node== src && !visited[node] ) {
				DFS_AllPathsHelper(node, dest, visited, inStack, path);
			}
		}

	}

	// Amazon inteview questions
	// https://leetcode.com/discuss/interview-question/436073/
	// https://leetcode.com/discuss/interview-question/372581
	// Theory: https://cp-algorithms.com/graph/cutpoints.html
	// geek-for-geek: https://www.geeksforgeeks.org/articulation-points-or-cut-vertices-in-a-graph/
	void listArticulationPoints()
	{
		map<T, T> parent;
		map<T, bool> visited;
		map<T, int> disc;
		map<T, int> low;
		set<T> cutPoints; // found points

		for (auto pair : adjList)
		{
			T node = pair.first;
			parent[node] = -1; // any node can potentially be a root
			low[node] = disc[node] = 0;
			visited[node] = false;
		}

		for (auto pair : adjList)
		{
			T node = pair.first;
			if (!visited[node])
				findCutPoints(node, visited, parent, disc, low, cutPoints);
		}

		cout << "AP: [";
		for (T e : cutPoints)
		{
			cout << e << " ";
		}
		cout << "]" << endl;
	}


	void findCutPoints(T node, map<T, bool> &visited, map<T, T> &parent, map<T, int> &disc, map<T, int> &low, set<T> &cutPoints)
	{
		static int time = 0;
		int childCount = 0;
		visited[node] = true;
		time++;
		disc[node] = time;
		low[node] = time;

		for (T adjNode : adjList[node])
		{
			if (!visited[adjNode])
			{
				childCount++;
				parent[adjNode] = node;
				findCutPoints(adjNode, visited, parent, disc, low, cutPoints);

				low[node] = min(low[node], low[adjNode]);
				
				// (1) u is root of DFS tree and has two or more chilren. 
				if (parent[node] == node && childCount > 1)
					cutPoints.insert(node);
				// (2) If u is not root and low value of one of its child is more 
				// than discovery value of u. 
				else if (parent[node] != node && low[adjNode] >= disc[node]) // > for bridges, >= for points
					cutPoints.insert(node);
			}
			else if (adjNode != parent[node]) // Update low value of u for parent function calls.
				low[node] = min(low[node], disc[adjNode]);
		}
	}

	// https://leetcode.com/discuss/interview-question/372581
	void listBridges()
	{
		map<T, T> parent;
		map<T, bool> visited;
		map<T, int> disc;
		map<T, int> low;
		map<T, list<T>> bridges; // found points

		for (auto pair : adjList)
		{
			T node = pair.first;
			parent[node] = node; // any node can potentially be a root
			low[node] = disc[node] = 0;
			visited[node] = false;
		}

		for (auto pair : adjList)
		{
			T node = pair.first;
			if (!visited[node])
				findCutEdges(node, visited, parent, disc, low, bridges);
		}

		cout << "Bridges: ";
		for (auto pair : bridges)
		{
			T node = pair.first;
			for (T e : bridges[node])
			{
				cout << "[" << node << " " << e << "], ";
			}
		}
		cout  << endl;
	}

	void findCutEdges(T node, map<T, bool> &visited, map<T, T> &parent, map<T, int> &disc, map<T, int> &low, map<T, list<T>> &bridges)
	{
		static int time = 0;
		int childCount = 0;
		visited[node] = true;
		time++;
		disc[node] = time;
		low[node] = time;

		for (T adjNode : adjList[node])
		{
			if (!visited[adjNode])
			{
				childCount++;
				parent[adjNode] = node;
				findCutEdges(adjNode, visited, parent, disc, low, bridges);

				low[node] = min(low[node], low[adjNode]);

				if (low[adjNode] > disc[node]) // > for bridges, >= for points
					bridges[node].push_back(adjNode);
			}
			else if (adjNode != parent[node]) // Update low value of u for parent function calls.
				low[node] = min(low[node], disc[adjNode]);
		}
	}


};

template <typename T>
class WeightGraph
{
private:
	map<T, list<pair<T, int>> > adjList;
public:
	void addEdge(T u, T v, int weight, bool biDir = true)
	{
		adjList[u].push_back( make_pair(v, weight) );
		if (biDir) { adjList[v].push_back( make_pair(u , weight) ); }
	}

	void print()
	{
		for (auto pair : adjList)
		{
			int node = pair.first;
			cout << "node " << node << " => ";
			for (auto wAdjPair : adjList[node])
			{
				T adjNode = wAdjPair.first;
				int weight = wAdjPair.second;

				cout << "(" << adjNode << "," << weight  << ") , ";
			}
			cout << endl;
		}
	}
	
};

int main(int argc, char* argv[])
{
	Graph<int> g;
	g.addEdge(0, 1);
	g.addEdge(1, 2);
	g.addEdge(2, 3);
	g.addEdge(3, 4);
	g.addEdge(3, 0);

	g.addEdge(5, 6);

	g.addEdge(7, 8);
	g.addEdge(8, 9);
	g.addEdge(9, 7);

	g.print();
	g.bfs(0);

	g.sssp(0, 4);
	g.dfs(0);

	g.listConnectedComponents();
	cout << "1-4 cycle:" << g.BFS_detectCycle_In_UndirectedGraph(0) << endl;
	cout << "5,6 cycle:" << g.BFS_detectCycle_In_UndirectedGraph(5) << endl;
	cout << "7-8-9 cycle:" << g.BFS_detectCycle_In_UndirectedGraph(7) << endl;

	g.DFS_AllPaths(0, 4);

	Graph<string> g1;
	g1.addEdge("English", "HTML", false);
	g1.addEdge("English", "Logic", false);
	g1.addEdge("Logic", "HTML", false);
	g1.addEdge("HTML", "Web Dev", false);
	g1.addEdge("Math", "Logic", false);
	g1.addEdge("Python", "Web Dev", false);

	g1.addEdge("Logic", "Python", false);

	g1.dfs_topologicalSort();
	g1.bfs_topologicalSort();

	Graph<int> g2;
	g2.addEdge(1, 2);
	g2.addEdge(1, 3);
	g2.addEdge(3, 4);
	g2.addEdge(1, 4);
	g2.addEdge(4, 5);


	g2.listArticulationPoints();
	g2.listBridges();
	g2.DFS_AllPaths(0, 4);

	Graph<int> g3;
	g3.addEdge(0, 2, false);
	g3.addEdge(0, 1, false);
	g3.addEdge(2, 3, false);
	g3.addEdge(2, 4, false);
	g3.addEdge(3, 0, false);
	g3.addEdge(4, 5, false);
	g3.addEdge(1, 5, false);
	g3.DFS_AllPaths(0, 5);

	cout << "DFS Detect Cycle in directed Graph:" << g3.DFS_detectCycle_In_directedGraph(0) << endl;


	return 0;
}
