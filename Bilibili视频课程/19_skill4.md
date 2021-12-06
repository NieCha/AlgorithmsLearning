- 技巧

  ```java
  很多递归的公式可以表示为
  F(N)=aF(N-1)+bF(N-2)
  这可以使用矩阵的形式表示，F(N)可以表示为以前的数 乘以一个行列式
  因此公示的递归可以求出行列式^2, ^4,^8，将F(N)的计算复杂度变为O(log(N))
  ```

- ```java
  字符串只由'0'和'1'两种字符构成，
  当字符串长度为1时，所有可能的字符串为"0"、"1"；
  当字符串长度为2时，所有可能的字符串为"00"、"01"、"10"、"11"；
  当字符串长度为3时，所有可能的字符串为"000"、"001"、"010"、"011"、"100"、
  "101"、"110"、"111"
  ... 如果某一个字符串中，只要是出现'0'的位置，左边就靠着'1'，这样的字符串叫作达
  标字符串。
  给定一个正数N，返回所有长度为N的字符串中，达标字符串的数量。
  比如，N=3，返回3，因为只有"101"、"110"、"111"达标
  // F[i]=F[i-1]+F[i-2]  前面i长度的第1个字符是1或0
  public static int getNum1(int n) {
      if (n < 1) {
          return 0;
      }
      return process(1, n);
  }
  
  public static int process(int i, int n) {
      if (i == n - 1) {
          return 2;
      }
      if (i == n) {
          return 1;
      }
      return process(i + 1, n) + process(i + 2, n);
  }
  public static int getNum2(int n) {
      if (n < 1) {
          return 0;
      }
      if (n == 1) {
          return 1;
      }
      int pre = 1;
      int cur = 1;
      int tmp = 0;
      for (int i = 2; i < n + 1; i++) {
          tmp = cur;
          cur += pre;
          pre = tmp;
      }
      return cur;
  }
  
  public static int getNum3(int n) {
      if (n < 1) {
          return 0;
      }
      if (n == 1 || n == 2) {
          return n;
      }
      int[][] base = { { 1, 1 }, { 1, 0 } };
      int[][] res = matrixPower(base, n - 2);
      return 2 * res[0][0] + res[1][0];
  }
  // [F(N),F(N-1)]^T= [1,1;1,0] * [F(N-1),F(N-2)]^T
  public static int[][] matrixPower(int[][] m, int p) {
      int[][] res = new int[m.length][m[0].length];
      for (int i = 0; i < res.length; i++) {
          res[i][i] = 1;
      }
      int[][] tmp = m;
      for (; p != 0; p >>= 1) {
          if ((p & 1) != 0) {
              res = muliMatrix(res, tmp);
          }
          tmp = muliMatrix(tmp, tmp);
      }
      return res;
  }
  
  public static int[][] muliMatrix(int[][] m1, int[][] m2) {
      int[][] res = new int[m1.length][m2[0].length];
      for (int i = 0; i < m1.length; i++) {
          for (int j = 0; j < m2[0].length; j++) {
              for (int k = 0; k < m2.length; k++) {
                  res[i][j] += m1[i][k] * m2[k][j];
              }
          }
      }
      return res;
  }
  ```

- ```java
  留下的是 1 2 3 5 8 13 21 
  public static int minDelete(int m) {
      if (m < 4) {
          return 0;
      }
      int k_2 = 2;
      int k_1 = 3;
      int num = 3;
      while (k_2 + k_1 <= m) {
          num++;
          k_1 += k_2;
          k_2 = k_1 - k_2;
      }
      return m - num;
  }
  ```

- 给定一个数组arr，如果通过调整可以做到arr中任意两个相邻的数字相乘是4的倍数， 返回true；如果不能返回false

  ```java
  public static boolean nearMultiple4Times(int[] arr) {
      int fourTimes = 0; // 是4的倍数的数有多少个
      int evenExpFourTimes = 0; // 是偶数但不是4的倍数的数有多少个
      int odd = 0; // 奇数有多少个
      for (int i = 0; i < arr.length; i++) {
          if ((arr[i] & 1) != 0) {
              odd++;
          } else {
              if (arr[i] % 4 == 0) {
                  fourTimes++;
              } else {
                  evenExpFourTimes++;
              }
          }
      }
      return evenExpFourTimes == 0 ? (fourTimes + 1 >= odd) : (evenExpFourTimes >= odd);
  ```

- ```java
  给定一个字符串，如果该字符串符合人们日常书写一个整数的形式，返回int类
  型的这个数；如果不符合或者越界返回-1或者报错。
  public static int convert(String str) {
      if (str == null || str.equals("")) {
          return 0; // can not convert
      }
      char[] chas = str.toCharArray();
      if (!isValid(chas)) {
          return 0; // can not convert
      }
      boolean posi = chas[0] == '-' ? false : true;
      int minq = Integer.MIN_VALUE / 10;
      int minr = Integer.MIN_VALUE % 10;
      int res = 0;
      int cur = 0;
      for (int i = posi ? 0 : 1; i < chas.length; i++) {
          cur = '0' - chas[i];
          if ((res < minq) || (res == minq && cur < minr)) {
              return 0; // can not convert
          }
          res = res * 10 + cur;
      }
      if (posi && res == Integer.MIN_VALUE) {
          return 0; // can not convert
      }
      return posi ? -res : res;
  }
  
  public static boolean isValid(char[] chas) {
      if (chas[0] != '-' && (chas[0] < '0' || chas[0] > '9')) {
          return false;
      }
      if (chas[0] == '-' && (chas.length == 1 || chas[1] == '0')) {
          return false;
      }
      if (chas[0] == '0' && chas.length > 1) {
          return false;
      }
      for (int i = 1; i < chas.length; i++) {
          if (chas[i] < '0' || chas[i] > '9') {
              return false;
          }
      }
      return true;
  }
  ```

- ```java
  设计并实现TopKRecord结构，可以不断地向其中加入字符串，并且可以根据字符串出现的情况随时打印加入次数最多的前k个字符串
  1）k在TopKRecord实例生成时指定，并且不再变化(k是构造TopKRecord的参数)。
  2）含有 add(String str)方法，即向TopKRecord中加入字符串。
  3）含有 printTopK()方法，即打印加入次数最多的前k个字符串，打印有哪些
  字符串和对应的次数即可，不要求严格按排名顺序打印。
  4）如果在出现次数最多的前k个字符串中，最后一名的字符串有多个，比如出
  现次数最多的前3个字符串具体排名为：
  A 100次 B 90次 C 80次 D 80次 E 80次，其他任何字符串出现次数都
  不超过80次
  那么只需要打印3个，打印ABC、ABD、ABE都可以。也就是说可以随意抛弃最
  后一名，只要求打印k个
  要求：
  1）在任何时候，add 方法的时间复杂度不超过 O(logk)
  2）在任何时候，printTopK方法的时间复杂度不超过O(k)
  
  不能直接使用系统实现的堆结构，因为里面的元素值可以变化
  public static class Node {
      public String str;
      public int times;
  
      public Node(String s, int t) {
          str = s;
          times = t;
      }
  }
  
  public static class TopKRecord {
      private Node[] heap;
      private int index;
      private HashMap<String, Node> strNodeMap;
      private HashMap<Node, Integer> nodeIndexMap;
  
      public TopKRecord(int size) {
          heap = new Node[size];
          index = 0;
          strNodeMap = new HashMap<String, Node>();
          nodeIndexMap = new HashMap<Node, Integer>();
      }
  
      public void add(String str) {
          Node curNode = null;
          int preIndex = -1;
          if (!strNodeMap.containsKey(str)) {
              curNode = new Node(str, 1);
              strNodeMap.put(str, curNode);
              nodeIndexMap.put(curNode, -1);
          } else {
              curNode = strNodeMap.get(str);
              curNode.times++;
              preIndex = nodeIndexMap.get(curNode);
          }
          if (preIndex == -1) {
              if (index == heap.length) {
                  if (heap[0].times < curNode.times) {
                      nodeIndexMap.put(heap[0], -1);
                      nodeIndexMap.put(curNode, 0);
                      heap[0] = curNode;
                      heapify(0, index);
                  }
              } else {
                  nodeIndexMap.put(curNode, index);
                  heap[index] = curNode;
                  heapInsert(index++);
              }
          } else {
              heapify(preIndex, index);
          }
      }
  
      public void printTopK() {
          System.out.println("TOP: ");
          for (int i = 0; i != heap.length; i++) {
              if (heap[i] == null) {
                  break;
              }
              System.out.print("Str: " + heap[i].str);
              System.out.println(" Times: " + heap[i].times);
          }
      }
  
      private void heapInsert(int index) {
          while (index != 0) {
              int parent = (index - 1) / 2;
              if (heap[index].times < heap[parent].times) {
                  swap(parent, index);
                  index = parent;
              } else {
                  break;
              }
          }
      }
  
      private void heapify(int index, int heapSize) {
          int l = index * 2 + 1;
          int r = index * 2 + 2;
          int smallest = index;
          while (l < heapSize) {
              if (heap[l].times < heap[index].times) {
                  smallest = l;
              }
              if (r < heapSize && heap[r].times < heap[smallest].times) {
                  smallest = r;
              }
              if (smallest != index) {
                  swap(smallest, index);
              } else {
                  break;
              }
              index = smallest;
              l = index * 2 + 1;
              r = index * 2 + 2;
          }
      }
  
      private void swap(int index1, int index2) {
          nodeIndexMap.put(heap[index1], index2);
          nodeIndexMap.put(heap[index2], index1);
          Node tmp = heap[index1];
          heap[index1] = heap[index2];
          heap[index2] = tmp;
      }
  }
  ```

- ```java
  牛牛准备参加学校组织的春游, 出发前牛牛准备往背包里装入一些零食, 牛牛的背包容量为w。
  牛牛家里一共有n袋零食, 第i袋零食体积为v[i]。
  牛牛想知道在总体积不超过背包容量的情况下，他一共有多少种零食放法(总体积为0也算一种放法)
  public static int ways(int[] arr, int w) {
      if (arr == null || arr.length == 0 || w < 0) {
          return 0;
      }
      int[][] dp = new int[arr.length][w + 1];
      for (int i = 0; i < arr.length; i++) {
          dp[i][0] = 1;
      }
      for (int j = 1; j <= w; j++) {
          dp[0][j] = j >= arr[0] ? 2 : 1;
      }
      for (int i = 1; i < arr.length; i++) {
          for (int j = 1; j <= w; j++) {
              dp[i][j] = dp[i - 1][j];
              if (j - arr[i] >= 0) {
                  dp[i][j] += dp[i - 1][j - arr[i]];
              }
          }
      }
      return dp[arr.length - 1][w];
  }
  ```

- ```java
  找到一棵二叉树中，最大的搜索二叉子树，返回最大搜索二叉子树的节点个数
  public static Node getMaxBST(Node head) {
      return process(head).maxBSTHead;
  }
  
  public static class ReturnType {
      public Node maxBSTHead;
      public int maxBSTSize;
      public int min;
      public int max;
  
      public ReturnType(Node maxBSTHead, int maxBSTSize, int min, int max) {
          this.maxBSTHead = maxBSTHead;
          this.maxBSTSize = maxBSTSize;
          this.min = min;
          this.max = max;
      }
  }
  
  public static ReturnType process(Node X) {
      // base case : 如果子树是空树
      // 最小值为系统最大
      // 最大值为系统最小
      if (X == null) {
          return new ReturnType(null, 0, Integer.MAX_VALUE, Integer.MIN_VALUE);
      }
      // 默认直接得到左树全部信息
      ReturnType lData = process(X.left);
      // 默认直接得到右树全部信息
      ReturnType rData = process(X.right);
      // 以下过程为信息整合
      // 同时以X为头的子树也做同样的要求，也需要返回如ReturnType描述的全部信息
      // 以X为头的子树的最小值是：左树最小、右树最小、X的值，三者中最小的
      int min = Math.min(X.value, Math.min(lData.min, rData.min));
      // 以X为头的子树的最大值是：左树最大、右树最大、X的值，三者中最大的
      int max = Math.max(X.value, Math.max(lData.max, rData.max));
      // 如果只考虑可能性一和可能性二，以X为头的子树的最大搜索二叉树大小
      int maxBSTSize = Math.max(lData.maxBSTSize, rData.maxBSTSize);
      // 如果只考虑可能性一和可能性二，以X为头的子树的最大搜索二叉树头节点
      Node maxBSTHead = lData.maxBSTSize >= rData.maxBSTSize ? lData.maxBSTHead
              : rData.maxBSTHead;
      // 利用收集的信息，可以判断是否存在可能性三
      if (lData.maxBSTHead == X.left && rData.maxBSTHead == X.right
              && X.value > lData.max && X.value < rData.min) {
          maxBSTSize = lData.maxBSTSize + rData.maxBSTSize + 1;
          maxBSTHead = X;
      }
      // 信息全部搞定，返回
      return new ReturnType(maxBSTHead, maxBSTSize, min, max);
  }
  ```

- 已知一棵二叉树中没有重复节点，并且给定了这棵树的中序遍历数组和先序遍历 数组，返回后序遍历数组

- 数组连续字串相加和最大值问题

  ```java
  public static int maxSum(int[] arr) {
      if (arr == null || arr.length == 0) {
          return 0;
      }
      int max = Integer.MIN_VALUE;
      int cur = 0;
      for (int i = 0; i != arr.length; i++) {
          cur += arr[i];
          max = Math.max(max, cur);
          cur = cur < 0 ? 0 : cur;
      }
      return max;
  }
  根据题目知道性质：
  假设最大子串是 L-R
  则1 ： L前面到L位置串的和肯定小于0，
  R到R后面子串和肯定小于0
  ```

- 求完全二叉树节点的个数

- ```java
  CC直播的运营部门组织了很多运营活动，每个活动需要花费一定的时间参与，主播每参
  加完一个活动即可得到一定的奖励，参与活动可以从任意活动开始，但一旦开始，就需
  要将后续活动参加完毕（注意：最后一个活动必须参与），活动之间存在一定的依赖关
  系（不存在环的情况），现在给出所有的活动时间与依赖关系，以及给出有限的时间，
  请帮主播计算在有限的时候内，能获得的最大奖励，以及需要的最少时长
  // 请保证只有唯一的最后节点
  public static int[] maxRevenue(int allTime, int[] revenue, int[] times, int[][] dependents) {
      int size = revenue.length;
      HashMap<Integer, ArrayList<Integer>> parents = new HashMap<>();
      for (int i = 0; i < size; i++) {
          parents.put(i, new ArrayList<>());
      }
      int end = -1;
      for (int i = 0; i < dependents.length; i++) {
          boolean allZero = true;
          for (int j = 0; j < dependents[0].length; j++) {
              if (dependents[i][j] != 0) {
                  parents.get(j).add(i);
                  allZero = false;
              }
          }
          if (allZero) {
              end = i;
          }
      }
      HashMap<Integer, TreeMap<Integer, Integer>> nodeCostRevenueMap = new HashMap<>();
      for (int i = 0; i < size; i++) {
          nodeCostRevenueMap.put(i, new TreeMap<>());
      }
      nodeCostRevenueMap.get(end).put(times[end], revenue[end]);
      LinkedList<Integer> queue = new LinkedList<>();
      queue.add(end);
      while (!queue.isEmpty()) {
          int cur = queue.poll();
          for (int last : parents.get(cur)) {
              for (Entry<Integer, Integer> entry : nodeCostRevenueMap.get(cur).entrySet()) {
                  int lastCost = entry.getKey() + times[last];
                  int lastRevenue = entry.getValue() + revenue[last];
                  TreeMap<Integer, Integer> lastMap = nodeCostRevenueMap.get(last);
                  if (lastMap.floorKey(lastCost) == null || lastMap.get(lastMap.floorKey(lastCost)) < lastRevenue) {
                      lastMap.put(lastCost, lastRevenue);
                  }
              }
              queue.add(last);
          }
      }
      TreeMap<Integer, Integer> allMap = new TreeMap<>();
      for (TreeMap<Integer, Integer> curMap : nodeCostRevenueMap.values()) {
          for (Entry<Integer, Integer> entry : curMap.entrySet()) {
              int cost = entry.getKey();
              int reven = entry.getValue();
              if (allMap.floorKey(cost) == null || allMap.get(allMap.floorKey(cost)) < reven) {
                  allMap.put(cost, reven);
              }
          }
      }
      return new int[] { allMap.floorKey(allTime), allMap.get(allMap.floorKey(allTime)) };
  }
  ```

- 最长递增子序列问题
