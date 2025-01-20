package Schanppt_Hubi.Structure.Logic.game;

import java.util.Scanner;
import java.util.Set;
import java.util.Arrays;
import java.awt.Color;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.HashMap;

import Schanppt_Hubi.Structure.Logic.map.House;
import Schanppt_Hubi.Structure.Logic.map.Map;
import Schanppt_Hubi.Structure.Logic.map.Wall;
import Schanppt_Hubi.Structure.OutputCapture;

public class Game{
    private ArrayList<Player> players;
    private static Hubi hubi;
    private static Map map;
    private static int room;
    private static House[][] house;
    private static String direction;
    private static Player currentPlayer;
    private static Scanner scanner;
    // private KeyboardInputs keyboardInputs;
    private static Random random;
    private static String level;
    private static final Set<String> VALID_LEVELS = new HashSet<>();
    private static String wallType;
    private static String answerType[] = {"MultipleChoice", "True answer", "Wrong answer"};
    private static ArrayList<int[]> MagicDoorList = new ArrayList<int[]>();
    private static int[][] MagicDoors;
    private static int[] wall;
    private static ArrayList<int[]> allWallsList = new ArrayList<int[]>();
    private static ArrayList<int[]> allRoomsList = new ArrayList<int[]>();
    private static int phase;
    private static HashMap<String, String> mappingNPCname;
    public Game(ArrayList<Player> givenPlayers){
        // if (givenPlayers.size() < 2 || givenPlayers.size() > 4)
        // 	throw new IllegalArgumentException("Game must be played with 2 to 4 players");

        // ArrayList<String> types = new ArrayList<String>();
        // for (Player player : givenPlayers) {
        // 	types.add(player.getType());
        // }
        // for (String type : types) {
        // 	if (Collections.frequency(types, type) > 1)
        // 		throw new IllegalArgumentException("Players must have different types");
        // }
        // keyboardInputs = new KeyboardInputs(this);
        // addKeyListener(keyboardInputs);
        random = new Random();
        scanner = new Scanner(System.in);
        VALID_LEVELS.add("easy");
        VALID_LEVELS.add("medium");
        VALID_LEVELS.add("hard");
        Collections.shuffle(givenPlayers);
        players = givenPlayers;
        // board = new Board();
        // deck = new Deck();
        GameRunner.setFirstPlayer();
        hubi = new Hubi();
        phase = 0;
    }

    public static void setPhase(){
        phase = 1;
    }

    public static int MagicDoorThreshold(){
        if(level.equals("easy")){
            return 1;
        }else if(level.equals("medium")){
            return 2;
        }else{
            return 3;
        }
    }

    public static int HubiMoveThreshold() {
        if(level.equals("easy")){
            return 10;
        }else if(level.equals("medium")){
            return 8;
        }else{
            return 5;
        }
    }

    public static void initializeResource()    {
        map = new Map();
        mappingNPCname = map.getMappingNPCname();
        house = map.getHouse();
        map.generateMap(MagicDoorThreshold());

        map.collectMagicDoors();
        MagicDoorList = map.getMagicLists();
        MagicDoors = map.getMagicDoors();

        map.collectAllWallLocations();
        // map.printAllWallLocations();
        allWallsList = map.getAllWallsLocation();

        map.collectAllRoomLocations();
        // map.printAllRoomLocations();
        allRoomsList = map.getAllRoomsLocation();

        setHubiInitialPosition();

        map.Display();
        map.printMagicWalls();

    }

    public static void movePlayer(String direction){
        currentPlayer = GameRunner.getCurrentPlayer();
        int[] currentLocation = currentPlayer.getLocation();
        int[] newLocation = currentLocation.clone();
        //System.out.println("Initial location is " + currentLocation[0] + currentLocation[1]);
        switch (direction.toLowerCase()){
            case ("right"):
                newLocation[1] += 2;
                break;
            case "left":
                newLocation[1] -= 2;
                break;
            case "up":
                newLocation[0] -= 2;
                break;
            case "down":
                newLocation[0] += 2;
                break;
        }

        int wallRow = (currentLocation[0] + newLocation[0]) / 2;
        int wallCol = (currentLocation[1] + newLocation[1]) / 2;

        wall = new int[]{wallRow, wallCol};

        wallType = house[wallRow][wallCol].getType();
        // house[wallRow][wallCol].setWallState();
        System.out.println(wallType);
        // System.out.println("Player's type: " + currentPlayer.getType());

        if(wallType.equals(currentPlayer.getType()) || wallType.equals("Free") || wallType.equals("Magic")){
            System.out.println("The move is valid");
            currentPlayer.updateLocation(newLocation[0], newLocation[1]);
        }else{
            System.out.println("Sorry, the move is invalid!!!");
        }

        System.out.println("Updated Location: " + "[" + currentPlayer.getLocation()[0] + ", " +currentPlayer.getLocation()[1] + "]" + "\n");
        System.out.println("_________________________________\n");
        // GameRunner.winGame(currentLocation, newLocation);
        if(phase != 1){
            magicDoorFound(wallType, wall);
        }
        GameRunner.updateMoreThan2PlayerRoom(currentLocation, newLocation);
        nextTurn();
    }

    // public static int[] getWall(){
    //     return wall;
    // }

    // public static String getWallType(){
    //     return wallType;
    // }

    public static void moveHubi(){

        // int[] currentLocation = hubi.getLocation();
        // System.out.println("The Hubi's initial location: " + "[" + currentLocation[0] + ", " + currentLocation[1] + "]");


        // int randomRoomX = random.nextInt(1,8);
        // int randomRoomY = random.nextInt(1,8);
        // hubi.setLocation(randomRoomX, randomRoomY);
        // if(GameRunner.containsRoom(GameRunner.getMoreThan2PlayeArrayList(),hubi.getLocation()) || randomRoomX % 2 == 0 || randomRoomY % 2 == 0){
        //     moveHubi();
        // }

        // System.out.println("+++++++++++++++++++++++++++++++++++");
        // System.out.println("Hubi is moving to a random room...");
        // System.out.println("Hubi moved to location: [" + randomRoomX + ", " + randomRoomY + "]");
        // System.out.println("+++++++++++++++++++++++++++++++++++");

        int[] HubiRandomRoom = allRoomsList.get(random.nextInt(allRoomsList.size()));

        if(GameRunner.containsRoom(GameRunner.getMoreThan2PlayeArrayList(),HubiRandomRoom)){
            moveHubi();
        }else{
            hubi.setLocation(HubiRandomRoom[0], HubiRandomRoom[1]);
        }

        System.out.println("+++++++++++++++++++++++++++++++++++");
        System.out.println("Hubi is moving to a random room...");
        System.out.println("Hubi moved to location: [" + hubi.getLocation()[0] + ", " + hubi.getLocation()[1] + "]");
        System.out.println("+++++++++++++++++++++++++++++++++++");

    }


    public static String getLevel() {

        String inputlevel = "";

        while (true) {
            System.out.print("Choose a level to start the game (easy, medium, hard): ");
            inputlevel = scanner.next().trim().toLowerCase();

            if (VALID_LEVELS.contains(inputlevel)) {
                level = inputlevel;
                return level;
            } else {
                System.out.println("Invalid level. Please choose again.");
            }
        }
    }

    public static void ask(OutputCapture outputCapture){
        System.out.println("asking command");
        outputCapture.stopCapture();
        currentPlayer = GameRunner.getCurrentPlayer();
        if (MagicDoorList.isEmpty()) {
            outputCapture.startCapture();
            askHubi();
            outputCapture.stopCapture();
            return;
        }
        outputCapture.startCapture();
        switch(level){
            case "easy":
                generateTrueAnswer();
                break;
            case "medium":
                String mediumAnswerType = answerType[random.nextInt(2)];
                if(mediumAnswerType.equals("True answer")){
                    generateTrueAnswer();
                }else {
                    generateMultipleChoice();
                }
                break;

            case "hard":
                String hardAnswerType = answerType[random.nextInt(3)];
                if(hardAnswerType.equals("MultipleChoice")){
                    generateMultipleChoice();
                }else if(hardAnswerType.equals("True answer")){
                    generateTrueAnswer();
                    System.out.println("But this could be a lie. Be careful!!!");
                }else{
                    generateWrongAnswer();
                    System.out.println("But this could be a lie. Be carful!!!");
                }
                break;
        }
        outputCapture.stopCapture();
        nextTurn();
    }

    public static void generateTrueAnswer(){
        //System.out.println("All Wall Locations:");
        int[] hiddenMagicDoor = MagicDoorList.get(random.nextInt(MagicDoorList.size()));
        //System.out.println("Hidden Magic Door: " + Arrays.toString(hiddenMagicDoor));
        int[][] neighbors = ((Wall)house[hiddenMagicDoor[0]][hiddenMagicDoor[1]]).findNeighbors(house,hiddenMagicDoor[0], hiddenMagicDoor[1]);
        // for(int[] neighbor : neighbors){
        //     System.out.println("[" + neighbor[0] + ", " + neighbor[1] + "]");
        // }
        //System.out.println("Answer Type: True answer");
        //System.out.println("The magic door is between room [" + neighbors[0][0] + ", " + neighbors[0][1] + "] and room [" + neighbors[1][0] + ", " + neighbors[1][1] + "]\n");
        System.out.println("The magic door is between room [" + mappingNPCname.get(house[neighbors[0][0]][neighbors[0][1]].getType()) + "] and room [" + mappingNPCname.get(house[neighbors[1][0]][neighbors[1][1]].getType()) + "]");
    }

    public static void generateWrongAnswer(){
        ArrayList<int[]> choices = allWallsList;
        removeMagicDoorsFromAllWalls(choices, MagicDoorList);
        Collections.shuffle(choices);
        int[] chosenWall = choices.get(random.nextInt(choices.size()));
        int[][] neighbors = ((Wall)house[chosenWall[0]][chosenWall[1]]).findNeighbors(house,chosenWall[0], chosenWall[1]);
        System.out.println("The fake wall is: [" + chosenWall[0] + ", " + chosenWall[1] + "]");

        System.out.println("The fake magic door is between room [" + neighbors[0][0] + ", " + neighbors[0][1] + "] and room [" + neighbors[1][0] + ", " + neighbors[1][1] + "]");

        System.out.println("The fake magic door is between room [" + mappingNPCname.get(house[neighbors[0][0]][neighbors[0][1]].getType()) + "] and room [" + mappingNPCname.get(house[neighbors[1][0]][neighbors[1][1]].getType()) + "]");
    }

    public static void generateMultipleChoice(){
        int[] hiddenMagicDoor = MagicDoorList.get(random.nextInt(MagicDoorList.size()));
        System.out.println("Hidden Magic Door: " + Arrays.toString(hiddenMagicDoor));
        System.out.println("The magic door could be: ");
        ArrayList<int[]> choices = new ArrayList<>(allWallsList);
        removeMagicDoorsFromAllWalls(choices, MagicDoorList);
        Collections.shuffle(choices);
        choices = new ArrayList<>(choices.subList(0,2));
        choices.add(hiddenMagicDoor);
        Collections.shuffle(choices);
        for(int[] choice : choices){
            int[][] neighbors = ((Wall)house[choice[0]][choice[1]]).findNeighbors(house,choice[0], choice[1]);
            System.out.println("  between room [" + neighbors[0][0] + ", " + neighbors[0][1] + "] and room [" + neighbors[1][0] + ", " + neighbors[1][1] + "]" );
            System.out.println("  between room [" + mappingNPCname.get(house[neighbors[0][0]][neighbors[0][1]].getType()) + "] and room [" + mappingNPCname.get(house[neighbors[1][0]][neighbors[1][1]].getType()) + "]\n");
        }
    }


    public static void askHubi(){
        currentPlayer = GameRunner.getCurrentPlayer();
        int[] hubiCurrentLocation = hubi.getLocation();
        //System.out.println("Hubi's current location: " + Arrays.toString(hubiCurrentLocation));
        // System.out.println("Hubi's current location: [" + hubiCurrentLocation[0] + ", " + hubiCurrentLocation[0] + "]");
        switch(level){
            case "easy":
                generateTrueAnswerHubi(hubiCurrentLocation);
                break;
            case "medium":
                String mediumAnswerType = answerType[random.nextInt(2)];
                System.out.println("Answer Type: " + mediumAnswerType);
                if(mediumAnswerType.equals("True answer")){
                    generateTrueAnswerHubi(hubiCurrentLocation);
                }else {
                    generateMultipleChoiceHubi(hubiCurrentLocation);
                }
                break;

            case "hard":
                String hardAnswerType = answerType[random.nextInt(3)];
                System.out.println("Answer Type: " + hardAnswerType);
                if(hardAnswerType.equals("MultipleChoice")){
                    generateMultipleChoiceHubi(hubiCurrentLocation);
                }else if(hardAnswerType.equals("True answer")){
                    generateTrueAnswerHubi(hubiCurrentLocation);
                    System.out.println("But this could be a lie. Be careful!!!");
                }else{
                    generateWrongAnswerHubi(hubiCurrentLocation);
                    System.out.println("But this could be a lie. Be careful!!!");
                }
                break;
        }
        //System.out.println("Updated Location: " + "[" + currentPlayer.getLocation()[0] + ", " + currentPlayer.getLocation()[1] + "]" + "\n");
        //System.out.println("_________________________________\n");
        nextTurn();
    }


    public static void generateTrueAnswerHubi(int[] hubiCurrentLocation){
        // int[][] neighbors = ((Room)house[hubiCurrentLocation[0]][hubiCurrentLocation[1]]).findNeighbors(house,hubiCurrentLocation[0], hubiCurrentLocation[1]);
        // for(int[] neighbor : neighbors){
        //     System.out.println("[" + neighbor[0] + ", " + neighbor[1] + "]");
        // }
        // System.out.println("Answer Type: True answer");
        // System.out.println("Hubi is between room [" + neighbors[0][0] + ", " + neighbors[0][1] + "] and room [" + neighbors[1][0] + ", " + neighbors[1][1] + "]");
        System.out.println("Hubi's current location: " + Arrays.toString(hubiCurrentLocation));
        System.out.println("Hubi is currently in room: [" + mappingNPCname.get(house[hubiCurrentLocation[0]][hubiCurrentLocation[1]].getType()) + "]");
    }

    public static void generateWrongAnswerHubi(int[] hubiCurrentLocation){
        ArrayList<int[]> choices = allRoomsList;
        choices.remove(hubiCurrentLocation);
        Collections.shuffle(choices);
        int[] chosenRoom = choices.get(random.nextInt(choices.size()));
        // int[][] neighbors = ((Room)house[chosenRoom[0]][chosenRoom[1]]).findNeighbors(house,chosenRoom[0], chosenRoom[1]);
        System.out.println("The fake Hubi's location is: [" + chosenRoom[0] + ", " + chosenRoom[1] + "]");
        // System.out.println("Hubi is between room [" + neighbors[0][0] + ", " + neighbors[0][1] + "] and room [" + neighbors[1][0] + ", " + neighbors[1][1] + "]");
        System.out.println("Hubi is currently in room: [" + mappingNPCname.get(house[chosenRoom[0]][chosenRoom[1]].getType()) + "]");
    }

    public static void generateMultipleChoiceHubi(int[] hubiCurrentLocation){
        ArrayList<int[]> choices = allRoomsList;
        choices.remove(hubiCurrentLocation);
        System.out.println("Hubi could be: ");
        Collections.shuffle(choices);
        choices = new ArrayList<>(choices.subList(0,2));
        choices.add(hubiCurrentLocation);
        Collections.shuffle(choices);
        for(int[] choice : choices){
            // int[][] neighbors = ((Room)house[choice[0]][choice[1]]).findNeighbors(house,choice[0], choice[1]);
            // System.out.println("between room [" + neighbors[0][0] + ", " + neighbors[0][1] + "] and room [" + neighbors[1][0] + ", " + neighbors[1][1] + "]" );
            System.out.println("  in room: " + Arrays.toString(choice));
            System.out.println("  in room: [" + mappingNPCname.get(house[hubiCurrentLocation[0]][hubiCurrentLocation[1]].getType()) + "]\n");
        }
    }

    public static void removeMagicDoorsFromAllWalls(ArrayList<int[]> allWalls, ArrayList<int[]> MagicDoors){
        allWalls.removeIf(aWall -> containsWall(MagicDoors, aWall));
    }


    public static boolean containsWall(ArrayList<int[]> MagicDoors, int[] aWall){
        for(int[] door : MagicDoors){
            if(Arrays.equals(door, aWall)){
                return true;
            }
        }
        return false;
    }

    public static void nextTurn(){
        GameRunner.nextPlayer();
        Player nextPlayer = GameRunner.getCurrentPlayer();
    }

    public static void setHubiInitialPosition(){
        // int randomRoomX = random.nextInt(1,8);
        // int randomRoomY = random.nextInt(1,8);
        // if(randomRoomX % 2 != 0 && randomRoomY % 2 != 0){
        //     hubi.setLocation(randomRoomX, randomRoomY);
        //     System.out.println("Hubi's initial location: [" + randomRoomX + ", " + randomRoomY + "]");
        // }else{
        //     setHubiInitialPosition();
        // }

        int[] HubiRandomRoom = allRoomsList.get(random.nextInt(allRoomsList.size()));
        hubi.setLocation(HubiRandomRoom[0], HubiRandomRoom[1]);
        System.out.println("Hubi's initial location: [" + HubiRandomRoom[0] + ", " + HubiRandomRoom[1] + "]");
    }

    public static boolean isHubiInRoom(int[] room){
        int[] hubiLocation = hubi.getLocation();
        return Arrays.equals(hubiLocation, room);
    }

    public static void magicDoorFound(String wallType, int[] wallCoordinate){
        if (wallType.equals("Magic")){
            for(int[] magicWall : MagicDoorList){
                if(Arrays.equals(magicWall, wallCoordinate)){
                    GameRunner.incrementMagicDoorCount();
                    MagicDoorList.remove(magicWall);
                    break;
                }
            }
        }
    }

    public static String setLevel(String inputLevel) {
        VALID_LEVELS.add("easy");
        VALID_LEVELS.add("medium");
        VALID_LEVELS.add("hard");
        if (VALID_LEVELS.contains(inputLevel)) {
            level = inputLevel;
            return level;
        } else {
            throw new IllegalArgumentException("Invalid level. Please choose again.");
        }
    }

    // public static boolean checkMagicDoorInMagicDoorList(int[] wallCoordinate){
    //     for(int[] magicWall : MagicDoorList){
    //         if(Arrays.equals(magicWall, wallCoordinate)){
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    // public static boolean checkMagicDoorInMagicDoorList(int[] wallCoordinate){
    //     for(int[] magicDoor : MagicDoors){
    //         if(Arrays.equals(magicDoor, wallCoordinate)){
    //             return true;
    //         }
    //     }
    //     return false;
    // }
}
