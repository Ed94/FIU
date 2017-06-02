package shape;

public class IsoscelesTri extends Triangle
{
	public IsoscelesTri(double sideBase, double sideEQ) 
	{
		super(sideBase, sideEQ, sideEQ);
	}
	
	public IsoscelesTri(IsoscelesTri triangle)
	{
		super(triangle.getSide(Sel.A), triangle.getSide(Sel.B), triangle.getSide(Sel.C));
	}
}