### B站算法视频课程(Java)

- https://www.bilibili.com/video/BV13g41157K?from=search&seid=17732463770812985445&spm_id_from=333.337.0.0

- 选择排序，每次选择第 i 小的位置并进行交换

  ```java
  public static void selectionSort(int[] arr) {
      if (arr == null || arr.length < 2) {
          return;
      }
      for (int i = 0; i < arr.length - 1; i++) {
          int minIndex = i;
          for (int j = i + 1; j < arr.length; j++) {
              minIndex = arr[j] < arr[minIndex] ? j : minIndex;
          }
          swap(arr, i, minIndex);
      }
  }
  public static void swap(int[] arr, int i, int j) {
      int tmp = arr[i];
      arr[i] = arr[j];
      arr[j] = tmp;
  }
  
  ```

- 冒泡排序，每次比较相邻两个
  **注意：**这里的交换涉及的是位操作，异或运算，相同为0，不同为1（可以理解为无进位相加）

  ```java
  public static void bubbleSort(int[] arr) {
      if (arr == null || arr.length < 2) {
          return;
      }
      for (int e = arr.length - 1; e > 0; e--) {
          for (int i = 0; i < e; i++) {
              if (arr[i] > arr[i + 1]) {
                  swap(arr, i, i + 1);
              }
          }
      }
  }
  public static void swap(int[] arr, int i, int j) {
      arr[i] = arr[i] ^ arr[j];
      arr[j] = arr[i] ^ arr[j]; 等价于 arr[i] ^ (arr[j] ^ arr[j])
      arr[i] = arr[i] ^ arr[j]; 等价于 arr[j] ^ (arr[i] ^ arr[i])
  }
  注意这种写法的缺陷是 A和B在内存中是独立的区域（交换数组下标绝对不能相同），可以A=B
  ```

- **异或运算满足交换律和结合律**：从无进制相加的角度看待

  ```java
  应用
  1. 一组数中，一个数仅出现奇次，其他都为偶数次：直接将所有数异或即可
  public static void printOddTimesNum1(int[] arr) {
      int eO = 0;
      for (int cur : arr) {
          eO ^= cur;
      }
      System.out.println(eO);
  }
  2. 一组数中，两个数仅出现奇次，其他都为偶数次：
  public static void printOddTimesNum2(int[] arr) {
      int eO = 0, eOhasOne = 0;          
      for (int curNum : arr) {
          eO ^= curNum;
      }
      // eO = a ^ b ， 由于a~=b， 则eO存在一个二进制位不为0，a和b在该二进制位不同
      int rightOne = eO & (~eO + 1);  // 提出出最右侧（小位）的1
      for (int cur : arr) {
          if ((cur & rightOne) != 0) {
              eOhasOne ^= cur;
          }
      }                             // 将a或者b将其他在该位为1的数进行异或，得到其中一个数
      System.out.println(eOhasOne + " " + (eO ^ eOhasOne));
  }   
  ```

- 插入排序：现将第n个数插到前面已经排好的序列中，然后找到合适自己的位置，使得插入第n个数的这个序列也是排好顺序的

  ```java
  public static void insertionSort(int[] arr) {
      if (arr == null || arr.length < 2) {
          return;
      }
      for (int i = 1; i < arr.length; i++) {
          for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
              swap(arr, j, j + 1);
          }
      }
  }
  public static void swap(int[] arr, int i, int j) {
      arr[i] = arr[i] ^ arr[j];
      arr[j] = arr[i] ^ arr[j];
      arr[i] = arr[i] ^ arr[j];
  }
  ```

- 二分法

  ```java
  1 有序数组中，找某个数是否存在
  public static boolean exist(int[] sortedArr, int num) {
      if (sortedArr == null || sortedArr.length == 0) {
          return false;
      }
      int L = 0;
      int R = sortedArr.length - 1;
      int mid = 0;
      while (L < R) {
          mid = L + ((R - L) >> 1); >> 1想下取整，右移一位
          if (sortedArr[mid] == num) {
              return true;
          } else if (sortedArr[mid] > num) {
              R = mid - 1;
          } else {
              L = mid + 1;
          }
      }
      return sortedArr[L] == num;
  }
  2 有序数组中，找>=某个数最左侧的位置
  public static int nearestIndex(int[] arr, int value) {
      int L = 0;
      int R = arr.length - 1;
      int index = -1;
      while (L < R) {
          int mid = L + ((R - L) >> 1);
          if (arr[mid] >= value) {
              index = mid;
              R = mid - 1;
          } else {
              L = mid + 1;
          }
      }
      return index;
  }
  3 无序数组也可以二分，例如特殊情况的局部极小值问题
  4 二分递归求最大值
  public static int getMax(int[] arr) {
      return process(arr, 0, arr.length - 1);
  }
  public static int process(int[] arr, int L, int R) {
      if (L == R) {
          return arr[L];
      }
      int mid = L + ((R - L) >> 1);
      int leftMax = process(arr, L, mid);
      int rightMax = process(arr, mid + 1, R);
      return Math.max(leftMax, rightMax);
  }
  ```

- 对数器：就是使用系统或简单的方法（保证正确）进行实验，然后随机生成样本比较
  
  

- 洗牌算法

  ```java
  最后一个数和前面任意n个数中的一个交换，然后倒数第二个数和前面(包括自身)任意 n-1 个数中的一个交换
  public static int[] shuffle(int[] arr) {
      int len = arr.length-1;
      while (len) {
          int idx = intValue(Math.random() * len);
          swap(arr, idx, len);
          len -= 1;
      }
      return arr;
  }
  ```

  

