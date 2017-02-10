import java.util.ArrayList;
import java.util.List;

/**
 * Created by energo7 on 10.02.2017.
 */
public class Compound {

    private List<Node> children = new ArrayList<>();

    public Compound()
    {

    }

    public void addChild(Node node)
    {
        children.add(node);
    }

}
