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
	
	@Override
	public String toString()
	{
		String className = this.getClass().getName();   //Name of the class
        
		return "[ ClassName "+ className+ " [ Name = "    + getName  ()+ " ; Color = "+getColor()+ " ; Dimensions = "+ dimensions()+ " ; Diameter = "+ getDiameter()
										+ " ; Radius = "  + getRadius()+ " ; Area = " +getArea ()+ " ; Volume = "    + getVolume ()+ "] ]";
	}
}