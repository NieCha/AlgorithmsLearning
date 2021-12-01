- 图的基本创建

  ```java
  import java.util.HashMap;
  import java.util.HashSet;
  
  public class Graph {
  	public HashMap<Integer,Node> nodes;
  	public HashSet<Edge> edges;
  
  	public Graph() {
  		nodes = new HashMap<>();
  		edges = new HashSet<>();
  	}
  }
  
  public class Edge {
  	public int weight;
  	public Node from;
  	public Node to;
  
  	public Edge(int weight, Node from, Node to) {
  		this.weight = weight;
  		this.from = from;
  		this.to = to;
  	}
  }
  
  public class Node {
  	public int value;
  	public int in;
  	public int out;
  	public ArrayList<Node> nexts;
  	public ArrayList<Edge> edges; //指向其它节点的边
  
  	public Node(int value) {
  		this.value = value;
  		in = 0;
  		out = 0;
  		nexts = new ArrayList<>();
  		edges = new ArrayList<>();
  	}
  }
  
  public class GraphGenerator {
  	public static Graph createGraph(Integer[][] matrix) {
  		Graph graph = new Graph();
  		for (int i = 0; i < matrix.length; i++) {
  			Integer weight = matrix[i][0];
  			Integer from = matrix[i][1];
  			Integer to = matrix[i][2];
  			if (!graph.nodes.containsKey(from)) {
  				graph.nodes.put(from, new Node(from));
  			}
  			if (!graph.nodes.containsKey(to)) {
  				graph.nodes.put(to, new Node(to));
  			}
  			Node fromNode = graph.nodes.get(from);
  			Node toNode = graph.nodes.get(to);
  			Edge newEdge = new Edge(weight, fromNode, toNode);
  			fromNode.nexts.add(toNode);
  			fromNode.out++;
  			toNode.in++;
  			fromNode.edges.add(newEdge);
  			graph.edges.add(newEdge);
  		}
  		return graph;
  	}
  }
  ```

- 图的宽度优先遍历BFS

  ```java
  1，利用队列实现
  2，从源节点开始依次按照宽度进队列，然后弹出
  3，每弹出一个点，把该节点所有没有进过队列的邻接点放入队列
  4，直到队列变空
  public static void bfs(Node node) {
      if (node == null) {
          return;
      }
      Queue<Node> queue = new LinkedList<>();
      HashSet<Node> map = new HashSet<>();   //判断包含关系 使之适用于有向图
      queue.add(node);
      map.add(node);
      while (!queue.isEmpty()) {
          Node cur = queue.poll();
          System.out.println(cur.value);
          for (Node next : cur.nexts) {
              if (!map.contains(next)) {
                  map.add(next);
                  queue.add(next);
              }
          }
      }
  }
  ```

- 深度优先遍历

  ```java
  1，利用栈实现
  2，从源节点开始把节点按照深度放入栈，然后弹出
  3，每弹出一个点，把该节点下一个没有进过栈的邻接点放入栈
  4，直到栈变空
  public static void dfs(Node node) {
      if (node == null) {
          return;
      }
      Stack<Node> stack = new Stack<>();
      HashSet<Node> set = new HashSet<>();
      stack.add(node);
      set.add(node);
      System.out.println(node.value);
      while (!stack.isEmpty()) {
          Node cur = stack.pop();
          for (Node next : cur.nexts) {
              if (!set.contains(next)) {
                  stack.push(cur);		//再压入的原因是 后面加入了break, 每次读完一个子节点重新开始，这也是为了适用于无向图（防止当前node指向压入未读取node）
                  stack.push(next);
                  set.add(next);
                  System.out.println(next.value);
                  break;
              }
          }
      }
  }
  ```

- 拓扑排序算法

  ```java
  适用范围：要求有向图，且有入度为0的节点，且没有环
  1. 例如，编译顺序，依赖包含
  import java.util.ArrayList;
  import java.util.HashMap;
  import java.util.LinkedList;
  import java.util.List;
  import java.util.Queue;
  // directed graph and no loop  不想相互依赖
  public static List<Node> sortedTopology(Graph graph) {
      HashMap<Node, Integer> inMap = new HashMap<>();
      Queue<Node> zeroInQueue = new LinkedList<>();
      for (Node node : graph.nodes.values()) {
          inMap.put(node, node.in);
          if (node.in == 0) {
              zeroInQueue.add(node);
          }
      }
      List<Node> result = new ArrayList<>();
      while (!zeroInQueue.isEmpty()) {
          Node cur = zeroInQueue.poll();
          result.add(cur);
          for (Node next : cur.nexts) {
              inMap.put(next, inMap.get(next) - 1);
              if (inMap.get(next) == 0) {
                  zeroInQueue.add(next);
              }
          }
      }
      return result;
  }
  ```

- kruskal算法：要求无向图（使用并查集时更加方便）

  ```java
  例子：最小生成树
  图到树结构，保证连通性且权值累加和最小，不能有环
  1. 每次加入权值最小且不会形成环的边， 返回是边的集合
  // Union-Find Set
  public static class UnionFind {
      private HashMap<Node, Node> fatherMap;
      private HashMap<Node, Integer> rankMap;
  
      public UnionFind() {
          fatherMap = new HashMap<Node, Node>();
          rankMap = new HashMap<Node, Integer>();
      }
  
      private Node findFather(Node n) {
          Node father = fatherMap.get(n);
          if (father != n) {
              father = findFather(father);
          }
          fatherMap.put(n, father);
          return father;
      }
  
      public void makeSets(Collection<Node> nodes) {
          fatherMap.clear();
          rankMap.clear();
          for (Node node : nodes) {
              fatherMap.put(node, node);
              rankMap.put(node, 1);
          }
      }
  
      public boolean isSameSet(Node a, Node b) {
          return findFather(a) == findFather(b);
      }
  
      public void union(Node a, Node b) {
          if (a == null || b == null) {
              return;
          }
          Node aFather = findFather(a);
          Node bFather = findFather(b);
          if (aFather != bFather) {
              int aFrank = rankMap.get(aFather);
              int bFrank = rankMap.get(bFather);
              if (aFrank <= bFrank) {
                  fatherMap.put(aFather, bFather);
                  rankMap.put(bFather, aFrank + bFrank);
              } else {
                  fatherMap.put(bFather, aFather);
                  rankMap.put(aFather, aFrank + bFrank);
              }
          }
      }
  }
  
  public static class EdgeComparator implements Comparator<Edge> {
  
      @Override
      public int compare(Edge o1, Edge o2) {
          return o1.weight - o2.weight;
      }
  
  }
  
  public static Set<Edge> kruskalMST(Graph graph) {
      UnionFind unionFind = new UnionFind();
      unionFind.makeSets(graph.nodes.values());
      PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());
      for (Edge edge : graph.edges) {
          priorityQueue.add(edge);
      }
      Set<Edge> result = new HashSet<>();
      while (!priorityQueue.isEmpty()) {
          Edge edge = priorityQueue.poll();
          if (!unionFind.isSameSet(edge.from, edge.to)) {
              result.add(edge);
              unionFind.union(edge.from, edge.to);
          }
      }
      return result;
  }
  ```

- prim算法：要求无向图

  ```java
  任意选择一点出发，每次加入一条与当前联通分量权重最小的边去连接未知node
  public static class EdgeComparator implements Comparator<Edge> {
  
      @Override
      public int compare(Edge o1, Edge o2) {
          return o1.weight - o2.weight;
      }
  
  }
  public static Set<Edge> primMST(Graph graph) {
      PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(
              new EdgeComparator());
      HashSet<Node> set = new HashSet<>();
      Set<Edge> result = new HashSet<>();
      for (Node node : graph.nodes.values()) {
          if (!set.contains(node)) {
              set.add(node);
              for (Edge edge : node.edges) {
                  priorityQueue.add(edge);
              }
              while (!priorityQueue.isEmpty()) {
                  Edge edge = priorityQueue.poll();
                  Node toNode = edge.to;
                  if (!set.contains(toNode)) {
                      set.add(toNode);
                      result.add(edge);
                      for (Edge nextEdge : toNode.edges) {
                          priorityQueue.add(nextEdge);
                      }
                  }
              }
          }
      }
      return result;
  }
  // 请保证graph是连通图
  // graph[i][j]表示点i到点j的距离，如果是系统最大值代表无路
  // 返回值是最小连通图的路径之和
  public static int prim(int[][] graph) {
      int size = graph.length;
      int[] distances = new int[size];
      boolean[] visit = new boolean[size];
      visit[0] = true;
      for (int i = 0; i < size; i++) {
          distances[i] = graph[0][i];
      }
      int sum = 0;
      for (int i = 1; i < size; i++) {
          int minPath = Integer.MAX_VALUE;
          int minIndex = -1;
          for (int j = 0; j < size; j++) {
              if (!visit[j] && distances[j] < minPath) {
                  minPath = distances[j];
                  minIndex = j;
              }
          }
          if (minIndex == -1) {
              return sum;
          }
          visit[minIndex] = true;
          sum += minPath;
          for (int j = 0; j < size; j++) {
              if (!visit[j] && distances[j] > graph[minIndex][j]) {
                  distances[j] = graph[minIndex][j];
              }
          }
      }
      return sum;
  }
  ```

- dijkstra算法：不能有累加和为负数的环。（注意还可以基于堆实现来加速）

  ```java
  最短路径算法
  public static HashMap<Node, Integer> dijkstra1(Node head) {
      HashMap<Node, Integer> distanceMap = new HashMap<>(); // 节点到原点的目前最短距离
      distanceMap.put(head, 0);
      HashSet<Node> selectedNodes = new HashSet<>(); // 已经确定路径的节点
  
      Node minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
      while (minNode != null) {
          int distance = distanceMap.get(minNode);
          for (Edge edge : minNode.edges) {
              Node toNode = edge.to;
              if (!distanceMap.containsKey(toNode)) {
                  distanceMap.put(toNode, distance + edge.weight);
              }
              distanceMap.put(edge.to, Math.min(distanceMap.get(toNode), distance + edge.weight));
          }
          selectedNodes.add(minNode);
          minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
      }
      return distanceMap;
  }
  
  public static Node getMinDistanceAndUnselectedNode(HashMap<Node, Integer> distanceMap, 
          HashSet<Node> touchedNodes) {
      Node minNode = null;
      int minDistance = Integer.MAX_VALUE;
      for (Entry<Node, Integer> entry : distanceMap.entrySet()) {
          Node node = entry.getKey();
          int distance = entry.getValue();
          if (!touchedNodes.contains(node) && distance < minDistance) {
              minNode = node;
              minDistance = distance;
          }
      }
      return minNode;
  }
  ```

- A*算法

  ```python
  很常用的路径查找和图形遍历算法
  每个节点的优先级包括两部分：节点n距离起点的代价g(n)（计算得到的）和节点n距离终点的预计代价h(n)（估计的）
  当h(n)=0的时候就是dijkstra
  加入预计代价是为了提高搜索速度
  
  在极端情况下，当启发函数h(n)始终为0，则将由g(n)决定节点的优先级，此时算法就退化成了Dijkstra算
  如果h(n)始终小于等于节点n到终点的代价，则A*算法保证一定能够找到最短路径。但是当h(n)的值越小，算法将遍历越多的节点，也就导致算法越慢。
  如果h(n)完全等于节点n到终点的代价，则A*算法将找到最佳路径，并且速度很快。可惜的是，并非所有场景下都能做到这一点。因为在没有达到终点之前，我们很难确切算出距离终点还有多远。
  如果h(n)的值比节点n到终点的代价要大，则A*算法不能保证找到最短路径，不过此时会很快。
  在另外一个极端情况下，如果h()n相较于g(n)大很多，则此时只有h(n)产生效果，这也就变成了最佳优先搜索。
  ```

- 并查集：主要用于解决一些元素分组的问题。它管理一系列不相交的集合https://zhuanlan.zhihu.com/p/93647900

