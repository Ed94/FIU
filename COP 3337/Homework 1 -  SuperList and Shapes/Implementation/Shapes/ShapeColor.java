package shape;

/**
 * Created by francisco on 5/23/17.
 */
public enum ShapeColor 
{
    BLUE  (1,1,1),
    RED   (1,1,1),
    GREEN (1,1,1),
    YELLOW(1,1,1),
    CYAN  (1,1,1),
    WHITE (1,1,1),
    BLACK (1,1,1),
    VIOLET(1,1,1),
    PINK  (1,1,1);
	
    private final int r;   // RED
    private final int g;   // GREEN
    private final int b;   // BLUE

    ShapeColor(int r, int g, int b) 
    {
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    private int r() { return r; }
    private int g() { return g; }
    private int b() { return b; }
}