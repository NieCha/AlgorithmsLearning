- KMP算法

  ```java
  字符串str1和str2，str1是否包含str2，如果包含返回str2在str1中开始的位置
  // 暴力匹配（伪码）
  int search(String pat, String txt) {
      int M = pat.length;
      int N = txt.length;
      for (int i = 0; i <= N - M; i++) {
          int j;
          for (j = 0; j < M; j++) {
              if (pat[j] != txt[i+j])
                  break;
          }
          // pat 全都匹配了
          if (j == M) return i;
      }
      // txt 中不存在 pat 子串
      return -1;
  }
  模式串的最长公共前后缀： 指的是小于该字符串长度的首尾相同的字符串，如果有多个取最长的
  next数组：第i个数表示当前不匹配后，需要与substr第next[i]个数进行比较
  class Solution:
      # 获取next数组
      def get_next(self, T):
          i = 0
          j = -1
          next_val = [-1] * len(T)
          while i < len(T)-1:
              if j == -1 or T[i] == T[j]:
                  i += 1
                  j += 1
                  # next_val[i] = j
                  if i < len(T) and T[i] != T[j]:
                      next_val[i] = j
                  else: # 如果字符重复则跳过
                      next_val[i] = next_val[j]
              else:  # 匹配不成功则在前面的子串中继续搜索，直至找不到
                  j = next_val[j]
          return next_val
  
      # KMP算法
      def kmp(self, S, T):
          i = 0
          j = 0
          next = self.get_next(T)
          while i < len(S) and j < len(T):
              if j == -1 or S[i] == T[j]:
                  i += 1
                  j += 1
              else:
                  j = next[j]
          return i - j if j == len(T) else -1
  ```

- BM算法（常用）：思想是有模式串中不存在的字符，那么肯定不匹配，往后多移动几位，提高效率

  - BM原理：**坏字符**规则，**好后缀**规则

- Sunday：其效率在匹配随机的字符串时比其他匹配算法还要更快

  ```python
  实现简单，但最差情况下复杂度高
  def sunday_find(src, dst):
      len_src = len(src)
      len_dst = len(dst)
      i = 0
      while i < len_src - len_dst + 1:
          flag = 0
          shift = len_dst
          for j in range(0, len_dst):
              if src[i+j] != dst[j]:
                  flag = -1
                  break
          if flag == 0:
              return i
          p = dst.rfind(src[i+shift])
          if p == -1:
              shift = len_dst + 1
          else:
              shift = len_dst - p
          i = i + shift
      return -1
  ```

- Manacher算法
  字符串str中，最长回文子串的长度如何求解

  ```python
  马拉车算法的核心思路就在于，对每个长回文串的对称中心向两边进行拓展，同时记下前面已经查找过的回文子串半径(原理是动态规划的思想)，其本质在于，如果能够找到一个长回文串，同时在它的对称中心左区域中找到一个回文子串，由于回文串的对称性，其右区域中必然是同样的回文子串。因此这个算法能快速地找出最长的回文子串。当然也能用来求解回文子串的个数
  1. 在每个字符间插入"#"，并且为了使得扩展的过程中，到边界后自动结束，在两端分别插入 "^" 和 "$"两个不可能在字符串中出现的字符
  
  #方法一  枚举所有长度大于等于 2 的子串再判断
  class Solution:
      # 暴力匹配（超时）
      def longestPalindrome(self, s: str) -> str:
          # 特判
          size = len(s)
          if size < 2:
              return s
  
          max_len = 1
          res = s[0]
  
          # 枚举所有长度大于等于 2 的子串
          for i in range(size - 1):
              for j in range(i + 1, size):
                  if j - i + 1 > max_len and self.__valid(s, i, j):
                      max_len = j - i + 1
                      res = s[i:j + 1]
          return res
  
      def __valid(self, s, left, right):
          # 验证子串 s[left, right] 是否为回文串
          while left < right:
              if s[left] != s[right]:
                  return False
              left += 1
              right -= 1
          return True
  
  #方法二：动态规划
  
  class Solution:
      def longestPalindrome(self, s: str) -> str:
          size = len(s)
          if size < 2:
              return s
  
          dp = [[False for _ in range(size)] for _ in range(size)]
  
          max_len = 1
          start = 0
  
          for i in range(size):
              dp[i][i] = True
  
          for j in range(1, size):
              for i in range(0, j):
                  if s[i] == s[j]:
                      if j - i < 3:
                          dp[i][j] = True
                      else:
                          dp[i][j] = dp[i + 1][j - 1]
                  else:
                      dp[i][j] = False
  
                  if dp[i][j]:
                      cur_len = j - i + 1
                      if cur_len > max_len:
                          max_len = cur_len
                          start = i
          return s[start:start + max_len]
  
  #方法三  中心扩散法
  
  class Solution:
      def longestPalindrome(self, s: str) -> str:
          size = len(s)
          if size < 2:
              return s
  
          # 至少是 1
          max_len = 1
          res = s[0]
  
          for i in range(size):
              palindrome_odd, odd_len = self.__center_spread(s, size, i, i)
              palindrome_even, even_len = self.__center_spread(s, size, i, i + 1)
  
              # 当前找到的最长回文子串
              cur_max_sub = palindrome_odd if odd_len >= even_len else palindrome_even
              if len(cur_max_sub) > max_len:
                  max_len = len(cur_max_sub)
                  res = cur_max_sub
  
          return res
  
      def __center_spread(self, s, size, left, right):
          """
          left = right 的时候，此时回文中心是一个字符，回文串的长度是奇数
          right = left + 1 的时候，此时回文中心是一个空隙，回文串的长度是偶数
          """
          i = left
          j = right
  
          while i >= 0 and j < size and s[i] == s[j]:
              i -= 1
              j += 1
          return s[i + 1:j], j - i - 1
  
  # 方法四：马拉车算法
  
  class Solution:
      # Manacher 算法
      def longestPalindrome(self, s: str) -> str:
          # 特判
          size = len(s)
          if size < 2:
              return s
  
          # 得到预处理字符串
          t = "#"
          for i in range(size):
              t += s[i]
              t += "#"
          # 新字符串的长度
          t_len = 2 * size + 1
  
          # 数组 p 记录了扫描过的回文子串的信息
          p = [0 for _ in range(t_len)]
  
          # 双指针，它们是一一对应的，须同时更新
          max_right = 0   # 当前最大右边界
          center = 0		# 最大右边界回文的中心   center i  max_right, max_len=max_right-center
  
          # 当前遍历的中心最大扩散步数，其值等于原始字符串的最长回文子串的长度
          max_len = 1
          # 原始字符串的最长回文子串的起始位置，与 max_len 必须同时更新
          start = 1
  
          for i in range(t_len):
              if i < max_right:
                  mirror = 2 * center - i  # center - (i-center)
                  # 这一行代码是 Manacher 算法的关键所在，要结合图形来理解
                  p[i] = min(max_right - i, p[mirror])
  
              # 下一次尝试扩散的左右起点，能扩散的步数直接加到 p[i] 中
              left = i - (1 + p[i])
              right = i + (1 + p[i])
  
              # left >= 0 and right < t_len 保证不越界
              # t[left] == t[right] 表示可以扩散 1 次
              while left >= 0 and right < t_len and t[left] == t[right]:
                  p[i] += 1
                  left -= 1
                  right += 1
  
              # 根据 max_right 的定义，它是遍历过的 i 的 i + p[i] 的最大者
              # 如果 max_right 的值越大，进入上面 i < max_right 的判断的可能性就越大，这样就可以重复利用之前判断过的回文信息了
              if i + p[i] > max_right:
                  # max_right 和 center 需要同时更新
                  max_right = i + p[i]
                  center = i
  
              if p[i] > max_len:
                  # 记录最长回文子串的长度和相应它在原始字符串中的起点
                  max_len = p[i]
                  start = (i - max_len) // 2
          return s[start: start + max_len]
  ```

- 蓄水池算法

  ```python
  # 不知道总体对象数量（或者数量巨大）的情况下抽取k个对象，使得每个对象被抽取到的概率相同
  import numpy as np
  def pool(L,k):
      arr = L[:k]
      for i,e in enumerate(L[k:]):
          r = np.random.randint(0,k+i+1)   # 第n个数以 k/n的概率保留
          if r<=k-1:
              arr[r] = e
      return arr
  ```

- 滑动窗口

  ```java
  求数组中每个滑动窗口中的最大值, 窗口大小为w
  窗口只能右边界或左边界向右滑的情况下，维持窗口内部最大值或者最小值快速更新的结构，复杂度为O(n)
  使用双端队列：核心思想是把当前窗口的最大值放在最前面，每次加入一个数需要把窗口内小于它的数去除
  
  public static int[] getMaxWindow(int[] arr, int w) {
      if (arr == null || w < 1 || arr.length < w) {
          return null;
      }
      LinkedList<Integer> qmax = new LinkedList<Integer>();
      int[] res = new int[arr.length - w + 1];
      int index = 0;
      for (int i = 0; i < arr.length; i++) {
          while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[i]) { 
              qmax.pollLast();//索引比当前小且值小可以直接抛弃
          }
  		// qmax 最大值是头部值，且前面的索引大于后面的索引
          qmax.addLast(i);
          if (qmax.peekFirst() == i - w) {
              qmax.pollFirst();
          }
          if (i >= w - 1) {
              res[index++] = arr[qmax.peekFirst()]; // peekFirst检索链表的第一个元素
          }
      }
      return res;
  }
  ```

- 单调栈

  ```java
  对数组中的每个数，找到其左边和右边比这个数大、且离这个数最近的位置。能整体代价达到O(N)
  1. 构建一个从大到小存储index的栈，如果当前压入的数不满足单调性则不压入，弹出栈顶元素并生成该元素的左右最大值信息（左最大值是弹出后的栈顶，右最大值是当前不满足单调性的数）
  
  例1：无重复值且求左右最小
  public static int[][] getNearLessNoRepeat(int[] arr) {
      int[][] res = new int[arr.length][2];
      Stack<Integer> stack = new Stack<>();
      for (int i = 0; i < arr.length; i++) {
          while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) { //栈从小到大
              int popIndex = stack.pop();
              int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
              res[popIndex][0] = leftLessIndex;
              res[popIndex][1] = i;
          }
          stack.push(i);
      }
      while (!stack.isEmpty()) {
          int popIndex = stack.pop();
          int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
          res[popIndex][0] = leftLessIndex;
          res[popIndex][1] = -1;
      }
      return res;
  }
  例2：有重复值且求左右最小
  每个压入的都是一个List，存放连续相同的值
  public static int[][] getNearLess(int[] arr) {
      int[][] res = new int[arr.length][2];
      Stack<List<Integer>> stack = new Stack<>();
      for (int i = 0; i < arr.length; i++) {
          while (!stack.isEmpty() && arr[stack.peek().get(0)] > arr[i]) {
              List<Integer> popIs = stack.pop();
              // 取位于下面位置的列表中，最晚加入的那个
              int leftLessIndex = stack.isEmpty() ? -1 : stack.peek().get(
                      stack.peek().size() - 1);
              for (Integer popi : popIs) {
                  res[popi][0] = leftLessIndex;
                  res[popi][1] = i;
              }
          }
          if (!stack.isEmpty() && arr[stack.peek().get(0)] == arr[i]) {
              stack.peek().add(Integer.valueOf(i));
          } else {
              ArrayList<Integer> list = new ArrayList<>();
              list.add(i);
              stack.push(list);
          }
      }
      while (!stack.isEmpty()) {
          List<Integer> popIs = stack.pop();
          // 取位于下面位置的列表中，最晚加入的那个
          int leftLessIndex = stack.isEmpty() ? -1 : stack.peek().get(
                  stack.peek().size() - 1);
          for (Integer popi : popIs) {
              res[popi][0] = leftLessIndex;
              res[popi][1] = -1;
          }
      }
      return res;
  }
  ```

- 单调栈应用：数组中累积和与最小值的乘积，假设叫做指标A。 给定一个数组，请返回子数组中，指标A最大的值。

  ```java
  数组中每一个数字作为子数组的最小值，求指标A，然后综合得到结果
  使用单调栈，左右分别求当前比其小的下标，得到子数组
  1. 暴力，获取任意窗口的指标A
  public static int max1(int[] arr) {
      int max = Integer.MIN_VALUE;
      for (int i = 0; i < arr.length; i++) {
          for (int j = i; j < arr.length; j++) {
              int minNum = Integer.MAX_VALUE;
              int sum = 0;
              for (int k = i; k <= j; k++) {
                  sum += arr[k];
                  minNum = Math.min(minNum, arr[k]);
              }
              max = Math.max(max, minNum * sum);
          }
      }
      return max;
  }
  2. 单调栈
  public static int max2(int[] arr) {
      int size = arr.length;
      int[] sums = new int[size];
      sums[0] = arr[0];
      for (int i = 1; i < size; i++) {
          sums[i] = sums[i - 1] + arr[i];
      }
      int max = Integer.MIN_VALUE;
      Stack<Integer> stack = new Stack<Integer>();
      for (int i = 0; i < size; i++) {
          while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
              int j = stack.pop();
              max = Math.max(max, (stack.isEmpty() ? sums[i - 1] : (sums[i - 1] - sums[stack.peek()])) * arr[j]);
          }
          stack.push(i);
      }
      while (!stack.isEmpty()) {
          int j = stack.pop();
          max = Math.max(max, (stack.isEmpty() ? sums[size - 1] : (sums[size - 1] - sums[stack.peek()])) * arr[j]);
      }
      return max;
  ```

  

