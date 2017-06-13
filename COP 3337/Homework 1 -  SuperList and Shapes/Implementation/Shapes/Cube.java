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
	
    public String toString()
    {
    	String classString = super.toString();
    	
		return classString.substring(                       0, classString.indexOf("Area") - 3)
			  +" ; Area = " + getArea ()+ " ; Volume = "+ getVolume()
			  +classString.substring(classString.indexOf("]") - 1, classString.length()    );
    }
	
	//Private
	private double side;
}
