import com.badlogic.gdx.ApplicationAdapter; 
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer; 
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle; 
import com.badlogic.gdx.math.Circle; 
import com.badlogic.gdx.Input.Keys; 
import com.badlogic.gdx.math.Vector2; 
import com.badlogic.gdx.math.MathUtils; 
import com.badlogic.gdx.math.Intersector; 
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.audio.*;
import java.util.*;

public class GameScreen extends ApplicationAdapter//A Pong object ___________ ApplicationAda
{
    private OrthographicCamera camera; //the camera to our world
    private Viewport viewport; //maintains the ratios of your world
    private ShapeRenderer renderer; //used to draw textures and fonts
    private BitmapFont font; //used to draw fonts (text)
    private BitmapFont font1; //used to draw fonts (text)
    private BitmapFont font2; //used to draw fonts (text)
    private SpriteBatch batch; //also needed to draw fonts (text)
    private Vector2 screenCoord;
    private Vector2 worldCoord;

    //Game Instance Variables
    private PacManGame game;
    private BackgroundCell[][] map;
    private ArrayList<Character> tempChar;
    private GlyphLayout text;
    private TextureRegion defaultPacmanImg;
    private TextureRegion background;
    private TextureRegion[] playButton;
    private TextureRegion[] exitButton;
    private boolean playing;
    private Sound[] sound;
    private int ticker;
    private Music backgroundMusic;

    //WORLD_WIDTH and WORLD_HEIGHT proportional to config.width and config.height in GameLauncher class
    public static final float WORLD_WIDTH = 617; 
    public static final float WORLD_HEIGHT = 750;

    //other constance we will need

    @Override//called once when the game is started (kind of like our constructor)
    public void create(){
        camera = new OrthographicCamera(); //camera for our world, it is not moving
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera); //maintains world units from screen units
        renderer = new ShapeRenderer();
        font = new BitmapFont(Gdx.files.internal("assets/PacFont1.fnt"), Gdx.files.internal("assets/PacFont1.png"), false);
        font1 = new BitmapFont(Gdx.files.internal("assets/PacFont2.fnt"), Gdx.files.internal("assets/PacFont2.png"), false);
        font2 = new BitmapFont(Gdx.files.internal("assets/PacFont3.fnt"), Gdx.files.internal("assets/PacFont3.png"), false);
        batch = new SpriteBatch(); 
        screenCoord = new Vector2();
        worldCoord = new Vector2();
        ticker = 0;
        game = new PacManGame();
        map = game.getMap();
        defaultPacmanImg = new TextureRegion(new Texture(Gdx.files.internal("assets/PacManRIGHT3.png")));
        text = new GlyphLayout(font1, "READY!");
        playing = false;
        background = new TextureRegion(new Texture(Gdx.files.internal("assets/PacBackground.png")));
        playButton = new TextureRegion[] {
            new TextureRegion(new Texture(Gdx.files.internal("assets/StartButton1.png"))),
            new TextureRegion(new Texture(Gdx.files.internal("assets/StartButton2.png"))),
            new TextureRegion(new Texture(Gdx.files.internal("assets/StartButton3.png"))),
            new TextureRegion(new Texture(Gdx.files.internal("assets/StartButton4.png"))),
            new TextureRegion(new Texture(Gdx.files.internal("assets/StartButtonHover.png")))
        };
        exitButton = new TextureRegion[] {
            new TextureRegion(new Texture(Gdx.files.internal("assets/ExitButton1.png"))),
            new TextureRegion(new Texture(Gdx.files.internal("assets/ExitButton2.png"))),
            new TextureRegion(new Texture(Gdx.files.internal("assets/ExitButtonHover.png")))
        };
        sound = new Sound[] {
            Gdx.audio.newSound(Gdx.files.internal("assets/Sounds/chomp.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("assets/Sounds/death.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("assets/Sounds/ghost.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("assets/Sounds/start.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("assets/Sounds/fruit.mp3")),
        };
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/backgroundMusic.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
    }

    @Override//this is called 60 times a second, all the drawing is in here, or helper
    //methods that are called from here
    public void render(){

        screenCoord.x = Gdx.input.getX();
        screenCoord.y = Gdx.input.getY();

        worldCoord = viewport.unproject(screenCoord);

        viewport.apply(); 
        Gdx.gl.glClearColor(0/255f, 0/255f, 0/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (playing) {
            if (!game.getPacMan().getTunnel()) {
                if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
                    game.getPacMan().setNextMove(Move.RIGHT);
                }
                else if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
                    game.getPacMan().setNextMove(Move.LEFT);
                }
                else if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) {
                    game.getPacMan().setNextMove(Move.UP);
                }
                else if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) {
                    game.getPacMan().setNextMove(Move.DOWN);
                }
            }

            batch.begin();
            map = game.getMap();
            boolean win = true;
            for (int r = 0; r < map.length; r++) {
                for (int c = 0; c < map[r].length; c++) {
                    batch.draw((map[r][c].getTile() == 34 && ticker % 26 < 13) ? map[14][0].getImg() : map[r][c].getImg(), c*22, (WORLD_HEIGHT-34-22-22*r), 22, 22);
                    if (map[r][c].getTile() == 34 || map[r][c].getTile() == 33)
                        win = false;
                }
            }
            if (win)
                game.levelUp();

            //batch.draw(map[3][1].getImg(), 13 * 22, (WORLD_HEIGHT-34-22-22*11), 22, 22);
            if (!game.getGameOver()) {

                for (Fruit f: game.getFruitsOnMap()) {
                    batch.draw(f.getSwitchedImg(), 22*(f.getPos().getX()) - ((f.getEaten() ? 33 : f.getWidth()) - 22)/2, (WORLD_HEIGHT-34-22-22*(f.getPos().getY())) - ((f.getEaten() ? 10.5f : f.getHeight()) - 22)/2, f.getEaten() ? 33 : f.getWidth(), f.getEaten() ? 10.5f : f.getHeight());
                }

                tempChar = game.getCharacters();
                for (Character c: tempChar) {
                    if (game.getFreeze() <= 0) {
                        c.move(map, game.getPacMan().getTarget(), game.getRedTarget(), game.getPacMan().getRecentMove());
                        c.update();
                    }

                    if (!c.isHidden()) {
                        batch.draw(c.getImg(), c.getX(), c.getY(), c.getWidth(), c.getHeight());
                    }
                }
                
                switch(game.getSound()) {
                    case 0:
                        //sound[0].stop();
                        break;
                    case 1:
                        //stopSounds();
                        sound[0].play();
                        break;
                    case 2:
                        stopSounds();
                        sound[1].play();
                        break;
                    case 3:
                        stopSounds();
                        sound[2].play();
                        break;
                    case 4:
                        stopSounds();
                        sound[3].play();
                        break;
                    case 5:
                        stopSounds();
                        sound[4].play();
                        break;
                }
                
                game.check();

                if (game.isStarting() && !game.getGameOver()) {
                    text.setText(font1, "READY!");
                    font1.draw(batch, text, WORLD_WIDTH / 2 - ("READY!").length() * 20 / 2.0f, 34 + (22 * 31) - (22 * 18) + 18);
                }

            } else {
                text.setText(font2, "GAME OVER");
                font2.draw(batch, text, WORLD_WIDTH / 2 - ("GAME OVER").length() * 20 / 2.0f, 34 + (22 * 31) - (22 * 18) + 18);
            }

            if ((game.isStarting() && ticker % 40 < 20) || !game.isStarting()) {
                text.setText(font, "LVL " + game.getLevel());
                font.draw(batch, text, 15, WORLD_HEIGHT - 10);
            }

            text.setText(font, "PTS " + game.getPoints());
            font.draw(batch, text, WORLD_WIDTH - ("PTS " + game.getPoints()).length() * 20 - 15, WORLD_HEIGHT - 10);

            for (int i = 15; i < game.getLives() * 35 + 15; i += 35) {
                batch.draw(defaultPacmanImg, i, 4.5f, 25, 25);
            }

            for (int i = 0; i < game.getFruitsEaten().size(); i++) {
                Fruit f = game.getFruitsEaten().get(i);
                batch.draw(f.getImg(), WORLD_WIDTH - 15 - 30 - (45 * i),4.5f, (25.0f/30) * f.getWidth(), (25.0f/30) * f.getHeight());
            }
            
            if (!hovering(screenCoord.x, screenCoord.y, WORLD_WIDTH / 2 - 40, WORLD_HEIGHT - 34, 80, 34) && (game.getGameOver() || game.isStarting()))
                batch.draw(exitButton[ticker / 10 % 2], WORLD_WIDTH / 2 - 40, WORLD_HEIGHT - 34, 80, 34);
            else if (!hovering(screenCoord.x, screenCoord.y, WORLD_WIDTH / 2 - 35.4167f, WORLD_HEIGHT - 34, 80, 34)) {
                batch.draw(exitButton[1], WORLD_WIDTH / 2 - 40, WORLD_HEIGHT - 34, 80, 34);
            } else{
                batch.draw(exitButton[2], WORLD_WIDTH / 2 - 40, WORLD_HEIGHT - 34, 80, 34);
                if (Gdx.input.justTouched()) {
                    playing = false;
                    stopSounds();
                    backgroundMusic.play();
                }
            }
            
            batch.end();
        } else { //Home screen
            batch.begin();

            batch.draw(background, 0, 0, 617, 750);

            if (!hovering(screenCoord.x, screenCoord.y, WORLD_WIDTH / 2 - 125, 244, 250, 100)) {
                batch.draw(playButton[ticker / 3 % 4], WORLD_WIDTH / 2 - 125, 244, 250, 100);
            } else {
                batch.draw(playButton[4], WORLD_WIDTH / 2 - 125, 244, 250, 100);
                if (Gdx.input.justTouched()) {
                    game = new PacManGame();
                    playing = true;
                    backgroundMusic.stop();
                }
            }

            batch.end();
        }

        if (ticker >= 1000)
            ticker = 0;
        else
            ticker++;
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width, height, true); 
    }

    @Override
    public void dispose(){
        renderer.dispose(); 
        batch.dispose(); 
        font.dispose();
        for (Sound s : sound) {
            s.dispose();
        }
        backgroundMusic.dispose();
    }

    public static boolean onCenterCell(Cell c, float x, float y) {
        float cx = c.getX() * 22 + 11;
        float cy = (WORLD_HEIGHT-34-22-22*c.getY()) + 11;
        //System.out.println("cx: " + cx + ", cy: " + cy + ", x: " + x + ", y: " + y + ", x-condition: " + Math.pow(cx - (x + 18), 2) + "y - condition: " + Math.pow(cy - (y + 18), 2));
        return ((Math.pow(cx - (x + 18), 2) < 16) && (Math.pow(cy - (y + 18), 2) < 16));
    }

    public boolean hovering(float x, float y, float bx, float by, float bw, float bh) {
        return (x >= bx && x <= bx + bw && y <= by + bh && y >= by);
    }
    
    public void stopSounds() {
        for (Sound s : sound) {
            s.stop();
        }
    }

}
