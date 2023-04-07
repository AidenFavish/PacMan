import java.util.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Gdx;
public class BackgroundCell
{
    private int tile;
    private TextureRegion img;
    
    public BackgroundCell(int i) {
        tile = i;
        img = new TextureRegion(new Texture("assets/Back" + i + ".png"));
    }
    
    public int getTile() {
        return tile;
    }
    
    public void setTile(int i) {
        tile = i;
        img = new TextureRegion(new Texture("assets/Back" + i + ".png"));
    }
    
    public TextureRegion getImg() {
        return img;
    }
    
    public void setImg(TextureRegion x) {
        img = x;
    }
}
