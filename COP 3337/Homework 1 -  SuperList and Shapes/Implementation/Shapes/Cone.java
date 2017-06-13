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