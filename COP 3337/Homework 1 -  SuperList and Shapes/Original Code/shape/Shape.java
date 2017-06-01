package shape;

import java.util.Comparator;

/**
 * Created by francisco on 5/23/17.
 */
public interface Shape
{
    double getArea();
    double getVolume();  //if 2D , volume is 0.
    int dimensions();
    String getName();
    ShapeColor getColor();
    void setColor(ShapeColor color);
    void setName(String name);

}
