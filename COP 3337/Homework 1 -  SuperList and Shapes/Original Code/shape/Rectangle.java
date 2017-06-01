package shape;

import java.util.Comparator;

/**
 * Created by francisco on 5/23/17.
 */
public class Rectangle extends AbstractShape
{
    public Rectangle(String name, ShapeColor color, double width,double heigth)
    {
        this.name = name;
        this.width = width;
        this.height = heigth;
        this.color = color;

    }

    public double getArea()
    {
        return this.width * this.height;
    }

    public double getVolume()
    {
        return 0.0;
    }

    public int dimensions()
    {
        return 2;
    }
    public String getName()
    {
        return name;
    }
    public ShapeColor getColor()
    {
        return this.color;
    }
    public void setColor(ShapeColor color)
    {
        this.color = color;
    }

    public void setName(String name)
    {
        this.name = name;
    }



    //instance variables
    private ShapeColor color;
    private String name;
    private double width;
    private double height;
}
