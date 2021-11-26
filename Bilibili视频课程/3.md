- 比较器：重载比较运算符

  ```java
  import java.util.Arrays;
  import java.util.Comparator;
  import java.util.PriorityQueue;
  import java.util.TreeSet;
  
  public class Code03_Comparator {
  	public static class Student {
  		public String name;
  		public int id;
  		public int age;
  		public Student(String name, int id, int age) {
  			this.name = name;
  			this.id = id;
  			this.age = age;
  		}
  	}
  
  	public static class IdAscendingComparator implements Comparator<Student> {
  		@Override
  		public int compare(Student o1, Student o2) {
  			return o1.id - o2.id;
  		}
  	}
  	public static class IdDescendingComparator implements Comparator<Student> {
  
  		@Override
  		public int compare(Student o1, Student o2) {
  			return o2.id - o1.id;
  		}
  	}
  	public static class AgeAscendingComparator implements Comparator<Student> {
  
  		@Override
  		public int compare(Student o1, Student o2) {
  			return o1.age - o2.age;
  		}
  
  	}
  	public static class AgeDescendingComparator implements Comparator<Student> {
  
  		@Override
  		public int compare(Student o1, Student o2) {
  			return o2.age - o1.age;
  		}
  	}
  	public static void printStudents(Student[] students) {
  		for (Student student : students) {
  			System.out.println("Name : " + student.name + ", Id : " + student.id + ", Age : " + student.age);
  		}
  	}
  	public static void printArray(Integer[] arr) {
  		if (arr == null) {
  			return;
  		}
  		for (int i = 0; i < arr.length; i++) {
  			System.out.print(arr[i] + " ");
  		}
  		System.out.println();
  	}
  	public static class MyComp implements Comparator<Integer> {
  		@Override
  		public int compare(Integer o1, Integer o2) {
  			return o2 - o1;
  		}
  
  	}
  
  	public static void main(String[] args) {
  		Student student1 = new Student("A", 2, 23);
  		Student student2 = new Student("B", 3, 21);
  		Student student3 = new Student("C", 1, 22);
  
  		Student[] students = new Student[] { student1, student2, student3 };
  
  		Arrays.sort(students, new IdAscendingComparator());
  		printStudents(students);
  		System.out.println("===========================");
  
  		Arrays.sort(students, new IdDescendingComparator());
  		printStudents(students);
  		System.out.println("===========================");
  
  		Arrays.sort(students, new AgeAscendingComparator());
  		printStudents(students);
  		System.out.println("===========================");
  
  		Arrays.sort(students, new AgeDescendingComparator());
  		printStudents(students);
  		System.out.println("===========================");
  		System.out.println("===========================");
  		System.out.println("===========================");
  		System.out.println("===========================");
  
  		PriorityQueue<Student> maxHeapBasedAge = new PriorityQueue<>(new AgeDescendingComparator());
  		maxHeapBasedAge.add(student1);
  		maxHeapBasedAge.add(student2);
  		maxHeapBasedAge.add(student3);
  		while (!maxHeapBasedAge.isEmpty()) {
  			Student student = maxHeapBasedAge.poll();
  			System.out.println("Name : " + student.name + ", Id : " + student.id + ", Age : " + student.age);
  		}
  		System.out.println("===========================");
  
  		PriorityQueue<Student> minHeapBasedId = new PriorityQueue<>(new IdAscendingComparator());
  		minHeapBasedId.add(student1);
  		minHeapBasedId.add(student2);
  		minHeapBasedId.add(student3);
  		while (!minHeapBasedId.isEmpty()) {
  			Student student = minHeapBasedId.poll();
  			System.out.println("Name : " + student.name + ", Id : " + student.id + ", Age : " + student.age);
  		}
  		System.out.println("===========================");
  		System.out.println("===========================");
  		System.out.println("===========================");
  
  		TreeSet<Student> treeAgeDescending = new TreeSet<>(new AgeDescendingComparator());
  		treeAgeDescending.add(student1);
  		treeAgeDescending.add(student2);
  		treeAgeDescending.add(student3);
  
  		Student studentFirst = treeAgeDescending.first();
  		System.out.println("Name : " + studentFirst.name + ", Id : " + studentFirst.id + ", Age : " + studentFirst.age);
  
  		Student studentLast = treeAgeDescending.last();
  		System.out.println("Name : " + studentLast.name + ", Id : " + studentLast.id + ", Age : " + studentLast.age);
  		System.out.println("===========================");
  	}
  }
  
  ```

- 计数排序：计数排序（counting sort）就是一种牺牲内存空间来换取低时间复杂度的排序算法，同时它也是一种不基于比较的算法（基于比较的算法最快是O(Nlog(N)）

  ```python
  def counting_sort(array):
      largest = max(array); smallest = min(array)  # 获取最大，最小值
      counter = [0 for i in range(largest-smallest+1)]  # 用于统计个数的空数组
      idx = 0  # 桶内索引值
      for i in range(len(array)):
          counter[array[i]-smallest] += 1  # 统计每个元素出现的次数
      for j in range(len(counter)):
          while counter[j] > 0:
              array[idx] = j + smallest  # 取出元素
              idx += 1
              counter[j] -= 1
      return array
  ```

- 桶排序：是不基于比较的排序，时间复杂度为O(N)，额外空间负载度O(M)
  应用范围有限，需要样本的数据状况满足桶的划分

  ```java
  将数组分到有限数量的桶里，每个桶再个别排序
  ```

- 基数排序：是一种非比较型整数排序算法，依次在每一个位上排序

  ```python
  将整数按位数切割成不同的数字，然后按每个位数分别比较
  ① 将所有待比较数值（正整数）统一为同样的数位长度，数位较短的数前面补零。
  ② 从最低位开始，依次进行一次排序。
  ③ 这样从最低位排序一直到最高位排序完成以后, 数列就变成一个有序序列。
  时间复杂度：O(k*N)
  空间复杂度：O(k + N)
  稳定性：稳定
  def radix_sort(s):
      """基数排序"""
      i = 0 # 记录当前正在排拿一位，最低位为1
      max_num = max(s)  # 最大值
      j = len(str(max_num))  # 记录最大值的位数
      while i < j:
          bucket_list =[[] for _ in range(10)] #初始化桶数组
          for x in s:
              bucket_list[int(x / (10**i)) % 10].append(x) # 找到位置放入桶数组
          print(bucket_list)
          s.clear()
          for x in bucket_list:   # 放回原序列
              for y in x:
                  s.append(y)
          i += 1
  java版本
  public static void radixSort(int[] arr) {
      if (arr == null || arr.length < 2) {
          return;
      }
      radixSort(arr, 0, arr.length - 1, maxbits(arr));
  }
  
  public static int maxbits(int[] arr) {
      int max = Integer.MIN_VALUE;
      for (int i = 0; i < arr.length; i++) {
          max = Math.max(max, arr[i]);
      }
      int res = 0;
      while (max != 0) {
          res++;
          max /= 10;
      }
      return res;
  }
  
  public static void radixSort(int[] arr, int begin, int end, int digit) {
      final int radix = 10;
      int i = 0, j = 0;
  
      int[] bucket = new int[end - begin + 1];
      for (int d = 1; d <= digit; d++) {
          int[] count = new int[radix];
          for (i = begin; i <= end; i++) {
              j = getDigit(arr[i], d);
              count[j]++;
          }
          for (i = 1; i < radix; i++) {
              count[i] = count[i] + count[i - 1];
          }
          for (i = end; i >= begin; i--) {
              j = getDigit(arr[i], d);
              bucket[count[j] - 1] = arr[i];
              count[j]--;
          }
          for (i = begin, j = 0; i <= end; i++, j++) {
              arr[i] = bucket[j];
          }
      }
  }
  
  public static int getDigit(int x, int d) {
      return ((x / ((int) Math.pow(10, d - 1))) % 10);
  }
  ```

- 希尔排序

  ```python
  希尔排序(Shell Sort)是插入排序的一种算法，是对直接插入排序的一个优化，也称缩小增量排序。
  # 希尔排序
  def shell_sort(alist):
      """希尔排序"""
      n = len(alist)
      gap = n // 2
      while gap >= 1:
          for j in range(gap, n):
              i = j
              while (i - gap) >= 0:
                  if alist[i] < alist[i - gap]:
                      alist[i], alist[i - gap] = alist[i - gap], alist[i]
                      i -= gap
                  else:
                      break
          gap //= 2
  ```

- 排序算法的稳定性：同样值的个体之间，如果不因为排序而改变相对次序，就是这个排序是有稳定性
  **不具备稳定性**的排序： 选择排序、快速排序、堆排序 
  具备**稳定性**的排序： 冒泡排序、插入排序、归并排序、一切桶排序思想下的排序
  目前没有找到时间复杂度O(N*logN)，额外空间复杂度O(1)，又稳定的排序
  一般基础类型使用快速排序，非基础类型建议归并排序