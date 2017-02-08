import com.sun.istack.internal.Nullable;

import java.math.BigDecimal;
import java.util.Deque;

/**
 * Created by energo7 on 02.02.2017.
 */
public class Parser {

    private String input;
    private Deque<Token> deque;
    private Lexer lexer;
    private Token currentToken;

    public Parser(String input) throws InterpretException {
        this.input = input.replaceAll(" ", "");
        this.lexer = new Lexer(this.input);
        this.deque = lexer.tokenize();
        this.currentToken = deque.poll();
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
        if(currentToken.getType() == Type.INTEGER) {
            result = new Num(currentToken);
            eat(Type.INTEGER);
        }
        else if(currentToken.getType() == Type.OPEN)
        {
            eat(Type.OPEN);
            result = expr();
            eat(Type.CLOSE);
        }
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

    @Nullable Node expr() throws InterpretException {
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
}
