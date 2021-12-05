- 实现一种狗猫队列的结构

  ```java
  要求如下:
  用户可以调用add方法将cat类或dog类的 实例放入队列中;
  用户可以调用pollAll方法，将队列中所有的实例按照进队列 的先后顺序依次弹出;
  用户可以调用pollDog方法，将队列中dog类的实例按照 进队列的先后顺序依次弹出;
  用户可以调用pollCat方法，将队列中cat类的实 例按照进队列的先后顺序依次弹出;
  用户可以调用isEmpty方法，检查队列中是 否还有dog或cat的实例;
  用户可以调用isDogEmpty方法，检查队列中是否有dog 类的实例;
  用户可以调用isCatEmpty方法，检查队列中是否有cat类的实例。
  要求以上所有方法时间复杂度都是O(1)
  
  使用两个队列分别存储猫和狗，以及相应的索引值，方便后续弹出比较大小
  ```

- 实现一个特殊的栈，在实现栈的基本功能的基础上，再实现返回栈中最小元素 的操作

  ```java
  // 使用两个栈，第二个数组存储的是当前已经压入元素的最小值
  public static class MyStack2 {
      private Stack<Integer> stackData;
      private Stack<Integer> stackMin;
  
      public MyStack2() {
          this.stackData = new Stack<Integer>();
          this.stackMin = new Stack<Integer>();
      }
  
      public void push(int newNum) {
          if (this.stackMin.isEmpty()) {
              this.stackMin.push(newNum);
          } else if (newNum < this.getmin()) {
              this.stackMin.push(newNum);
          } else {
              int newMin = this.stackMin.peek();
              this.stackMin.push(newMin);
          }
          this.stackData.push(newNum);
      }
  
      public int pop() {
          if (this.stackData.isEmpty()) {
              throw new RuntimeException("Your stack is empty.");
          }
          this.stackMin.pop();
          return this.stackData.pop();
      }
  
      public int getmin() {
          if (this.stackMin.isEmpty()) {
              throw new RuntimeException("Your stack is empty.");
          }
          return this.stackMin.peek();
      }
  }
  ```

- ```java
  // 如何仅用栈结构实现队列结构?
  // 使用两个栈，如果存入，就往第一个栈里面放东西，如果pop，第二个栈为空则把第一个栈全部倒入后再pop，否则直接pop
  public static class TwoStacksQueue {
      private Stack<Integer> stackPush;
      private Stack<Integer> stackPop;
  
      public TwoStacksQueue() {
          stackPush = new Stack<Integer>();
          stackPop = new Stack<Integer>();
      }
  
      public void push(int pushInt) {
          stackPush.push(pushInt);
      }
  
      public int poll() {
          if (stackPop.empty() && stackPush.empty()) {
              throw new RuntimeException("Queue is empty!");
          } else if (stackPop.empty()) {
              while (!stackPush.empty()) {
                  stackPop.push(stackPush.pop());
              }
          }
          return stackPop.pop();
      }
  
      public int peek() {
          if (stackPop.empty() && stackPush.empty()) {
              throw new RuntimeException("Queue is empty!");
          } else if (stackPop.empty()) {
              while (!stackPush.empty()) {
                  stackPop.push(stackPush.pop());
              }
          }
          return stackPop.peek();
      }
  }
  如何仅用队列结构实现栈结构?
  public static class TwoQueuesStack {
      private Queue<Integer> queue;
      private Queue<Integer> help;
  
      public TwoQueuesStack() {
          queue = new LinkedList<Integer>();
          help = new LinkedList<Integer>();
      }
  
      public void push(int pushInt) {
          queue.add(pushInt);
      }
  
      public int peek() {
          if (queue.isEmpty()) {
              throw new RuntimeException("Stack is empty!");
          }
          while (queue.size() != 1) {
              help.add(queue.poll());
          }
          int res = queue.poll();
          help.add(res);
          swap();
          return res;
      }
  
      public int pop() {
          if (queue.isEmpty()) {
              throw new RuntimeException("Stack is empty!");
          }
          while (queue.size() > 1) {
              help.add(queue.poll());
          }
          int res = queue.poll();
          swap();
          return res;
      }
  
      private void swap() {
          Queue<Integer> tmp = help;
          help = queue;
          queue = tmp;
      }
  
  }
  ```

- 动态规划的空间压缩技巧

  ```java
  给你一个二维数组matrix，其中每个数都是正数，要求从左上角走到右下角。每一步只能向右或者向下，沿途经过的数字要累加起来。最后请返回最小的路径和
  public static int minPathSum2(int[][] m) {
      if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
          return 0;
      }
      int more = Math.max(m.length, m[0].length);
      int less = Math.min(m.length, m[0].length); 
      boolean rowmore = more == m.length;
      int[] arr = new int[less];
      arr[0] = m[0][0];
      for (int i = 1; i < less; i++) {
          arr[i] = arr[i - 1] + (rowmore ? m[0][i] : m[i][0]);
      }
      for (int i = 1; i < more; i++) {
          arr[0] = arr[0] + (rowmore ? m[i][0] : m[0][i]);
          for (int j = 1; j < less; j++) {
              arr[j] = Math.min(arr[j - 1], arr[j])
                      + (rowmore ? m[i][j] : m[j][i]);
          }
      }
      return arr[less - 1];
  }
  ```

- 给定一个数组arr，已知其中所有的值都是非负的，将这个数组看作一个容器， 请返回容器能装多少水

  ```java
  public static int getWater4(int[] arr) {
      if (arr == null || arr.length < 3) {
          return 0;
      }
      int value = 0;
      int leftMax = arr[0];
      int rightMax = arr[arr.length - 1];
      int l = 1;
      int r = arr.length - 2;
      while (l <= r) {
          if (leftMax <= rightMax) {
              value += Math.max(0, leftMax - arr[l]);
              leftMax = Math.max(leftMax, arr[l++]);
          } else {
              value += Math.max(0, rightMax - arr[r]);
              rightMax = Math.max(rightMax, arr[r--]);
          }
      }
      return value;
  }
  ```

- ```java
  给定一个数组arr长度为N，你可以把任意长度大于0且小于N的前缀作为左部分，剩下的作为右部分。但是每种划分下都有左部分的最大值和右部分的最大值，请返回最大的，左部分最大值减去右部分最大值的绝对值
  public static int maxABS3(int[] arr) {
      int max = Integer.MIN_VALUE;
      for (int i = 0; i < arr.length; i++) {
          max = Math.max(arr[i], max);
      }
      return max - Math.min(arr[0], arr[arr.length - 1]);
  }
  ```

- ```java
  如果一个字符串为str，把字符串str前面任意的部分挪到后面形成的字符串叫作str的旋转词。比如str="12345"，str的旋转词有"12345"、"23451"、"34512"、"45123"和"51234"。给定两个字符串a和b，请判断a和b是否互为旋转词。
  让 a = a+a， 判断b是否为a的字串即可，KMP算法
  ```

- ```java
  https://blog.csdn.net/weixin_43923436/article/details/118357402
  题目描述
  首先，给你几个数据：
  数组arr：表示几个咖啡机，这几个咖啡机生产一杯咖啡所需要的时间就是数组中的值，例如arr=[2,3,7]就表示第一台咖啡机生产一杯咖啡需要2单位时间，第二台需要3单位时间，第三台需要7单位时间。
  int N：表示有N个人需要用咖啡机制作咖啡，每人一杯，同时，假设制作完咖啡后，喝咖啡时间为0，一口闷。
  int a：表示用洗碗机洗一个咖啡杯需要的时间，串行运行。
  int b：表示咖啡杯也可以不洗，自然晾干的时间，咖啡杯要么洗，要么晾干。
  现在，请你求出这N个人从开始用咖啡杯制作咖啡到杯子洗好或者晾干的最少时间？
  ```

as
