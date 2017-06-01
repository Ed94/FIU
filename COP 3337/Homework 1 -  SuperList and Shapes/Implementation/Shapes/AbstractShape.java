package shape;


/**
 * Created by Francisco on 5/24/17.
 */
public abstract class AbstractShape implements Shape, Comparable<AbstractShape>
   //public abstract class AbstractShape<T extends Comparable<? super T>> implements Shape.
{
    public int compareTo(AbstractShape other)
    {
        double d = this.getArea() - other.getArea();
        
        if      (isZero(d, 0.00001))
            return 0;
        else if (d > 0)
            return 1;
        else
            return -1;
    }

    public boolean equals(Object other)
    {
        AbstractShape s = (AbstractShape)other;
        
        return (this.getName().equals(s.getName()));
    }

    public static boolean isZero(double value, double threshold)
    {
        return value >= -threshold && value <= threshold;
    }

    public abstract double getArea  ();
    public abstract double getVolume();  //if 2D , volume is 0.
    
    public abstract int dimensions();
    
    public abstract String getName();
    
    public abstract ShapeColor getColor();
    
    public abstract void setColor(ShapeColor color);
    public abstract void setName (String name	  );

    public String toString()
    {   
        String className = this.getClass().getName();   //Name of the class
        
        return "[ className "+ className+ " [Area= " + this.getArea()
              +" ; Name = "+ this.getName()+ " ; Color = "+ this.getColor()+ "] ]";
    }
}