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
	
	@Override
	public String toString()
	{
		String className = this.getClass().getName();   //Name of the class
        
		return "[ ClassName "+ className+ " [ Name = "  + getName     ()+ " ; Color = " + getColor     ()+ " ; Dimensions = "+ dimensions()+ " ; Side A = "+ getSide(Sel.A)
										+ " ; Side B = "+ getSide(Sel.B)+ " ; Side C = "+ getSide (Sel.C)+ " ; Area = "      + getArea   ()
										+ getClassification()+ "] ]";
	}
}