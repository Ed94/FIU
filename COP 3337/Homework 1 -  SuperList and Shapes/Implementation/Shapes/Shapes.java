package shape;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;

/**
 * Created by francisco on 5/23/17.
 */
public class Shapes
{
    public Shapes(List<AbstractShape> list)
    {
        this.list = list;
    }

    public void test()
    {
        list.add(new Rectangle("R1",ShapeColor.BLUE  , 20, 20));
        list.add(new Rectangle("R2",ShapeColor.YELLOW, 10, 20));
        
        list.add(new Square("S1",ShapeColor.CYAN,  12));
        list.add(new Square("S1",ShapeColor.CYAN, 120));
        
        System.out.println(list);
        
        System.out.println("======");

        Collections.sort(list);
        
        System.out.println(list);
    }

    private List<AbstractShape> list;
}