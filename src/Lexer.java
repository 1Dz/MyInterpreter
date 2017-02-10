import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by energo7 on 02.02.2017.
 */
public class Lexer {

    private String input;
    private int pos;
    private char currentChar;
    private static final Map<String, Token<String>> RESERVED_KEY_WORDS = new HashMap<>();
    static {
        RESERVED_KEY_WORDS.put("BEGIN", new Token<>(Type.BEGIN, "BEGIN"));
        RESERVED_KEY_WORDS.put("END", new Token<>(Type.END, "END"));
    }

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
    private char peek() throws InterpretException {
        int peekPos = pos + 1;
        if(peekPos > input.length())
            throw new InterpretException("Peek position is larger then input text length");
        else return input.charAt(peekPos);
    }
    private Token<String> getID() throws InterpretException {
        String result = "";
        while (currentChar != 0 && Character.isAlphabetic(currentChar))
        {
            result += currentChar;
            advance();
        }
        if(Lexer.RESERVED_KEY_WORDS.containsKey(result))
            return Lexer.RESERVED_KEY_WORDS.get(result);
        else if(Lexer.RESERVED_KEY_WORDS.containsKey(result))
            return new Token<>(Type.ID, result);
        else throw new InterpretException("Error in getID Method");
    }
    private Token indent() throws InterpretException {
        if(Character.isDigit(currentChar) && currentChar != 0)
        {
            return new Token<>(Type.INTEGER, multidigit());
        }
        if(Character.isAlphabetic(currentChar))
        {
            return getID();
        }
        if(currentChar == ':' && peek() == '=')
        {
            advance();
            advance();
            return new Token<>(Type.ASSIGN, ":=");
        }
        else {
            switch (currentChar)
            {
                case ';':
                    advance();
                    return new Token<>(Type.SEMI, ';');
                case '.':
                    advance();
                    return new Token<>(Type.DOT, '.');
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
        throw new InterpretException("Not valid character was entered.");
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
