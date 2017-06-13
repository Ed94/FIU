package shape;

public class IsoscelesTri extends Triangle
{
	public IsoscelesTri(String name, ShapeColor color, double sideBase, double sideEQ) 
	{
		super(name, color, sideBase, sideEQ, sideEQ);
	}
	
	public IsoscelesTri(IsoscelesTri triangle)
	{
		super(triangle.getName(), triangle.getColor(), triangle.getSide(Sel.A), triangle.getSide(Sel.B), triangle.getSide(Sel.C));
	}
}