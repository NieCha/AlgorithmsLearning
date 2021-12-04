- 打包机器问题

  ```java
  有n个打包机器从左到右一字排开，上方有一个自动装置会抓取一批放物品到每个打包机上，放到每个机器上的这些物品数量有多有少，由于物品数量不相同，需要工人将每个机器上的物品进行移动从而到达物品数量相等才能打包。每个物品重量太大、每次只能搬一个物品进行移动，为了省力，只在相邻的机器上移动（沿着一个方向）。请计算在搬动最小轮数的前提下，使每个机器上的物品数量相等。如果不能使每个机器上的物品相同，返回-1。例如[1,0,5]表示有3个机器，每个机器上分别有1、0、5个物品，经过这些轮后：第一轮：1 0 <- 5 => 1 1 4 第二轮：1 <-1<- 4 => 2 1 3 第三轮：2 1 <- 3 => 2 2 2移动了3轮，每个机器上的物品相等，所以返回3例如[2,2,3]表示有3个机器，每个机器上分别有2、2、3个物品，这些物品不管怎么移动，都不能使三个机器上物品数量相等，返回-1
  
  public static int MinOps(int[] arr) {
      if (arr == null || arr.length == 0) {
          return 0;
      }
      int size = arr.length;
      int sum = 0;
      for (int i = 0; i < size; i++) {
          sum += arr[i];
      }
      if (sum % size != 0) {
          return -1;
      }
      int avg = sum / size;
      int leftSum = 0;
      int ans = 0;
      for (int i = 0; i < arr.length; i++) {
          int L = i * avg - leftSum;             //i左边少的
          int R = (size - i - 1) * avg - (sum - leftSum - arr[i]);  // i 右边少的
          if (L > 0 && R > 0) {
              ans = Math.max(ans, Math.abs(L) + Math.abs(R));
          } else {
              ans = Math.max(ans, Math.max(Math.abs(L), Math.abs(R)));
          }
          leftSum += arr[i];
      }
      return ans;
  }
  ```

- 题目二 用zigzag的方式打印矩阵，比如如下的矩阵

  ```java
  public static void printMatrixZigZag(int[][] matrix) {
      int tR = 0;
      int tC = 0;
      int dR = 0;
      int dC = 0;
      int endR = matrix.length - 1;
      int endC = matrix[0].length - 1;
      boolean fromUp = false;
      while (tR != endR + 1) {
          printLevel(matrix, tR, tC, dR, dC, fromUp);
          tR = tC == endC ? tR + 1 : tR;
          tC = tC == endC ? tC : tC + 1;
          dC = dR == endR ? dC + 1 : dC;
          dR = dR == endR ? dR : dR + 1;
          fromUp = !fromUp;
      }
      System.out.println();
  }
  
  public static void printLevel(int[][] m, int tR, int tC, int dR, int dC,
          boolean f) {
      if (f) {
          while (tR != dR + 1) {
              System.out.print(m[tR++][tC--] + " ");
          }
      } else {
          while (dR != tR - 1) {
              System.out.print(m[dR--][dC++] + " ");
          }
      }
  }
  ```

- 用螺旋的方式打印矩阵

  ```java
  public static void spiralOrderPrint(int[][] matrix) {
      int tR = 0;
      int tC = 0;
      int dR = matrix.length - 1;
      int dC = matrix[0].length - 1;
      while (tR <= dR && tC <= dC) {
          printEdge(matrix, tR++, tC++, dR--, dC--);
      }
  }
  
  public static void printEdge(int[][] m, int tR, int tC, int dR, int dC) {
      if (tR == dR) {
          for (int i = tC; i <= dC; i++) {
              System.out.print(m[tR][i] + " ");
          }
      } else if (tC == dC) {
          for (int i = tR; i <= dR; i++) {
              System.out.print(m[i][tC] + " ");
          }
      } else {
          int curC = tC;
          int curR = tR;
          while (curC != dC) {
              System.out.print(m[tR][curC] + " ");
              curC++;
          }
          while (curR != dR) {
              System.out.print(m[curR][dC] + " ");
              curR++;
          }
          while (curC != tC) {
              System.out.print(m[dR][curC] + " ");
              curC--;
          }
          while (curR != tR) {
              System.out.print(m[curR][tC] + " ");
              curR--;
          }
      }
  }
  ```

- 矩阵中每个位置的数顺时针转动 90度


  ```java
  public static void rotate(int[][] matrix) {
      int tR = 0;
      int tC = 0;
      int dR = matrix.length - 1;
      int dC = matrix[0].length - 1;
      while (tR < dR) {
          rotateEdge(matrix, tR++, tC++, dR--, dC--);
      }
  }
  
  public static void rotateEdge(int[][] m, int tR, int tC, int dR, int dC) {
      int times = dC - tC; 
      int tmp = 0;
      for (int i = 0; i != times; i++) {
          tmp = m[tR][tC + i];
          m[tR][tC + i] = m[dR - i][tC];
          m[dR - i][tC] = m[dR][dC - i];
          m[dR][dC - i] = m[tR + i][dC];
          m[tR + i][dC] = tmp;
      }
  }
  ```

- ```java
  给定一个元素为非负整数的二维数组matrix，每行和每列都是从小到大有序的。再给定一个非负整数aim，请判断aim是否在matrix中。
  public static boolean isContains(int[][] matrix, int K) {
      int row = 0;
      int col = matrix[0].length - 1;
      while (row < matrix.length && col > -1) {
          if (matrix[row][col] == K) {
              return true;
          } else if (matrix[row][col] > K) {
              col--;
          } else {
              row++;
          }
      }
      return false;
  }
  ```

- ```java
  假设s和m初始化，s = "a"; m = s;
  再定义两种操作，第一种操作：
  m = s;
  s = s + s;
  第二种操作：
  s = s + m;
  求最小的操作步骤数，可以将s拼接到长度等于n
  
  // 附加题：怎么判断一个数是不是质数？
  public static boolean isPrim(int n) {
      if (n < 2) {
          return false;
      }
      int max = (int) Math.sqrt((double) n);
      for (int i = 2; i <= max; i++) {
          if (n % i == 0) {
              return false;
          }
      }
      return true;
  }
  
  // 请保证n不是质数
  // 返回:
  // 0) 所有因子的和，但是因子不包括1
  // 1) 所有因子的个数，但是因子不包括1
  public static int[] divsSumAndCount(int n) {
      int sum = 0;
      int count = 0;
      for (int i = 2; i <= n; i++) {
          while (n % i == 0) {
              sum += i;
              count++;
              n /= i;
          }
      }
      return new int[] { sum, count };
  }
  
  public static int minOps(int n) {
      if (n < 2) {
          return 0;
      }
      if (isPrim(n)) {
          return n - 1;
      }
      int[] divSumAndCount = divsSumAndCount(n);
      return divSumAndCount[0] - divSumAndCount[1];
  }
  ```

- ```java
  给定一个字符串类型的数组arr，求其中出现次数最多的前K个
  public static class Node {
      public String str;
      public int times;
  
      public Node(String s, int t) {
          str = s;
          times = t;
      }
  }
  
  public static class NodeComparator implements Comparator<Node> {
      @Override
      public int compare(Node o1, Node o2) {
          return o2.times - o1.times;
      }
  }
  
  public static void printTopKAndRank(String[] arr, int topK) {
      if (arr == null || arr.length == 0 || topK < 1) {
          return;
      }
      HashMap<String, Integer> map = new HashMap<>();
      for (String str : arr) {
          if (!map.containsKey(str)) {
              map.put(str, 0);
          }
          map.put(str, map.get(str) + 1);
      }
      topK = Math.min(arr.length, topK);
      PriorityQueue<Node> heap = new PriorityQueue<>(new NodeComparator());
      for (Entry<String, Integer> entry : map.entrySet()) {
          Node cur = new Node(entry.getKey(), entry.getValue());
          if (heap.size() < topK) {
              heap.add(cur);
          } else {
              if (heap.peek().times < cur.times) {
                  heap.poll();
                  heap.add(cur);
              }
          }
  
      }
      while (!heap.isEmpty()) {
          System.out.println(heap.poll().str);
      }
  }
  ```