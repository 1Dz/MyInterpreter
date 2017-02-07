import com.sun.istack.internal.Nullable;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by energo7 on 02.02.2017.
 */
public class Lexer {

    private String input;
    private int pos;
    private char currentChar;

    public Lexer(String input)
    {
        this.input = input;
        this.pos = 0;
        this.currentChar = input.charAt(pos);
    }

    private double multidigit() throws InterpretException {
        String res = "";
        while (Character.isDigit(currentChar))
        {
            res += currentChar;
            advance();
        }
        return Double.parseDouble(res);
    }
    private void advance() throws InterpretException {
        pos++;
        if(pos < input.length())
        {
            currentChar = input.charAt(pos);
        }
        else currentChar = 0;
    }
    private @Nullable Token indent() throws InterpretException {
        if(Character.isDigit(currentChar) && currentChar != 0)
        {
            return new Token<>(Type.INTEGER, multidigit());
        }
        else {
            switch (currentChar)
            {
                case '+':
                    advance();
                    return new Token<>(Type.PLUS, '+');
                case '-':
                    advance();
                    return new Token<>(Type.MINUS, '-');
                case '*':
                    advance();
                    return new Token<>(Type.MULT, '*');
                case '/':
                    advance();
                    return new Token<>(Type.DIV, '/');
                case ')':
                    advance();
                    return new Token<>(Type.CLOSE, '(');
                case '(':
                    advance();
                    return new Token<>(Type.OPEN, ')');
            }
        }
        return null;
    }
    public Deque<Token> tokenize() throws InterpretException {
        Deque<Token> deque = new LinkedList<>();
        Token t = indent();
        while (t != null)
        {
            deque.add(t);
            t= indent();
        }
        return deque;
    }
}
