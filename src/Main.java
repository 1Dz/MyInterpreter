/**
 * Created by energo7 on 02.02.2017.
 */
public class Main {
    public static void main(String[] args) {
        try {
            Parser in = new Parser("BEGIN\n" +
                    "BEGIN\n" +
                    "number := 2;\n" +
                    "b := 10 * a + 10 * number / 4;\n" +
                    "c := a - - b;\n" +
                    "END;\n" +
                    "x := 11;\n" +
                    "END.");
            //Parser in = new Parser("BEGIN a := 11; b := a + 9 - 5 * 2 END.");
            Interpreter inter = new Interpreter();
            System.out.println(inter.init(in.parse()));
        } catch (InterpretException e) {
            System.out.println(e.getMessage());
        }
    }
}
