
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

public class Game {
    private ArrayList<Player> players;
    private static Hubi hubi;
    private static Map map;
    private static int room;
    private static House[][] house;
    private static String direction;
    private static Player currentPlayer;
    private static Scanner scanner;
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

    public Game(ArrayList<Player> givenPlayers) {
        random = new Random();
        scanner = new Scanner(System.in);
        VALID_LEVELS.add("easy");
        VALID_LEVELS.add("medium");
        VALID_LEVELS.add("hard");
        getLevel();
        Collections.shuffle(givenPlayers);
        players = givenPlayers;
        GameRunner.setFirstPlayer();
        hubi = new Hubi();
        phase = 0;
    }

    public static void setPhase() {
        phase = 1;
    }

    public static int MagicDoorThreshold() {
        if (level.equals("easy")) {
            return 1;
        } else if (level.equals("medium")) {
            return 2;
        } else {
            return 3;
        }
    }

    public static int HubiMoveThreshold() {
        if (level.equals("easy")) {
            return 10;
        } else if (level.equals("medium")) {
            return 8;
        } else {
            return 5;
        }
    }

    public static void initializeResource() {
        map = new Map();
        mappingNPCname = map.getMappingNPCname();
        house = map.getHouse();
        map.generateMap(MagicDoorThreshold());

        map.collectMagicDoors();
        MagicDoorList = map.getMagicLists();
        MagicDoors = map.getMagicDoors();

        map.collectAllWallLocations();
        allWallsList = map.getAllWallsLocation();

        map.collectAllRoomLocations();
        allRoomsList = map.getAllRoomsLocation();

        setHubiInitialPosition();

        map.Display();
        map.printMagicWalls();
    }

    public static void movePlayer(String direction) {
        currentPlayer = GameRunner.getCurrentPlayer();
        int[] currentLocation = currentPlayer.getLocation();
        int[] newLocation = currentLocation.clone();
        switch (direction.toLowerCase()) {
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
        System.out.println(wallType);

        if (wallType.equals(currentPlayer.getType()) || wallType.equals("Free") || wallType.equals("Magic")) {
            System.out.println("The move is valid");
            currentPlayer.updateLocation(newLocation[0], newLocation[1]);
        } else {
            System.out.println("Sorry, the move is invalid!!!");
        }

        System.out.println("Updated Location: " + "[" + currentPlayer.getLocation()[0] + ", " + currentPlayer.getLocation()[1] + "]" + "\n");
        System.out.println("_________________________________\n");
        if (phase != 1) {
            magicDoorFound(wallType, wall);
        }
        GameRunner.updateMoreThan2PlayerRoom(currentLocation, newLocation);
        nextTurn();
    }

    public static void moveHubi() {
        int[] HubiRandomRoom = allRoomsList.get(random.nextInt(allRoomsList.size()));

        if (GameRunner.containsRoom(GameRunner.getMoreThan2PlayeArrayList(), HubiRandomRoom)) {
            moveHubi();
        } else {
            hubi.setLocation(HubiRandomRoom[0], HubiRandomRoom[1]);
        }

        System.out.println("+++++++++++++++++++++++++++++++++++");
        System.out.println("Hubi is moving to a random room...");
        System.out.println("Hubi moved to location: [" + hubi.getLocation()[0] + ", " + hubi.getLocation()[1] + "]");
        System.out.println("+++++++++++++++++++++++++++++++++++");
    }

    public static String getLevel() {
        return level;
    }

    public static void setLevel(String inputLevel) {
        if (VALID_LEVELS.contains(inputLevel)) {
            level = inputLevel;
        } else {
            throw new IllegalArgumentException("Invalid level. Please choose again.");
        }
    }

    public static void ask() {
        currentPlayer = GameRunner.getCurrentPlayer();
        if (MagicDoorList.isEmpty()) {
            System.out.println("No magic doors available to provide a hint.");
            askHubi();
            return;
        }

        switch (level) {
            case "easy":
                generateTrueAnswer();
                break;
            case "medium":
                String mediumAnswerType = answerType[random.nextInt(2)];
                System.out.println("Answer Type: " + mediumAnswerType);
                if (mediumAnswerType.equals("True answer")) {
                    generateTrueAnswer();
                } else {
                    generateMultipleChoice();
                }
                break;

            case "hard":
                String hardAnswerType = answerType[random.nextInt(3)];
                System.out.println("Answer Type: " + hardAnswerType);
                if (hardAnswerType.equals("MultipleChoice")) {
                    generateMultipleChoice();
                } else if (hardAnswerType.equals("True answer")) {
                    generateTrueAnswer();
                    System.out.println("But this could be a lie. Be careful!!!");
                } else {
                    generateWrongAnswer();
                    System.out.println("But this could be a lie. Be careful!!!");
                }
                break;
        }

        System.out.println("Updated Location: " + "[" + currentPlayer.getLocation()[0] + ", " + currentPlayer.getLocation()[1] + "]" + "\n");
        System.out.println("_________________________________\n");
        nextTurn();
    }

    public static void generateTrueAnswer() {
        System.out.println("All Wall Locations:");
        int[] hiddenMagicDoor = MagicDoorList.get(random.nextInt(MagicDoorList.size()));
        System.out.println("Hidden Magic Door: " + Arrays.toString(hiddenMagicDoor));
        int[][] neighbors = ((Wall) house[hiddenMagicDoor[0]][hiddenMagicDoor[1]]).findNeighbors(house, hiddenMagicDoor[0], hiddenMagicDoor[1]);
        System.out.println("Answer Type: True answer");
        System.out.println("The magic door is between room [" + neighbors[0][0] + ", " + neighbors[0][1] + "] and room [" + neighbors[1][0] + ", " + neighbors[1][1] + "]\n");
        System.out.println("The magic door is between room [" + mappingNPCname.get(house[neighbors[0][0]][neighbors[0][1]].getType()) + "] and room [" + mappingNPCname.get(house[neighbors[1][0]][neighbors[1][1]].getType()) + "]");
    }

    public static void generateWrongAnswer() {
        ArrayList<int[]> choices = allWallsList;
        removeMagicDoorsFromAllWalls(choices, MagicDoorList);
        Collections.shuffle(choices);
        int[] chosenWall = choices.get(random.nextInt(choices.size()));
        int[][] neighbors = ((Wall) house[chosenWall[0]][chosenWall[1]]).findNeighbors(house, chosenWall[0], chosenWall[1]);
        System.out.println("The fake wall is: [" + chosenWall[0] + ", " + chosenWall[1] + "]");
        System.out.println("The fake magic door is between room [" + neighbors[0][0] + ", " + neighbors[0][1] + "] and room [" + neighbors[1][0] + ", " + neighbors[1][1] + "]");
        System.out.println("The fake magic door is between room [" + mappingNPCname.get(house[neighbors[0][0]][neighbors[0][1]].getType()) + "] and room [" + mappingNPCname.get(house[neighbors[1][0]][neighbors[1][1]].getType()) + "]");
    }

    public static void generateMultipleChoice() {
        int[] hiddenMagicDoor = MagicDoorList.get(random.nextInt(MagicDoorList.size()));
        System.out.println("Hidden Magic Door: " + Arrays.toString(hiddenMagicDoor));
        System.out.println("The magic door could be: ");
        ArrayList<int[]> choices = new ArrayList<>(allWallsList);
        removeMagicDoorsFromAllWalls(choices, MagicDoorList);
        Collections.shuffle(choices);
        choices = new ArrayList<>(choices.subList(0, 2));
        choices.add(hiddenMagicDoor);
        Collections.shuffle(choices);
        for (int[] choice : choices) {
            int[][] neighbors = ((Wall) house[choice[0]][choice[1]]).findNeighbors(house, choice[0], choice[1]);
            System.out.println("  between room [" + neighbors[0][0] + ", " + neighbors[0][1] + "] and room [" + neighbors[1][0] + ", " + neighbors[1][1] + "]");
            System.out.println("  between room [" + mappingNPCname.get(house[neighbors[0][0]][neighbors[0][1]].getType()) + "] and room [" + mappingNPCname.get(house[neighbors[1][0]][neighbors[1][1]].getType()) + "]\n");
        }
    }

    public static void askHubi() {
        currentPlayer = GameRunner.getCurrentPlayer();
        int[] hubiCurrentLocation = hubi.getLocation();
        System.out.println("Hubi's current location: " + Arrays.toString(hubiCurrentLocation));
        switch (level) {
            case "easy":
                generateTrueAnswerHubi(hubiCurrentLocation);
                break;
            case "medium":
                String mediumAnswerType = answerType[random.nextInt(2)];
                System.out.println("Answer Type: " + mediumAnswerType);
                if (mediumAnswerType.equals("True answer")) {
                    generateTrueAnswerHubi(hubiCurrentLocation);
                } else {
                    generateMultipleChoiceHubi(hubiCurrentLocation);
                }
                break;

            case "hard":
                String hardAnswerType = answerType[random.nextInt(3)];
                System.out.println("Answer Type: " + hardAnswerType);
                if (hardAnswerType.equals("MultipleChoice")) {
                    generateMultipleChoiceHubi(hubiCurrentLocation);
                } else if (hardAnswerType.equals("True answer")) {
                    generateTrueAnswerHubi(hubiCurrentLocation);
                    System.out.println("But this could be a lie. Be careful!!!");
                } else {
                    generateWrongAnswerHubi(hubiCurrentLocation);
                    System.out.println("But this could be a lie. Be careful!!!");
                }
                break;
        }
        System.out.println("Updated Location: " + "[" + currentPlayer.getLocation()[0] + ", " + currentPlayer.getLocation()[1] + "]" + "\n");
        System.out.println("_________________________________\n");
        nextTurn();
    }

    public static void generateTrueAnswerHubi(int[] hubiCurrentLocation) {
        System.out.println("Hubi's current location: " + Arrays.toString(hubiCurrentLocation));
        System.out.println("Hubi is currently in room: [" + mappingNPCname.get(house[hubiCurrentLocation[0]][hubiCurrentLocation[1]].getType()) + "]");
    }

    public static void generateWrongAnswerHubi(int[] hubiCurrentLocation) {
        ArrayList<int[]> choices = allRoomsList;
        choices.remove(hubiCurrentLocation);
        Collections.shuffle(choices);
        int[] chosenRoom = choices.get(random.nextInt(choices.size()));
        System.out.println("The fake Hubi's location is: [" + chosenRoom[0] + ", " + chosenRoom[1] + "]");
        System.out.println("Hubi is currently in room: [" + mappingNPCname.get(house[chosenRoom[0]][chosenRoom[1]].getType()) + "]");
    }

    public static void generateMultipleChoiceHubi(int[] hubiCurrentLocation) {
        ArrayList<int[]> choices = allRoomsList;
        choices.remove(hubiCurrentLocation);
        System.out.println("Hubi could be: ");
        Collections.shuffle(choices);
        choices = new ArrayList<>(choices.subList(0, 2));
        choices.add(hubiCurrentLocation);
        Collections.shuffle(choices);
        for (int[] choice : choices) {
            System.out.println("  in room: " + Arrays.toString(choice));
            System.out.println("  in room: [" + mappingNPCname.get(house[hubiCurrentLocation[0]][hubiCurrentLocation[1]].getType()) + "]\n");
        }
    }

    public static void removeMagicDoorsFromAllWalls(ArrayList<int[]> allWalls, ArrayList<int[]> MagicDoors) {
        allWalls.removeIf(aWall -> containsWall(MagicDoors, aWall));
    }

    public static boolean containsWall(ArrayList<int[]> MagicDoors, int[] aWall) {
        for (int[] door : MagicDoors) {
            if (Arrays.equals(door, aWall)) {
                return true;
            }
        }
        return false;
    }

    public static void nextTurn() {
        GameRunner.nextPlayer();
        Player nextPlayer = GameRunner.getCurrentPlayer();
    }

    public static void setHubiInitialPosition() {
        int[] HubiRandomRoom = allRoomsList.get(random.nextInt(allRoomsList.size()));
        hubi.setLocation(HubiRandomRoom[0], HubiRandomRoom[1]);
        System.out.println("Hubi's initial location: [" + HubiRandomRoom[0] + ", " + HubiRandomRoom[1] + "]");
    }

    public static boolean isHubiInRoom(int[] room) {
        int[] hubiLocation = hubi.getLocation();
        return Arrays.equals(hubiLocation, room);
    }

    public static void magicDoorFound(String wallType, int[] wallCoordinate) {
        if (wallType.equals("Magic")) {
            for (int[] magicWall : MagicDoorList) {
                if (Arrays.equals(magicWall, wallCoordinate)) {
                    GameRunner.incrementMagicDoorCount();
                    MagicDoorList.remove(magicWall);
                    break;
                }
            }
        }
    }
}





