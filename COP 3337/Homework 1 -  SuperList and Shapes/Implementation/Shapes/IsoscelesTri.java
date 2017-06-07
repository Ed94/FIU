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
	
	@Override
	public String toString()
	{
		String className = this.getClass().getName();   //Name of the class
        
		return "[ ClassName "+ className+ " [ Name = "  + getName     ()+ " ; Color = " + getColor     ()+ " ; Dimensions = "+ dimensions()+ " ; Side A = "+ getSide(Sel.A)
										+ " ; Side B = "+ getSide(Sel.B)+ " ; Side C = "+ getSide (Sel.C)+ " ; Area = "      + getArea   ()
										+ getClassification()+ "] ]";
	}
}