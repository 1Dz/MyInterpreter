/**
 * Created by energo7 on 02.02.2017.
 */
public class Main {
    public static void main(String[] args) {
        try {
            Parser in = new Parser("2 + 3 * 4 / 5");
            Interpreter inter = new Interpreter();
            System.out.println(inter.init(in.expr()));
        } catch (InterpretException e) {
            System.out.println(e.getMessage());
        }
    }
}
