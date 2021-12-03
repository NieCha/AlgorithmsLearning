- Morris遍历

  ```java
  一种遍历二叉树的方式，并且时间复杂度O(N)，额外空间复杂度O(1)
  通过利用原树中大量空闲指针的方式，达到节省空间的目的
  
  Morris遍历细节
  假设来到当前节点cur，开始时cur来到头节点位置
  1）如果cur没有左孩子，cur向右移动(cur = cur.right)
  2）如果cur有左孩子，找到左子树上最右的节点mostRight：
  a.如果mostRight的右指针指向空，让其指向cur，
  然后cur向左移动(cur = cur.left)
  b.如果mostRight的右指针指向cur，让其指向null，
  然后cur向右移动(cur = cur.right)
  3）cur为空时遍历停止
  
  public static class Node {
      public int value;
      Node left;
      Node right;
  
      public Node(int data) {
          this.value = data;
      }
  }
  
  public static void morrisIn(Node head) {
      if (head == null) {
          return;
      }
      Node cur1 = head;
      Node cur2 = null;
      while (cur1 != null) {
          cur2 = cur1.left;
          if (cur2 != null) {
              while (cur2.right != null && cur2.right != cur1) {
                  cur2 = cur2.right;
              }
              if (cur2.right == null) {
                  cur2.right = cur1;
                  cur1 = cur1.left;
                  continue;
              } else {
                  cur2.right = null;
              }
          }
          System.out.print(cur1.value + " ");
          cur1 = cur1.right;
      }
      System.out.println();
  }
  
  public static void morrisPre(Node head) {
      if (head == null) {
          return;
      }
      Node cur1 = head;
      Node cur2 = null;
      while (cur1 != null) {
          cur2 = cur1.left;
          if (cur2 != null) {
              while (cur2.right != null && cur2.right != cur1) {
                  cur2 = cur2.right;
              }
              if (cur2.right == null) {
                  cur2.right = cur1;
                  System.out.print(cur1.value + " ");
                  cur1 = cur1.left;
                  continue;
              } else {
                  cur2.right = null;
              }
          } else {
              System.out.print(cur1.value + " ");
          }
          cur1 = cur1.right;
      }
      System.out.println();
  }
  
  public static void morrisPos(Node head) {
      if (head == null) {
          return;
      }
      Node cur1 = head;
      Node cur2 = null;
      while (cur1 != null) {
          cur2 = cur1.left;
          if (cur2 != null) {
              while (cur2.right != null && cur2.right != cur1) {
                  cur2 = cur2.right;
              }
              if (cur2.right == null) {
                  cur2.right = cur1;
                  cur1 = cur1.left;
                  continue;
              } else {
                  cur2.right = null;
                  printEdge(cur1.left);
              }
          }
          cur1 = cur1.right;
      }
      printEdge(head);
      System.out.println();
  }
  public static void printEdge(Node head) {
      Node tail = reverseEdge(head);
      Node cur = tail;
      while (cur != null) {
          System.out.print(cur.value + " ");
          cur = cur.right;
      }
      reverseEdge(tail);
  }
  
  public static Node reverseEdge(Node from) {
      Node pre = null;
      Node next = null;
      while (from != null) {
          next = from.right;
          from.right = pre;
          pre = from;
          from = next;
      }
      return pre;
  }
  先序、中序可以由morris遍历加工得到
  后序遍历也可由morris遍历加工得到，但是把处理时机放在，能够达到两次的节点并且是第二次
  到达的时候
  ```

- 树形DP

  ```java
  例1
  叉树节点间的最大距离问题
  从二叉树的节点a出发，可以向上或者向下走，但沿途的节点只能经过一次，到达节点b时路径上的节点个数叫作a到b的距离，那么二叉树任何两个节点之间都有距离，求整棵树上的最大距离
  答：每棵子树需提供 其左子树最深节点的距离和右子树最深节点的距离
  第一种递归方式实现
  public static class Node {
      public int value;
      public Node left;
      public Node right;
  
      public Node(int data) {
          this.value = data;
      }
  }
  
  public static int maxDistance(Node head) {
      int[] record = new int[1];
      return posOrder(head, record);
  }
  
  public static int posOrder(Node head, int[] record) {
      if (head == null) {
          record[0] = 0;
          return 0;
      }
      int lMax = posOrder(head.left, record);
      int maxfromLeft = record[0];
      int rMax = posOrder(head.right, record);
      int maxFromRight = record[0];
      int curNodeMax = maxfromLeft + maxFromRight + 1;
      record[0] = Math.max(maxfromLeft, maxFromRight) + 1;
      return Math.max(Math.max(lMax, rMax), curNodeMax);
  }
  第二种方式实现
  public static class ReturnType{
      public int maxDistance;
      public int h;
  
      public ReturnType(int m, int h) {
          this.maxDistance = m;;
          this.h = h;
      }
  }
  
  public static ReturnType process(Node head) {
      if(head == null) {
          return new ReturnType(0,0);
      }
      ReturnType leftReturnType = process(head.left);
      ReturnType rightReturnType = process(head.right);
      int includeHeadDistance = leftReturnType.h + 1 + rightReturnType.h;
      int p1 = leftReturnType.maxDistance;
      int p2 = rightReturnType.maxDistance;
      int resultDistance = Math.max(Math.max(p1, p2), includeHeadDistance);
      int hitself  = Math.max(leftReturnType.h, leftReturnType.h) + 1;
      return new ReturnType(resultDistance, hitself);
  }
  ```

  ```java
  派对的最大快乐值
  员工信息的定义如下:
  class Employee {
  public int happy; // 这名员工可以带来的快乐值
  List<Employee> subordinates; // 这名员工有哪些直接下级
  }
  公司的每个员工都符合 Employee 类的描述。整个公司的人员结构可以看作是一棵标准的、 没有环的
  多叉树。树的头节点是公司唯一的老板。除老板之外的每个员工都有唯一的直接上级。 叶节点是没有
  任何下属的基层员工(subordinates列表为空)，除基层员工外，每个员工都有一个或多个直接下级。
  这个公司现在要办party，你可以决定哪些员工来，哪些员工不来。但是要遵循如下规则。
  1.如果某个员工来了，那么这个员工的所有直接下级都不能来
  2.派对的整体快乐值是所有到场员工快乐值的累加
  3.你的目标是让派对的整体快乐值尽量大
  给定一棵多叉树的头节点boss，请返回派对的最大快乐值
  
  public class Code03_MaxHappy {
  
  	public static int maxHappy(int[][] matrix) {
  		int[][] dp = new int[matrix.length][2];
  		boolean[] visited = new boolean[matrix.length];
  		int root = 0;
  		for (int i = 0; i < matrix.length; i++) {
  			if (i == matrix[i][0]) {
  				root = i;
  			}
  		}
  		process(matrix, dp, visited, root);
  		return Math.max(dp[root][0], dp[root][1]);
  	}
  
  	public static void process(int[][] matrix, int[][] dp, boolean[] visited, int root) {
  		visited[root] = true;
  		dp[root][1] = matrix[root][1];
  		for (int i = 0; i < matrix.length; i++) {
  			if (matrix[i][0] == root && !visited[i]) {
  				process(matrix, dp, visited, i);
  				dp[root][1] += dp[i][0];
  				dp[root][0] += Math.max(dp[i][1], dp[i][0]);
  			}
  		}
  	}
  
  	public static void main(String[] args) {
  		int[][] matrix = { { 1, 8 }, { 1, 9 }, { 1, 10 } };
  		System.out.println(maxHappy(matrix));
  	}
  }
  ```

  