import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Tree interface and BinaryTree class from lec4b.
 */

public class BinaryTree implements Tree {
	class Node {
		int data;
		Node left, right;

		Node(int key) {
			this(key, null, null);
		}

		Node(int data, Node left, Node right) {
			this.data = data;
			this.left = left;
			this.right = right;
		}

		boolean isLeaf() {
			return left == null && right == null;
		}
	}

	Node root;
	int n;

	public void insert(int key) {
		n++;
		if (root == null)
			root = new Node(key);
		else if (root.left == null)
			root.left = new Node(key);
		else if (root.right == null)
			root.right = new Node(key);
		else if (key % 2 == 0)
			root = new Node(key, root, null);
		else
			root = new Node(key, null, root);
	}

	public boolean contains(int key) {
		return containsHelper(key, root);
	}

	private boolean containsHelper(int key, Node p) {
		if (p == null)
			return false;
		if (p.data == key)
			return true;
		return containsHelper(key, p.left) || containsHelper(key, p.right);
	}

	public int size() {
		return n;
	}

	/**
	 * Recursive removing leaves from given tree
	 * 
	 * @param node
	 *            given tree
	 * @return tree that already removed leaves
	 */
	private Node pruneLeavesRemove(Node node) {
		if (node.left == null)
			if (node.right == null) {
				n--;
				return null;
			} else
				node.right = this.pruneLeavesRemove(node.right);
		else {
			node.left = this.pruneLeavesRemove(node.left);
			if (node.right != null)
				node.right = this.pruneLeavesRemove(node.right);
		}
		return node;
	}

	/**
	 * Remove leaves from root
	 * 
	 * @return tree that already removed leaves
	 */
	public Node pruneLeaves() {
		return this.pruneLeavesRemove(root);
	}

	/**
	 * return tree in level order
	 * 
	 * for each time we will add a level of nodes to the list using the child of
	 * upper level's node
	 * 
	 * for each node or leaves, we will only insert it to the list once, so the
	 * running time will be linear.
	 * 
	 * @return List that in level order
	 */
	public List<Integer> levelOrder() {
		List<Node> nodes = new ArrayList<Node>();
		int p = 0;
		nodes.add(root);

		while (nodes.size() < this.size()) {
			int end = nodes.size();
			for (int i = p; i < end; i++) {
				if (nodes.get(i).left != null)
					nodes.add(nodes.get(i).left);
				if (nodes.get(i).right != null)
					nodes.add(nodes.get(i).right);
			}
			p = end;
		}

		List<Integer> keys = new ArrayList<Integer>();
		for (int i = 0; i < n; i++)
			keys.add(nodes.get(i).data);
		return keys;
	}

	@Test
	public void test() {
		int[] a = new int[] { 3, 9, 7, 2, 1, 5, 6, 4, 8 };
		BinaryTree tr = new BinaryTree();
		assert tr.isEmpty();
		for (int key : a)
			tr.insert(key);
		assert tr.size() == a.length;
		assert !tr.root.isLeaf();
		for (int key : a)
			assert tr.contains(key);
		try {
			tr.remove(3);
		} catch (UnsupportedOperationException e) {
		}

		// test level order
		assertEquals("[4, 5, 8, 6, 2, 3, 1, 9, 7]", tr.levelOrder().toString());

		// test pruneLeaves;
		tr.pruneLeaves();

		assert tr.contains(3);
		assert !tr.contains(9);
		assert !tr.contains(7);
		assert tr.contains(2);
		assert !tr.contains(1);
		assert tr.contains(5);
		assert !tr.contains(6);
		assert tr.contains(4);
		assert !tr.contains(8);

		// test level order
		assertEquals("[4, 5, 2, 3]", tr.levelOrder().toString());

		System.out.println("Passed all tests...");
	}
}

interface Tree {
	void insert(int key);

	default void remove(int key) {
		throw new UnsupportedOperationException();
	}

	boolean contains(int key);

	int size();

	default boolean isEmpty() {
		return size() == 0;
	}
}
