package map;
import game.*;
import auxiliary.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Arrays;
import java.util.HashMap;
// import map.Wall;
// import map.Room;
// import map.House;
public class Map {
    private House[][] house;
    private int[][] MagicWalls;
    private ArrayList<int[]> MagicDoorList = new ArrayList<>();
    private ArrayList<int[]> allWallLocations = new ArrayList<>();
    private ArrayList<int[]> allRoomLocations = new ArrayList<>();
    private List<String> npcShortNameList = Arrays.asList("WO1", "BS1", "WF1", "BO1", "BF1", "WB1", "BB1", "WS1", "WS2", "BB2", "WB2", "BF2", "BO2", "WF2", "BS2", "WO2");
    private List<String> npcFUllNameList = Arrays.asList("White Owl", "Black Sworm", "White Frog", "Black Owl", "Black Frog", "White Bat", "Black Bat", "White Sworm", "White Sworm", "Black Bat", "White Bat", "Black Frog", "Black Owl", "White Frog", "Black Sworm", "White Owl");

    private HashMap<String, String> mappingNPCname = new HashMap<String, String>();

    public HashMap<String, String> getMappingNPCname(){
        return mappingNPCname;
    }   

    public House[][] getHouse()     {
        return house;
    }

    public int[][] getMagicDoors() {
        return MagicWalls;
    } 

    public ArrayList<int[]> getMagicLists()  {
        return MagicDoorList;
    }

    // public void getAllWallsLocation(ArrayList<int[]> allWallsList){
    //     for(int[] wall : allWallLocations){
    //         allWallsList.add(wall);
    //     }
    // }

    // public void getAllRoomsLocation(ArrayList<int[]> allRoomsList){
    //     for(int[] room : allRoomLocations){
    //         allRoomsList.add(room);
    //     }
    // }
    
    public ArrayList<int[]> getAllWallsLocation(){
        return allWallLocations;
    }

    public ArrayList<int[]> getAllRoomsLocation(){
        return allRoomLocations;
    }

    public Map() {
        for(int i = 0; i < npcShortNameList.size(); i++){
            mappingNPCname.put(npcShortNameList.get(i), npcFUllNameList.get(i));
        }
        int z = 0;
        house = new House[9][9];
        for (int i = 1; i < 9; i += 2) { // Loop through each room-wall-room row
            for (int j = 1; j < 9; j += 2) { // Loop through each object in a row
                house[i][j] = new Room();
                house[i][j].setType(npcShortNameList.get(z)); //room
                house[i][j + 1] = new Wall(); //wall
                z++;
            }
        }

        for (int i = 2; i < 9; i += 2) { // Loop through wall-only row
            for (int j = 1; j < 9; j += 2) { // Loop through each object in a row
                house[i][j] = new Wall();

                house[i][j + 1] = new House();
                house[i][j + 1].setType("Empty");  //intersection of walls
            }
        }
            
        for (int i = 1; i <= 7; i++) {
            house[0][i] = new Wall();
            house[0][i].setType("_");
            house[8][i] = new Wall();
            house[8][i].setType("_");
        }

        for (int i = 1; i <= 7; i++) {
            house[i][0] = new Wall();
            house[i][0].setType("|");
            house[i][8] = new Wall();
            house[i][8].setType("|");
        }
        
        house[0][0] = new Wall(); house[0][0].setType("E");
        house[0][8] = new Wall(); house[0][8].setType("E");
        house[8][0] = new Wall(); house[8][0].setType("E");
        house[8][8] = new Wall(); house[8][8].setType("E");
        
        }

    public void generateMap(int magicNums)  {
        PossibleGameplay("Rabbit");
        PossibleGameplay("Rat");
        setMagicWall(magicNums); 
        //fill the remaining valid wall (if any)
        Random random = new Random();
        String[] Types= {"Free","Block","Rabbit","Block","Rat","Block"};          
        for (int i = 1; i <=7; i ++) { // Loop through wall-only row
            for (int j = 1; j <=7; j ++) { // Loop through each object in a row
                if (house[i][j].getType()==null)    {
                    house[i][j].setType(Types[random.nextInt(Types.length)]);
                }
            }
        }
    }
    void setMagicWall(int magicNums)     {
        MagicWalls = new int[magicNums][2];
        int i=0;
        Random random = new Random();
        Set<int[]> wallsIndex = Auxiliary.generateDistinctArrays(magicNums);
        for (int[] wallid : wallsIndex) {
            house[wallid[0]][wallid[1]].setType("Magic");
            MagicWalls[i][0]=wallid[0];
            MagicWalls[i++][1]=wallid[1];
        }
    }    


    public void Display() {
        for (int i = 0; i < 9; i++) { // Loop through each row
            for (int j = 0; j < 9; j++) { // Loop through each object in a row
                System.out.print(house[i][j].getType() + "\t");
            }
            System.out.println();
        }
    }
    
    private void PossibleGameplay(String AnimalType) {
    int[][] connectedToRoot = new int[9][9]; connectedToRoot[1][1]=1;
    Random rand = new Random();
    int[][] adjacentSteps = {{0, -2}, {0, 2}, {-2, 0}, {2, 0}};
    List<int[]> rooms = new ArrayList<>();
    List<int[]> adjacentRooms = new ArrayList<>();
    int[] initialRoom = {1, 1};
    adjacentRooms.add(initialRoom);
    while (rooms.size() < 16) {
        // create a graph such such that there is at least a path to access all of the rooms
        if (!adjacentRooms.isEmpty()) {
            int choice = rand.nextInt(adjacentRooms.size());
            int[] ChosenRoom = adjacentRooms.get(choice);
            adjacentRooms.remove(choice);
            rooms.add(ChosenRoom);
            for (int i = 0; i < 4; i++) {
                int nextRoomRow = ChosenRoom[0] + adjacentSteps[i][0];
                int nextRoomCol = ChosenRoom[1] + adjacentSteps[i][1];
                if ((nextRoomRow < 1 || nextRoomRow > 8) || nextRoomCol < 1 || nextRoomCol > 8) {
                    continue;
                }
                int[] adjacentChosenRoom = {nextRoomRow, nextRoomCol};
                if (!containsArray(rooms, adjacentChosenRoom)) {
                    if (!containsArray(adjacentRooms, adjacentChosenRoom)) { // avoid duplication
                        adjacentRooms.add(adjacentChosenRoom);
                    }
                }
                // modify the wall because adjacent of the chosen room already in room list
                else if (connectedToRoot[ChosenRoom[0]][ChosenRoom[1]]!=1 
                        || connectedToRoot[adjacentChosenRoom[0]][adjacentChosenRoom[1]]!=1){

                    int WalRow = (ChosenRoom[0] + adjacentChosenRoom[0]) / 2;
                    int WalCol = (ChosenRoom[1] + adjacentChosenRoom[1]) / 2;

                    if (house[WalRow][WalCol].getType()==null) {
                        house[WalRow][WalCol].setType(AnimalType);
                    } else {
                        house[WalRow][WalCol].setType("Free");
                    }
                    connectedToRoot[ChosenRoom[0]][ChosenRoom[1]] = 1;
                    connectedToRoot[adjacentChosenRoom[0]][adjacentChosenRoom[1]]=1;
                    
                }
            }

        }
    }
}

// Custom method to check if a list contains an array with the same content
    public static boolean containsArray(List<int[]> list, int[] array) {
    for (int[] item : list) {
        if (arraysEqual(item, array)) {
            return true;
        }
    }
    return false;
}

// Custom method to check if two arrays are equal
    public static boolean arraysEqual(int[] a, int[] b) {
        if (a == b) return true;
        if (a == null || b == null) return false;
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) return false;
        }
        return true;
    }

    public void collectMagicDoors(){
        MagicDoorList.clear();
        int[][] magicWalls = MagicWalls;
        for(int[] wall : magicWalls){
            MagicDoorList.add(wall);
        }
    }

    public void printMagicWalls() {
        System.out.println("MagicWalls: ");
        for (int[] wall : MagicWalls){
            // System.out.println("[" + wall[0] + ", " + wall[1] + "]");
            System.out.println(Arrays.toString(wall));
        }
    }

    public void collectAllWallLocations(){
        allWallLocations.clear();
        for(int i = 1; i < 8; i++){
            for(int j = 1; j < 8; j++){
                if(house[i][j] instanceof Wall){
                    allWallLocations.add(new int[]{i, j});
                }
            }
        }
    }

    public void printAllWallLocations() {
        System.out.println("All Wall Locations:");
        for (int[] location : allWallLocations) {
            System.out.println("[" + location[0] + ", " + location[1] + "]");
        }
    }

    public void collectAllRoomLocations(){
        allRoomLocations.clear();
        for(int i = 1; i < 8; i+=2){
            for(int j = 1; j < 8; j+=2){
                if(house[i][j] instanceof Room){
                    allRoomLocations.add(new int[]{i, j});
                }
            }
        }
    }

    public void printAllRoomLocations() {
        System.out.println("All Room Locations:");
        for (int[] location : allRoomLocations) {
            System.out.println("[" + location[0] + ", " + location[1] + "]");
        }
    }

}

