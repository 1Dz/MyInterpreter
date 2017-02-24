import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by energo7 on 06.02.2017.
 */
public class Interpreter {

    static Map<String, BigDecimal> map = new HashMap<>();

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
        else if(node instanceof Compound)
            visitCompound((Compound) node);
        else if(node instanceof NoOp)
            visitNoOp(node);
        else if(node instanceof Assign)
            visitAssign(node);
        else if(node instanceof Var)
            return visitVar(node);
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

    private void visitCompound(Compound node) throws InterpretException {
        for(Node x : node.getChildren())
            visit(x);
    }

    private void visitNoOp(Node node)
    {
        //does nothing
    }

    private void visitAssign(Node node) throws InterpretException {
        String name = (String) node.getLeft().getToken().getValue();
        map.put(name, visit(node.getRight()));
    }

    private BigDecimal visitVar(Node node) throws InterpretException {
        String name = (String) node.getToken().getValue();
        BigDecimal res = map.get(name);
        if(res != null)
            return res;
        else throw new InterpretException("No such variable in global map");
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
