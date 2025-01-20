package Schanppt_Hubi.Structure.Logic.game;

import Schanppt_Hubi.Structure.OutputCapture;

import java.awt.*;
import java.util.List;
import java.util.*;



    public class GameRunner{
        private static OutputCapture outputCapture;
        private static Player currentPlayer;
        private static int numberPlayers;
        private static int index = 0;
        private static ArrayList<Player> players = new ArrayList<Player>();
        public static Game game;
        private static Player winner;
        private static ArrayList<ArrayList<Integer>> playerStartPosition = new ArrayList<>();
        private static Scanner scanner;
        private static Random random;
        private static int MagicDoorCount;
        private static int MagicDoorThreshold;
        private static int playerIndex;

        private static int turnCount;
        private static int hubiMoveThreshold;
        private static ArrayList<int[]> MoreThan2PlayerRoom = new ArrayList<int[]>();

        public GameRunner(OutputCapture outputCapture){
            this.outputCapture = outputCapture;
            scanner = new Scanner(System.in);
            random = new Random();

            // game = new Game(players);
            turnCount = 1;
            MagicDoorCount = 0;

            // initializePlayers();
            // game = new Game(players);
            // turnCount = 1;
            // MagicDoorCount = 0;
            // hubiMoveThreshold = game.HubiMoveThreshold();
            // MagicDoorThreshold = game.MagicDoorThreshold();

            // gamePanel = new GamePanel(this);
            // game.setGamePanel(gamePanel);
            // gameWindow = new GameWindow(gamePanel);
            // mouseInputs = new MouseInputs(game, gamePanel);

        }

        public static ArrayList<String> getAllPlayersPosition(){
            ArrayList<String> positionsList = new ArrayList<>();
            String position;
            for(int i = 0; i < numberPlayers; i++){
                position = String.format("%d %d", getPlayer(i).getLocation()[0], getPlayer(i).getLocation()[1]);
                positionsList.add(position);
            }
            return positionsList;
        }

        public static void initializePlayers(int numPlayers) {
            numberPlayers = numPlayers;
            inputNumberPlayer(numberPlayers);
            for(int i = 0; i < numberPlayers; i++){
                getPlayer(i).setLocation(playerStartPosition.get(i).get(0), playerStartPosition.get(i).get(1));
            }
        }

        public static void inputNumberPlayer(int numberPlayers) {
            int option = random.nextInt(2);
            switch (numberPlayers) {
                case 2:
                    players.add(new Player(Color.GREEN, "Rabbit"));
                    players.add(new Player(Color.YELLOW, "Rat"));

                    playerStartPosition.add(new ArrayList<>(Arrays.asList(1, 1)));
                    playerStartPosition.add(new ArrayList<>(Arrays.asList(7, 7)));
                    break;
                case 3:
                    if (option == 0) {
                        players.add(new Player(Color.GREEN, "Rabbit"));
                        players.add(new Player(Color.RED, "Rat"));
                        players.add(new Player(Color.BLUE, "Rabbit"));

                        playerStartPosition.add(new ArrayList<>(Arrays.asList(1, 1)));
                        playerStartPosition.add(new ArrayList<>(Arrays.asList(1, 7)));
                        playerStartPosition.add(new ArrayList<>(Arrays.asList(7, 1)));
                    } else {
                        players.add(new Player(Color.RED, "Rat"));
                        players.add(new Player(Color.BLUE, "Rabbit"));
                        players.add(new Player(Color.YELLOW, "Rat"));

                        playerStartPosition.add(new ArrayList<>(Arrays.asList(1, 7)));
                        playerStartPosition.add(new ArrayList<>(Arrays.asList(7, 1)));
                        playerStartPosition.add(new ArrayList<>(Arrays.asList(7, 7)));
                    }
                    break;

                case 4:
                    players.add(new Player(Color.GREEN, "Rabbit"));
                    players.add(new Player(Color.RED, "Rat"));
                    players.add(new Player(Color.BLUE, "Rabbit"));
                    players.add(new Player(Color.YELLOW, "Rat"));

                    playerStartPosition.add(new ArrayList<>(Arrays.asList(1, 1)));
                    playerStartPosition.add(new ArrayList<>(Arrays.asList(1, 7)));
                    playerStartPosition.add(new ArrayList<>(Arrays.asList(7, 1)));
                    playerStartPosition.add(new ArrayList<>(Arrays.asList(7, 7)));
                    break;

                default:
                    System.out.println("Invalid number of players!!!");
                    break;
            }
        }

        public static void setUpNumber_Level(){
            game = new Game(players);
            hubiMoveThreshold = game.HubiMoveThreshold();
            MagicDoorThreshold = game.MagicDoorThreshold();
            scanner = new Scanner(System.in);
            for(String position : getAllPlayersPosition()){
                System.out.println(position);
            }
            game.initializeResource();
        }

        public static void run() {
            scanner = new Scanner(System.in);
            game.initializeResource();
            playerIndex = 0;
        }

        public int getHubiMoveThreshold()   {
            return hubiMoveThreshold;
        }

        public static boolean canFindHubi()     {
            if (MagicDoorCount>=MagicDoorThreshold)   {
                return true;
            }
            else { return false;}
        }
        public static void findHubiPhase(){
            System.out.println("*********************************\n");
            System.out.println("Entering findHubiPhase. Players need to search for Hubi.");


            int playerIndex = GameRunner.getPlayerIndex();

            // Check if the there are enough players in a same room with hubi at the beginning of the find Hubi Phase. If yes, set win to players, if not start the find Hubi Phase.


        }
        public static void actionOption(String action){
            outputCapture.startCapture();
            if(!action.toLowerCase().equals("ask")){
                game.movePlayer(action);
            }else if(action.toLowerCase().equals("ask")){
                outputCapture.stopCapture();
                outputCapture.startCapture();
                game.ask(outputCapture);

            }else{
                System.out.println("Invalid action, please type the choose anther action!!");
                System.out.println("_________________________________");
                game.nextTurn();
            }
            outputCapture.stopCapture();
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
        public Game getGame(){
            return game;
        }
    }
