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
	@Override
	public double getArea()
	{ return ( super.getArea() + Math.PI * super.getRadius() * length ); }
	
	public double getLength() 
	{ return length; }
	
	@Override
	public double getVolume()
	{ return ( (1.0 / 3.0) * super.getRadius() * length ); }

	//Private
	private double length;
}