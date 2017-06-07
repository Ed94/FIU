package shape;

public class Cylinder extends Circle
{
	public Cylinder(String name, ShapeColor color, double length, double radius) 
	{
		super(name, color, radius);
		
		this.length = length;
	}
	
	public Cylinder(Cylinder cylinder)
	{
		super(cylinder.getName(), cylinder.getColor(), cylinder.getRadius());
		
		this.length = cylinder.getLength();
	}
	
	//Public
	public double getLength()
	{ return length; }
	
	@Override
	public double getArea  () { return ( 2.0 * super.getArea() + (2.0 * Math.PI * super.getRadius()) * length ); }
	public double getVolume() { return ( super.getVolume() * length )										   ; }
	
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