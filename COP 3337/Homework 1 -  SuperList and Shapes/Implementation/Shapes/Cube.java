package shape;

public class Cube extends Rectangle
{
	public Cube(String name, ShapeColor color, double length) 
	{
		super(name, color, length, length);
		
		side = length;
	}
	
	//Public
	@Override 
	public double getArea  () { return Math.pow(side, 2) * 6; }
	public double getVolume() { return Math.pow(side, 3)    ; }
	
	@Override
	public int dimensions()
	{ return 3; }
	
	@Override
	public String toString()
	{
    	String className = this.getClass().getName();   //Name of the class
        
		return "[ ClassName "+ className+ " [ Name = "  + getName  ()+ " ; Color = "+ getColor()+ " ; Dimensions = "+ dimensions()
										+ " ; Height = "+ getHeight()+ " ; Width = "+ getWidth()+ " ; Area = "      + getArea   ()+ " ; Volume = "+ getVolume()+ "] ]";
	}
	
	//Private
	private double side;
}
