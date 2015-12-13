import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by assafm on 14/10/15.
 */
public class Tree {

	private Node root;
	private HashMap<Integer, Integer> nodesInLevelMap;
	/**
	 * This method add new new node to tree 
	 * if tree is null then the parent is root
	 * @param parent The char value of the parent node
	 * @param child The char value of the child node
	 * @return false If the parent is not found, otherwise true
	 */
	public boolean addNode(char parent, char child) {

		//Find the child node
		Node childNode = searchNode(root, child);
		//If the child exists then return false, 
		//we don't want to create circle in the tree
		if(childNode != null)
			return false;
		//Creates new child node
		childNode = new Node(child);
		//Search for the parent node
		Node parentNode =  searchNode(root, parent);
		//In case the parent doesn't exists, it could be only the root
		//otherwise return false
		if(parentNode == null && root == null) {
			parentNode = new Node(parent);
			root = parentNode;
		}			
		else if(parentNode == null && root != null)
			return false;
		//Add the child to the parent
		parentNode.addChild(childNode);
		return true;
	}

	/**
	 * This method search the tree for the input node for the given value 
	 * @param node The node to start the search from 
	 * @param value the value to search for
	 * @return if found returns the node with the value, else null
	 */
	private Node searchNode(Node node, char value) {

		if(node == null)
			return null;
		//The node has been found
		if(node.getValue() == value)
			return node;

		else {
			//Search the node on the children
			for(Node childNode : node.getChildren()) {
				Node temp = searchNode(childNode, value);
				//In case node was found, no need to continue the search
				if(temp != null)
					return temp;
			}
			return null;
		}
	}

	/**
	 * This method is wrapper method for printing the tree
	 * calculates the height of the tree and creating the indented tree
	 * @return The indented treeparentNode == null && root == null
	 */
	public StringBuffer getIndentedTree() {
		if(root != null) {
			int treeHeight = calculateTreeHeight(root);	
			nodesInLevelMap =  numOfLeafInLevel(treeHeight);
			int sizeInLevel = nodesInLevelMap.get(treeHeight-1)>treeHeight?treeHeight:nodesInLevelMap.get(treeHeight-1);			
			return printTree(root, treeHeight*(sizeInLevel));
		}
		return null;
	}

	/**
	 * This method calculate the tree height
	 * @param node The root of the tree
	 * @return The height of the tree
	 */
	private int calculateTreeHeight(Node node) {

		if(node == null)
			return 0;

		if(!node.hasChildern())
			return 1;

		int maxHeight = 1;
		for(Node nodeInTree : node.getChildren()) {
			int highet = calculateTreeHeight(nodeInTree)+1;
			//if the new height is higher than the current value replace them 
			if(highet > maxHeight)
				maxHeight = highet;
		}	
		return maxHeight;    		    	
	}	

	/**
	 * This method creates the indented tree view tree
	 * @param root The root of the tree
	 * @param treeHeight The height of the tree
	 * @return The tree to print
	 */
	private StringBuffer printTree(Node root, int treeHeight) {

		//In case the tree does not exist
		if(treeHeight == 0 )
			return null;
		if(treeHeight%2 != 0 )
			treeHeight++;
		ArrayList<ArrayList<Node>> currentLevel = new ArrayList<ArrayList<Node>>();
		ArrayList<ArrayList<Node>> nextLevel = new ArrayList<ArrayList<Node>>();		
		StringBuffer strBuf = new StringBuffer();		

		//Creates an array for the root
		ArrayList<Node> treeRoot = new ArrayList<Node>();
		treeRoot.add(root);
		currentLevel.add(treeRoot);

		int parentBuffer = treeHeight;
		//Run on all the tree levels
		for(int level = 0; level < treeHeight ; level++) {

			int preTab, postTab = 0;
			int totalNodeInLevel = 0;			
			//Iterates on group of children
			for(ArrayList<Node> childrenArray : currentLevel) {

				int numOfSibling = childrenArray.size();		
				//In case the array is empty, prints spaces for the next array
				if(numOfSibling == 0) {							
					addIndentation(strBuf, treeHeight/nodesInLevelMap.get(level-1));					
				}														
				int limit = 0;
				for(Node nodeInTree : childrenArray) {	
					limit = parentBuffer/numOfSibling;					
					if(numOfSibling == 1) {
						limit /=2;
					}
						
					preTab = limit/numOfSibling;					
					postTab = limit - preTab;

					addIndentation(strBuf, preTab);
					strBuf.append(nodeInTree.getValue());	
					if(childrenArray.indexOf(nodeInTree) != childrenArray.size() -1)
						addIndentation(strBuf, postTab);
					strBuf.append(" ");
					nextLevel.add((ArrayList<Node>)nodeInTree.getChildren());
				}	

				totalNodeInLevel += childrenArray.size();
			}												
			//Ending the level
			strBuf.append("\n");

			parentBuffer = (totalNodeInLevel!=0)?parentBuffer/totalNodeInLevel:0;			
			//Calculates the buffer for the next iteration			
			currentLevel = nextLevel;
			nextLevel = new ArrayList<ArrayList<Node>>();						
		}
		return strBuf;
	}

	/**
	 * This method appends to the StringBuffer param the " " as needed
	 * @param strBuf The StringBuffer to append to
	 * @param indentation The number of indentations
	 */
	private void addIndentation(StringBuffer strBuf, int indentation) {
		for(int i = 0; i< indentation; i++)
			strBuf.append(" ");
	}	

	private HashMap<Integer, Integer> numOfLeafInLevel(int treeHeight) {

		HashMap<Integer, Integer> retMap = new HashMap<Integer, Integer>();
		ArrayList<ArrayList<Node>> currentLevel = new ArrayList<ArrayList<Node>>();
		ArrayList<ArrayList<Node>> nextLevel = new ArrayList<ArrayList<Node>>();
		//Creates an array for the root
		ArrayList<Node> treeRoot = new ArrayList<Node>();
		treeRoot.add(root);
		currentLevel.add(treeRoot);

		for(int level = 0; level < treeHeight ; level++) {

			int totalNodeInLevel = 0;
			//Iterates on group of children
			for(ArrayList<Node> childrenArray : currentLevel) {
				for(Node nodeInTree : childrenArray) 
				nextLevel.add((ArrayList<Node>)nodeInTree.getChildren());

				totalNodeInLevel += childrenArray.size();
			}
			currentLevel = nextLevel;
			nextLevel = new ArrayList<ArrayList<Node>>(); 
			retMap.put(level, totalNodeInLevel);
		}
		return retMap;
	}
}
