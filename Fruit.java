import java.util.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Gdx;
public class Fruit
{
    private Cell pos;
    private float width;
    private float height;
    private TextureRegion img;
    private int points;
    private TextureRegion pointImg;
    private int timer;
    private boolean eaten;
    public boolean switcher;
    private String name;
    
    public Fruit(Cell pos, float width, float height, TextureRegion img, int points, TextureRegion pointImg) {
        name = "none";
        this.pos = pos;
        this.width = width;
        this.height = height;
        this.img = img;
        this.points = points;
        this.pointImg = pointImg;
        timer = 450;
        eaten = false;
        switcher = false;
    }
    
    public Fruit(float width, float height, TextureRegion img, int points, TextureRegion pointImg) {
        name = "none";
        this.pos = new Cell(0, 0);
        this.width = width;
        this.height = height;
        this.img = img;
        this.points = points;
        this.pointImg = pointImg;
        timer = 450;
        eaten = false;
        switcher = false;
    }
    
    public Fruit(String name, float width, float height, TextureRegion img, int points, TextureRegion pointImg) {
        this.name = name;
        this.pos = new Cell(0, 0);
        this.width = width;
        this.height = height;
        this.img = img;
        this.points = points;
        this.pointImg = pointImg;
        timer = 450;
        eaten = false;
        switcher = false;
    }
    
    public Fruit(Fruit x) {
        this.name = x.getName();
        pos = new Cell(x.getPos().getX(), x.getPos().getY());
        this.width = x.getWidth();
        this.height = x.getHeight();
        img = x.getImg();
        points = x.getPoints();
        pointImg = x.getPointImg();
        timer = x.getTimer();
        eaten = x.getEaten();
    }
    
    public String getName() {
        return name;
    }
    
    public Cell getPos() {
        return pos;
    }
    
    public TextureRegion getImg() {
        return img;
    }
    
    public int getPoints() {
        return points;
    }
    
    public TextureRegion getPointImg() {
        return pointImg;
    }
    
    public int getTimer() {
        return timer;
    }
    
    public boolean getEaten() {
        return eaten;
    }
    
    public float getWidth() {
        return width;
    }
    
    public float getHeight() {
        return height;
    }
    
    public void setX(int x) {
        pos.setX(x);
    }
    
    public void setY(int y) {
        pos.setY(y);
    }
    
    public void setTimer(int t) {
        timer = t;
    }
    
    public void setEaten(boolean x) {
        eaten = x;
    }
    
    public void switchImages(boolean x) {
        switcher = x;
    }
    
    public TextureRegion getSwitchedImg() {
        if (switcher) {
            return pointImg;
        } 
        return img;
    }
    
    public void setWidth(int w) {
        width = w;
    }
    
    public void setHeight(int h) {
        height = h;
    }
}
