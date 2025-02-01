package Schanppt_Hubi.Structure.Logic.map;


public class Room extends House {
    // private NPC npcAnimals;
    // private String npcName;
    // public String getNPC()  {
    //     // the return type and this method can be changed later depends on the defined methods and attributes in NPC.java
    //     return npcName;
    // }

    public Room()  {
    }

    // public void setNPC(String npcName)     {
    //     this.npcName = npcName;
    // }
    //List<int[]> SourceRoom = new ArrayList<>();
    // public void setSource(int[] SourceRoom)     {
    //     int temp[] = {SourceRoom[0],SourceRoom[1]};
    //     this.SourceRoom.add(temp);
    // }
    // public List<int[]> getSource()     {
    //     return SourceRoom;
    // }
    @Override
    public int[][] findNeighbors(House[][] house, int rowIndex, int colIndex)    {
        int[][] result = new int[4][2];
        int[][] step = {{0,-2},{0,2},{-2,0},{2,0}};
        int curRow=rowIndex;
        int curCol=colIndex;
        for (int i=0;i<4;i++)   {
            int nextRow=step[i][0]+curRow;
            int nextCol=step[i][1]+curCol;
            if (nextRow>=1 && nextRow<=7 && nextCol>=1 && nextCol<=7)   {
                result[i][0] = nextRow;
                result[i][1] = nextCol;
            }else{
                result[i][0] = -1;
                result[i][1] = -1;
            }
        }
        return result;
    }

}
