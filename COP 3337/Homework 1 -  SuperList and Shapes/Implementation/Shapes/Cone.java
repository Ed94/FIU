package shape;

public class Cone extends Circle
{
	public Cone(String name, ShapeColor color, double length, double radius) 
	{
		super(name, color, radius);
		
		this.length = length;
	}
	
	public Cone(Cone cone)
	{
		super(cone.getName(), cone.getColor(), cone.getRadius());
		
		this.length = cone.getLength();
	}
	
	//Public
	public double getLength() 
	{ return length; }
	
	@Override
	public double getArea  () { return ( super.getArea() + Math.PI * super.getRadius() * length ); }
	public double getVolume() { return ( (1.0 / 3.0) * super.getRadius() * length )				 ; }
	
	@Override
	public int dimensions()
	{ return 3; }
	
	@Override
	public String toString()
	{
		String className = this.getClass().getName();   //Name of the class
        
		return "[ ClassName "+ className+ " [ Name = "    + getName    ()+ " ; Color = " + getColor ()+ " ; Dimensions = "+ dimensions()
		                                + " ; Diameter = "+ getDiameter()+ " ; Radius = "+ getRadius()+ " ; Length = "    + getLength ()
		                                + " ; Area = "    + getArea    ()+ " ; Volume = "+ getVolume ()+ "] ]";
	}

	//Private
	private double length;
}