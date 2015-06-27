
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * A coding tree is used for the data compression of the file input.
 *
 *
 * @author Casey Morrison and Vladimir Gudzyuk
 * @version Dec 4, 2013 TCSS 242A Fall 2013
 */
public class CodingTree {
	
	Node root;

	String codeStr;
	
	MyHashTable<String, String> codes;
	
	List<Node> nodes;
	
	List<String> my_words;
	
	List<Integer> count;
	
	List<String> codeList;

	/**
	 * Constructs a coding tree.
	 * @param text
	 */
	public CodingTree(String text) {
		nodes = new ArrayList<>();
		
		count = new ArrayList<>();
		
		my_words = new ArrayList<>();
		
		nodes = new ArrayList<>();
		
		codes = new MyHashTable(16384);
		
		codeStr = "";
		
		codeList = new ArrayList<>();
		
		
		String word = "";
		int word_count = 0;
		for(int i = 0; i < text.length(); i++) {
			if (isLetter(text.charAt(i))) {
				word+=text.charAt(i);
				//add letter to string
			} else { // separator
				//add word to list
				if(word.length() < 1) {
					word+=text.charAt(i);
				}
				if (!my_words.contains(word)) {
					my_words.add(word);
					word_count++;
					word = "";
					count.add(1);
				} else {
					//then already contains word
					int index = my_words.indexOf(word);
					count.set(index, count.get(index) + 1);
				}
				
				word = "";
				
			}
							
		}
		for (int i = 0; i < my_words.size(); i++) {
			Node temp = new Node(my_words.get(i), count.get(i));
			nodes.add(temp);
			//for each node that is added, add to hashtable
		}
		
		PriorityQueue<Node> leastFirst = createPriority();
		
		adding(leastFirst);
		
		System.out.println(word_count + " This is the word count");
		
		preorder();
		
		for (String a : codeList) {
			codeStr += "(" + a + ")\n";
		}
		//codeStr = "";
	}
	
	/**
	 * Adds the two nodes to create a new tree.
	 * @param pq
	 */
	private void adding(PriorityQueue<Node> pq) {
		while (pq.size() > 1) {
			Node temp = pq.poll();
			Node temp2 = pq.poll();

			//temp2.frequency = temp.frequency + temp2.frequency;
			pq.add(combineNodes(temp, temp2));
			//System.out.println(temp.frequency + temp2.frequency);
		}
		//System.out.println(pq);
		root = pq.poll();
//		Node tempNode = root;
//		while (!(tempNode.left == null)) {
//			tempNode = tempNode.left;
//		}
		//System.out.println(root.frequency + "root");
	}
	
	// recursive function to read codes out of the finished tree
	/**
	 * 
	 * @param n1 node 1 to be combined
	 * @param n2 node 2 to be combined with n1
	 * @return the head node with combined nodes
	 */
	public Node combineNodes(Node n1, Node n2) {
		Node tempnode = new Node();
		int frequency = n1.frequency + n2.frequency;
		if (n1.frequency > n2.frequency) {
			tempnode.right = n1;
			tempnode.left = n2;
			tempnode.frequency = frequency;
			
		} else {
			tempnode.right = n2; 
			tempnode.left = n1;
			tempnode.frequency = frequency;
			
		}
		return tempnode;
	}
	
	/**
	 * Tests if the input is a letter.
	 * @param c
	 * @return
	 */
	private boolean isLetter(char c) {
		if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122)  || c == '\'') {
			return true;
		}
		return false;
	}
	
	/**
	 * Method used for ordering and creating the tree.
	 * @param bits
	 * @param root
	 */
	private void preorder(String bits, Node root) {
		
		if(!(root.my_word == "") && !(root == null)) {
			codeList.add(root.my_word + ", " + bits);
			//adds leaf node to hashtable
			codes.put(root.my_word, bits);
		} else {
			String a = bits + "0";
			String b = bits + "1";
			preorder(a, root.left);
			preorder(b, root.right);
		}
	}

	/**
	 * method used to add to list of codes used in sequence with healper method
	 */
	public void preorder() {
		 preorder("", root);
		
	}
	
	/**
	 * The inner class used for creates nodes to compare and link in the tree.
	 */
	private class Node implements Comparable<Node>{
		/**
		 * the node String
		 */
		String my_word;
		/**
		 * the frequency count for the charchter
		 */
		int frequency;
		
		Node left;
		
		Node right;
		
		/**
		 * 
		 * @param ch the char that is tracked
		 * @param frequency the char frequency set
		 * constructs node
		 */
		public Node(String ch, int frequency) {
			my_word = ch;
			this.frequency = frequency;

		}
		
		public Node() {
			my_word = "";
		}
		
		public String toString(){
			return (my_word+ "*****" + frequency + "\n");
		}
		/**
		 * a method used to compare nodes by frequnecy
		 */
		@Override
		public int compareTo(Node o) {
			return frequency - o.frequency;
		}
	}
	
	/**
	 * Method to create a priority queue.
	 * @return
	 */
	private PriorityQueue<Node> createPriority() {
		PriorityQueue<Node> pq = new PriorityQueue<Node>(nodes);
		return pq;
	}

}
