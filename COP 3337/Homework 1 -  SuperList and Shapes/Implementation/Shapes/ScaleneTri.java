package shape;

public class ScaleneTri extends Triangle
{ 
	public ScaleneTri(double sideA, double sideB, double sideC) 
	{
		super(sideA, sideB, sideC);
	}
	
	public ScaleneTri(ScaleneTri triangle)
	{
		super(triangle.getSide(Sel.A), triangle.getSide(Sel.B), triangle.getSide(Sel.C));
	}
}