package shape;

/**
 * Created by francisco on 5/23/17.
 */
public class Rectangle extends AbstractShape
{
    public Rectangle(String name, ShapeColor color, double height, double width)
    {
        this.name   = name	;
        this.color  = color	;
        this.height = height;
        this.width  = width	;
    }
    
    public Rectangle(Rectangle passedRectangle)
    {
    	this.name   = passedRectangle.getName  ();
    	this.color  = passedRectangle.getColor ();
    	this.height = passedRectangle.getHeight();
    	this.width  = passedRectangle.getWidth ();
    }

    //Public
    @Override
    public double getArea  () { return this.width * this.height; }
    public double getWidth () { return width				   ; }
    public double getHeight() { return height				   ; }
    public double getVolume() { return 0.0					   ; }

    @Override
    public int dimensions()
    { return 2; }
    
    @Override
    public ShapeColor getColor()
    { return this.color; }
    
    @Override
    public String getName()
    { return name; }
    
    @Override
    public String toString()
    {
    	String className = this.getClass().getName();   //Name of the class
        
		return "[ ClassName "+ className+ " [ Name = "  + name  + " ; Color = "+ color+ " ; Dimensions = "+ dimensions()
										+ " ; Height = "+ height+ " ; Width = "+ width+ " ; Area = "      + getArea   ()+ "] ]";
    }
    
    @Override
    public void setColor(ShapeColor color) { this.color = color; }
    public void setName (String name	 ) { this.name  = name ; }
    
    //Private
    //Instance Variables
    private double width ;
    private double height;
    
    private ShapeColor color;
    private String 		name;
}