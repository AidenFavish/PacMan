import java.util.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Gdx;
public class PacMan implements Character
{
    private float x;
    private float y;
    private float width;
    private float height;
    private TextureRegion[] images;
    private TextureRegion[][] allImages;
    private int img;
    private Cell target;
    private Move currMove;
    private Move nextMove;
    private final float SPEED = 2.9f;
    private boolean tunnel;
    private boolean opening;
    private boolean dead;
    private boolean isHidden;
    private TextureRegion realImage;
    private Move mostRecentMove;

    public PacMan() {
        x = 308 - 36 / 2;
        y = 34 + (22 * 31) - (22 * 25) + 15;
        width = 36;
        height = 36;
        img = 0;
        target = new Cell(13, 23);
        currMove = Move.LEFT;
        nextMove = Move.STILL;
        tunnel = false;
        opening = true;
        allImages = new TextureRegion[][] {
            {new TextureRegion(new Texture(Gdx.files.internal("assets/PacManLEFT1.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManLEFT2.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManLEFT3.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManLEFT4.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManLEFT5.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManLEFT6.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManLEFT7.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManLEFT8.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManLEFT9.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManLEFT10.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManLEFT11.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManLEFT12.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManLEFT13.png")))
            },
            {new TextureRegion(new Texture(Gdx.files.internal("assets/PacManRIGHT1.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManRIGHT2.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManRIGHT3.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManRIGHT4.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManRIGHT5.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManRIGHT6.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManRIGHT7.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManRIGHT8.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManRIGHT9.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManRIGHT10.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManRIGHT11.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManRIGHT12.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManRIGHT13.png")))

            },
            {new TextureRegion(new Texture(Gdx.files.internal("assets/PacManUP1.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManUP2.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManUP3.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManUP4.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManUP5.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManUP6.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManUP7.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManUP8.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManUP9.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManUP10.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManUP11.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManUP12.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManUP13.png")))

            },
            {new TextureRegion(new Texture(Gdx.files.internal("assets/PacManDOWN1.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManDOWN2.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManDOWN3.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManDOWN4.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManDOWN5.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManDOWN6.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManDOWN7.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManDOWN8.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManDOWN9.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManDOWN10.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManDOWN11.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManDOWN12.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("assets/PacManDOWN13.png")))

            }
        };
        images = allImages[0];
        realImage = images[0];
        dead = false;
        mostRecentMove = Move.LEFT;
    }

    public void move(BackgroundCell[][] map, Cell unused, Cell redTarget, Move facing) {
        //Tunnel logic
        if (target.getY() == 14) { //debug while leaving the tunnel as well
            if (target.getX() == 27 && (currMove == Move.RIGHT)) {
                if (x <= GameScreen.WORLD_WIDTH) {
                    tunnel = true;
                    x += SPEED;
                } else {
                    x = -36;
                    target.setX(0);
                }
                return;
            } else if (target.getX() == 0 && (currMove == Move.LEFT)) {
                if (x + 36 >= 0) {
                    tunnel = true;
                    x -= SPEED;
                } else {
                    x = GameScreen.WORLD_WIDTH;
                    target.setX(27);
                }
                return;
            } else if (target.getX() == 0 && tunnel) { //checks for button mashing
                if (x > 0) {
                    target.setX(1);
                    tunnel = false;
                } else {
                    x += SPEED;
                }
                return;
            } else if (target.getX() == 27 && tunnel) { //checks for button mashing
                if (x < 581) {
                    target.setX(26);
                    tunnel = false;
                } else {
                    x -= SPEED;
                }
                return;
            }
        }

        //System.out.println("curr: " + currMove + ", next: " + nextMove + ", tx: " + target.getX() + ", ty: " + target.getY());
        if (currMove == nextMove)
            nextMove = Move.STILL;

        //checks if its okay to turn
        if (currMove != nextMove && nextMove != Move.STILL) {
            if (nextMove == Move.LEFT) {
                if (GameScreen.onCenterCell(target, x, y)) {
                    target.setX(target.getX() - 1);
                    int type = getCellType(target, map);
                    if (type == 0 || type == 33 || type == 34) {
                        currMove = nextMove;
                        nextMove = Move.STILL;
                    }
                    target.setX(target.getX() + 1);
                } else if (currMove == Move.RIGHT) { //moves right away if on the same axis
                    target.setX(target.getX() - 1);
                    int type = getCellType(target, map);
                    if (type == 0 || type == 33 || type == 34) {
                        currMove = nextMove;
                        nextMove = Move.STILL;
                    } else {
                        target.setX(target.getX() + 1);
                    }
                }
            } else if (nextMove == Move.RIGHT) {
                if (GameScreen.onCenterCell(target, x, y)) {
                    target.setX(target.getX() + 1);
                    int type = getCellType(target, map);
                    if ((type == 0 || type == 33 || type == 34)) {
                        currMove = nextMove;
                        nextMove = Move.STILL;
                    }
                    target.setX(target.getX() - 1);
                } else if (currMove == Move.LEFT) { //moves right away if on the same axis
                    target.setX(target.getX() + 1);
                    int type = getCellType(target, map);
                    if (type == 0 || type == 33 || type == 34) {
                        currMove = nextMove;
                        nextMove = Move.STILL;
                    } else {
                        target.setX(target.getX() - 1);
                    }
                }
            } else if (nextMove == Move.UP) {
                if (GameScreen.onCenterCell(target, x, y)) {
                    target.setY(target.getY() - 1);
                    int type = getCellType(target, map);
                    if ((type == 0 || type == 33 || type == 34)) {
                        currMove = nextMove;
                        nextMove = Move.STILL;
                    }
                    target.setY(target.getY() + 1);
                } else if (currMove == Move.DOWN) { //moves right away if on the same axis
                    target.setY(target.getY() - 1);
                    int type = getCellType(target, map);
                    if ((type == 0 || type == 33 || type == 34)) {
                        currMove = nextMove;
                        nextMove = Move.STILL;
                    } else
                        target.setY(target.getY() + 1);
                }
            } else if (nextMove == Move.DOWN) {
                if (GameScreen.onCenterCell(target, x, y)) {
                    target.setY(target.getY() + 1);
                    int type = getCellType(target, map);
                    if ((type == 0 || type == 33 || type == 34)) {
                        currMove = nextMove;
                        nextMove = Move.STILL;
                    }
                    target.setY(target.getY() - 1);
                } else if (currMove == Move.UP) { //moves right away if on the same axis
                    target.setY(target.getY() + 1);
                    int type = getCellType(target, map);
                    if ((type == 0 || type == 33 || type == 34)) {
                        currMove = nextMove;
                        nextMove = Move.STILL;
                    } else
                        target.setY(target.getY() - 1);
                }
            }
        }

        //actual move method
        if (currMove == Move.LEFT) {
            images = allImages[0];
            if (GameScreen.onCenterCell(target, x, y)) {
                target.setX(target.getX() - 1);
                int type = getCellType(target, map);
                if (!(type == 0 || type == 33 || type == 34)) {
                    target.setX(target.getX() + 1);
                    currMove = nextMove;
                    nextMove = Move.STILL;
                }
            } else {
                y = 34 + (22 * 31) - (22 * (target.getY() + 2)) + 15;
                x -= SPEED;
            }
        } else if (currMove == Move.RIGHT) {
            images = allImages[1];
            if (GameScreen.onCenterCell(target, x, y)) {
                target.setX(target.getX() + 1);
                int type = getCellType(target, map);
                if (!(type == 0 || type == 33 || type == 34)) {
                    target.setX(target.getX() - 1);
                    currMove = nextMove;
                    nextMove = Move.STILL;
                }
            } else {
                y = 34 + (22 * 31) - (22 * (target.getY()+2)) + 15;
                x += SPEED;
            }
        } else if (currMove == Move.UP) {
            images = allImages[2];
            if (GameScreen.onCenterCell(target, x, y)) {
                target.setY(target.getY() - 1);
                int type = getCellType(target, map);
                if (!(type == 0 || type == 33 || type == 34)) {
                    target.setY(target.getY() + 1);
                    currMove = nextMove;
                    nextMove = Move.STILL;
                }
            } else {
                x = 22 * target.getX() - 7;
                y += SPEED;
            }
        } else if (currMove == Move.DOWN) {
            images = allImages[3];
            if (GameScreen.onCenterCell(target, x, y)) {
                target.setY(target.getY() + 1);
                int type = getCellType(target, map);
                if (!(type == 0 || type == 33 || type == 34)) {
                    target.setY(target.getY() - 1);
                    currMove = nextMove;
                    nextMove = Move.STILL;
                }
            } else {
                x = 22 * target.getX() - 7;
                y -= SPEED;
            }
        }
        
        if (currMove != Move.STILL) {
            mostRecentMove = currMove;
        }
    }

    public void update() {
        if (currMove != Move.STILL) {
            if (img >= 38) {
                img -= 2;
                opening = false;
            } else if (img <= 1) {
                img += 2;
                opening = true;
            } else if (opening)
                img += 2;
            else
                img -= 2;
        }
        realImage = images[img/10];
    }
    
    public int getSoundIndicator() {
        if (currMove == Move.STILL) {
            return 0;
        } else if (img == 38 && !dead) {
            return 1;
        }
        return -1;
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

    public ArrayList<Float> cellsOccupied() {
        return null;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setImg(TextureRegion x) {
        realImage = x;
    }

    public TextureRegion getImg() {
        return realImage;
    }

    public Move getNextMove() {
        return nextMove;
    }

    public void setNextMove(Move m) {
        nextMove = m;
    }

    public Cell getTarget() {
        return target;
    }

    public void setTarget(Cell c) {
        //target = c;
    }

    public int getCellType(Cell c, BackgroundCell[][] map) {
        //System.out.println("x: " + c.getX() + ", y: " + c.getY() + ", tile: " + map[17][12].getTile());
        return map[c.getY()][c.getX()].getTile();
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean x) {
        dead = x;
    }

    public boolean getTunnel() {
        return tunnel;
    }
    
    public boolean isHidden() {
        return isHidden;
    }
    
    public void setHidden(boolean x) {
        isHidden = x;
    }
    
    public void kill() {
        dead = true;
    }
    
    public boolean nextDead() {
        img += 2;
        if (img/10 > 12) {
            return true;
        }
        realImage = images[img/10];
        return false;
    }
    
    public void reset() {
        images = allImages[0];
        dead = false;
        x = 308 - 36 / 2;
        y = 34 + (22 * 31) - (22 * 25) + 15;
        width = 36;
        height = 36;
        img = 0;
        target = new Cell(13, 23);
        currMove = Move.LEFT;
        nextMove = Move.STILL;
        tunnel = false;
        opening = true;
        realImage = images[0];
    }
    
    public Move getRecentMove() {
        return mostRecentMove;
    }
}
