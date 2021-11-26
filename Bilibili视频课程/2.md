-  用master公式来求解时间复杂度: 
  $$
  T(N)=a*T(N/b)+O(N^d)
  $$

- O(Nlog(N))归并排序：

 ```java
整体就是一个简单递归，左边排好序、右边排好序、让其整体有序,额外空间复杂度O(N)
public static void mergeSort(int[] arr) {
    if (arr == null || arr.length < 2) {
        return;
    }
    mergeSort(arr, 0, arr.length - 1);
}
public static void mergeSort(int[] arr, int l, int r) {
    if (l == r) {
        return;
    }
    int mid = l + ((r - l) >> 1);
    mergeSort(arr, l, mid);
    mergeSort(arr, mid + 1, r);
    merge(arr, l, mid, r);
}
public static void merge(int[] arr, int l, int m, int r) {
    int[] help = new int[r - l + 1];
    int i = 0;
    int p1 = l;
    int p2 = m + 1;
    while (p1 <= m && p2 <= r) {
        help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
    }
    while (p1 <= m) {
        help[i++] = arr[p1++];
    }
    while (p2 <= r) {
        help[i++] = arr[p2++];
    }
    for (i = 0; i < help.length; i++) {
        arr[l + i] = help[i];
    }
}
 ```

- 小和问题：当前数左边比其小的数的和，求取数组所有小和的和可以使用归并排序

```java
public static int smallSum(int[] arr) {
    if (arr == null || arr.length < 2) {
        return 0;
    }
    return mergeSort(arr, 0, arr.length - 1);
}
public static int mergeSort(int[] arr, int l, int r) {
    if (l == r) {
        return 0;
    }
    int mid = l + ((r - l) >> 1);
    return mergeSort(arr, l, mid) 
            + mergeSort(arr, mid + 1, r) 
            + merge(arr, l, mid, r);
}
public static int merge(int[] arr, int l, int m, int r) {
    int[] help = new int[r - l + 1];
    int i = 0;
    int p1 = l;
    int p2 = m + 1;
    int res = 0;
    while (p1 <= m && p2 <= r) {
        res += arr[p1] < arr[p2] ? (r - p2 + 1) * arr[p1] : 0;
        help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
    }
    while (p1 <= m) {
        help[i++] = arr[p1++];
    }
    while (p2 <= r) {
        help[i++] = arr[p2++];
    }
    for (i = 0; i < help.length; i++) {
        arr[l + i] = help[i];
    }
    return res;
}
```

- 快速排序

  ``` java
  1. 给定一个数组arr，和一个数num，请把小于num的数放在数组的 左边，等于num的数放
  在数组的中间，大于num的数放在数组的 右边。要求额外空间复杂度O(1)，时间复杂度
  O(N)
  注意： 划分值越靠近两侧，复杂度越高；划分值越靠近中间，复杂度越低，一般是把数组范围中的最后一个数作为划分值
  在小样本上排序直接使用快速排序即可。例如R-L<60
  public static void quickSort(int[] arr) {
      if (arr == null || arr.length < 2) {
          return;
      }
      quickSort(arr, 0, arr.length - 1);
  }
  public static void quickSort(int[] arr, int l, int r) {
      if (l < r) {
          swap(arr, l + (int) (Math.random() * (r - l + 1)), r); //随机选择一个数放到最后作为划分值
          int[] p = partition(arr, l, r);
          quickSort(arr, l, p[0] - 1);
          quickSort(arr, p[1] + 1, r);
      }
  }
  public static int[] partition(int[] arr, int l, int r) {
      int less = l - 1;
      int more = r;
      while (l < more) {
          if (arr[l] < arr[r]) {
              swap(arr, ++less, l++);  //与自身交换, less后移
          } else if (arr[l] > arr[r]) {// more自减，
              swap(arr, --more, l);
          } else {                    //l 直接后移
              l++;
          }
      }
      swap(arr, more, r);
      return new int[] { less + 1, more };
  }
  public static void swap(int[] arr, int i, int j) {
      int tmp = arr[i];
      arr[i] = arr[j];
      arr[j] = tmp;
  }
  ```

- 堆排序，重要的是堆结构，优先级队列底层是堆结构

  ```java
  1. 堆结构就是用数组实现的完全二叉树结构, 子树的最大值都在顶部就是大根堆, 最小值都在顶部就是小根堆(优先级队列结构，就是堆结构)
  public static void heapSort(int[] arr) {
      if (arr == null || arr.length < 2) {
          return;
      }
      for (int i = 0; i < arr.length; i++) {
          heapInsert(arr, i);
      }                             //让数组是大根堆
      //for (int i = arr.length-1; i >=0; i--) {
      //    heapify(arr, i, arr.length);
      //}  
      int size = arr.length;
      swap(arr, 0, --size);
      while (size > 0) {
          heapify(arr, 0, size);
          swap(arr, 0, --size);  //把堆的最大值和堆末尾的值交换，然后减少堆的大小之后，再去调整堆，一直周而复始，时间复杂度为O(N*logN)
      }
  }
  public static void heapInsert(int[] arr, int index) {
      while (arr[index] > arr[(index - 1) / 2]) {
          swap(arr, index, (index - 1) /2);
          index = (index - 1)/2 ;
      }
  }
  public static void heapify(int[] arr, int index, int size) {
      int left = index * 2 + 1;
      while (left < size) {
  		// 存在孩子，得到较大的孩子值
          int largest = left + 1 < size && arr[left + 1] > arr[left] ? left + 1 : left;
  		// 父亲和较大孩子值比较
          largest = arr[largest] > arr[index] ? largest : index;
          if (largest == index) {
              break;
          }
          swap(arr, largest, index);
          index = largest;
          left = index * 2 + 1;
      }
  }
  public static void swap(int[] arr, int i, int j) {
      int tmp = arr[i];
      arr[i] = arr[j];
      arr[j] = tmp;
  }
  ```

- 几乎有序的数组排序

  ```java
  已知一个几乎有序的数组，几乎有序是指，如果把数组排好顺序的话，每个元
  素移动的距离可以不超过k，并且k相对于数组来说比较小。请选择一个合适的
  排序算法针对这个数据进行排序
  public void sortedArrDistanceLessK(int[] arr, int k) {
      PriorityQueue<Integer> heap = new PriorityQueue<>();
      int index = 0;
      for (; index < Math.min(arr.length, k); index++) {
          heap.add(arr[index]);
      }
      int i = 0;
      for (; index < arr.length; i++, index++) {
          heap.add(arr[index]);
          arr[i] = heap.poll();
      }
      while (!heap.isEmpty()) {
          arr[i++] = heap.poll();
      }
  }
  // PriorityQueue 仅适用于部分需要获取最大的的堆场景，更多的场景需要自己实现
  ```

  