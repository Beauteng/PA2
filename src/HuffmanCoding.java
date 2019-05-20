import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class HuffmanCoding {

    HuffmanCoding() {
    	
    }
    
    class Node {
		int value;
		String name;
        Node left, right, parent;
        
        public Node(int i, String s) {
            value = i;
            name = s;
            left = right = parent = null;
        }
        public int getVal() {
        	return value;
        }
    }
    
	static boolean check(char[] cArray, char i) {
		boolean found = false;
		for(char element : cArray) {
			if(element == i) {
				found = true;
			}
		}
		return found;
	}
	
	static int findSpot(char[] cArray, char i) {
		for(int j = 0; j<cArray.length; j++) {
			if(cArray[j] == i) {
				return j;
			}
		}
		return 0;
	}
	
	static int findLengthNeeded(int[] f) {
		int finalVal = 0;
		while(f[finalVal] != 0) {
			finalVal++;
		}
		return finalVal;
	}
	
	public static String findMap(String name, Node node, String bits) {
		if(node.left != null || node.right != null) {
			if(node.left.name.contains(name)) {
				bits = String.valueOf(bits) + String.valueOf(0);
				return findMap(name, node.left, bits);
			}else {
				bits = String.valueOf(bits) + String.valueOf(1);
				return findMap(name, node.right, bits);
			}
		}else {
			
		}
		return bits;
	}
	
	class NodeComparator implements Comparator<Node>{
		public int compare(Node n1, Node n2) {
			if(n1.getVal()>n2.getVal()) {
				return 1;
			}else if (n1.getVal()<n2.getVal()) {
				return -1;
			}else {
				return 0;
			}
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		HuffmanCoding hTree = new HuffmanCoding();
		File f = new File("input.txt");
		Scanner scan = new Scanner(new File("input.txt")).useDelimiter("'");
		char[] allCArray = new char[(int) f.length()];
		char[] cArray = new char[((int) f.length())+1];
		int[] fArray = new int[((int) f.length())+1];
		while(scan.hasNext()) {
			String s = scan.next();
			allCArray = s.toCharArray();
		}

		int a = 0;
		for(int i = 0; i<allCArray.length; i++) {
			if(!check(cArray, allCArray[i])) {
				cArray [a] = allCArray[i];
				fArray [a] = 1;
				a++;
			}else {
				fArray[findSpot(cArray, allCArray[i])]++;
			}
		}
		int finalLength = findLengthNeeded(fArray);
		char[] finalChar = new char[finalLength];
		int[] finalFreq = new int[finalLength];
		for(int i = 0; i<finalLength; i++) {
			finalChar[i] = cArray[i];
			finalFreq[i] = fArray[i];
		}
		Queue<Node> q = new LinkedList<Node>();
		PriorityQueue<Node> pQueue = new PriorityQueue<Node>(finalFreq.length, hTree.new NodeComparator());
		PriorityQueue<Node> pQueue2 = new PriorityQueue<Node>(finalFreq.length, hTree.new NodeComparator());
		for(int i = 0; i< finalFreq.length; i++) {
			Node node = hTree.new Node(finalFreq[i], String.valueOf(finalChar[i]));
			pQueue.add(node);
			q.add(node);
	    }
		for(int i = 0; i<allCArray.length; i++) {
			System.out.print(allCArray[i]);
		}
		System.out.println();
		for(int i = 0; i<finalFreq.length; i++) {
			Node s = pQueue.poll();
			pQueue2.add(s);
		}
		Node root,node3,node2,node1;
		node1 = node2 = node3 = root = null;
		while(!pQueue2.isEmpty()) {
			node1 = (Node) pQueue2.poll();
			node2 = (Node) pQueue2.poll();
			node3 = hTree.new Node((node1.value+node2.value), node1.name + node2.name);
			node3.left = node1;
			node3.right = node2;
			node1.parent = node2.parent = node3;
			if(!pQueue2.isEmpty()) {
				pQueue2.add(node3);
			} else {
				root = node3;
			}
		}
		String arr[] = new String[finalFreq.length]; 
		int k = 0;
		while(!q.isEmpty()) {
			Node look = q.poll();
			arr[k] = findMap(look.name, root, "");
			k++;
		}
		for(int i = 0; i<finalFreq.length; i++) {
			System.out.print(finalChar[i] + " | ");
			System.out.print(finalFreq[i] + " | ");
			System.out.print(arr[i]);
			System.out.println();
		}
		System.out.println();
	}
}
