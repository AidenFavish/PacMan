import java.util.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Gdx;
public interface Character
{

    public abstract void move(BackgroundCell[][] map, Cell c, Cell redTarget, Move facing);

    public abstract void update();

    public abstract float getX();

    public abstract float getY();

    public abstract float getWidth();

    public abstract float getHeight();

    public abstract void setX(float x);

    public abstract void setY(float y);

    public abstract TextureRegion getImg();

    public abstract Cell getTarget();

    public abstract void setTarget(Cell c);
    
    public abstract void setHidden(boolean x);
    
    public abstract boolean isHidden();
    
    public abstract void setDead(boolean x);
    
    public abstract boolean isDead();
    
    public abstract void reset();
}
