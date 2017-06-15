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
    	//Added

    	//Circulars
    	list.add(new Circle  ("Circle1"  , ShapeColor.VIOLET, 25.0		));
    	list.add(new Cone    ("Cone1"    , ShapeColor.GREEN , 12.0, 18.0));
    	list.add(new Cylinder("Cylinder1", ShapeColor.WHITE ,  5.0,  6.0));
    	list.add(new Sphere  ("Sphere1"  , ShapeColor.YELLOW, 32.0      ));

    	//Triangular
    	list.add(new Triangle      ("GenTriangle"   , ShapeColor.GREEN,  4.0, 5.0,   7.0));
    	list.add(new EquilateralTri("EqualateralTri", ShapeColor.RED  ,  3.0	   		));
    	list.add(new IsoscelesTri  ("IsocelesTri"   , ShapeColor.BLACK, 20.0, 90.0		));
    	list.add(new ScaleneTri    ("ScaleneTri"    , ShapeColor.BLUE , 40.0, 85.0, 95.0));

    	//Four Siders
    	list.add(new Cube("Cube1", ShapeColor.PINK, 8.0));

    	//Original
        list.add(new Rectangle("R1", ShapeColor.BLUE  , 20, 20));
        list.add(new Rectangle("R2", ShapeColor.YELLOW, 10, 20));

        list.add(new Square("S1", ShapeColor.CYAN,  12));
        list.add(new Square("S2", ShapeColor.CYAN, 120));

        //Driver 2.
        list.add(new Circle		   ("C1" , ShapeColor.BLACK , 20	   ));
        list.add(new Circle		   ("C2" , ShapeColor.BLACK , 40	   ));
        list.add(new Cylinder	   ("CY1", ShapeColor.PINK  , 40, 20   ));
        list.add(new Cylinder	   ("CY2", ShapeColor.PINK  , 40, 30   ));
        list.add(new Cone		   ("CO2", ShapeColor.GREEN , 40, 30   ));
        list.add(new Cone		   ("CO2", ShapeColor.GREEN , 40, 30   ));
        list.add(new Sphere		   ("SP1", ShapeColor.VIOLET, 40	   ));
        list.add(new Sphere		   ("SP2", ShapeColor.VIOLET, 40	   ));
        list.add(new ScaleneTri	   ("T1" , ShapeColor.WHITE ,  5,  3, 4));
        list.add(new EquilateralTri("T2" , ShapeColor.WHITE , 10	   ));
        list.add(new IsoscelesTri  ("T3" , ShapeColor.WHITE ,  5,  6   ));

        System.out.println(list);

        System.out.println("======");

        Collections.sort(list);

        for (Object o : list)
        {
        	System.out.println(o);
        }
    }

    private List<AbstractShape> list;
}