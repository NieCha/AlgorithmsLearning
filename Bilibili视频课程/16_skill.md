- 双指针：滑动窗口最大值最小值滑动

  ```java
  给定一个有序数组arr，代表数轴上从左到右有n个点arr[0]、arr[1]...arr[n－1]，
  给定一个正数L，代表一根长度为L的绳子，求绳子最多能覆盖其中的几个点
  1. 使用左右两个指针，左指针在当前位置，右指针划到最远不越界得到当前结果，左指针向前滑动到下一个位置，右指针向前滑动得到结果
  ```

- 双指针

  ```java
  小虎去附近的商店买苹果，奸诈的商贩使用了捆绑交易，只提供6个每袋和8个
  每袋的包装包装不可拆分。可是小虎现在只想购买恰好n个苹果，小虎想购买尽
  量少的袋数方便携带。如果不能购买恰好n个苹果，小虎将不会购买。输入一个
  整数n，表示小虎想购买的个苹果，返回最小使用多少袋子。如果无论如何都不
  能正好装下，返回-1。
  答案：N/8 是8的袋子最多个数。N/6是6的袋子最少个数，然后构建数组使用双指针分别向两端减少
  
  或者可以根据N%8和N/8的结果直接算出最终结果，6的袋子是（N%8）/2个袋子
  ```

- 打表法则：先使用暴力规则直接输出当输入数值较低时，输出结果产生的规律，然后直接输出规律结果

  ```java
  牛牛和羊羊都很喜欢青草。今天他们决定玩青草游戏。最初有一个装有n份青草的箱子,牛牛和羊羊依次进行,牛牛先开始。在每个回合中,每个玩家必须吃一些箱子中的青草,所吃的青草份数必须是4的x次幂,比如1,4,16,64等等。不能在箱子中吃到有效份数青草的玩家落败。假定牛牛和羊羊都是按照最佳方法进行游戏,请输出胜利者的名字。
  public static void printWinner(int n) {
      if (n % 5 == 0 || n % 5 == 2) {
          System.out.println("yang");
      } else {
          System.out.println("niu");
      }
  }
  ```

- 构建统计数组

  ```java
  牛牛有一些排成一行的正方形。每个正方形已经被染成红色或者绿色。牛牛现在可
  以选择任意一个正方形然后用这两种颜色的任意一种进行染色,这个正方形的颜色将
  会被覆盖。牛牛的目标是在完成染色之后,每个红色R都比每个绿色G距离最左侧近。
  牛牛想知道他最少需要涂染几个正方形。
  如样例所示: s = RGRGR
  我们涂染之后变成RRRGG满足要求了,涂染的个数为2,没有比这个更好的涂染方案。
  
  答案：先使用一个数组遍历一遍得到当前位置前面有多少个R，然后可以知道每个位置前面和后面R和G的个数
  再遍历一遍就可以得到结果
  ```

- 构建统计数组：给定一个N*N的矩阵matrix，只有0和1两种值，返回边框全是1的最大正方形的边

  ```
  使用两个矩阵
  第一个矩阵统计每个元素当前右边有多少个连续的1
  第一个矩阵统计每个元素当前下方有多少个连续的1
  public class Problem04_MaxOneBorderSize {
  	public static void setBorderMap(int[][] m, int[][] right, int[][] down) {
  		int r = m.length;
  		int c = m[0].length;
  		if (m[r - 1][c - 1] == 1) {
  			right[r - 1][c - 1] = 1;
  			down[r - 1][c - 1] = 1;
  		}
  		for (int i = r - 2; i != -1; i--) {
  			if (m[i][c - 1] == 1) {
  				right[i][c - 1] = 1;
  				down[i][c - 1] = down[i + 1][c - 1] + 1;
  			}
  		}
  		for (int i = c - 2; i != -1; i--) {
  			if (m[r - 1][i] == 1) {
  				right[r - 1][i] = right[r - 1][i + 1] + 1;
  				down[r - 1][i] = 1;
  			}
  		}
  		for (int i = r - 2; i != -1; i--) {
  			for (int j = c - 2; j != -1; j--) {
  				if (m[i][j] == 1) {
  					right[i][j] = right[i][j + 1] + 1;
  					down[i][j] = down[i + 1][j] + 1;
  				}
  			}
  		}
  	}
  
  	public static int getMaxSize(int[][] m) {
  		int[][] right = new int[m.length][m[0].length];
  		int[][] down = new int[m.length][m[0].length];
  		setBorderMap(m, right, down);
  		for (int size = Math.min(m.length, m[0].length); size != 0; size--) {
  			if (hasSizeOfBorder(size, right, down)) {
  				return size;
  			}
  		}
  		return 0;
  	}
  
  	public static boolean hasSizeOfBorder(int size, int[][] right, int[][] down) {
  		for (int i = 0; i != right.length - size + 1; i++) {
  			for (int j = 0; j != right[0].length - size + 1; j++) {
  				if (right[i][j] >= size && down[i][j] >= size
  						&& right[i + size - 1][j] >= size
  						&& down[i][j + size - 1] >= size) {
  					return true;
  				}
  			}
  		}
  		return false;
  	}
  
  	public static int[][] generateRandom01Matrix(int rowSize, int colSize) {
  		int[][] res = new int[rowSize][colSize];
  		for (int i = 0; i != rowSize; i++) {
  			for (int j = 0; j != colSize; j++) {
  				res[i][j] = (int) (Math.random() * 2);
  			}
  		}
  		return res;
  	}
  
  	public static void printMatrix(int[][] matrix) {
  		for (int i = 0; i != matrix.length; i++) {
  			for (int j = 0; j != matrix[0].length; j++) {
  				System.out.print(matrix[i][j] + " ");
  			}
  			System.out.println();
  		}
  	}
  	public static void main(String[] args) {
  		int[][] matrix = generateRandom01Matrix(7, 8);
  		printMatrix(matrix);
  		System.out.println(getMaxSize(matrix));
  	}
  }
  ```

- 二进制

  ```java
  给定一个函数f，可以1～5的数字等概率返回一个。请加工出1～7的数字等概率返回一个的函数g。
  给定一个函数f，可以a～b的数字等概率返回一个。请加工出c～d的数字等概率返回一个的函数g。
  答案：选择产生区间中点，将f先加工为0-1的产生器。然后使用二进制位产生目标区间的数字，例如区间a-b，判断需要的二进制位，不在区间内重做
  
  给定一个函数f，以p概率返回0，以1-p概率返回1。请加工出等概率返回0和1的函数g
  答案：输出两次f，输出为01或者10即可代表g的0和1，输出11或00重做
  ```

- 给定一个非负整数n，代表二叉树的节点个数。返回能形成多少种不同的二叉树结构

  ```java
  动态规划直接解决
  F(N)=F(1)*F(N-1)+F(2)*F(N-2+...+F(N-1)*F(1))
  public static int numTrees(int n) {
      if (n < 2) {
          return 1;
      }
      int[] num = new int[n + 1];
      num[0] = 1;
      for (int i = 1; i < n + 1; i++) {
          for (int j = 1; j < i + 1; j++) {
              num[i] += num[j - 1] * num[i - j];
          }
      }
      return num[n];
  }
  ```

- 括号匹配

  ```java
  一个完整的括号字符串定义规则如下:
  ①空字符串是完整的。
  ②如果s是完整的字符串，那么(s)也是完整的。
  ③如果s和t是完整的字符串，将它们连接起来形成的st也是完整的。
  例如，"(()())", ""和"(())()"是完整的括号字符串，"())(", "()(" 和 ")" 是不完整的括号字符串。
  牛牛有一个括号字符串s,现在需要在其中任意位置尽量少地添加括号,将其转化
  为一个完整的括号字符串。请问牛牛至少需要添加多少个括号。
  public static int needParentheses(String str) {
      int leftRest = 0;
      int needSolveRight = 0;
      for (int i = 0; i < str.length(); i++) {
          if (str.charAt(i) == '(') {
              leftRest++;
          } else {
              if (leftRest == 0) {
                  needSolveRight++;
              } else {
                  leftRest--;
              }
          }
      }
      return leftRest + needSolveRight;
  }
  ```

- 给定一个数组arr，求差值为k的去重数字对

  ```
  使用哈希表遍历所有，然后
  public static List<List<Integer>> allPair(int[] arr, int k) {
      HashSet<Integer> set = new HashSet<>();
      for (int i = 0; i < arr.length; i++) {
          set.add(arr[i]);
      }
      List<List<Integer>> res = new ArrayList<>();
      for (Integer cur : set) {
          if (set.contains(cur + k)) {
              res.add(Arrays.asList(cur, cur + k));
          }
      }
      return res;
  }
  ```

- 贪心策略

  ```java
  给一个包含n个整数元素的集合a，一个包含m个整数元素的集合b。
  定义magic操作为，从一个集合中取出一个元素，放到另一个集合里，且操作过
  后每个集合的平均值都大大于于操作前。
  注意以下两点：
  1）不可以把一个集合的元素取空，这样就没有平均值了
  2）值为x的元素从集合b取出放入集合a，但集合a中已经有值为x的元素，则a的
  平均值不变（因为集合元素不会重复），b的平均值可能会改变（因为x被取出
  了）
  问最多可以进行多少次magic操作？
  
  
  // 请保证arr1无重复值、arr2中无重复值，且arr1和arr2肯定有数字
  public static int maxOps(int[] arr1, int[] arr2) {
      double sum1 = 0;
      for (int i = 0; i < arr1.length; i++) {
          sum1 += (double) arr1[i];
      }
      double sum2 = 0;
      for (int i = 0; i < arr2.length; i++) {
          sum2 += (double) arr2[i];
      }
      if (avg(sum1, arr1.length) == avg(sum2, arr2.length)) {
          return 0;
      }
      int[] arrMore = null;
      int[] arrLess = null;
      double sumMore = 0;
      double sumLess = 0;
      if (avg(sum1, arr1.length) > avg(sum2, arr2.length)) {
          arrMore = arr1;
          sumMore = sum1;
          arrLess = arr2;
          sumLess = sum2;
      } else {
          arrMore = arr2;
          sumMore = sum2;
          arrLess = arr1;
          sumLess = sum1;
      }
      Arrays.sort(arrMore);
      HashSet<Integer> setLess = new HashSet<>();
      for (int num : arrLess) {
          setLess.add(num);
      }
      int moreSize = arrMore.length;
      int lessSize = arrLess.length;
      int ops = 0;
      for (int i = 0; i < arrMore.length; i++) {
          double cur = (double) arrMore[i];
          if (cur < avg(sumMore, moreSize) && cur > avg(sumLess, lessSize)
                  && !setLess.contains(arrMore[i])) {
              sumMore -= cur;
              moreSize--;
              sumLess += cur;
              lessSize++;
              setLess.add(arrMore[i]);
              ops++;
          }
      }
      return ops;
  }
  
  public static double avg(double sum, int size) {
      return sum / (double) (size);
  }
  ```

- ```java
  将给定的数转换为字符串，原则如下：1对应 a，2对应b，…..26对应z，例如12258
  可以转换为"abbeh", "aveh", "abyh", "lbeh" and "lyh"，个数为5，编写一个函
  数，给出可以转换的不同字符串的个数
  ```

- ```java
  一个合法的括号匹配序列有以下定义:
  ①空串""是一个合法的括号匹配序列
  ②如果"X"和"Y"都是合法的括号匹配序列,"XY"也是一个合法的括号匹配序列
  ③如果"X"是一个合法的括号匹配序列,那么"(X)"也是一个合法的括号匹配序列
  ④每个合法的括号序列都可以由以上规则生成。
  例如: "","()","()()","((()))"都是合法的括号序列
  对于一个合法的括号序列我们又有以下定义它的深度:
  ①空串""的深度是0
  ②如果字符串"X"的深度是x,字符串"Y"的深度是y,那么字符串"XY"的深度为
  max(x,y) 3、如果"X"的深度是x,那么字符串"(X)"的深度是x+1
  例如: "()()()"的深度是1,"((()))"的深度是3。牛牛现在给你一个合法的括号
  序列,需要你计算出其深度
  
  // 括号字符串的有效性
  public static boolean isValid(char[] str) {
      if (str == null || str.equals("")) {
          return false;
      }
      int status = 0;
      for (int i = 0; i < str.length; i++) {
          if (str[i] != ')' && str[i] != '(') {
              return false;
          }
          if (str[i] == ')' && --status < 0) {
              return false;
          }
          if (str[i] == '(') {
              status++;
          }
      }
      return status == 0;
  }
  // 括号字符串深度
  public static int deep(String s) {
      char[] str = s.toCharArray();
      if (!isValid(str)) {
          return 0;
      }
      int count = 0;
      int max = 0;
      for (int i = 0; i < str.length; i++) {
          if (str[i] == '(') {
              max = Math.max(max, ++count);
          } else {
              count--;
          }
      }
      return max;
  }
  // 有效字串长度
  public static int maxLength(String str) {
      if (str == null || str.equals("")) {
          return 0;
      }
      char[] chas = str.toCharArray();
      int[] dp = new int[chas.length];
      int pre = 0;
      int res = 0;
      for (int i = 1; i < chas.length; i++) {
          if (chas[i] == ')') {
              pre = i - dp[i - 1] - 1;
              if (pre >= 0 && chas[pre] == '(') {
                  dp[i] = dp[i - 1] + 2 + (pre > 0 ? dp[pre - 1] : 0);
              }
          }
          res = Math.max(res, dp[i]);
      }
      return res;
  }
  ```

- ```java
  对一个栈里的整型数据，按升序进行排序（即排序前，栈里的数据是无序的，排序后最大元素位于栈顶），要求最多只能使用一个额外的栈存放临时数据，但不得将元素复制到别的数据结构中
  public static void sortStackByStack(Stack<Integer> stack) {
      Stack<Integer> help = new Stack<Integer>();
      while (!stack.isEmpty()) {
          int cur = stack.pop();
          while (!help.isEmpty() && help.peek() < cur) {
              stack.push(help.pop());
          }
          help.push(cur);
      }
      while (!help.isEmpty()) {
          stack.push(help.pop());
      }
  }
  ```

- ```java
  二叉树每个结点都有一个int型权值，给定一棵二叉树，要求计算出从根结点到叶结点的所有路径中，权值和最大的值为多少。
  public static class Node {
      public int value;
      public Node left;
      public Node right;
  
      public Node(int val) {
          value = val;
      }
  }
  
  public static int maxSumRecursive(Node head) {
      return process(head, 0);
  }
  
  public static int process(Node x, int pre) {
      if (x == null) {
          return Integer.MIN_VALUE;
      }
      if (x.left == null && x.right == null) {
          return pre + x.value;
      }
      int leftMax = process(x.left, pre + x.value);
      int rightMax = process(x.right, pre + x.value);
      return Math.max(leftMax, rightMax);
  }
  
  public static int maxSumUnrecursive(Node head) {
      int max = 0;
      HashMap<Node, Integer> sumMap = new HashMap<>();
      if (head != null) {
          Stack<Node> stack = new Stack<Node>();
          stack.add(head);
          sumMap.put(head, head.value);
          while (!stack.isEmpty()) {
              head = stack.pop();
              if (head.left == null && head.right == null) {
                  max = Math.max(max, sumMap.get(head));
              }
              if (head.right != null) {
                  sumMap.put(head.right, sumMap.get(head) + head.right.value);
                  stack.push(head.right);
              }
              if (head.left != null) {
                  sumMap.put(head.left, sumMap.get(head) + head.left.value);
                  stack.push(head.left);
              }
          }
      }
      return max;
  }
  ```