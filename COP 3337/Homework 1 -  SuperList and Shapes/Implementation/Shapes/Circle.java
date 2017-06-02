package shape;

public class Circle extends AbstractShape
{
	public Circle(String name, ShapeColor color, double radius)
	{
		this.name   = name  ;
		this.color  = color ;
		this.radius = radius;
	}
	
	public Circle(Circle circle)
	{
		this.name   = circle.getName  ();
		this.color  = circle.getColor ();
		this.radius = circle.getRadius();
	}
	
	//Private
	@Override
	public double getArea    () { return (Math.PI * Math.pow(radius, 2)); }
	public double getDiameter() { return radius*2                       ; }
	public double getRadius  () { return radius							; }

	@Override
	public double getVolume() //2D 
	{
		System.out.println("Function: getVolume, not supported for 2D shape."); 
		
		return -1;
	}

	@Override
	public int dimensions() 
	{ return 1; }
	
	@Override
	public ShapeColor getColor() 
	{ return color; }

	@Override
	public String getName() 
	{ return name; }
	
	@Override
	public void setColor(ShapeColor color) { this.color = color; }
	public void setName (String name     ) { this.name = name  ; }
	
	//Private
	private double 	   radius;
	private ShapeColor color ;
	private String     name  ;
}