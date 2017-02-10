import java.math.BigDecimal;

/**
 * Created by energo7 on 06.02.2017.
 */
public class Interpreter {

    public Interpreter() {

    }

    public BigDecimal init(Node tree) throws InterpretException {
        return visit(tree);
    }

    private BigDecimal visit(Node node) throws InterpretException {
        if(node instanceof Num)
            return visitNum(node).stripTrailingZeros();
        else if(node instanceof BinOP)
            return visitBinOp(node).stripTrailingZeros();
        else if(node instanceof UnOp)
            return visitUnOp(node).stripTrailingZeros();
        throw new InterpretException("Something went wrong");
    }

    private BigDecimal visitUnOp(Node node) throws InterpretException {
        if(node.getOp().getType() == Type.PLUS)
        {
            return BigDecimal.ZERO.add(visit(node.getRight()));
        }
        if(node.getOp().getType() == Type.MINUS)
        {
            return BigDecimal.ZERO.subtract(visit(node.getRight()));
        }
        throw new InterpretException("Something went wrong in visitUnOp method");
    }

    private BigDecimal visitNum(Node node)
    {
        return new BigDecimal(String.valueOf(node.getToken().getValue()));
    }

    private BigDecimal visitBinOp(Node node) throws InterpretException {
        if(node.getOp().getType() == Type.PLUS)
        {
            return visit(node.getLeft())
                    .add(visit(node.getRight()));
        }
        if(node.getOp().getType() == Type.MINUS)
        {
            return visit(node.getLeft())
                    .subtract(visit(node.getRight()));
        }
        if(node.getOp().getType() == Type.MULT)
        {
            return visit(node.getLeft())
                    .multiply(visit(node.getRight()));
        }
        if(node.getOp().getType() == Type.DIV)
        {
            return visit(node.getLeft())
                    .divide(visit(node.getRight()), 4, BigDecimal.ROUND_HALF_DOWN);
        }
        throw new InterpretException("Something went wrong");
    }
}
