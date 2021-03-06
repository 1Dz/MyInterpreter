import com.sun.istack.internal.Nullable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Created by energo7 on 02.02.2017.
 */
public class Parser {

    private String input;
    private Deque<Token> deque;
    private Lexer lexer;
    private Token currentToken;
    private List<Node> list = new ArrayList<>();
    public Parser(String input) throws InterpretException {
        this.input = input.replaceAll(" ", "");
        this.lexer = new Lexer(this.input);
        this.deque = lexer.tokenize();
        this.currentToken = deque.poll();
    }

    public Node parse() throws InterpretException {
        return program();
    }
    private void eat(Type type) throws InterpretException {
        if(currentToken != null && currentToken.getType() == type)
        {
            Token t = deque.poll();
            if(t != null)
                currentToken = t;
        }
        else throw new InterpretException("Type are not equal in EAT method");
    }

    private @Nullable Node factor() throws InterpretException {
        Node result = null;
        if(currentToken.getType() == Type.PLUS)
        {
            Token t = currentToken;
            eat(Type.PLUS);
            return new UnOp(t, factor());
        }
        else if(currentToken.getType() == Type.MINUS)
        {
            Token t = currentToken;
            eat(Type.MINUS);
            return new UnOp(t, factor());
        }
        else if(currentToken.getType() == Type.INTEGER) {
            result = new Num(currentToken);
            eat(Type.INTEGER);
        }
        else if(currentToken.getType() == Type.OPEN)
        {
            eat(Type.OPEN);
            result = expr();
            eat(Type.CLOSE);
        }
        else result = variable();
        return result;
    }

    private Node term() throws InterpretException {
        Node result = factor();
        Token token;
        while (currentToken.getType() == Type.MULT || currentToken.getType() == Type.DIV) {
            token = currentToken;
            if (currentToken.getType() == Type.MULT) {
                eat(Type.MULT);
                result = new BinOP(result, token, factor());
            }
            else if (currentToken.getType() == Type.DIV) {
                eat(Type.DIV);
                result = new BinOP(result, token, factor());
            }
        }
        return result;
    }

    private @Nullable Node expr() throws InterpretException {
        Node result = term();
        Token token;
        while (currentToken.getType() == Type.PLUS || currentToken.getType() == Type.MINUS)
        {
            token = currentToken;
            if(currentToken.getType() == Type.PLUS) {
                eat(Type.PLUS);
                result = new BinOP(result, token, term());
            }
            else if(currentToken.getType() == Type.MINUS) {
                eat(Type.MINUS);
                result = new BinOP(result, token, term());
            }
        }
        return result;
    }

    private Node empty()
    {
        return new NoOp();
    }

    private Node variable() throws InterpretException {
        Node node = new Var(currentToken);
        eat(Type.ID);
        return node;
    }

    private Node assigmentSt() throws InterpretException {
        Node left = variable();
        Token token = currentToken;
        eat(Type.ASSIGN);
        Node right = expr();
        return new Assign(left, right, token);
    }

    private Node compoundSt() throws InterpretException {
        eat(Type.BEGIN);
        stList();
        eat(Type.END);
        Compound c = new Compound();
        for (Node x : list)
            c.addChild(x);
        return c;
    }

    private void stList() throws InterpretException {
        list.add(statement());
        while (currentToken.getType() == Type.SEMI)
        {
            eat(Type.SEMI);
            list.add(statement());
        }
        if(currentToken.getType() == Type.ID)
            throw new InterpretException("Wrong node data in stList method");
    }

    private Node statement() throws InterpretException {
        Node node;
        if(currentToken.getType() == Type.BEGIN)
            node = compoundSt();
        else if(currentToken.getType() == Type.ID)
            node = assigmentSt();
        else node = empty();
        return node;
    }

    private Node program() throws InterpretException {
        Node node = compoundSt();
        eat(Type.DOT);
        return node;
    }

}