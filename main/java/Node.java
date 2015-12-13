import java.util.ArrayList;
import java.util.List;

/**
 * Created by assafm on 12/10/15.
 */
public class Node {

    private char value;
    private  List<Node> children;

    public Node(char value) {
        this.value = value;
        children = new ArrayList<Node>();
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public void addChild(Node node) {
        this.children.add(node);
    }

    public boolean hasChildern() {
        return !children.isEmpty();
    }
    
    public int getNumOfChildren() {
    	return children.size();
    }    
}
