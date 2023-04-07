import java.util.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Gdx;
public class PinkGhost extends Ghost
{
    private Cell target;
    private Cell completed;
    private TextureRegion[][] allImages;
    private TextureRegion[] currImages;
    private int img; 
    private float speed;
    private Move prevPlan;
    private boolean waitingUp;

    public PinkGhost(float x, float y) {
        super(x, y, 36, 36);
        allImages = new TextureRegion[][]{
            {
                new TextureRegion(new Texture(Gdx.files.internal("assets/PinkGhostRIGHT0.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PinkGhostRIGHT1.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/EyesRIGHT.png")))
            },
            {new TextureRegion(new Texture(Gdx.files.internal("assets/PinkGhostLEFT0.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PinkGhostLEFT1.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/EyesLEFT.png")))
            },
            {new TextureRegion(new Texture(Gdx.files.internal("assets/PinkGhostUP0.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PinkGhostUP1.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/EyesUP.png")))
            },
            {new TextureRegion(new Texture(Gdx.files.internal("assets/PinkGhostDOWN0.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PinkGhostDOWN1.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/EyesDOWN.png")))
            }

        };
        currImages = allImages[3];
        img = 0;
        completed = new Cell(13, 11);
        target = new Cell(14, 11);
        speed = DEFAULT_SPEED;
        prevPlan = Move.RIGHT;
        waitingUp = false;
        setExiting(true);
    }

    public void move(BackgroundCell[][] map, Cell pacTarget, Cell redTarget, Move facing) {
        if (getStatus() == Status.ENTERING) {
            super.enter();
            return;
        } else if (getStatus() == Status.WAITING && !getExiting()) {
            if (getY() <= 34 + (22 * 31) - (22 * 16) + 2) {
                waitingUp = true;
                currImages = allImages[2];
                setY(getY() + 1.5f);
            } else if (getY() >= 34 + (22 * 31) - (22 * 15) + 2) {
                waitingUp = false;
                currImages = allImages[3];
                setY(getY() - 1.5f);
            } else if (waitingUp) {
                setY(getY() + 1.5f);
            } else {
                setY(getY() - 1.5f);
            }
            return;
        } else if (getStatus() == Status.WAITING) {
            if (getY() >= 34 + (22 * 31) - (22 * 13) + 15) {
                setStatus(getDefaultStatus());
                setExiting(false);
            } else {
                setY(getY() + 1.5f);
                currImages = allImages[2];
            }
            return;
        }

        Cell theTarget;

        if (getStatus() == Status.CHASE) {
            theTarget = adjustedTarget(pacTarget, redTarget, facing);
            speed = DEFAULT_SPEED;
        } else if (getStatus() == Status.FRIGHTENED) {
            Cell temp;
            do {
                temp = new Cell((int)(Math.random() * 27), (int)(Math.random() * 31));
            } while(temp.getY() >= 13 && temp.getY() <= 15 && (temp.getX() <= 5 || temp.getX() >= 22));
            theTarget = temp;
            speed = 2f;
        } else if (getStatus() == Status.SCATTER) {
            theTarget = new Cell(1, 1);
        } else { //eyes
            theTarget = new Cell(14, 11);
            speed = 4;
            if (completed.equals(theTarget)) {
                setX(290);
                setStatus(Status.ENTERING);
                setExiting(true);
                speed = 1.5f;
                currImages = allImages[3];
            }

        }

        if (GameScreen.onCenterCell(target, super.getX(), super.getY())) {
            ArrayList<Cell> choices = super.lookAround(target, map, prevPlan);
            Cell best = null;

            for (int i = choices.size() - 1; i >= 0; i--) {
                if (choices.get(i).equals(completed)) {
                    choices.remove(i);
                } else if (best == null || distance(choices.get(i), theTarget) < distance(best, theTarget)) {
                    best = choices.get(i);
                }
            }

            completed = target;
            target = best;
        }

        if (distance(coordToCell(getX(), getY()), target) > 2) { //tunneling
            if (target.getX() == 27) {
                setX(22 * 28);
            } else if (target.getX() == 0) {
                setX(22 * -1);
            }
        }

        Move plan = direction(getX(), getY(), target, prevPlan);
        if (plan == null) {
            plan = prevPlan;
            System.out.println(plan);
        } else {
            prevPlan = plan;
        }

        if (plan == Move.LEFT) {
            currImages = allImages[1];
            setX(getX() - speed);
            setY(34 + (22 * 31) - (22 * (target.getY() + 2)) + 15);
        } else if (plan == Move.RIGHT) {
            currImages = allImages[0];
            setX(getX() + speed);
            setY(34 + (22 * 31) - (22 * (target.getY() + 2)) + 15);
        } else if (plan == Move.UP) {
            currImages = allImages[2];
            setX(22 * target.getX() - 7);
            setY(getY() + speed);
        } else if (plan == Move.DOWN) {
            currImages = allImages[3];
            setX(22 * target.getX() - 7);
            setY(getY() - speed);
        }

    }

    public void update() {
        if (img >= 19) {
            img = 0;
        } else {
            img++;
        }
    }

    public Cell getTarget() {
        return target;
    }

    public void setTarget(Cell c){
        target = c;
    }

    public Move getNextMove(){
        return null;
    }

    public void setNextMove(Move m){
    }

    @Override
    public TextureRegion getImg() {
        if (getStatus() == Status.CAUGHT || getStatus() == Status.ENTERING) {
            return currImages[2];
        } else if (getStatus() == Status.FRIGHTENED) {
            return getFrightened(getFlasher() ? 1 : 0)[getFlasher() ? img / 5 : img / 10];
        } else
            return currImages[img/10];
    }

    public void setImg(int i) {
        currImages = allImages[i];
    }

    private float distance(Cell c, Cell target) {
        return (float)Math.sqrt(Math.pow(c.getX() - target.getX(), 2) + Math.pow(c.getY() - target.getY(), 2));
    }

    public void switchDirection() {
        Cell temp = completed;
        completed = target;
        target = temp;
    }

    public void reset() {
        setX(GameScreen.WORLD_WIDTH / 2 - 18);
        setY(34 + (22 * 31) - (22 * 15) + 2);
        currImages = allImages[3];
        img = 0;
        completed = new Cell(13, 11);
        target = new Cell(14, 11);
        speed = DEFAULT_SPEED;
        prevPlan = Move.RIGHT;
        waitingUp = false;
        setExiting(true);
        setStatus(Status.WAITING);
    }

    public Cell adjustedTarget(Cell pac, Cell red, Move f) {
        Cell t;
        if (f == Move.UP)
            t = new Cell(pac.getX(), pac.getY() - 4);
        else if (f == Move.DOWN)
            t = new Cell(pac.getX(), pac.getY() + 4);
        else if (f == Move.LEFT)
            t = new Cell(pac.getX() - 4, pac.getY());
        else if (f == Move.RIGHT)
            t = new Cell(pac.getX() + 4, pac.getY());
        else
            t = new Cell(pac.getX(), pac.getY());
            
        return t;
    }
}
