- 前缀树

  ```java
  public static class TrieNode {
      public int path;
      public int end;
      public TrieNode[] nexts;
  
      public TrieNode() {
          path = 0;
          end = 0;
          nexts = new TrieNode[26];
      }
  }
  
  public static class Trie {
      private TrieNode root;
  
      public Trie() {
          root = new TrieNode();
      }
  
      public void insert(String word) {
          if (word == null) {
              return;
          }
          char[] chs = word.toCharArray();
          TrieNode node = root;
          int index = 0;
          for (int i = 0; i < chs.length; i++) {
              index = chs[i] - 'a';
              if (node.nexts[index] == null) {
                  node.nexts[index] = new TrieNode();
              }
              node = node.nexts[index];
              node.path++;
          }
          node.end++;
      }
  
      public void delete(String word) {
          if (search(word) != 0) {
              char[] chs = word.toCharArray();
              TrieNode node = root;
              int index = 0;
              for (int i = 0; i < chs.length; i++) {
                  index = chs[i] - 'a';
                  if (--node.nexts[index].path == 0) {
                      node.nexts[index] = null;
                      return;
                  }
                  node = node.nexts[index];
              }
              node.end--;
          }
      }
  
      public int search(String word) {
          if (word == null) {
              return 0;
          }
          char[] chs = word.toCharArray();
          TrieNode node = root;
          int index = 0;
          for (int i = 0; i < chs.length; i++) {
              index = chs[i] - 'a';
              if (node.nexts[index] == null) {
                  return 0;
              }
              node = node.nexts[index];
          }
          return node.end;
      }
  
      public int prefixNumber(String pre) {
          if (pre == null) {
              return 0;
          }
          char[] chs = pre.toCharArray();
          TrieNode node = root;
          int index = 0;
          for (int i = 0; i < chs.length; i++) {
              index = chs[i] - 'a';
              if (node.nexts[index] == null) {
                  return 0;
              }
              node = node.nexts[index];
          }
          return node.path;
      }
  }
  ```

- 贪心算法：根据某标准建立一个比较器来排序，根据某标准建立一个比较器来组成堆

  ```java
  1. 给定一个字符串类型的数组strs，找到一种拼接方式，使得把所有字符串拼起来之后形成的
  字符串具有最小的字典序
  public static class MyComparator implements Comparator<String> {
      @Override
      public int compare(String a, String b) {
          return (a + b).compareTo(b + a);
      }
  }
  
  public static String lowestString(String[] strs) {
      if (strs == null || strs.length == 0) {
          return "";
      }
      Arrays.sort(strs, new MyComparator());
      String res = "";
      for (int i = 0; i < strs.length; i++) {
          res += strs[i];
      }
      return res;
  }
  ```

- 切金条问题

  ```java
  Huffman编码问题（前缀不重复）
  public static int lessMoney(int[] arr) {
      PriorityQueue<Integer> pQ = new PriorityQueue<>();
      for (int i = 0; i < arr.length; i++) {
          pQ.add(arr[i]);
      }
      int sum = 0;
      int cur = 0;
      while (pQ.size() > 1) {
          cur = pQ.poll() + pQ.poll();
          sum += cur;
          pQ.add(cur);
      }
      return sum;
  }
  ```

- 一些项目要占用一个会议室宣讲，会议室不能同时容纳两个项目的宣讲。 给你每一个项目开始的时间和结束的时间(给你一个数 组，里面是一个个具体 的项目)，你来安排宣讲的日程，要求会议室进行的宣讲的场次最多。 返回这个最多的宣讲场次

  ```java
  谁先结束就先安排谁
  public static class Program {
      public int start;
      public int end;
  
      public Program(int start, int end) {
          this.start = start;
          this.end = end;
      }
  }
  
  public static class ProgramComparator implements Comparator<Program> {
  
      @Override
      public int compare(Program o1, Program o2) {
          return o1.end - o2.end;
      }
  }
  
  public static int bestArrange(Program[] programs, int start) {
      Arrays.sort(programs, new ProgramComparator());
      int result = 0;
      for (int i = 0; i < programs.length; i++) {
          if (start <= programs[i].start) {
              result++;
              start = programs[i].end;
          }
      }
      return result;
  }
  ```

- 投资问题

  ```java
  输入：
  正数数组costs
  正数数组profits
  正数k
  正数m
  含义：
  costs[i]表示i号项目的花费
  profits[i]表示i号项目在扣除花费之后还能挣到的钱(利润)
  k表示你只能串行的最多做k个项目
  m表示你初始的资金
  说明：
  你每做完一个项目，马上获得的收益，可以支持你去做下一个项目。
  输出：
  你最后获得的最大钱数
  1. 花费数组构建小根堆，利润构建大根堆，每次选利润最高的项目
  public static class Node {
      public int p;//利润
      public int c;//花费
  
      public Node(int p, int c) {
          this.p = p;
          this.c = c;
      }
  }
  
  public static class MinCostComparator implements Comparator<Node> {
  
      @Override
      public int compare(Node o1, Node o2) {
          return o1.c - o2.c;
      }
  
  }
  
  public static class MaxProfitComparator implements Comparator<Node> {
  
      @Override
      public int compare(Node o1, Node o2) {
          return o2.p - o1.p;
      }
  
  }
  
  public static int findMaximizedCapital(int k, int W, int[] Profits, int[] Capital) {
      Node[] nodes = new Node[Profits.length];
      for (int i = 0; i < Profits.length; i++) {
          nodes[i] = new Node(Profits[i], Capital[i]);
      }
  
      PriorityQueue<Node> minCostQ = new PriorityQueue<>(new MinCostComparator());
      PriorityQueue<Node> maxProfitQ = new PriorityQueue<>(new MaxProfitComparator());
      for (int i = 0; i < nodes.length; i++) {
          minCostQ.add(nodes[i]);
      }
      for (int i = 0; i < k; i++) {
          while (!minCostQ.isEmpty() && minCostQ.peek().c <= W) {
              maxProfitQ.add(minCostQ.poll());
          }  //所有可以解锁的项目
          if (maxProfitQ.isEmpty()) {
              return W;
          }
          W += maxProfitQ.poll().p;
      }
      return W;
  }
  
  ```

- 一个数据流中，随时可以取得中位数（堆的应用）

  ```java
  构建两个堆，分别表示数据两头，并随时调整大小
  public static class MedianHolder {
      private PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(new MaxHeapComparator());
      private PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>(new MinHeapComparator());
  
      private void modifyTwoHeapsSize() {
          if (this.maxHeap.size() == this.minHeap.size() + 2) {
              this.minHeap.add(this.maxHeap.poll());
          }
          if (this.minHeap.size() == this.maxHeap.size() + 2) {
              this.maxHeap.add(this.minHeap.poll());
          }
      }
  
      public void addNumber(int num) {
          if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
              maxHeap.add(num);
          } else {
              minHeap.add(num);
          }
          modifyTwoHeapsSize();
      }
  
      public Integer getMedian() {
          int maxHeapSize = this.maxHeap.size();
          int minHeapSize = this.minHeap.size();
          if (maxHeapSize + minHeapSize == 0) {
              return null;
          }
          Integer maxHeapHead = this.maxHeap.peek();
          Integer minHeapHead = this.minHeap.peek();
          if (((maxHeapSize + minHeapSize) & 1) == 0) {
              return (maxHeapHead + minHeapHead) / 2;
          }
          return maxHeapSize > minHeapSize ? maxHeapHead : minHeapHead;
      }
  
  }
  
  public static class MaxHeapComparator implements Comparator<Integer> {
      @Override
      public int compare(Integer o1, Integer o2) {
          if (o2 > o1) {
              return 1;
          } else {
              return -1;
          }
      }
  }
  
  public static class MinHeapComparator implements Comparator<Integer> {
      @Override
      public int compare(Integer o1, Integer o2) {
          if (o2 < o1) {
              return 1;
          } else {
              return -1;
          }
      }
  }
  ```
