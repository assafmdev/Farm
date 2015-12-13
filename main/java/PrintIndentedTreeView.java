import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

/**
 * Created by assafm on 14/10/15.
 */
public class PrintIndentedTreeView {

	public static void main(String[] args) {

		Tree tree = new Tree();

		BufferedReader bufferedRead = new BufferedReader(new InputStreamReader(System.in));
		String inputString = null;
		try {
			while (true) {
				boolean isTestDataCanbeAdded = true;
				//Printing the menu of instructions for the user
				printMenu();
				//Reading the user input
				inputString = bufferedRead.readLine();
				//The user command to exit the program
				if("exit".equalsIgnoreCase(inputString))
					System.exit(1);
				//The user command to print the tree
				else if ("print".equalsIgnoreCase(inputString)) 					
					print(tree);				
				//Hint: In case the user wants auto created tree for testing
				else if("test".equalsIgnoreCase(inputString) && isTestDataCanbeAdded) {
					createTestTree(tree);					
					isTestDataCanbeAdded = false;
				}
				//The user input node to tree
				else if(inputString != null && inputString.toLowerCase().startsWith("input")) {
					input(tree, inputString);
				}
				//The command incorrect
				else {
					//In case the command entered is not supported or invalid
					System.out.println("The command you have entered is invalid");
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method insert new node to the tree from the user input
	 * @param tree The tree
	 * @param inputString The input from the user
	 */
	private static void input(Tree tree, String inputString) {	
		if(inputString.length() == 9) {
			Character parent = inputString.charAt(6);
			Character child = inputString.charAt(8);		
			if(Character.isLetter(parent) && Character.isLetter(child)) {
				System.out.println("> input " + parent + " " + child);
				tree.addNode(parent, child);
			}
			else {
				System.out.println("One or both of the chars you entered are invalid");
			}
		}
		else {
			System.out.println("The input command is not corect");
		}
	}

	/**
	 * This method prints out the tree in indented view
	 * @param tree The tree
	 */
	private static void print(Tree tree) {
		StringBuffer strBuf = tree.getIndentedTree();
		if(strBuf != null) {
			System.out.println("The indented tree:");
			System.out.println(strBuf);
		}
		else {
			System.out.println("The tree is empty");
		}
	}

	/**3
	 * The method prints out the menu for the user
	 */
	private static void printMenu() {
		System.out.println("\nplease type the command:\ninput - input for the program concatenated with 2 chars\n" +
				"print - printing the indented tree\nexit - quit the program");
	}

	/**
	 * This method is for test 
	 * creating the tree as in the example
	 * @param tree
	 */
	private static void createTestTree(Tree tree) {
		tree.addNode('a', 'b');
		tree.addNode('b', 'c');
		tree.addNode('c', 'd');
		tree.addNode('c', 'f');
		tree.addNode('b', 'e');
		tree.addNode('e', 'g');
		System.out.println("Test tree created succesfully");
	}
}
