package shape;

public class ScaleneTri extends Triangle
{ 
	public ScaleneTri(String name, ShapeColor color, double sideA, double sideB, double sideC) 
	{
		super(name, color, sideA, sideB, sideC);
	}
	
	public ScaleneTri(ScaleneTri triangle)
	{
		super(triangle.getName(), triangle.getColor(), triangle.getSide(Sel.A), triangle.getSide(Sel.B), triangle.getSide(Sel.C));
	}
}