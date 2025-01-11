package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Random;
import java.util.Set;
import java.util.Arrays;


// import javax.swing.JPanel;


// import javafx.application.Platform;
// import javafx.scene.Scene;
// import javafx.stage.Stage;

// import Input.MouseInputs;

public class GameRunner{
    
    private static Player currentPlayer;
    private static int numberPlayers;
    private static int index = 0;
    private static ArrayList<Player> players = new ArrayList<Player>();
    private static Game game;
    private static Player winner;
	private static ArrayList<ArrayList<Integer>> playerStartPosition = new ArrayList<>();
	private static Scanner scanner;
	private static Random random;
    private static int MagicDoorCount;
    private static int MagicDoorThreshold;
    private static int playerIndex;
    // private MouseInputs mouseInputs;
    // private GamePanel gamePanel;
    // private Stage thisprimaryStage;
    private static int turnCount;
    private static int hubiMoveThreshold;
    private static ArrayList<int[]> MoreThan2PlayerRoom = new ArrayList<int[]>();

    public GameRunner(){
        scanner = new Scanner(System.in);
        random = new Random();

        initializePlayers();
        game = new Game(players);
        turnCount = 1;
        MagicDoorCount = 0;
        hubiMoveThreshold = game.HubiMoveThreshold();
        MagicDoorThreshold = game.MagicDoorThreshold();

        // gamePanel = new GamePanel(this);
        // game.setGamePanel(gamePanel);
        // gameWindow = new GameWindow(gamePanel);
        // mouseInputs = new MouseInputs(game, gamePanel);
        
    }

    // public GameRunner(Stage primaryStage){
    //     this.thisprimaryStage = primaryStage;
    //     initializePlayers();
    //     game = new Game(players);
    //     gamePanel = new GamePanel(this);
    //     // game.setGamePanel(gamePanel);
    //     // gameWindow = new GameWindow(gamePanel);
    //     // mouseInputs = new MouseInputs(game, gamePanel);
    //     game.setGamePanel(gamePanel);

    //     Scene scene = new Scene(gamePanel, 800, 600);
    //     thisprimaryStage.setScene(scene);
    //     thisprimaryStage.show();
    // }

    public static void initializePlayers() {
		
		System.out.print("Enter the number of players: ");
		numberPlayers = scanner.nextInt();
		
		inputNumberPlayer(numberPlayers);

        // players.add(new Player(Color.GREEN , "Rabbit"));
        // players.add(new Player(Color.RED, "Mouse"));
        // players.add(new Player(Color.BLUE, "Rabbit"));
        // players.add(new Player(Color.YELLOW, "Mouse"));
        // numberPlayers = players.size();
                    
        // int[][] playerStartPosition = {{1,1}, {1,7}, {7,1}, {7,7}};

        for(int i = 0; i < numberPlayers; i++){
            getPlayer(i).setLocation(playerStartPosition.get(i).get(0), playerStartPosition.get(i).get(1));
        }
    }

	public static void inputNumberPlayer(int numberPlayers){
		int option = random.nextInt(2);
		switch(numberPlayers){
			case 2:
				players.add(new Player(Color.GREEN, "Rabbit"));
				players.add(new Player(Color.YELLOW, "Rat"));

				playerStartPosition.add(new ArrayList<>(List.of(1, 1)));
				playerStartPosition.add(new ArrayList<>(List.of(7, 7)));
				break;
			case 3:
				if(option == 0){
					players.add(new Player(Color.GREEN , "Rabbit"));
					players.add(new Player(Color.RED, "Rat"));
					players.add(new Player(Color.BLUE, "Rabbit"));

					playerStartPosition.add(new ArrayList<>(List.of(1, 1)));
					playerStartPosition.add(new ArrayList<>(List.of(1, 7)));
					playerStartPosition.add(new ArrayList<>(List.of(7, 1)));
				}else{
					players.add(new Player(Color.RED, "Rat"));
					players.add(new Player(Color.BLUE, "Rabbit"));
					players.add(new Player(Color.YELLOW, "Rat"));

					playerStartPosition.add(new ArrayList<>(List.of(1, 7)));
					playerStartPosition.add(new ArrayList<>(List.of(7, 1)));
					playerStartPosition.add(new ArrayList<>(List.of(7, 7)));
				}
				break;

			case 4:
				players.add(new Player(Color.GREEN , "Rabbit"));
				players.add(new Player(Color.RED, "Rat"));
				players.add(new Player(Color.BLUE, "Rabbit"));
				players.add(new Player(Color.YELLOW, "Rat"));

				playerStartPosition.add(new ArrayList<>(List.of(1, 1)));
				playerStartPosition.add(new ArrayList<>(List.of(1, 7)));
				playerStartPosition.add(new ArrayList<>(List.of(7, 1)));
				playerStartPosition.add(new ArrayList<>(List.of(7, 7)));
				break;

			default:
				System.out.println("Invalid number of players!!!");
				break;
		}
	}

    public static void run() {
        scanner = new Scanner(System.in);
        game.initializeResource();
        playerIndex = 0;

        while (true) {
            System.out.println("Turn: " + turnCount);
            System.out.println("Player number " + (playerIndex + 1) + ": " + getPlayer(playerIndex).getType());
            System.out.println("Current location: " + "[" + getPlayer(playerIndex).getLocation()[0] + ", " + getPlayer(playerIndex).getLocation()[1] + "]" + "\n");

            System.out.print("Input player's action: ");
            String input = scanner.nextLine();
            actionOption(input);
            playerIndex = (playerIndex + 1) % numberPlayers;
            
            // Game.magicDoorFound(game.getWallType(), game.getWall());

            System.out.println("MagicDoorCount: " + MagicDoorCount);
            System.out.println("MagicDoorThreshold: " + MagicDoorThreshold);

            turnCount++;
            if(turnCount > hubiMoveThreshold){
                game.moveHubi();
                turnCount = 1;
            }

            if (MagicDoorCount >= MagicDoorThreshold) {
                game.setPhase();
                findHubiPhase();
                break;
            }
        }
    }

    public static void findHubiPhase(){
        System.out.println("*********************************\n");
        System.out.println("Entering findHubiPhase. Players need to search for Hubi.");


        int playerIndex = GameRunner.getPlayerIndex();

        // Check if the there are enough players in a same room with hubi at the beginning of the find Hubi Phase. If yes, set win to players, if not start the find Hubi Phase.
        if(winGame()){
            return;
        }
        while(true){
            System.out.println("Turn: " + turnCount);
            System.out.println("Player number " + (playerIndex + 1) + ": " + GameRunner.getPlayer(playerIndex).getType());
            System.out.println("Current location: " + "[" + GameRunner.getPlayer(playerIndex).getLocation()[0] + ", " + GameRunner.getPlayer(playerIndex).getLocation()[1] + "]" + "\n");

            System.out.print("Input player's action: ");
            String input = scanner.nextLine();
            actionOption(input);
            playerIndex = (playerIndex + 1) % numberPlayers;
            /*Check whether the user won the game or not */
            if(winGame()){
                break;
            }
            
            turnCount++;
            if(turnCount > hubiMoveThreshold){
                game.moveHubi();
                turnCount = 1;
            }
        }
    }

    public static void actionOption(String action){
        
        if(action.toLowerCase().equals("move")){
            System.out.print("Input the direction to move by clicking the arrow: ");
            /*
             * At this part code will call the GUI to take the input from the mouse click to the button!!!
             * 
             */
            String direction = scanner.nextLine();
            game.movePlayer(direction);
        }else if(action.toLowerCase().equals("ask")){
            game.ask();
        }else{
            System.out.println("Invalid action, please type the choose anther action!!");
            System.out.println("_________________________________");
            game.nextTurn();
        }
    }

    // public void movePlayer(String direction){
    //     game.movePlayer(direction);
    // }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }
    
    public static void nextPlayer() {
        currentPlayer = getPlayer((index + 1) % numberPlayers);
        index = (index + 1) % numberPlayers;
    }
    
    public static void setFirstPlayer() {
        currentPlayer = getPlayer(0);
    }
    
    // public static void setWinner(Player p) {
    //     winner = p;
    // }
    
    // public static Player getWinner() {
    //     return winner;
    // }

    
    public static int getNumbPlayers() {
        return numberPlayers;
    }
    
    public static Player getPlayer(int i) {
        return players.get(i);
    }

    public static int getPlayerIndex(){
        return playerIndex;
    }

    


    public static void incrementMagicDoorCount() {
        MagicDoorCount++;
        System.out.println("MagicDoorCount incremented to: " + MagicDoorCount);
    }

    // public void checkForMagicDoor(){
    //     magicDoorFound = magicDoorFound(String wallType);
    //     if(magicDoorFound){
    //         incrementMagicDoorCount();
    //     }

    public static boolean winGame(){
        for (int[] room : MoreThan2PlayerRoom){
            if(game.isHubiInRoom(room)){
                System.out.println("Players win the game. Congratulations!!!");
                return true;
            }
        }
        return false;
    }

    public static void updateMoreThan2PlayerRoom(int[] prevRoom, int[] currentRoom){
        if(containsRoom(MoreThan2PlayerRoom, prevRoom)){
            if (getNumberOfPlayersInRoom(prevRoom) >= 2){
                // Do nothing
            } else{
                removeRoom(MoreThan2PlayerRoom, prevRoom);
            }
        }

        if(containsRoom(MoreThan2PlayerRoom, currentRoom)){
            // Do nothing
        }else{
            if (getNumberOfPlayersInRoom(currentRoom) >= 2){
                addRoom(MoreThan2PlayerRoom, currentRoom);
            } else{
                // Do nothing
            }
        }
    }

    public static void addRoom(List<int[]> roomList, int[] room){
        roomList.add(room);
    }

    public static void removeRoom(List<int[]> roomList, int[] room){
        roomList.removeIf(r -> Arrays.equals(r, room));
    }

    public static boolean containsRoom(List<int[]> roomList, int[] room){
        for (int[] roomInList : roomList){
            if (Arrays.equals(roomInList, room)){
                return true;
            }
        }
        return false;
    }

    public static int getNumberOfPlayersInRoom(int[] room){
        int count = 0;
        for (Player player : players){
            if(Arrays.equals(player.getLocation(), room)){
                count ++;
            }
        }
        return count;
    }

    public static ArrayList<int[]> getMoreThan2PlayeArrayList(){
        return MoreThan2PlayerRoom;
    }
}