public abstract class Node {

    private Token token;
    private Node left;
    private Token op;
    private Node right;

    public Node(Token token) {
        this.token = token;
    }

    public Node(Node left, Token op, Node right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    public Token getToken() {
        return token;
    }

    public Node getLeft() {
        return left;
    }

    public Token getOp() {
        return op;
    }

    public Node getRight() {
        return right;
    }
}
