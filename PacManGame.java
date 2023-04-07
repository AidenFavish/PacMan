import java.util.*;
import com.badlogic.gdx.math.Rectangle; 
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Gdx;
public class PacManGame
{
    private BackgroundCell[][] map;
    private ArrayList<Character> characters;
    private PacMan pacman;
    private int vulnurable;
    private int freeze;
    private int level;
    private int lives;
    private int points;
    private ModeTimer timer;
    private int starting;
    private boolean gameOver;
    private Cell redTarget;
    private int dots;
    private Fruit[] fruitOptions;
    private int fruitsThisLevel;
    private ArrayList<Fruit> fruitsOnMap;
    private ArrayList<Fruit> fruitsEaten;
    private ArrayList<Cell> spacesOpen;
    private long pointLog;
    private int ghostsEaten;
    private int prioritySound;

    public PacManGame() {
        int[][] grid = new int[][]{
                {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 5},
                {20, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 11, 32, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 6},
                {20, 33, 15, 14, 14, 16, 33, 15, 14, 14, 14, 16, 33, 11, 32, 33, 15, 14, 14, 14, 16, 33, 15, 14, 14, 16, 33, 6},
                {20, 34, 11, 0, 0, 32, 33, 11, 0, 0, 0, 32, 33, 11, 32, 33, 11, 0, 0, 0, 32, 33, 11, 0, 0, 32, 34, 6},
                {20, 33, 12, 31, 31, 13, 33, 12, 31, 31, 31, 13, 33, 12, 13, 33, 12, 31, 31, 31, 13, 33, 12, 31, 31, 13, 33, 6},
                {20, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 6},
                {20, 33, 15, 14, 14, 16, 33, 15, 16, 33, 15, 14, 14, 14, 14, 14, 14, 16, 33, 15, 16, 33, 15, 14, 14, 16, 33, 6},
                {20, 33, 12, 31, 31, 13, 33, 11, 32, 33, 12, 31, 31, 16, 15, 31, 31, 13, 33, 11, 32, 33, 12, 31, 31, 13, 33, 6},
                {20, 33, 33, 33, 33, 33, 33, 11, 32, 33, 33, 33, 33, 11, 32, 33, 33, 33, 33, 11, 32, 33, 33, 33, 33, 33, 33, 6},
                {19, 18, 18, 18, 18, 16, 33, 11, 12, 14, 14, 16, 0, 11, 32, 0, 15, 14, 14, 13, 32, 33, 15, 18, 18, 18, 18, 7},
                {0, 0, 0, 0, 0, 20, 33, 11, 15, 31, 31, 13, 0, 12, 13, 0, 12, 31, 31, 16, 32, 33, 6, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 20, 33, 11, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 32, 33, 6, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 20, 33, 11, 32, 0, 26, 18, 8, 23, 23, 9, 18, 25, 0, 11, 32, 33, 6, 0, 0, 0, 0, 0},
                {2, 2, 2, 2, 2, 13, 33, 12, 13, 0, 6, 0, 0, 0, 0, 0, 0, 20, 0, 12, 13, 33, 12, 2, 2, 2, 2, 2},
                {0, 0, 0, 0, 0, 0, 33, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0, 33, 0, 0, 0, 0, 0, 0},
                {18, 18, 18, 18, 18, 16, 33, 15, 16, 0, 6, 0, 0, 0, 0, 0, 0, 20, 0, 15, 16, 33, 15, 18, 18, 18, 18, 18},
                {0, 0, 0, 0, 0, 20, 33, 11, 32, 0, 28, 2, 2, 2, 2, 2, 2, 27, 0, 11, 32, 33, 6, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 20, 33, 11, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 32, 33, 6, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 20, 33, 11, 32, 0, 15, 14, 14, 14, 14, 14, 14, 16, 0, 11, 32, 33, 6, 0, 0, 0, 0, 0},
                {1, 2, 2, 2, 2, 13, 33, 12, 13, 0, 12, 31, 31, 16, 15, 31, 31, 13, 0, 12, 13, 33, 12, 2, 2, 2, 2, 5},
                {20, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 11, 32, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 6},
                {20, 33, 15, 14, 14, 16, 33, 15, 14, 14, 14, 16, 33, 11, 32, 33, 15, 14, 14, 14, 16, 33, 15, 14, 14, 16, 33, 6},
                {20, 33, 12, 31, 16, 32, 33, 12, 31, 31, 31, 13, 33, 12, 13, 33, 12, 31, 31, 31, 13, 33, 11, 15, 31, 13, 33, 6},
                {20, 34, 33, 33, 11, 32, 33, 33, 33, 33, 33, 33, 33, 0, 0, 33, 33, 33, 33, 33, 33, 33, 11, 32, 33, 33, 34, 6},
                {22, 14, 16, 33, 11, 32, 33, 15, 16, 33, 15, 14, 14, 14, 14, 14, 14, 16, 33, 15, 16, 33, 11, 32, 33, 15, 14, 10},
                {21, 31, 13, 33, 12, 13, 33, 11, 32, 33, 12, 31, 31, 16, 15, 31, 31, 13, 33, 11, 32, 33, 12, 13, 33, 12, 31, 17},
                {20, 33, 33, 33, 33, 33, 33, 11, 32, 33, 33, 33, 33, 11, 32, 33, 33, 33, 33, 11, 32, 33, 33, 33, 33, 33, 33, 6},
                {20, 33, 15, 14, 14, 14, 14, 13, 12, 14, 14, 16, 33, 11, 32, 33, 15, 14, 14, 13, 12, 14, 14, 14, 14, 16, 33, 6},
                {20, 33, 12, 31, 31, 31, 31, 31, 31, 31, 31, 13, 33, 12, 13, 33, 12, 31, 31, 31, 31, 31, 31, 31, 31, 13, 33, 6},
                {20, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 6},
                {19, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 7}
            };
        map = new BackgroundCell[31][28];
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                map[r][c] = new BackgroundCell(grid[r][c]);
            }
        }

        level = 1;
        lives = 3;
        points = 0;
        vulnurable = 0;
        freeze = 240;
        timer = new ModeTimer(level);
        pacman = new PacMan();
        characters = new ArrayList<Character>();
        characters.add(pacman);
        characters.add(new RedGhost(308 - 36 / 2, 34 + (22 * 31) - (22 * 13) + 15));
        characters.add(new PinkGhost(GameScreen.WORLD_WIDTH / 2 - 18, 34 + (22 * 31) - (22 * 15) + 2));
        characters.add(new CyanGhost(11.25f * 22, 34 + (22 * 31) - (22 * 16) + 2));
        characters.add(new OrangeGhost(16.75f * 22 - 36, 34 + (22 * 31) - (22 * 16) + 2));
        gameOver = false;
        starting = 240;
        dots = 0;
        fruitOptions = new Fruit[] {
            new Fruit(30, 30, new TextureRegion(new Texture(Gdx.files.internal("assets/Cherry.png")))
            , 100, new TextureRegion(new Texture(Gdx.files.internal("assets/Point100.png")))),

            new Fruit(27.5f, 30, new TextureRegion(new Texture(Gdx.files.internal("assets/Strawberry.png")))
            , 300, new TextureRegion(new Texture(Gdx.files.internal("assets/Point300.png")))),

            new Fruit(30, 30, new TextureRegion(new Texture(Gdx.files.internal("assets/Orange.png")))
            , 500, new TextureRegion(new Texture(Gdx.files.internal("assets/Point500.png")))),

            new Fruit("MC", 30, 30, new TextureRegion(new Texture(Gdx.files.internal("assets/MC.png")))
            , 1000, new TextureRegion(new Texture(Gdx.files.internal("assets/Point1000.png")))),

            new Fruit(30, 30, new TextureRegion(new Texture(Gdx.files.internal("assets/Apple.png")))
            , 700, new TextureRegion(new Texture(Gdx.files.internal("assets/Point700.png")))),

            new Fruit(27.5f, 35, new TextureRegion(new Texture(Gdx.files.internal("assets/Melon.png")))
            , 1000, new TextureRegion(new Texture(Gdx.files.internal("assets/Point1000.png")))),

            new Fruit(30, 30, new TextureRegion(new Texture(Gdx.files.internal("assets/Galaxian_Starship.png")))
            , 2000, new TextureRegion(new Texture(Gdx.files.internal("assets/Point2000.png")))),

            new Fruit(30, 32.5f, new TextureRegion(new Texture(Gdx.files.internal("assets/Bell.png")))
            , 3000, new TextureRegion(new Texture(Gdx.files.internal("assets/Point3000.png")))),

            new Fruit(27.5f, 32.5f, new TextureRegion(new Texture(Gdx.files.internal("assets/Key.png")))
            , 5000, new TextureRegion(new Texture(Gdx.files.internal("assets/Point5000.png")))),
        };
        fruitsThisLevel = 0;
        fruitsOnMap = new ArrayList<Fruit>();
        fruitsEaten = new ArrayList<Fruit>();
        fillSpacesOpen();
        pointLog = 0;
        ghostsEaten = 0;
        prioritySound = -1;
    }

    public BackgroundCell[][] getMap() {
        return map;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public PacMan getPacMan() {
        return pacman;
    }

    public void setPoints(int p) {
        points = p;
    }

    public int getPoints() {
        return points;
    }

    public void check() {
        prioritySound = -1;
        timer.next();

        if (vulnurable > 0)
            vulnurable--;

        if (freeze > 0)
            freeze--;

        if (starting > 0) {
            if (starting == 240)
                prioritySound = 4;
                
            starting--;
            return;
        }
            
            
        if (points >= 7500 + 7500 * pointLog) {
            pointLog++;
            lives++;
        }

        checkPointCollision();

        checkFruitPlacement();

        checkFruitCollision();

        checkGhostCollision();

        if (pacman.isDead()) {
            boolean doneDying = pacman.nextDead();
            if (doneDying) {
                lives--;
                reset();
                
                if (lives <= 0) {
                    gameOver = true;
                }
            }
        }

        for (Character c : characters) {
            if (freeze <= 0) {
                c.setHidden(false);
            }

            if (c instanceof RedGhost)
                redTarget = c.getTarget();

            if (c instanceof Ghost) {
                Ghost g = (Ghost)c;
                if (g.getStatus() != Status.WAITING && g.getStatus() != Status.ENTERING && g.getStatus() != Status.CAUGHT) {
                    if (vulnurable == 60 * ((int)(-(7.0/30) * level) + 8)) {
                        g.setStatus(Status.FRIGHTENED);
                        g.setFlasher(false);
                    }
                    if (vulnurable < 60 * 2 && vulnurable > 1) {
                        g.setFlasher(true);
                    }
                    if (vulnurable == 1 && g.getStatus() == Status.FRIGHTENED) {
                        g.setStatus(Status.CHASE);
                        g.setFlasher(false);
                    }
                    if (vulnurable <= 0 && (g.getStatus() == Status.CHASE || g.getStatus() == Status.SCATTER)) {

                        if (g instanceof RedGhost && redAngry()) {
                            g.setStatus(Status.CHASE);
                        } else {
                            g.setStatus(timer.getMode());
                        }

                    }
                } else if (g instanceof CyanGhost) {
                    if (level == 1 && dots >= 30) {
                        g.setExiting(true);
                    } else if (level > 1) {
                        g.setExiting(true);
                    }
                } else if (g instanceof OrangeGhost) {
                    if (level == 1 && dots >= 90) {
                        g.setExiting(true);
                    } else if (level == 2 && dots >= 50) {
                        g.setExiting(true);
                    } else if (level > 2) {
                        g.setExiting(true);
                    }
                }

            }
        }

    }

    private void checkPointCollision() {
        int type;

        if (GameScreen.onCenterCell(pacman.getTarget(), pacman.getX(), pacman.getY())) {
            type = pacman.getCellType(pacman.getTarget(), map);
            if (type == 33) {
                map[pacman.getTarget().getY()][pacman.getTarget().getX()].setTile(0);
                points += 10;
                dots++;
            } else if (type == 34) {
                map[pacman.getTarget().getY()][pacman.getTarget().getX()].setTile(0);
                points += 50;
                vulnurable = 60 * ((int)(-(7.0/30) * level) + 8);
                dots++;
            }
        }

    }

    private void checkGhostCollision() {
        for (Character c: characters) {
            if (c instanceof Ghost && !c.isDead() && !pacman.isDead()) {
                if (intersects(c.getX(), c.getY(), 36, 36, pacman.getX(), pacman.getY(), 36, 36)) {
                    Ghost g = (Ghost)c;
                    if (g.getStatus() != Status.FRIGHTENED) {
                        timer = new ModeTimer(level);
                        setFreeze(240);
                        pacman.kill();
                        prioritySound = 2;
                    } else {
                        prioritySound = 3;
                        setFreeze(60);
                        ghostsEaten++;
                        points += (int)Math.pow(2, ghostsEaten <= 4 ? ghostsEaten : 4) * 100;
                        g.setHidden(true);
                        g.setStatus(Status.CAUGHT);
                        pacman.setImg(new TextureRegion(new Texture(Gdx.files.internal("assets/Point" + ((int)Math.pow(2, ghostsEaten <= 4 ? ghostsEaten : 4) * 100) + ".png"))));
                    }
                }
            }
        }
    }

    private boolean intersects(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2) {
        Rectangle g = new Rectangle(x1 + 1, y1 + 1, w1 - 1, h1 - 1);
        Rectangle p = new Rectangle(x2 + 1, y2 + 1, w2 - 1, h2 - 1);

        return Intersector.overlaps(g, p);
    }

    public void setFreeze(int f) {
        freeze = f;
    }

    public int getFreeze() {
        return freeze;
    }

    public void reset() {
        for (Character c : characters) {
            c.reset();
        }
        freeze = 240;
        starting = 240;
        vulnurable = 0;
        timer = new ModeTimer(level);
        fruitsOnMap = new ArrayList<Fruit>();
        fillSpacesOpen();
        prioritySound = -1;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public int getLevel() {
        return level;
    }

    public boolean isStarting() {
        return starting > 0;
    }

    public int getLives() {
        return lives;
    }

    public void levelUp() {
        level++;
        reset();
        int[][] grid = new int[][]{
                {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 5},
                {20, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 11, 32, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 6},
                {20, 33, 15, 14, 14, 16, 33, 15, 14, 14, 14, 16, 33, 11, 32, 33, 15, 14, 14, 14, 16, 33, 15, 14, 14, 16, 33, 6},
                {20, 34, 11, 0, 0, 32, 33, 11, 0, 0, 0, 32, 33, 11, 32, 33, 11, 0, 0, 0, 32, 33, 11, 0, 0, 32, 34, 6},
                {20, 33, 12, 31, 31, 13, 33, 12, 31, 31, 31, 13, 33, 12, 13, 33, 12, 31, 31, 31, 13, 33, 12, 31, 31, 13, 33, 6},
                {20, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 6},
                {20, 33, 15, 14, 14, 16, 33, 15, 16, 33, 15, 14, 14, 14, 14, 14, 14, 16, 33, 15, 16, 33, 15, 14, 14, 16, 33, 6},
                {20, 33, 12, 31, 31, 13, 33, 11, 32, 33, 12, 31, 31, 16, 15, 31, 31, 13, 33, 11, 32, 33, 12, 31, 31, 13, 33, 6},
                {20, 33, 33, 33, 33, 33, 33, 11, 32, 33, 33, 33, 33, 11, 32, 33, 33, 33, 33, 11, 32, 33, 33, 33, 33, 33, 33, 6},
                {19, 18, 18, 18, 18, 16, 33, 11, 12, 14, 14, 16, 0, 11, 32, 0, 15, 14, 14, 13, 32, 33, 15, 18, 18, 18, 18, 7},
                {0, 0, 0, 0, 0, 20, 33, 11, 15, 31, 31, 13, 0, 12, 13, 0, 12, 31, 31, 16, 32, 33, 6, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 20, 33, 11, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 32, 33, 6, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 20, 33, 11, 32, 0, 26, 18, 8, 23, 23, 9, 18, 25, 0, 11, 32, 33, 6, 0, 0, 0, 0, 0},
                {2, 2, 2, 2, 2, 13, 33, 12, 13, 0, 6, 0, 0, 0, 0, 0, 0, 20, 0, 12, 13, 33, 12, 2, 2, 2, 2, 2},
                {0, 0, 0, 0, 0, 0, 33, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0, 33, 0, 0, 0, 0, 0, 0},
                {18, 18, 18, 18, 18, 16, 33, 15, 16, 0, 6, 0, 0, 0, 0, 0, 0, 20, 0, 15, 16, 33, 15, 18, 18, 18, 18, 18},
                {0, 0, 0, 0, 0, 20, 33, 11, 32, 0, 28, 2, 2, 2, 2, 2, 2, 27, 0, 11, 32, 33, 6, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 20, 33, 11, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 32, 33, 6, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 20, 33, 11, 32, 0, 15, 14, 14, 14, 14, 14, 14, 16, 0, 11, 32, 33, 6, 0, 0, 0, 0, 0},
                {1, 2, 2, 2, 2, 13, 33, 12, 13, 0, 12, 31, 31, 16, 15, 31, 31, 13, 0, 12, 13, 33, 12, 2, 2, 2, 2, 5},
                {20, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 11, 32, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 6},
                {20, 33, 15, 14, 14, 16, 33, 15, 14, 14, 14, 16, 33, 11, 32, 33, 15, 14, 14, 14, 16, 33, 15, 14, 14, 16, 33, 6},
                {20, 33, 12, 31, 16, 32, 33, 12, 31, 31, 31, 13, 33, 12, 13, 33, 12, 31, 31, 31, 13, 33, 11, 15, 31, 13, 33, 6},
                {20, 34, 33, 33, 11, 32, 33, 33, 33, 33, 33, 33, 33, 0, 0, 33, 33, 33, 33, 33, 33, 33, 11, 32, 33, 33, 34, 6},
                {22, 14, 16, 33, 11, 32, 33, 15, 16, 33, 15, 14, 14, 14, 14, 14, 14, 16, 33, 15, 16, 33, 11, 32, 33, 15, 14, 10},
                {21, 31, 13, 33, 12, 13, 33, 11, 32, 33, 12, 31, 31, 16, 15, 31, 31, 13, 33, 11, 32, 33, 12, 13, 33, 12, 31, 17},
                {20, 33, 33, 33, 33, 33, 33, 11, 32, 33, 33, 33, 33, 11, 32, 33, 33, 33, 33, 11, 32, 33, 33, 33, 33, 33, 33, 6},
                {20, 33, 15, 14, 14, 14, 14, 13, 12, 14, 14, 16, 33, 11, 32, 33, 15, 14, 14, 13, 12, 14, 14, 14, 14, 16, 33, 6},
                {20, 33, 12, 31, 31, 31, 31, 31, 31, 31, 31, 13, 33, 12, 13, 33, 12, 31, 31, 31, 31, 31, 31, 31, 31, 13, 33, 6},
                {20, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 6},
                {19, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 7}
            };
        map = new BackgroundCell[31][28];
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                map[r][c] = new BackgroundCell(grid[r][c]);
            }
        }
        dots = 0;
        fruitsThisLevel = 0;
        fruitsOnMap = new ArrayList<Fruit>();
        fillSpacesOpen();
        ghostsEaten = 0;
    }

    public Cell getRedTarget() {
        return redTarget;
    }

    public void setDots(int x) {
        dots = x;
    }

    public int getDots() {
        return dots;
    }

    public boolean redAngry() {
        int n;
        switch (level) {
            case 1:
                n = 20;
                break;
            case 2:
                n = 30;
                break;
            case 3:
            case 4:
            case 5:
                n = 40;
                break;
            case 6:
            case 7:
            case 8:
                n = 50;
                break;
            case 9:
            case 10:
            case 11:
                n = 60;
                break;
            case 12:
            case 13:
            case 14:
                n = 80;
                break;
            case 15:
            case 16:
            case 17:
            case 18:
                n = 100;
                break;
            default:
                n = 120;
        }
        return 244 - dots <= n;
    }

    public void checkFruitPlacement() {
        for (int i = fruitsOnMap.size() - 1; i >= 0; i--) {
            if (fruitsOnMap.get(i).getTimer() == 0) {
                fruitsOnMap.remove(i);
            } else if (freeze == 0 && fruitsOnMap.get(i).getTimer() != -1) {
                fruitsOnMap.get(i).setTimer(fruitsOnMap.get(i).getTimer() - 1);
            }
        }

        if (fruitsThisLevel == 0 && dots >= 75) {
            int r = (int)(Math.random() * 100 + 1);
            int picker = (int)(Math.random() * spacesOpen.size());
            
            if (r <= 25)
                fruitsOnMap.add(new Fruit(fruitOptions[0]));
            else if (r <= 45)
                fruitsOnMap.add(new Fruit(fruitOptions[1]));
            else if (r <= 60)
                fruitsOnMap.add(new Fruit(fruitOptions[2]));
            else if (r <= 70) {
                fruitsOnMap.add(new Fruit(fruitOptions[3]));
            } else if (r <= 80)
                fruitsOnMap.add(new Fruit(fruitOptions[4]));
            else if (r <= 90)
                fruitsOnMap.add(new Fruit(fruitOptions[5]));
            else if (r <= 95)
                fruitsOnMap.add(new Fruit(fruitOptions[6]));
            else if (r <= 99)
                fruitsOnMap.add(new Fruit(fruitOptions[7]));
            else
                fruitsOnMap.add(new Fruit(fruitOptions[8]));
            
            fruitsOnMap.get(fruitsOnMap.size() - 1).setX(spacesOpen.get(picker).getX());
            fruitsOnMap.get(fruitsOnMap.size() - 1).setY(spacesOpen.get(picker).getY());
            spacesOpen.remove(picker);
            fruitsThisLevel++;
        } else if (fruitsThisLevel == 1 && dots >= 125) {
            int r = (int)(Math.random() * 100 + 1);
            int picker = (int)(Math.random() * spacesOpen.size());
            
            if (r <= 25)
                fruitsOnMap.add(new Fruit(fruitOptions[0]));
            else if (r <= 45)
                fruitsOnMap.add(new Fruit(fruitOptions[1]));
            else if (r <= 60)
                fruitsOnMap.add(new Fruit(fruitOptions[2]));
            else if (r <= 70) {
                fruitsOnMap.add(new Fruit(fruitOptions[3]));
            } else if (r <= 80)
                fruitsOnMap.add(new Fruit(fruitOptions[4]));
            else if (r <= 90)
                fruitsOnMap.add(new Fruit(fruitOptions[5]));
            else if (r <= 95)
                fruitsOnMap.add(new Fruit(fruitOptions[6]));
            else if (r <= 99)
                fruitsOnMap.add(new Fruit(fruitOptions[7]));
            else
                fruitsOnMap.add(new Fruit(fruitOptions[8]));
            
            fruitsOnMap.get(fruitsOnMap.size() - 1).setX(spacesOpen.get(picker).getX());
            fruitsOnMap.get(fruitsOnMap.size() - 1).setY(spacesOpen.get(picker).getY());
            spacesOpen.remove(picker);
            fruitsThisLevel++;
        }
    }

    public void checkFruitCollision() {
        for (int i = 0; i < fruitsOnMap.size(); i++) {
            Fruit f = fruitsOnMap.get(i);
            if (intersects(pacman.getX(), pacman.getY(), 36, 36, 22*(f.getPos().getX()) - (f.getWidth() - 22)/2, (GameScreen.WORLD_HEIGHT-34-22-22*(f.getPos().getY())) - (f.getHeight() - 22)/2, f.getWidth(), f.getWidth()) && !f.getEaten()) {
                fruitsEaten.add(f);
                f.setTimer(90);
                f.switchImages(true);
                f.setEaten(true);
                points += f.getPoints();
                prioritySound = 5;
                if (f.getName().equals("MC")) {
                    lives++;
                }
            }
        }
    }
    
    public ArrayList<Fruit> getFruitsOnMap() {
        return fruitsOnMap;
    }
    
    public ArrayList<Fruit> getFruitsEaten() {
        return fruitsEaten;
    }
    
    public void fillSpacesOpen() {
        spacesOpen = new ArrayList<Cell>();
        for (int i = 9; i <= 18; i++) {
            spacesOpen.add(new Cell(i, 11));
            spacesOpen.add(new Cell(i, 17));
        }
        for (int i = 11; i <= 17; i++) {
            spacesOpen.add(new Cell(9, i));
            spacesOpen.add(new Cell(18, i));
        }
    }
    
    public int getSound() {
        int n = pacman.getSoundIndicator();
        if (prioritySound > n)
            n = prioritySound;
        
        return n;
    }
}
