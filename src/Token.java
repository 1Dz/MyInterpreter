/**
 * Created by energo7 on 02.02.2017.
 */
public class Token<T> {

    private Type type;
    private T value;

    public Token(Type type, T value)
    {
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public T getValue() {
        return value;
    }
}
