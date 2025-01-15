package Schanppt_Hubi.Structure.View;

import Schanppt_Hubi.Structure.Flow.FlowThread;
import Schanppt_Hubi.Structure.Logic.map.Wall;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import Schanppt_Hubi.Structure.Main;
import java.io.BufferedReader;
import java.util.*;

public class MapGUI implements Screen {
    private Main game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Room[][] rooms;
    private Stage stage;
    private Skin skin;
    private TextButton leftButton;
    private TextButton rightButton;
    private TextButton downButton;
    private TextButton upButton;
    private TextButton QuestionButton;

    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private List<Player> PlayerList;
    private int currentPLayerIndexing;
    public FlowThread fl;
    private int test;
    public boolean ButtonEnabled;

    private WallGUI wallGUI;

    private List<WallGUI> wallGUIlist;
    private SpriteBatch wallBatch;

    private Texture sampleTexture;

    private List<Texture> WallTextureList;
    public MapGUI(final Main game, FlowThread fl) {
        sampleTexture= new Texture("Wall/BlockWall.png");
        wallBatch = new SpriteBatch();
        wallGUIlist = new ArrayList<>();
        test = 50;
        ButtonEnabled=true;
        this.fl=fl;
        rooms = new Room[4][4];
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 850, 850);
        viewport = new ScreenViewport();
        viewport.apply();
        PlayerList= new ArrayList<>();
        currentPLayerIndexing=0;
        camera.position.set(850 / 2f, 850 / 2f, 0);
        camera.update();

        TmxMapLoader mapLoader = new TmxMapLoader();
        tiledMap = mapLoader.load("MapGUI.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        RoomInfoAssign();
        InfoTest();

        player4 = new Player("77",3,3,(int)rooms[0][3].x,(int)rooms[0][3].y,1);
        player4.setQuarterPosition(56,56);
        PlayerList.add(player4);

        player3 = new Player("71",3,0,(int)rooms[0][0].x,(int)rooms[0][0].y,1);
        player3.setQuarterPosition(0,56);
        PlayerList.add(player3);


        player2 = new Player("17",0,3,(int)rooms[3][3].x,(int)rooms[3][3].y,1);
        player2.setQuarterPosition(56,0);
        PlayerList.add(player2);


        player1 = new Player("11",0,0,(int)rooms[3][0].x,(int)rooms[3][0].y,1);
        player1.setQuarterPosition(0,0);
        PlayerList.add(player1);

        wallGUI = new WallGUI(5,5);

        WallTextureList = new ArrayList<>();
        createTexture();
        fl.currentTurnState=1;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json")); // Make sure to have a skin file in your assets


        leftButton = new TextButton("Left", skin);
        leftButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (ButtonEnabled)  {
                    ButtonEnabled=false;
                    fl.currentTurnState=0;
                    fl.returnData = "Left";
                    --PlayerList.get(currentPLayerIndexing).roomy;

                }
            }
        });
        stage.addActor(leftButton);

        rightButton = new TextButton("Right", skin);
        rightButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (ButtonEnabled)   {
                    ButtonEnabled=false;
                    fl.currentTurnState=0;
                    fl.returnData = "Right";
                    ++PlayerList.get(currentPLayerIndexing).roomy;
                }
            }
        });
        stage.addActor(rightButton);

        downButton = new TextButton("Down", skin);
        downButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (ButtonEnabled) {
                    ButtonEnabled=false;
                    fl.currentTurnState=0;
                    fl.returnData = "Down";
                    ++PlayerList.get(currentPLayerIndexing).roomx;
                }
            }
        });
        stage.addActor(downButton);

        upButton = new TextButton("Up", skin);
        upButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (ButtonEnabled) {
                    ButtonEnabled=false;
                    fl.currentTurnState=0;
                    fl.returnData = "Up";
                    --PlayerList.get(currentPLayerIndexing).roomx;
                }
            }
        });
        stage.addActor(upButton);

        QuestionButton = new TextButton("?", skin);
        stage.addActor(QuestionButton);

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }

    public void createTexture()    {
        for (int i=0;i<PlayerList.size();i++)   {
            PlayerList.get(i).batch=new SpriteBatch();
            PlayerList.get(i).texture=new Texture("Character_skin/blue_mouse.png");
            PlayerList.get(i).animalRenderer=new Sprite(PlayerList.get(i).texture);
            WallTextureList.add(new Texture("Wall/BlockWall.png"));
            WallTextureList.add(new Texture("Wall/FreeWall.png"));
            WallTextureList.add(new Texture("Wall/MagicWall.png"));
            WallTextureList.add(new Texture("Wall/MouseWall.png"));
            WallTextureList.add(new Texture("Wall/RabbitWall.png"));
        }

    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15f, 0.65f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        mapRenderer.setView(camera);

        mapRenderer.render();


        for (int i=0;i<PlayerList.size();i++) {
            if (PlayerList.get(i).displayShape) {
                PlayerList.get(i).batch.setProjectionMatrix(camera.combined);
                PlayerList.get(i).batch.begin();
                PlayerList.get(i).animalRenderer.setPosition(PlayerList.get(i).x, PlayerList.get(i).y);
                PlayerList.get(i).animalRenderer.setSize(56, 56);
                PlayerList.get(i).animalRenderer.draw(PlayerList.get(i).batch);
                PlayerList.get(i).batch.end();
            }
        }


        for (int i=0;i<wallGUIlist.size();i++)      {
            wallBatch.setProjectionMatrix(camera.combined);
            wallBatch.begin();
            wallGUIlist.get(i).wallRenderer.setPosition(wallGUIlist.get(i).x, wallGUIlist.get(i).y);
            System.out.println("Displaying "+i+""+wallGUIlist.get(i).x+wallGUIlist.get(i).y);
            wallGUIlist.get(i).wallRenderer.setSize(32,112);
            wallGUIlist.get(i).wallRenderer.draw(wallBatch);
            wallBatch.end();
        }
        stage.act(delta);
        stage.draw();
    }
    public void RoomInfoAssign() {
        MapObjects objects = tiledMap.getLayers().get("Rooms").getObjects();
        int currentIndexing = 0;
        for (int i = 3; i >= 0; i--) {
            for (int j = 0; j <= 3; j++) {
                Rectangle rect = ((RectangleMapObject) objects.get(currentIndexing++)).getRectangle();
                rooms[i][j] = new Room(rect.x, rect.y, rect.width, rect.height);
                System.out.println("Assigned Room: x=" + rect.x + ", y=" + rect.y + ", width=" + rect.width + ", height=" + rect.height);
            }
        }
    }

    public void InfoTest() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j <= 3; j++) {
                if (rooms[i][j] != null) {
                    System.out.println("Room at (" + i + ", " + j + "): x=" + rooms[i][j].x + ", y=" + rooms[i][j].y);
                } else {
                    System.out.println("Room at (" + i + ", " + j + ") is not initialized.");
                }
            }
        }
    }

    public void nextPlayer()    {
        if (++currentPLayerIndexing>=PlayerList.size()) {
            currentPLayerIndexing=0;
        }
        System.out.println("Next player is "+currentPLayerIndexing);
    }

    public void leftAnimation()     {
        Timer.schedule(new Timer.Task() {
            private float i = 1;
            @Override
            public void run() {
                PlayerList.get(currentPLayerIndexing).toogleDisplay();
                if (i <= 144) {
                    --PlayerList.get(currentPLayerIndexing).x;
                    ++i;
                } else {
                    PlayerList.get(currentPLayerIndexing).toogleDisplay();
                    nextPlayer();
                    PlayerList.get(currentPLayerIndexing).toogleDisplay();
                    this.cancel();
                }
                PlayerList.get(currentPLayerIndexing).toogleDisplay();
            }
        }, 0, 0.009f, 145);
    }

    public void rightAnimation()    {
        Timer.schedule(new Timer.Task() {
            private float i = 1;

            @Override
            public void run() {
                PlayerList.get(currentPLayerIndexing).toogleDisplay();
                if (i <= 144) {
                    ++PlayerList.get(currentPLayerIndexing).x;
                    ++i;
                } else {
                    PlayerList.get(currentPLayerIndexing).toogleDisplay();
                    nextPlayer();
                    PlayerList.get(currentPLayerIndexing).toogleDisplay();
                    this.cancel();
                }
                PlayerList.get(currentPLayerIndexing).toogleDisplay();
            }
        }, 0, 0.009f, 145);
    }

    public void downAnimation()     {
        Timer.schedule(new Timer.Task() {
            private float i = 1;

            @Override
            public void run() {
                PlayerList.get(currentPLayerIndexing).toogleDisplay();
                if (i <= 144) {
                    --PlayerList.get(currentPLayerIndexing).y;
                    ++i;
                } else {
                    PlayerList.get(currentPLayerIndexing).toogleDisplay();
                    nextPlayer();
                    PlayerList.get(currentPLayerIndexing).toogleDisplay();
                    this.cancel();
                }
                PlayerList.get(currentPLayerIndexing).toogleDisplay();
            }
        }, 0, 0.009f, 145);
    }

    public void upAnimation()   {
        Timer.schedule(new Timer.Task() {
            private float i = 1;

            @Override
            public void run() {
                PlayerList.get(currentPLayerIndexing).toogleDisplay();
                if (i <= 144) {
                    ++PlayerList.get(currentPLayerIndexing).y;
                    ++i;
                } else {
                    PlayerList.get(currentPLayerIndexing).toogleDisplay();
                    nextPlayer();
                    PlayerList.get(currentPLayerIndexing).toogleDisplay();
                    this.cancel();
                }
                PlayerList.get(currentPLayerIndexing).toogleDisplay();
            }
        }, 0, 0.009f, 145);

    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stage.getViewport().update(width, height, true);

        // Scale the button size and position based on screen dimensions
        float buttonWidth = width * 0.05f; // 5% of the screen width
        float buttonHeight = height * 0.05f; // 5% of the screen height
        leftButton.setSize(buttonWidth, buttonHeight);
        rightButton.setSize(buttonWidth,buttonHeight);
        downButton.setSize(buttonHeight,buttonWidth);
        upButton.setSize(buttonHeight,buttonWidth);
        QuestionButton.setSize(buttonWidth*3/5,buttonWidth*3/5);

        leftButton.setPosition(0.835f*width, 0.5f*height);
        rightButton.setPosition(0.935f*width,0.5f*height);
        upButton.setPosition(0.895f*width,0.575f*height);
        downButton.setPosition(0.895f*width,0.375f*height);
        QuestionButton.setPosition(0.893f*width,0.495f*height);
    }

    public void movementDisplay(int nextRow, int nextCol)   {

    }
    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        tiledMap.dispose();
        mapRenderer.dispose();
        for (int i=0;i<PlayerList.size();i++) {
            PlayerList.get(i).batch.dispose();
            PlayerList.get(i).texture.dispose();
        }
        for (int i=0;i<wallGUIlist.size();i++) {
            wallGUIlist.get(i).batch.dispose();
            wallGUIlist.get(i).texture.dispose();
        }
        stage.dispose();
        skin.dispose();

    }

    public void ReArrangePLayer(ArrayList<String> validPlayer)  {
        HashMap<String,Player> map = new HashMap<>();
        map.put("11",player1);
        map.put("17",player2);
        map.put("71",player3);
        map.put("77",player4);
        List<Player> tempPlayerArray = new ArrayList<>();
        for (int i=0;i<validPlayer.size();i++)     {
            System.out.println(validPlayer.get(i));
            tempPlayerArray.add( map.get(validPlayer.get(i)) );
        }
        currentPLayerIndexing=0;
        PlayerList = tempPlayerArray;
        for (int i=0;i<PlayerList.size();i++)     {

        }
    }

    public void addWall() {
        WallGUI NewWallGUI = new WallGUI((int)PlayerList.get(currentPLayerIndexing).x,(int)PlayerList.get(currentPLayerIndexing).y);
        test+=50;
        NewWallGUI.texture = sampleTexture;
        wallGUIlist.add(NewWallGUI);
        NewWallGUI.wallRenderer = new Sprite(NewWallGUI.texture);
    }
}

class Room {
    public float x, y, width, height;
    public Room(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


}

class Player {
    public Sprite animalRenderer;
    public SpriteBatch batch;
    public Texture texture;
    public boolean displayShape;
    public String id;
    public boolean userControlled;
    public float x;
    public float y;

    public float[] quarter;
    public int roomx;
    public int roomy;

    public int originalX;
    public int originalY;
    public Player(String id, int roomx, int roomy, float x, float y, int userControlled) {
        quarter = new float[2];
        this.x = x;
        this.y = y;
        originalX=(int)x;
        originalY=(int)y;
        this.id = id;
        if (userControlled==1) {
            this.userControlled = true;
        }
        else    {
            this.userControlled = false;
        }
        this.displayShape=true;
        this.roomx=roomx;
        this.roomy=roomy;
    }

    public void setQuarterPosition(float x, float y)    {
        this.x=this.x+x;
        this.y=this.y+y;
    }
    public void toogleDisplay()  {
        displayShape = !displayShape;
    }
}

class WallGUI {
    public String id;
    public Sprite wallRenderer;
    public SpriteBatch batch;
    public Texture texture;

    public int x;
    public int y;

    public WallGUI(int x, int y) {
        this.x=x;
        this.y=y;
        id = Integer.toString(x)+Integer.toString(y);
    }

}
