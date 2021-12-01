- 哈希函数：

  ```java
  哈希函数又称为散列函数，就是把任意长度的输入（又叫做预映射， pre-image），通过散列算法，变换成固定长度的输出，该输出就是散列值
  哈希函数还有以下特点:免碰撞（理论上不成立，但是概率上可忽略不计），隐匿性（不能还原），常见的哈希函数有：SHA1、MD5、SHA2等
  ```

- 哈希表就是利用哈希函数，可以根据关键码而直接进行访问的数据结构，也就是将关键码（Key value）通过哈希函数映射到表中的一个位置来进行访问。
  而计算散列地址的方法有很多种，通常我们使用的是除留余数法

  ```java
  首先使用hash函数进行映射，然后取模得到位置
  HashMap<String, String> map = new HashMap<>();
  ```

- 设计RandomPool结构

  ```java
  设计一种结构，在该结构中有如下三个功能:
  insert(key):将某个key加入到该结构，做到不重复加入
  delete(key):将原本在结构中的某个key移除
  getRandom(): 等概率随机返回结构中的任何一个key。
  Insert、delete和getRandom方法的时间复杂度都是O(1)
  
  import java.util.HashMap;
  public class Code02_RandomPool {
  
  	public static class Pool<K> {
  		private HashMap<K, Integer> keyIndexMap;
  		private HashMap<Integer, K> indexKeyMap;
  		private int size;
  
  		public Pool() {
  			this.keyIndexMap = new HashMap<K, Integer>();
  			this.indexKeyMap = new HashMap<Integer, K>();
  			this.size = 0;
  		}
  
  		public void insert(K key) {
  			if (!this.keyIndexMap.containsKey(key)) {
  				this.keyIndexMap.put(key, this.size);
  				this.indexKeyMap.put(this.size++, key);
  			}
  		}
  
  		public void delete(K key) {
  			if (this.keyIndexMap.containsKey(key)) {
  				int deleteIndex = this.keyIndexMap.get(key);
  				int lastIndex = --this.size;
  				K lastKey = this.indexKeyMap.get(lastIndex);
  				this.keyIndexMap.put(lastKey, deleteIndex);
  				this.indexKeyMap.put(deleteIndex, lastKey);
  				this.keyIndexMap.remove(key);
  				this.indexKeyMap.remove(lastIndex);
  			}
  		}
  
  		public K getRandom() {
  			if (this.size == 0) {
  				return null;
  			}
  			int randomIndex = (int) (Math.random() * this.size); // 0 ~ size -1
  			return this.indexKeyMap.get(randomIndex);
  		}
  
  	}
  
  	public static void main(String[] args) {
  		Pool<String> pool = new Pool<String>();
  		pool.insert("zuo");
  		pool.insert("cheng");
  		pool.insert("yun");
  		System.out.println(pool.getRandom());
  		System.out.println(pool.getRandom());
  		System.out.println(pool.getRandom());
  		System.out.println(pool.getRandom());
  		System.out.println(pool.getRandom());
  		System.out.println(pool.getRandom());
  	}
  }
  ```

- 布隆过滤器Bloom Filter

  ```java
  用于检索一个元素是否在一个集合中。它的优点是空间效率和查询时间都比一般的算法要好的多，缺点是有一定的误识别率和删除困难
  时间复杂度低，增加和查询元素的时间复杂为O(N)
  保密性强，布隆过滤器不存储元素本身，存储空间小
  有点一定的误判率，无法获取元素本身，很难删除元素
  
  位图 bit map 
  根据样本量、失误率来确定m和k（向上取整），计算实际失误率  https://blog.csdn.net/gaoyueace/article/details/90410735
  1. 开辟一个m位的bitArray(位数组)
  2. 开始所有数据全部置 0 。当一个元素过来时，能过多个哈希函数（h1,h2,h3....）计算不同的在哈希值，并通过哈希值找到对应的bitArray下标处，将里面的值 0 置为 1 。
  3. 布隆过滤器有一个误判率的概念，误判率越低，则数组越长，所占空间越大。误判率越高则数组越小，所占的空间越小
  4. 判断数据可能存在或一定不存在
  ```

- 一致性哈希原理**consistent hashing**

  ```java
  可以保证当机器增加或者减少时，节点之间的数据迁移只限于两个节点之间，不会造成全局的网络问题
  节点分布不均匀、虚拟节点技术
  https://www.cnblogs.com/myshowtime/p/15145988.html
  ```
- 岛问题

    ```java
    一个矩阵中只有0和1两种值，每个位置都可以和自己的上、下、左、右 四个位置相连，如 果有一片1连在一起，这个部分叫做一个岛，求一个矩阵中有多少个岛
    public static int countIslands(int[][] m) {
        if (m == null || m[0] == null) {
            return 0;
        }
        int N = m.length;
        int M = m[0].length;
        int res = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (m[i][j] == 1) {
                    res++;
                    infect(m, i, j, N, M);
                }
            }
        }
        return res;
    }

    public static void infect(int[][] m, int i, int j, int N, int M) {
        if (i < 0 || i >= N || j < 0 || j >= M || m[i][j] != 1) {
            return;
        }
        m[i][j] = 2;
        infect(m, i + 1, j, N, M);
        infect(m, i - 1, j, N, M);
        infect(m, i, j + 1, N, M);
        infect(m, i, j - 1, N, M);
    }
    并行算法
    1. 分块分别统计，每一块记录边界信息
    2. 利用边界连接条件进行合并
    ```

- 有序表

  ```java
  有序表，是指这样的线性表，其中所有元素以递增或递减方式有序排列
  ```

- 并查集https://hrz123.github.io/2020/07/15/python%E5%AE%9E%E7%8E%B0%E5%B9%B6%E6%9F%A5%E9%9B%86/

  ```java
  并查集（UnionFind）也被称为不相交集数据结构。顾名思义，并查集主要操作是合并与查询，它是把初始不相交的集合经过多次合并操作后合并为一个大集合，然后可以通过查询判断两个元素是否已经在同一个集合中了
  
  public static class Element<V> {
      public V value;
  
      public Element(V value) {
          this.value = value;
      }
  
  }
  
  public static class UnionFindSet<V> {
      public HashMap<V, Element<V>> elementMap;
      public HashMap<Element<V>, Element<V>> fatherMap;
      public HashMap<Element<V>, Integer> rankMap;
  
      // 初始化
      public UnionFindSet(List<V> list) {
          elementMap = new HashMap<>();
          fatherMap = new HashMap<>();
          rankMap = new HashMap<>();
          for (V value : list) {
              Element<V> element = new Element<V>(value);
              elementMap.put(value, element);
              fatherMap.put(element, element);
              rankMap.put(element, 1);
          }
      }
  	// 一直往上找，把代表元素返回
      private Element<V> findHead(Element<V> element) {
          Stack<Element<V>> path = new Stack<>();
          while (element != fatherMap.get(element)) {
              path.push(element);
              element = fatherMap.get(element);
          }
          while (!path.isEmpty()) {
              fatherMap.put(path.pop(), element);
          }
          return element;
      }
  
      public boolean isSameSet(V a, V b) {
          if (elementMap.containsKey(a) && elementMap.containsKey(b)) {
              return findHead(elementMap.get(a)) == findHead(elementMap.get(b));
          }
          return false;
      }
  
      public void union(V a, V b) {
          if (elementMap.containsKey(a) && elementMap.containsKey(b)) {
              Element<V> aF = findHead(elementMap.get(a));
              Element<V> bF = findHead(elementMap.get(b));
              if (aF != bF) {
                  Element<V> big = rankMap.get(aF) >= rankMap.get(bF) ? aF : bF;
                  Element<V> small = big == aF ? bF : aF;
                  fatherMap.put(small, big);
                  rankMap.put(big, rankMap.get(aF) + rankMap.get(bF));
                  rankMap.remove(small);
              }
          }
      }
  ```

  







