import java.util.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Gdx;
public abstract class Ghost implements Character
{
    private float x;
    private float y;
    private float width;
    private float height;
    private boolean isHidden;
    private Status status;
    private TextureRegion[][] frightened;
    private boolean flasher;
    private boolean exiting;
    private Status defaultStatus;
    public final int DEFAULT_SPEED = 2;

    public Ghost() {
        x = 0;
        y = 0;
        width = 36;
        height = 36;
        this.status = Status.WAITING;
        isHidden = false;
        frightened = new TextureRegion[][] {
            {
                new TextureRegion(new Texture(Gdx.files.internal("assets/FrightenedGhost1.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/FrightenedGhost2.png")))
            },
            {
                new TextureRegion(new Texture(Gdx.files.internal("assets/FrightenedGhost1.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/FrightenedGhost2.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/FrightenedGhost3.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/FrightenedGhost4.png")))
            }
        };
        flasher = false;
        defaultStatus = Status.CHASE;
        exiting = false;
    }

    public Ghost(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.status = Status.WAITING;
        isHidden = false;
        frightened = new TextureRegion[][] {
            {
                new TextureRegion(new Texture(Gdx.files.internal("assets/FrightenedGhost1.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/FrightenedGhost2.png")))
            },
            {
                new TextureRegion(new Texture(Gdx.files.internal("assets/FrightenedGhost1.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/FrightenedGhost2.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/FrightenedGhost3.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/FrightenedGhost4.png")))
            }
        };
        flasher = false;
        defaultStatus = Status.CHASE;
        exiting = false;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status s) {
        status = s;
    }

    public Move direction(float x, float y, Cell target, Move defaultMove) {
        Move ans = null;

        float cx = target.getX() * 22 - 7;
        float cy = (GameScreen.WORLD_HEIGHT - 34 - 22 - 22 * target.getY()) - 7;

        float ax = (float)Math.pow(cx - x, 2);
        float ay = (float)Math.pow(cy - y, 2);
        
        /*if (x < 0 || x > 22 * 28) {
            return null;
        }*/
        
        if (ax == ay) {
            return null;
        }

        if (ax < ay) {
            if (cy - y > 0) {
                ans = Move.UP;
            } else {
                ans = Move.DOWN;
            }
        } else {
            if (cx - x > 0) {
                ans = Move.RIGHT;
            } else if (target.getX() == 27) {
                ans = defaultMove;
            } else {
                ans = Move.LEFT;
            }
        }

        return ans;
    }

    public ArrayList<Cell> lookAround(Cell c, BackgroundCell[][] map, Move currMove) {
        ArrayList<Cell> spots = new ArrayList<Cell>();
        
        if (c.getY() == 14 && (c.getX() == 0 || c.getX() == 27)) {
            if (currMove == Move.LEFT && c.getX() == 0) {
                spots.add(new Cell(-1, 14));
            } else if (currMove == Move.LEFT && c.getX() == 27) {
                spots.add(new Cell(26, 14));
            } else if (currMove == Move.RIGHT && c.getX() == 0) {
                spots.add(new Cell(1, 14));
            } else if (currMove == Move.RIGHT && c.getX() == 27) {
                spots.add(new Cell(28, 14));
            }
            return spots;
        }
        if (c.getY() == 14 && (c.getX() <= -1 || c.getX() >= 28)) {
            spots.add(new Cell(c.getX() <= -1 ? 27 : 0, 14));
            return spots;
        }

        if (walkable(map[c.getY() + 1][c.getX()])) {
            spots.add(new Cell(c.getX(), c.getY() + 1));
        }
        if (walkable(map[c.getY() - 1][c.getX()])) {
            spots.add(new Cell(c.getX(), c.getY() - 1));
        }
        if (walkable(map[c.getY()][c.getX() + 1])) {
            spots.add(new Cell(c.getX() + 1, c.getY()));
        }
        if (walkable(map[c.getY()][c.getX() - 1])) {
            spots.add(new Cell(c.getX() - 1, c.getY()));
        }

        return spots;
    }

    private boolean walkable(BackgroundCell x) {
        if (x.getTile() == 0 || x.getTile() == 33 || x.getTile() == 34) {
            return true;
        }
        return false;
    }

    public Cell coordToCell(float x, float y) {
        int cellX = Math.round((x + 7) / 22);
        int cellY = Math.round((y-GameScreen.WORLD_HEIGHT + 34 + 22 + 7) / (-22));
        return new Cell(cellX, cellY);
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean x) {
        isHidden = x;
    }

    public boolean isDead() {
        return status == Status.CAUGHT || status == Status.ENTERING || status == Status.WAITING;
    }

    public void setDead(boolean x) {
        status = x ? Status.CAUGHT : Status.WAITING;
    }

    public TextureRegion[] getFrightened(int i) {
        return frightened[i];
    }

    public void setFlasher(boolean x) {
        flasher = x;
    }

    public boolean getFlasher() {
        return flasher;
    }
    
    public Status getDefaultStatus() {
        return defaultStatus;
    }
    
    public void setDefaultStatus(Status s) {
        if ((defaultStatus == Status.CHASE && s == Status.SCATTER) || (defaultStatus == Status.SCATTER && s == Status.CHASE)) {
            this.switchDirection();
        }
        defaultStatus = s;
    }
    
    public void switchDirection() {
        System.out.println("dead method exectued");
    }
    
    public void setExiting(boolean exit) {
        exiting = exit;
    }
    
    public boolean getExiting() {
        return exiting;
    }
    
    public void enter() {
        if (y <= 34 + (22 * 31) - (22 * 16) + 2) {
            status = Status.WAITING;
        } else {
            y -= 4;
        }
    }
}
