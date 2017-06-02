package shape;

public class EquilateralTri extends Triangle
{
	public EquilateralTri(double side) 
	{
		super(side, side, side);
	}
	
	public EquilateralTri(EquilateralTri triangle)
	{
		super(triangle.getSide(Sel.A), triangle.getSide(Sel.B), triangle.getSide(Sel.C));
	}
	
	@Override
	public double getArea()
	{ return ( Math.sqrt(3.0) / 4.0 ) * super.getSide(Sel.A); }
}