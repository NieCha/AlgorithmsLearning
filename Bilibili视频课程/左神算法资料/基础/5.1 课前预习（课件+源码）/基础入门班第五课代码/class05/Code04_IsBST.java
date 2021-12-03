package class05;

import java.util.LinkedList;

public class Code04_IsBST {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

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
}
