package shape;

public class Triangle extends AbstractShape
{
	public Triangle(String name, ShapeColor color, double sideA, double sideB, double sideC)
	{
		this.name  = name ;
		this.color = color;
		this.sideA = sideA;
		this.sideB = sideB;
		this.sideC = sideC;
	}
	
	public Triangle(Triangle triangle)
	{
		this.name  = triangle.getName ();
		this.color = triangle.getColor();
		
		this.sideA = triangle.getSide(Sel.A);
		this.sideB = triangle.getSide(Sel.B);
		this.sideC = triangle.getSide(Sel.C);
	}
	
	//Public
	enum Sel { A, B, C }   //Used to pick a specific side or angle.
	
	public double getAngle(Sel angle)
	{
		if (angle == Sel.A) { return (Math.acos((Math.pow(sideB, 2.0) + Math.pow(sideC, 2.0) - Math.pow(sideA, 2.0)) / ( 2.0  * sideB * sideC)) * (180.0 / Math.PI)); }
		if (angle == Sel.B) { return (Math.acos((Math.pow(sideC, 2.0) + Math.pow(sideA, 2.0) - Math.pow(sideB, 2.0)) / (sideA *  2.0  * sideC)) * (180.0 / Math.PI)); }
		if (angle == Sel.C) { return (Math.acos((Math.pow(sideA, 2.0) + Math.pow(sideB, 2.0) - Math.pow(sideC, 2.0)) / (sideA * sideB *  2.0 )) * (180.0 / Math.PI)); }
		
		System.out.println("Did not identify angle. Returning negative.");
		
		return -1; 
	}
	
	public double getSide(Sel side)
	{
		if (side == Sel.A) { return sideA; }
		if (side == Sel.B) { return sideB; }
		if (side == Sel.C) { return sideC; }
		
		System.out.println("Something went wrong... Passing 0.");
		
		return 0;
	}
	
	public String getClassification()
	{
		if (getAngle(Sel.A) == getAngle(Sel.B) && getAngle(Sel.B) == getAngle(Sel.C))
			return "Triangle Type: Equilateral   Internal Angles: "+ getAngle(Sel.A)+ " "+ getAngle(Sel.B)+ " "+ getAngle(Sel.C);
		
		if (getAngle(Sel.A) == getAngle(Sel.B) || getAngle(Sel.A) == getAngle(Sel.C) || getAngle(Sel.B) == getAngle(Sel.C))
			return "Triangle Type: Isosceles     Internal Angles: "+ getAngle(Sel.A)+ " "+ getAngle(Sel.B)+ " "+ getAngle(Sel.C);
		
		if (getAngle(Sel.A) > 90 || getAngle(Sel.B) > 90 || getAngle(Sel.C) > 90)
			return "Triangle Type: Scalene       Internal Angles: "+ getAngle(Sel.A)+ " "+ getAngle(Sel.B)+ " "+ getAngle(Sel.C);
		
		return "Could not get classification...  Internal Angles: "+ getAngle(Sel.A)+ " "+ getAngle(Sel.B)+ " "+ getAngle(Sel.C);
	}
	
	@Override
	public double getArea() 
	{
		double val = (sideA + sideB + sideC) / 2.0;
		
		return Math.sqrt( val * (val - sideA) * (val - sideB) * (val - sideC));
	}
	
	@Override
	public double getVolume() 
	{
		System.out.println("Not applicable to a 2D shape.");
		
		return -1;
	}

	@Override
	public int dimensions() 
	{ return 2; }

	@Override
	public String getName() 
	{ return this.name; }
	
	
	
	public String toString()
	{
		String classString = super.toString();
		
        return classString.substring(                              0, classString.indexOf("Area") - 3)
        	  +" ; Dimensions = "+ dimensions()+ " ; Side A = "+ sideA+ " ; Side B = "+ sideB+ " ; Side C = "+ sideC
        	  +classString.substring(classString.indexOf("Area") - 3, classString.indexOf("]"   )    )
        	  +"; "+ getClassification()
        	  +classString.substring(classString.indexOf("]"   ));
	}
	
	@Override
	public ShapeColor getColor() 
	{ return this.color; }

	@Override
	public void setColor(ShapeColor color) { this.color = color; }
	public void setName (String name	 ) { this.name  = name ; }
	
	//Private
	private double sideA;
	private double sideB;
	private double sideC;

	private ShapeColor color;
	private String 		name;
}