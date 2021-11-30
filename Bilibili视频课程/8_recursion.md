- 打印n层汉诺塔从最左边移动到最右边的全部过程

  ```java
  public static void hanoi(int n) {
      if (n > 0) {
          func(n, n, "left", "mid", "right");
      }
  }
  
  public static void func(int rest, int down, String from, String help, String to) {
      if (rest == 1) {
          System.out.println("move " + down + " from " + from + " to " + to);
      } else {
          func(rest - 1, down - 1, from, to, help);
          func(1, down, from, help, to);
          func(rest - 1, down - 1, help, from, to);
      }
  }
  ```

- 打印一个字符串的全部子序列，包括空字符串

  ```java
  public static void printAllSubsquence(String str) {
      char[] chs = str.toCharArray();
      process(chs, 0);
  }
  
  public static void process(char[] chs, int i) {
      if (i == chs.length) {
          System.out.println(String.valueOf(chs));
          return;
      }
      process(chs, i + 1);  //每个位置有包含不包含两种情况
      char tmp = chs[i];
      chs[i] = 0;
      process(chs, i + 1);
      chs[i] = tmp;
  }
  ```

- 打印一个字符串的全部排列

  ```java
  python
  def permutations(arr, position, end):
      if position == end:
          print(arr)
      else:
          for index in range(position, end):
              arr[index], arr[position] = arr[position], arr[index]
              permutations(arr, position + 1, end)
              arr[index], arr[position] = arr[position], arr[index]  # 还原到交换前的状态，
  arr = [1, 2, 3, 4]
  permutations(arr, 0, len(arr)
  
  java  要求不要出现重复的排列
  public static ArrayList<String> Permutation(String str) {
      ArrayList<String> res = new ArrayList<>();
      if (str == null || str.length() == 0) {
          return res;
      }
      char[] chs = str.toCharArray();
      process(chs, 0, res);
      res.sort(null);
      return res;
  }
  
  public static void process(char[] chs, int i, ArrayList<String> res) {
      if (i == chs.length) {
          res.add(String.valueOf(chs));
      }
      boolean[] visit = new boolean[26];				//
      for (int j = i; j < chs.length; j++) {
          if (!visit[chs[j] - 'a']) {                  //分支限界，防止重复排列，不能重复swap相同的
              visit[chs[j] - 'a'] = true;				//
              swap(chs, i, j);
              process(chs, i + 1, res);
              swap(chs, i, j);
          }
      }
  }
  
  public static void swap(char[] chs, int i, int j) {
      char tmp = chs[i];
      chs[i] = chs[j];
      chs[j] = tmp;
  }
  
  ```

- 给你一个栈，请你逆序这个栈，不能申请额外的数据结构，只能使用递归函数

  ```java
  public static void reverse(Stack<Integer> stack) {
      if (stack.isEmpty()) {
          return;
      }
      int i = getAndRemoveLastElement(stack);
      reverse(stack);
      stack.push(i);
  }
  
  public static int getAndRemoveLastElement(Stack<Integer> stack) {
      int result = stack.pop();
      if (stack.isEmpty()) {
          return result;
      } else {
          int last = getAndRemoveLastElement(stack);
          stack.push(result);
          return last;
      }
  }
  ```

- 规定1和A对应、2和B对应、3和C对应... 那么一个数字字符串比如"111"，就可以转化为"AAA"、"KA"和"AK"

  ```java
  public static int number(String str) {
      if (str == null || str.length() == 0) {
          return 0;
      }
      return process(str.toCharArray(), 0);
  }
  
  public static int process(char[] chs, int i) {
      if (i == chs.length) {
          return 1;
      }
      if (chs[i] == '0') {
          return 0;
      }
      if (chs[i] == '1') {
          int res = process(chs, i + 1);
          if (i + 1 < chs.length) {
              res += process(chs, i + 2);
          }
          return res;
      }
      if (chs[i] == '2') {
          int res = process(chs, i + 1);
          if (i + 1 < chs.length && (chs[i + 1] >= '0' && chs[i + 1] <= '6')) {
              res += process(chs, i + 2);
          }
          return res;
      }
      return process(chs, i + 1);
  }
  ```

- ```java
  给定两个长度都为N的数组weights和values，weights[i]和values[i]分别代表i号物品的重量和价值。给定一个正数bag，表示一个载重bag的袋子，你装的物品不能超过这个重量。返回你能装下最多的价值是多少
  动态规划和递归两种方式
  public static int maxValue1(int[] weights, int[] values, int bag) {
  		return process1(weights, values, 0, 0, bag);
  	}
  
  public static int process1(int[] weights, int[] values, int i, int alreadyweight, int bag) {
      if (alreadyweight > bag) {
          return 0;
      }
      if (i == weights.length) {
          return 0;
      }
      return Math.max(
  
              process1(weights, values, i + 1, alreadyweight, bag),
  
              values[i] + process1(weights, values, i + 1, alreadyweight + weights[i], bag));
  }
  
  public static int maxValue2(int[] c, int[] p, int bag) {
      int[][] dp = new int[c.length + 1][bag + 1];
      for (int i = c.length - 1; i >= 0; i--) {
          for (int j = bag; j >= 0; j--) {
              dp[i][j] = dp[i + 1][j];
              if (j + c[i] <= bag) {
                  dp[i][j] = Math.max(dp[i][j], p[i] + dp[i + 1][j + c[i]]);
              }
          }
      }
      return dp[0][0];
  }
  ```

- 扑克牌游戏

  ```java
  给定一个整型数组arr，代表数值不同的纸牌排成一条线。玩家A和玩家B依次拿走每张纸
  牌，规定玩家A先拿，玩家B后拿，但是每个玩家每次只能拿走最左或最右的纸牌，玩家A
  和玩家B都绝顶聪明。请返回最后获胜者的分数。
  【举例】
  arr=[1,2,100,4]。
  开始时，玩家A只能拿走1或4。如果开始时玩家A拿走1，则排列变为[2,100,4]，接下来
  玩家 B可以拿走2或4，然后继续轮到玩家A... 如果开始时玩家A拿走4，则排列变为[1,2,100]，接下来玩家B可以拿走1或100，然后继
  续轮到玩家A... 玩家A作为绝顶聪明的人不会先拿4，因为拿4之后，玩家B将拿走100。所以玩家A会先拿1，
  让排列变为[2,100,4]，接下来玩家B不管怎么选，100都会被玩家 A拿走。玩家A会获胜，
  分数为101。所以返回101。
  arr=[1,100,2]。
  开始时，玩家A不管拿1还是2，玩家B作为绝顶聪明的人，都会把100拿走。玩家B会获胜，
  分数为100。所以返回100。
  
  public static int win1(int[] arr) { // 返回最后获胜者的分数
      if (arr == null || arr.length == 0) {
          return 0;
      }
      return Math.max(f(arr, 0, arr.length - 1), s(arr, 0, arr.length - 1));
  }
  
  public static int f(int[] arr, int i, int j) { //先手分数
      if (i == j) {
          return arr[i];
      }
      return Math.max(arr[i] + s(arr, i + 1, j), arr[j] + s(arr, i, j - 1));
  }
  
  public static int s(int[] arr, int i, int j) {  // 后手分数
      if (i == j) {
          return 0;
      }
      return Math.min(f(arr, i + 1, j), f(arr, i, j - 1));
  }
  
  public static int win2(int[] arr) {  // 改成动态规划加速
      if (arr == null || arr.length == 0) {
          return 0;
      }
      int[][] f = new int[arr.length][arr.length];
      int[][] s = new int[arr.length][arr.length];
      for (int j = 0; j < arr.length; j++) {
          f[j][j] = arr[j];
          for (int i = j - 1; i >= 0; i--) {
              f[i][j] = Math.max(arr[i] + s[i + 1][j], arr[j] + s[i][j - 1]);
              s[i][j] = Math.min(f[i + 1][j], f[i][j - 1]);
          }
      }
      return Math.max(f[0][arr.length - 1], s[0][arr.length - 1]);
  }
  public static void main(String[] args) {
      int[] arr = { 1, 9, 1 };
      System.out.println(win1(arr));
      System.out.println(win2(arr));
  
  }
  ```

- N皇后问题(暴力递归)

  ```java
  N皇后问题是指在N*N的棋盘上要摆N个皇后，要求任何两个皇后不同行、不同列，也不在同一条斜线上
  public static int num1(int n) {
      if (n < 1) {
          return 0;
      }
      int[] record = new int[n];  //record[0]表示第1行列的位置
      return process1(0, record, n);
  }
  // 当前来到第i 行
  public static int process1(int i, int[] record, int n) {
      if (i == n) {
          return 1;
      }
      int res = 0;
      for (int j = 0; j < n; j++) {
          if (isValid(record, i, j)) {
              record[i] = j;
              res += process1(i + 1, record, n);
          }
      }
      return res;
  }
  
  public static boolean isValid(int[] record, int i, int j) {
      for (int k = 0; k < i; k++) {
          if (j == record[k] || Math.abs(record[k] - j) == Math.abs(i - k)) {
              return false;
          }
      }
      return true;
  }
  // 第二种实现方式（加速），要求n<=32, 位运算加速
  public static int num2(int n) {
      if (n < 1 || n > 32) {
          return 0;
      }
      int upperLim = n == 32 ? -1 : (1 << n) - 1; //N皇后问题生成一个数，第一个位是0，后n位是1
      return process2(upperLim, 0, 0, 0);
  }
  // colLim 表示列限制，rightDiaLim是右斜线限制， leftDiaLim左斜线限制
  public static int process2(int upperLim, int colLim, int leftDiaLim,
          int rightDiaLim) {
      if (colLim == upperLim) {
          return 1;
      }
      int pos = 0;
      int mostRightOne = 0;
      pos = upperLim & (~(colLim | leftDiaLim | rightDiaLim));
      int res = 0;
      while (pos != 0) {
          mostRightOne = pos & (~pos + 1);
          pos = pos - mostRightOne;
          res += process2(upperLim, colLim | mostRightOne,
                  (leftDiaLim | mostRightOne) << 1,
                  (rightDiaLim | mostRightOne) >>> 1);
      }
      return res;
  }
  ```

  

