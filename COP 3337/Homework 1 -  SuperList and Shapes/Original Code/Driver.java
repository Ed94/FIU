import prog2.ds.SuperList;
import shape.Shape;
import shape.Shapes;
import shape.AbstractShape;
import java.util.List;
/**
 * Created by francisco on 5/19/17.
 */
public class Driver
{
    public static void main(String[] args)
    {
        SuperList<String> mylist = new SuperList<String>();

        System.out.println("The size is = " + mylist.size());


        for (int i =0;i< 1024; i++)
        {
            String str = "Num: " + i;
            mylist.add(str);
        }

        System.out.println("The size is = " + mylist.size());


        System.out.println("Output = " + mylist.toString());

        System.out.println("Getting index 100 [Contains =>>>] :" + mylist.get(100));

        String removeItem100 = mylist.remove(100);

        System.out.println("Getting index 100 [Contains =>>>] :" + mylist.get(100));

        String removeItemLast = mylist.removeLast();

        for (String s : mylist)
            System.out.println(s);

        mylist.clear();

        System.out.println("There is nothing now");
        for (String s : mylist)
            System.out.println(s);

        System.out.println("The size is = " + mylist.size());


        mylist.add("hello");

        for (String s : mylist)
            System.out.println(s);

        System.out.println("The size is = " + mylist.size());

        System.out.println("Testing other features ===");

        List<AbstractShape> list2 = new SuperList();
        Shapes shapes = new Shapes(list2);
        shapes.test();




    }
}
