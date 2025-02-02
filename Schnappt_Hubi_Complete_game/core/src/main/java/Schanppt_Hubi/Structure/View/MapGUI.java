package Schanppt_Hubi.Structure.View;

import Schanppt_Hubi.Structure.Flow.FlowThread;
import Schanppt_Hubi.Structure.Main;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    private SpriteBatch GeneralBatch;

    private HashMap<String,Texture> curPlayerIndicator;

    private Sprite playerIndicator;
    public TextDisplay textDisplay;
    private Music music;
    public MapGUI(final Main game, FlowThread fl) {
        curPlayerIndicator = new HashMap<>();
        playerIndicator = new Sprite(new Texture("Indicator/Yellow_neon.jpg"));
        GeneralBatch = new SpriteBatch();
        wallBatch = new SpriteBatch();
        wallGUIlist = new ArrayList<>();
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
        music = Gdx.audio.newMusic(Gdx.files.internal("Game-song.mp3"));
        music.setVolume(0.75f);

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


        WallTextureList = new ArrayList<>();
        createTexture();
        fl.currentTurnState=1;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json")); // Make sure to have a skin file in your assets


        textDisplay = new TextDisplay(stage, skin);
        // Display a message for 5 seconds
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
        QuestionButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (ButtonEnabled) {
                    ButtonEnabled=false;
                    fl.currentTurnState=0;
                    fl.returnData = "Ask";
                    System.out.println("Asking");
                    ButtonEnabled=true;
                }
            }
        });
        stage.addActor(QuestionButton);
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }

    public void createTexture()    {
        String[] characterSkinDir = {"Character_skin/YellowGuy.png","Character_skin/BlueGuy.png","Character_skin/RedGuy.png","Character_skin/GreenGuy.png"};
        for (int i=0;i<PlayerList.size();i++)   {
            PlayerList.get(i).batch=new SpriteBatch();
            PlayerList.get(i).texture=new Texture(characterSkinDir[i]);
            PlayerList.get(i).animalRenderer=new Sprite(PlayerList.get(i).texture);
            WallTextureList.add(new Texture("Wall/blockwald.png"));
            WallTextureList.add(new Texture("Wall/freiwald.png"));
            WallTextureList.add(new Texture("Wall/magikwald.png"));
            WallTextureList.add(new Texture("Wall/mauswald.png"));
            WallTextureList.add(new Texture("Wall/hasewald.png"));

            curPlayerIndicator.put("71",new Texture("Indicator/Blue_neon.jpg"));
            curPlayerIndicator.put("11",new Texture("Indicator/Green_neon.jpg"));
            curPlayerIndicator.put("17",new Texture("Indicator/Red_neon.jpg"));
            curPlayerIndicator.put("77",new Texture("Indicator/Yellow_neon.jpg"));
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
            if (wallGUIlist.get(i) !=null) {
                wallGUIlist.get(i).wallRenderer.setPosition(wallGUIlist.get(i).x, wallGUIlist.get(i).y);
                wallGUIlist.get(i).wallRenderer.setSize(wallGUIlist.get(i).width, wallGUIlist.get(i).height);
                wallGUIlist.get(i).wallRenderer.draw(wallBatch);
                wallBatch.end();
            }
        }

        GeneralBatch.setProjectionMatrix(camera.combined);
        GeneralBatch.begin();
        playerIndicator.setPosition(735,700);
        playerIndicator.setSize(85,85);
        playerIndicator.draw(GeneralBatch);
        GeneralBatch.end();


        stage.act(delta);
        stage.draw();
    }
    public void ChangeIndicator()   {
        String currentId = PlayerList.get(currentPLayerIndexing).id;
        playerIndicator = new Sprite(curPlayerIndicator.get(currentId));
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
        ChangeIndicator();
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
                    PlayerList.get(currentPLayerIndexing).updateOrginalPos(-144,0);
                    PlayerList.get(currentPLayerIndexing).toogleDisplay();
                    System.out.println("Left");
                    nextPlayer();
                    PlayerList.get(currentPLayerIndexing).toogleDisplay();
                    ButtonEnabled=true;
                    System.out.println("Button enabled");
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
                    PlayerList.get(currentPLayerIndexing).updateOrginalPos(144,0);
                    PlayerList.get(currentPLayerIndexing).toogleDisplay();
                    System.out.println("Right");
                    nextPlayer();
                    PlayerList.get(currentPLayerIndexing).toogleDisplay();
                    ButtonEnabled=true;
                    System.out.println("Button enabled");
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
                    PlayerList.get(currentPLayerIndexing).updateOrginalPos(0,-144);
                    PlayerList.get(currentPLayerIndexing).toogleDisplay();
                    System.out.println("Down");
                    nextPlayer();
                    PlayerList.get(currentPLayerIndexing).toogleDisplay();
                    ButtonEnabled=true;
                    System.out.println("Button enabled");
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
                    PlayerList.get(currentPLayerIndexing).updateOrginalPos(0,144);
                    PlayerList.get(currentPLayerIndexing).toogleDisplay();
                    System.out.println("Up");
                    nextPlayer();
                    PlayerList.get(currentPLayerIndexing).toogleDisplay();
                    ButtonEnabled=true;
                    System.out.println("Button enabled");
                    this.cancel();
                }
                PlayerList.get(currentPLayerIndexing).toogleDisplay();
            }
        }, 0, 0.009f, 145);

    }

    public void ExitApp()   {
        Gdx.app.exit();
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
    public void show() {
        playBackgroundMusic();
    }

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            if (tiledMap != null) {
                tiledMap.dispose();
            }
            if (mapRenderer != null) {
                mapRenderer.dispose();
            }
            for (int i = 0; i < PlayerList.size(); i++) {
                Player player = PlayerList.get(i);
                if (player != null) {
                    player.batch.dispose();
                    player.texture.dispose();
                }
            }
            for (int i = 0; i < wallGUIlist.size(); i++) {
                WallGUI wallGUI = wallGUIlist.get(i);
                if (wallGUI != null) {
                    wallGUI.batch.dispose();
                    wallGUI.texture.dispose();
                }
            }
            if (GeneralBatch != null) {
                GeneralBatch.dispose();
            }
            if (playerIndicator != null && playerIndicator.getTexture() != null) {
                playerIndicator.getTexture().dispose();
            }
            if (stage != null) {
                stage.dispose();
            }
            if (skin != null) {
                skin.dispose();
            }
        } else {
            Gdx.app.error("OpenGL context is not active", "Cannot dispose of OpenGL resources without an active context.");
        }
    }


    public void ReArrangePLayer(ArrayList<String> validPlayer)  {
        HashMap<String,Player> map = new HashMap<>();
        map.put("1 1",player1);
        map.put("1 7",player2);
        map.put("7 1",player3);
        map.put("7 7",player4);
        List<Player> tempPlayerArray = new ArrayList<>();
        for (int i=0;i<validPlayer.size();i++)     {
            System.out.println(validPlayer.get(i));
            tempPlayerArray.add( map.get(validPlayer.get(i)) );
        }
        currentPLayerIndexing=0;
        PlayerList = tempPlayerArray;
        ChangeIndicator();
    }
//Do this before performing "moving" animation
    public void addWall(String wallType) {
        WallGUI NewWallGUI = new WallGUI((int)PlayerList.get(currentPLayerIndexing).originalX,(int)PlayerList.get(currentPLayerIndexing).originalY);
        System.out.println( "Original coord is "+(int)PlayerList.get(currentPLayerIndexing).originalX+" "+(int)PlayerList.get(currentPLayerIndexing).originalY );
        HashMap<String,Texture> WallMap = new HashMap<>();
        WallMap.put("block",WallTextureList.get(0));
        WallMap.put("free",WallTextureList.get(1));
        WallMap.put("magic",WallTextureList.get(2));
        WallMap.put("rat",WallTextureList.get(3));
        WallMap.put("rabbit",WallTextureList.get(4));
        System.out.println("Wall type is "+wallType);
        NewWallGUI.texture = WallMap.get(wallType.toLowerCase());
        if (fl.returnData.equalsIgnoreCase("left"))     {
            NewWallGUI.modifyActualPosition(-32,0);
            NewWallGUI.width=32;
            NewWallGUI.height=112;
        }
        else if (fl.returnData.equalsIgnoreCase("right"))     {
            NewWallGUI.modifyActualPosition(112,0);
            NewWallGUI.width=32;
            NewWallGUI.height=112;
        }
        else if (fl.returnData.equalsIgnoreCase("up"))     {
            NewWallGUI.modifyActualPosition(0,112);
            NewWallGUI.width=112;
            NewWallGUI.height=32;
        }
        else if (fl.returnData.equalsIgnoreCase("down"))     {
            NewWallGUI.modifyActualPosition(0,-32);
            NewWallGUI.width=112;
            NewWallGUI.height=32;
        }
        wallGUIlist.add(NewWallGUI);
        NewWallGUI.wallRenderer = new Sprite(NewWallGUI.texture);
    }

    public int getScreenWidth()     {
        return Gdx.graphics.getWidth();
    }
    public int getScreenHeight()    {
        return Gdx.graphics.getHeight();
    }

    public void playBackgroundMusic(){
        if (!music.isPlaying()) {
            music.setLooping(true);  // Loop the music
            music.play();  // Start playing the music
        }

    }
    public void stopBackgroundMusic() {
        // Stop the music when it's not needed
        if (music.isPlaying()) {
            music.stop();
        }
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
    public void updateOrginalPos(int inputX, int inputY)  {
        originalX+=inputX;
        originalY+=inputY;
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
    public int width;
    public int height;
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
    public void modifyActualPosition(int additionalX, int additionalY)  {
        x+=additionalX;
        y+=additionalY;
    }
}
