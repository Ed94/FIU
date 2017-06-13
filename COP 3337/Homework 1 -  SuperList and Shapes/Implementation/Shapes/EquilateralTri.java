package shape;

public class EquilateralTri extends Triangle
{
	public EquilateralTri(String name, ShapeColor color, double side) 
	{
		super(name, color, side, side, side);
	}
	
	public EquilateralTri(EquilateralTri triangle)
	{
		super(triangle.getName(), triangle.getColor(), triangle.getSide(Sel.A), triangle.getSide(Sel.B), triangle.getSide(Sel.C));
	}
	
	@Override
	public double getArea()
	{ return ( Math.sqrt(3.0) / 4.0 ) * super.getSide(Sel.A); }
}