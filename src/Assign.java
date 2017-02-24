/**
 * Created by energo7 on 13.02.2017.
 */
public class Assign extends Node{

    private Node left;
    private Node right;
    private Token op;

    public Assign(Node left, Node right, Token op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public Token getOp() {
        return op;
    }
}
