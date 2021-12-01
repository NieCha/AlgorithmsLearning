- 注意后续的求解套路，见判断一颗二叉树是否是平衡二叉树（**树形DP的方式**）

- 用递归和非递归两种方式实现二叉树的先序、中序、后序遍历

  ```java
  public static class Node {
      public int value;
      public Node left;
      public Node right;
  
      public Node(int data) {
          this.value = data;
      }
  }
  
  public static void preOrderRecur(Node head) {
      if (head == null) {
          return;
      }
      System.out.print(head.value + " ");
      preOrderRecur(head.left);
      preOrderRecur(head.right);
  }
  
  public static void inOrderRecur(Node head) {
      if (head == null) {
          return;
      }
      inOrderRecur(head.left);
      System.out.print(head.value + " ");
      inOrderRecur(head.right);
  }
  
  public static void posOrderRecur(Node head) {
      if (head == null) {
          return;
      }
      posOrderRecur(head.left);
      posOrderRecur(head.right);
      System.out.print(head.value + " ");
  }
  
  public static void preOrderUnRecur(Node head) {
      System.out.print("pre-order: ");
      if (head != null) {
          Stack<Node> stack = new Stack<Node>();
          stack.add(head);
          while (!stack.isEmpty()) {
              head = stack.pop();
              System.out.print(head.value + " ");
              if (head.right != null) {
                  stack.push(head.right);
              }
              if (head.left != null) {
                  stack.push(head.left);
              }
          }
      }
      System.out.println();
  }
  
  public static void inOrderUnRecur(Node head) {
      System.out.print("in-order: ");
      if (head != null) {
          Stack<Node> stack = new Stack<Node>();
          while (!stack.isEmpty() || head != null) {
              if (head != null) {
                  stack.push(head);
                  head = head.left;
              } else {
                  head = stack.pop();
                  System.out.print(head.value + " ");
                  head = head.right;
              }
          }
      }
      System.out.println();
  }
  
  public static void posOrderUnRecur2(Node h) {
      System.out.print("pos-order: ");
      if (h != null) {
          Stack<Node> stack = new Stack<Node>();
          stack.push(h);
          Node c = null;
          while (!stack.isEmpty()) {
              c = stack.peek();
              if (c.left != null && h != c.left && h != c.right) {
                  stack.push(c.left);
              } else if (c.right != null && h != c.right) {
                  stack.push(c.right);
              } else {
                  System.out.print(stack.pop().value + " ");
                  h = c;
              }
          }
      }
      System.out.println();
  }
  ```

- 判断一颗二叉树是否是搜索二叉树(二叉查找树)？中序遍历是递增序列

  ```java
  直接使用 inOrderList中序遍历然后判断
  public static boolean isBST(Node head) {
      if (head == null) {
          return true;
      }
      LinkedList<Node> inOrderList = new LinkedList<>();
      process(head, inOrderList);
      int pre = Integer.MIN_VALUE;
      for (Node cur : inOrderList) {
          if (pre >= cur.value) {
              return false;
          }
          pre = cur.value;
      }
      return true;
  }
  public static void process(Node node, LinkedList<Node> inOrderList) {
      if (node == null) {
          return;
      }
      process(node.left, inOrderList);
      inOrderList.add(node);
      process(node.right, inOrderList);
  }
  
  非循环方式判断
  public static boolean isBSTNoCur(Node head) {
      if (head != null) {
          int preValue = Interger.MIN_VALUE;
          Stack<Node> stack = new Stack<Node>();
          while (!stack.isEmpty() || head != null) {
              if (head != null) {
                  stack.push(head);
                  head = head.left;
              } else {
                  head = stack.pop();
                  if (head.value <= preValue) {
                      return False;
                  }else{
                      preValue = head.value;
                  }
                  head = head.right;
              }
          }
      }
      return true;
  }
  ```

- 求一棵二叉树的宽度

  ```java
  public static int getMaxWidth(Node head) {
      if (head == null) {
          return 0;
      }
      int maxWidth = 0;
      int curWidth = 0;
      int curLevel = 0;
      HashMap<Node, Integer> levelMap = new HashMap<>();
      levelMap.put(head, 1);
      LinkedList<Node> queue = new LinkedList<>();
      queue.add(head);
      Node node = null;
      Node left = null;
      Node right = null;
      while (!queue.isEmpty()) {
          node = queue.poll();
          left = node.left;
          right = node.right;
          if (left != null) {
              levelMap.put(left, levelMap.get(node) + 1);
              queue.add(left);
          }
          if (right != null) {
              levelMap.put(right, levelMap.get(node) + 1);
              queue.add(right);
          }
          if (levelMap.get(node) > curLevel) {
              curWidth = 0;
              curLevel = levelMap.get(node);
          } else {
              curWidth++;
          }
          maxWidth = Math.max(maxWidth, curWidth);
      }
      return maxWidth;
  }
  ```

- 如何判断一颗二叉树是完全二叉树？ 

```java
按照宽度遍历，第一个出现左右节点非空时，后面都为叶子节点
public static boolean isCBT(Node head) {
    if (head == null) {
        return true;
    }
    LinkedList<Node> queue = new LinkedList<>();
    boolean leaf = false;
    Node l = null;
    Node r = null;
    queue.add(head);
    while (!queue.isEmpty()) {
        head = queue.poll();
        l = head.left;
        r = head.right;
        if ((leaf && (l != null || r != null)) || (l == null && r != null)) {
            return false; //叶子结点有孩子  或者   只有右孩子无左孩子 
        }
        if (l != null) {
            queue.add(l);
        }
        if (r != null) {
            queue.add(r);
        } else {
            leaf = true;
        }
    }
    return true;
}
```

- 如何判断一颗二叉树是否是满二叉树？ 

  ```java
  满二叉树 树的深度和节点个数的关系是 N=2^L-1
  ```

- 如何判断一颗二叉树是否是平衡二叉树？左子树高度和右子树高度相差最大为1

  ```java
  public static class Node {
      public int value;
      public Node left;
      public Node right;
  
      public Node(int data) {
          this.value = data;
      }
  }
  
  public static boolean isBalanced(Node head) {
      return process(head).isBalanced;
  }
  
  public static class ReturnType {
      public boolean isBalanced;
      public int height;
  
      public ReturnType(boolean isB, int hei) {
          isBalanced = isB;
          height = hei;
      }
  }
  
  public static ReturnType process(Node x) {
      if (x == null) {
          return new ReturnType(true, 0);
      }
      ReturnType leftData = process(x.left);
      ReturnType rightData = process(x.right);
      int height = Math.max(leftData.height, rightData.height) + 1;
      boolean isBalanced = leftData.isBalanced && rightData.isBalanced
              && Math.abs(leftData.height - rightData.height) < 2;
      return new ReturnType(isBalanced, height);
  }
  ```

- 树形DP：https://www.cnblogs.com/ljy-endl/p/11612275.html

- 给定两个二叉树的节点node1和node2，找到他们的最低公共祖先节点

  ```java
  1. 生成各自向上父节点的表（先使用HashMap保存所有节点的父节点和自身）
  使用set保存一个node1的向上结合，然后判断node2每个父节点是否在set里面
  2. 第二种方法：
  第一种情况是一个节点是另一个节点的祖先
  另一种情况是一个节点和另一个节点在不同子树
  public static Node lowestAncestor(Node head, Node o1, Node o2) {
      if (head == null || head == o1 || head == o2) {
          return head;
      }
      Node left = lowestAncestor(head.left, o1, o2);
      Node right = lowestAncestor(head.right, o1, o2);
      if (left != null && right != null) { //两棵树里面都有返回值，一个O1一个O2
          return head;
      }
      return left != null ? left : right;
  }
  ```

- 二叉树中找到一个节点的后继节点(后继节点是中序遍历的前一个节点)

  ```java
  public static class Node {
      public int value;
      public Node left;
      public Node right;
      public Node parent;
  
      public Node(int data) {
          this.value = data;
      }
  }
  
  public static Node getSuccessorNode(Node node) {
      if (node == null) {
          return node;
      }
      if (node.right != null) {
          return getLeftMost(node.right);
      } else {
          Node parent = node.parent;
          while (parent != null && parent.left != node) {
              node = parent;
              parent = node.parent;
          }
          return parent;
      }
  }
  
  public static Node getLeftMost(Node node) {
      if (node == null) {
          return node;
      }
      while (node.left != null) {
          node = node.left;
      }
      return node;
  }
  ```

- 二叉树的序列化和反序列化

  ```java
  就是内存里的一棵树如何变成字符串形式，又如何从字符串形式变成内存里的树
  1. 根据先序遍历
  public static String serialByPre(Node head) {
      if (head == null) {
          return "#!";
      }
      String res = head.value + "!";
      res += serialByPre(head.left);
      res += serialByPre(head.right);
      return res;
  }
  
  public static Node reconByPreString(String preStr) {
      String[] values = preStr.split("!");
      Queue<String> queue = new LinkedList<String>();
      for (int i = 0; i != values.length; i++) {
          queue.offer(values[i]);
      }
      return reconPreOrder(queue);
  }
  
  public static Node reconPreOrder(Queue<String> queue) {
      String value = queue.poll();
      if (value.equals("#")) {
          return null;
      }
      Node head = new Node(Integer.valueOf(value));
      head.left = reconPreOrder(queue);
      head.right = reconPreOrder(queue);
      return head;
  }
  2. serialByLevel
  ```

- 折纸问题

  ```java
  请把一段纸条竖着放在桌子上，然后从纸条的下边向上方对折1次，压出折痕后展开。
  此时折痕是凹下去的，即折痕突起的方向指向纸条的背面。
  如果从纸条的下边向上方连续对折2次，压出折痕后展开，此时有三条折痕，从
  上到下依次是下折痕、下折痕和上折痕。
  给定一个输入参数N，代表纸条都从下边向上方连续对折N次。
  请从上到下打印所有折痕的方向。
  例如:N=1时，打印: down N=2时，打印: down down up
  public static void printAllFolds(int N) {
      printProcess(1, N, true);
  }
  
  public static void printProcess(int i, int N, boolean down) {
      if (i > N) {
          return;
      }
      printProcess(i + 1, N, true);			//中序遍历
      System.out.println(down ? "down " : "up ");
      printProcess(i + 1, N, false);
  }
  ```

