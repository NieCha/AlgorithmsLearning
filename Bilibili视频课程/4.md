- 哈希表

  ```java
  哈希表在使用层面上可以理解为一种集合结构
  2）如果只有key，没有伴随数据value，可以使用HashSet结构(C++中叫UnOrderedSet)
  3）如果既有key，又有伴随数据value，可以使用HashMap结构(C++中叫UnOrderedMap)
  4）有无伴随数据，是HashMap和HashSet唯一的区别，底层的实际结构是一回事
  5）使用哈希表增(put)、删(remove)、改(put)和查(get)的操作，可以认为时间复杂度为
  O(1)，但是常数时间比较大
  6）放入哈希表的东西，如果是基础类型，内部按值传递，内存占用就是这个东西的大小
  7）放入哈希表的东西，如果不是基础类型，内部按引用传递，内存占用是这个东西内存地
  址的大小(8字节)
  HashSet<Integer> hashSet1 = new HashSet<>();
  HashMap<String, Integer> hashMap1 = new HashMap<>();
  ```

- 有序表（复杂度为O（logN））treeSet，treeMap

  ```java
  1）有序表在使用层面上可以理解为一种集合结构
  2）如果只有key，没有伴随数据value，可以使用TreeSet结构(C++中叫OrderedSet)
  3）如果既有key，又有伴随数据value，可以使用TreeMap结构(C++中叫OrderedMap)
  4）有无伴随数据，是TreeSet和TreeMap唯一的区别，底层的实际结构是一回事
  5）有序表和哈希表的区别是，有序表把key按照顺序组织起来，而哈希表完全不组织
  5）红黑树、AVL树、size-balance-tree和跳表等都属于有序表结构，只是底层具体实现
  不同
  6）放入哈希表的东西，如果是基础类型，内部按值传递，内存占用就是这个东西的大小
  7）放入哈希表的东西，如果不是基础类型，必须提供比较器，内部按引用传递，内存占
  用是这个东西内存地址的大小
  8）不管是什么底层具体实现，只要是有序表，都有以下固定的基本功能和固定的时间复
  杂度
  TreeSet<Node> treeSet = new TreeSet<>();
  treeSet = new TreeSet<>(new NodeComparator());
  ```

- 链表：双链表，单链表，链表反转

  ```java
  public static class Node {
      public int value;
      public Node next;
  
      public Node(int data) {
          this.value = data;
      }
  }
  public static Node reverseList(Node head) {
      Node pre = null;
      Node next = null;
      while (head != null) {
          next = head.next;
          head.next = pre;
          pre = head;
          head = next;
      }
      return pre;
  }
  
  public static class DoubleNode {
      public int value;
      public DoubleNode last;
      public DoubleNode next;
  
      public DoubleNode(int data) {
          this.value = data;
      }
  }
  
  public static DoubleNode reverseList(DoubleNode head) {
      DoubleNode pre = null;
      DoubleNode next = null;
      while (head != null) {
          next = head.next;
          head.next = pre;
          head.last = next;
          pre = head;
          head = next;
      }
      return pre;
  }
  ```

- 打印两个有序链表的公共部分

  ```java
  public static void printCommonPart(Node head1, Node head2) {
      System.out.print("Common Part: ");
      while (head1 != null && head2 != null) {
          if (head1.value < head2.value) {
              head1 = head1.next;
          } else if (head1.value > head2.value) {
              head2 = head2.next;
          } else {
              System.out.print(head1.value + " ");
              head1 = head1.next;
              head2 = head2.next;
          }
      }
      System.out.println();
  }
  ```

- 判断一个链表是否为回文结构

  ```java
  给定一个单链表的头节点head，请判断该链表是否为回文结构。
  快慢指针，快指针结束时候慢指针到达中点，然后将后半段逆序，判断回文再恢复结构
  public static boolean isPalindrome3(Node head) {
      if (head == null || head.next == null) {
          return true;
      }
      Node n1 = head;
      Node n2 = head;
      while (n2.next != null && n2.next.next != null) { // find mid node
          n1 = n1.next; // n1 -> mid
          n2 = n2.next.next; // n2 -> end
      }
      n2 = n1.next; // n2 -> right part first node
      n1.next = null; // mid.next -> null
      Node n3 = null;
      while (n2 != null) { // right part convert
          n3 = n2.next; // n3 -> save next node
          n2.next = n1; // next of right node convert
          n1 = n2; // n1 move
          n2 = n3; // n2 move
      }
      n3 = n1; // n3 -> save last node
      n2 = head;// n2 -> left first node
      boolean res = true;
      while (n1 != null && n2 != null) { // check palindrome
          if (n1.value != n2.value) {
              res = false;
              break;
          }
          n1 = n1.next; // left to mid
          n2 = n2.next; // right to mid
      }
      n1 = n3.next;
      n3.next = null;
      while (n1 != null) { // recover list
          n2 = n1.next;
          n1.next = n3;
          n3 = n1;
          n1 = n2;
      }
      return res;
  }
  ```

- 将单向链表按某值划分成左边小、中间相等、右边大的形式

  ```java
  时间复杂度请达到O(N)，额外空间复杂度请达到O(1),原始相对次序不变
  public static Node listPartition2(Node head, int pivot) {
      Node sH = null; // small head
      Node sT = null; // small tail
      Node eH = null; // equal head
      Node eT = null; // equal tail
      Node bH = null; // big head
      Node bT = null; // big tail
      Node next = null; // save next node
      // every node distributed to three lists
      while (head != null) {
          next = head.next;
          head.next = null;
          if (head.value < pivot) {
              if (sH == null) {
                  sH = head;
                  sT = head;
              } else {
                  sT.next = head;
                  sT = head;
              }
          } else if (head.value == pivot) {
              if (eH == null) {
                  eH = head;
                  eT = head;
              } else {
                  eT.next = head;
                  eT = head;
              }
          } else {
              if (bH == null) {
                  bH = head;
                  bT = head;
              } else {
                  bT.next = head;
                  bT = head;
              }
          }
          head = next;
      }
      // small and equal reconnect
      if (sT != null) {
          sT.next = eH;
          eT = eT == null ? sT : eT;
      }
      // all reconnect
      if (eT != null) {
          eT.next = bH;
      }
      return sH != null ? sH : eH != null ? eH : bH;
  }
  ```

- 复制含有随机指针节点的链表

  ```java
  public static class Node {
      public int value;
      public Node next;
      public Node rand;
  
      public Node(int data) {
          this.value = data;
      }
  }
  直接使用HashMap
  public static Node copyListWithRand1(Node head) {
      HashMap<Node, Node> map = new HashMap<Node, Node>();
      Node cur = head;
      while (cur != null) {
          map.put(cur, new Node(cur.value));
          cur = cur.next;
      }
      cur = head;
      while (cur != null) {
          map.get(cur).next = map.get(cur.next);
          map.get(cur).rand = map.get(cur.rand);
          cur = cur.next;
      }
      return map.get(head);
  }
  时间复杂度O(N)，额外空间复杂度O(1)，不使用HashMap
  1->1'>2>2'>3>3'
  public static Node copyListWithRand2(Node head) {
      if (head == null) {
          return null;
      }
      Node cur = head;
      Node next = null;
      // copy node and link to every node
      while (cur != null) {
          next = cur.next;
          cur.next = new Node(cur.value);
          cur.next.next = next;
          cur = next;
      }
      cur = head;
      Node curCopy = null;
      // set copy node rand
      while (cur != null) {
          next = cur.next.next;
          curCopy = cur.next;
          curCopy.rand = cur.rand != null ? cur.rand.next : null;
          cur = next;
      }
      Node res = head.next;
      cur = head;
      // split
      while (cur != null) {
          next = cur.next.next;
          curCopy = cur.next;
          cur.next = next;
          curCopy.next = next != null ? next.next : null;
          cur = next;
      }
      return res;
  }
  ```

- 两个单链表相交的一系列问题

  ```java
  给定两个可能有环也可能无环的单链表，头节点head1和head2。请实现一个函数，如果两个链表相交，请返回相交的第一个节点。如果不相交，返回null
  public static class Node {
      public int value;
      public Node next;
  
      public Node(int data) {
          this.value = data;
      }
  }
  
  public static Node getIntersectNode(Node head1, Node head2) {
      if (head1 == null || head2 == null) {
          return null;
      }
      Node loop1 = getLoopNode(head1);
      Node loop2 = getLoopNode(head2);
      if (loop1 == null && loop2 == null) {
          return noLoop(head1, head2);
      }
      if (loop1 != null && loop2 != null) {
          return bothLoop(head1, loop1, head2, loop2);
      }
      return null;
  }
  判断是否有环，否则返回环交节点
  public static Node getLoopNode(Node head) {
      if (head == null || head.next == null || head.next.next == null) {
          return null;
      }
      Node n1 = head.next; // n1 -> slow
      Node n2 = head.next.next; // n2 -> fast
      while (n1 != n2) {
          if (n2.next == null || n2.next.next == null) {
              return null;
          }
          n2 = n2.next.next;
          n1 = n1.next;
      }
      n2 = head; // n2 -> walk again from head
      while (n1 != n2) {
          n1 = n1.next;
          n2 = n2.next;
      }
      return n1;
  }
  
  public static Node noLoop(Node head1, Node head2) {
      if (head1 == null || head2 == null) {
          return null;
      }
      Node cur1 = head1;
      Node cur2 = head2;
      int n = 0;
      while (cur1.next != null) {
          n++;
          cur1 = cur1.next;
      }
      while (cur2.next != null) {
          n--;
          cur2 = cur2.next;
      }
      if (cur1 != cur2) {
          return null;
      }
      cur1 = n > 0 ? head1 : head2;
      cur2 = cur1 == head1 ? head2 : head1;
      n = Math.abs(n);
      while (n != 0) {
          n--;
          cur1 = cur1.next;
      }
      while (cur1 != cur2) {
          cur1 = cur1.next;
          cur2 = cur2.next;
      }
      return cur1;
  }
  
  public static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
      Node cur1 = null;
      Node cur2 = null;
      if (loop1 == loop2) {
          cur1 = head1;
          cur2 = head2;
          int n = 0;
          while (cur1 != loop1) {
              n++;
              cur1 = cur1.next;
          }
          while (cur2 != loop2) {
              n--;
              cur2 = cur2.next;
          }
          cur1 = n > 0 ? head1 : head2;
          cur2 = cur1 == head1 ? head2 : head1;
          n = Math.abs(n);
          while (n != 0) {
              n--;
              cur1 = cur1.next;
          }
          while (cur1 != cur2) {
              cur1 = cur1.next;
              cur2 = cur2.next;
          }
          return cur1;
      } else {
          cur1 = loop1.next;
          while (cur1 != loop1) {
              if (cur1 == loop2) {
                  return loop1;
              }
              cur1 = cur1.next;
          }
          return null;
      }
  }
  ```

