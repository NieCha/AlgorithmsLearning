- 位运算

  ```java
  给定两个有符号32位整数a和b，返回a和b中较大的。
  【要求】不用做任何比较判断
  public static int flip(int n) {
      return n ^ 1;
  }
  
  public static int sign(int n) {
      return flip((n >> 31) & 1);
  }
  
  public static int getMax1(int a, int b) {
      int c = a - b;
      int scA = sign(c);  //符号为正a大，符号为负b大  scA表示c的符号，scB表示c的相反的符号
      int scB = flip(scA);
      return a * scA + b * scB;  //scA是1返回a，scA是0返回B. 注意a-b的值可能出现溢出
  }
  
  public static int getMax2(int a, int b) {
      int c = a - b;  // 先比较a与b两个数的符号，符号不同difSab==1，sameSab==0
      int sa = sign(a);
      int sb = sign(b);
      int sc = sign(c);
      int difSab = sa ^ sb;
      int sameSab = flip(difSab); // 如果a为0或正，那么b为负(sa==1,sb==0)，则返回a;
      int returnA = difSab * sa + sameSab * sc;
      int returnB = flip(returnA); // 如果a为负，那么b为0或正(sa==0,sb==1)，则返回b;
      return a * returnA + b * returnB;  //如果符号相同 就看c的符号
  }
  ```

- 给定两个有符号32位整数a和b，不能使用算术运算符，分别实现a和b的加、减、乘、除运 算

  ```java
  【要求】如果给定a、b执行加减乘除的运算结果就会导致数据的溢出，那么你实现的函数不必对此
  负责，除此之外请保证计算过程不发生溢出
  public static int add(int a, int b) {
      int sum = a;
      while (b != 0) {
          sum = a ^ b;
          b = (a & b) << 1;
          a = sum;
      }
      return sum;
  }
  
  public static int negNum(int n) {
      return add(~n, 1);
  }
  
  public static int minus(int a, int b) {
      return add(a, negNum(b));
  }
  
  public static int multi(int a, int b) {
      int res = 0;
      while (b != 0) {
          if ((b & 1) != 0) {
              res = add(res, a);
          }
          a <<= 1;
          b >>>= 1;
      }
      return res;
  }
  
  public static boolean isNeg(int n) {
      return n < 0;
  }
  
  public static int div(int a, int b) {
      int x = isNeg(a) ? negNum(a) : a;
      int y = isNeg(b) ? negNum(b) : b;
      int res = 0;
      for (int i = 31; i > -1; i = minus(i, 1)) {
          if ((x >> i) >= y) {
              res |= (1 << i);
              x = minus(x, y << i);
          }
      }
      return isNeg(a) ^ isNeg(b) ? negNum(res) : res;
  }
  
  public static int divide(int a, int b) {
      if (b == 0) {
          throw new RuntimeException("divisor is 0");
      }
      if (a == Integer.MIN_VALUE && b == Integer.MIN_VALUE) {
          return 1;
      } else if (b == Integer.MIN_VALUE) {
          return 0;
      } else if (a == Integer.MIN_VALUE) {
          int res = div(add(a, 1), b);
          return add(res, div(minus(a, multi(res, b)), b));
      } else {
          return div(a, b);
      }
  }
  ```

- 判断一个32位正数是不是2的幂、4的幂

  ```java
  public static boolean is2Power(int n) {
      return (n & (n - 1)) != 0; // 2的幂，是只有一位二进制是1，其余二进制位都是0
  }
  public static boolean is4Power(int n) { // 在n为2的幂次的基础上再添加一个条件：n的二进制的偶数位为1，奇数位为0
      return (n & (n - 1)) != 0 && (n & 0x55555555) != 0;
  }
  ```

  

