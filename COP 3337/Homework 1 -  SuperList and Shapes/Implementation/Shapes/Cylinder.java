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
	public double getVolume() { return ( super.getArea() * length )										       ; }
	
	@Override
	public int dimensions()
	{ return 3; }
	
	
	public String toString()
	{
		String classString = super.toString();
		
		return classString.substring(                              0, classString.indexOf("Area") - 3)
			  +" ; Length = "+ getLength()
			  +classString.substring(classString.indexOf("Area") - 3, classString.indexOf("]"   )	 )
			  +"; Volume = " + getVolume()
			  +classString.substring(classString.indexOf("]"   ) - 1);
	}
	
	//Private
	private double length;
}