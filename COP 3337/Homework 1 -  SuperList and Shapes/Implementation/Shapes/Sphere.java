package shape;

public class Sphere extends Circle
{
	public Sphere(String name, ShapeColor color, double radius) 
	{
		super(name, color, radius);
	}
	
	public Sphere(Sphere sphere)
	{
		super(sphere.getName(), sphere.getColor(), sphere.getRadius());
	}
	
	//Public
	@Override
	public double getArea  () { return ( super.getArea() * 4.0)								      ; }
	public double getVolume() { return ( Math.PI * Math.pow(this.getRadius(), 3.0) * (4.0 / 3.0) ); }
	
	@Override
	public int dimensions()
	{ return 3; }
	
	
	public String toString()
	{
		String classString = super.toString();
		
		return classString.substring(                              0, classString.indexOf("Area") - 3)
		      +classString.substring(classString.indexOf("Area") - 3, classString.indexOf("]"   )	 )
		      +"; Volume = "+ getVolume()
			  +classString.substring(classString.indexOf("]"   ) - 1);
	}
}