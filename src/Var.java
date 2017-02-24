/**
 * Created by energo7 on 13.02.2017.
 */
public class Var extends Node{

    private Token token;

    public Var(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }
}
