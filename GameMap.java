import java.util.Random;
public class Main {
    public static void main(String[] args) {

        Map map = new Map();
        map.Display();
    }
}

class Map {
    private House[][] house;
    String Types[] = {"Free","Block","Rabbit","Rat"};
    Map() {
        Random rand = new Random();
        house = new House[9][9];

        for (int i = 1; i < 9; i += 2) { // Loop through each room-wall-room row
            for (int j = 1; j < 9; j += 2) { // Loop through each object in a row
                house[i][j] = new House();
                house[i][j].setType("Room");

                house[i][j + 1] = new House();
                house[i][j + 1].setType(Types[rand.nextInt(4)]);
            }
        }

        for (int i = 2; i < 9; i += 2) { // Loop through wall-only row
            for (int j = 1; j < 9; j += 2) { // Loop through each object in a row
                house[i][j] = new House();
                house[i][j].setType(Types[rand.nextInt(4)]);

                house[i][j + 1] = new House();
                house[i][j + 1].setType("Empty");
            }
        }

        for (int i = 1; i <= 7; i++) {
            house[0][i] = new House();
            house[0][i].setType("_");
            house[8][i] = new House();
            house[8][i].setType("_");
        }

        for (int i = 1; i <= 7; i++) {
            house[i][0] = new House();
            house[i][0].setType("|");
            house[i][8] = new House();
            house[i][8].setType("|");
        }

        house[0][0] = new House(); house[0][0].setType("E");
        house[0][8] = new House(); house[0][8].setType("E");
        house[8][0] = new House(); house[8][0].setType("E");
        house[8][8] = new House(); house[8][8].setType("E");
    }

    public void Display() {
        for (int i = 0; i < 9; i++) { // Loop through each row
            for (int j = 0; j < 9; j++) { // Loop through each object in a row
                System.out.print(house[i][j].getType() + "\t");
            }
            System.out.println();
        }
    }
    
    // public void GenerateMap()   {
    //      int visit[9][9]; //start at top left
        
    // }
    // private void travel(int array[][], int currentRow, int currentCol)  {
    //     int currentRow;
    //     int currentCol;
        
    // }
}

class House {
    private String type;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
    public String[] findNeighbors(House[][] house, int rowIndex, int colIndex)     
    {
        String[] result={};
        return result;
    }
}

class Wall extends House {
    @Override
    public String[] findNeighbors(House[][] house, int rowIndex, int colIndex)    {
        //find neighboring room from current wall location, room will be displayed from order left,right,up,down
        //"" indicate absent of room at the position 
        String[] result={"","","",""};
        if (house[rowIndex][colIndex-1].equals("Room"))   { //left room check
           result[0]=result[0] + rowIndex + Integer.toString(colIndex-1);
        }
        if (house[rowIndex][colIndex+1].equals("Room"))   { //right room check
           result[1]=result[1] + rowIndex + Integer.toString(colIndex+1);
        }
        if (house[rowIndex-1][colIndex].equals("Room"))   { //up room check
           result[2]=result[2] + Integer.toString(rowIndex-1) + colIndex;
        }
        if (house[rowIndex+1][colIndex].equals("Room"))   { //down room check
           result[3]=result[3] + Integer.toString(rowIndex+1) + colIndex;
        }
        return result;
    }
}

class Room extends House {
    @Override
    public String[] findNeighbors(House[][] house, int rowIndex, int colIndex)    {
        String[] result={"","","",""};
        if (!house[rowIndex][colIndex-1].equals("|") && !house[rowIndex][colIndex-1].equals("_"))   { //left wall check
           result[0]=result[0] + rowIndex + Integer.toString(colIndex-1);
        }
        if (!house[rowIndex][colIndex+1].equals("|") && !house[rowIndex][colIndex+1].equals("_"))   { //right wall check
           result[1]=result[1] + rowIndex + Integer.toString(colIndex+1);
        }
        if (!house[rowIndex-1][colIndex].equals("|") && !house[rowIndex-1][colIndex].equals("_"))   { //up wall check
           result[2]=result[2] + Integer.toString(rowIndex-1) + colIndex;
        }
        if (!house[rowIndex+1][colIndex].equals("|") && !house[rowIndex+1][colIndex].equals("_"))   { //down wall check
           result[3]=result[3] + Integer.toString(rowIndex+1) + colIndex;
        }
        return result;
    }
}
