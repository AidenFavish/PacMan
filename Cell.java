
public class Cell
{
    private int x;
    private int y;
    
    public Cell() {
        x = 0;
        y = 0;
    }
    
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public boolean equals(Cell c) {
        if (c.getX() == x && c.getY() == y)
            return true;
        return false;
    }
    
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
