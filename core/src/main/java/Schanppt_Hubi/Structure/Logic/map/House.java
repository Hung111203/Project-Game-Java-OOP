package Schanppt_Hubi.Structure.Logic.map;
import java.awt.Color;

public class House {
    private String type;
    private Color color;

    public House(){
        this.color = Color.WHITE;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public Color getColor(){
        return this.color;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
    public int[][] findNeighbors(House[][] house, int rowIndex, int colIndex)
    {
        return new int[0][0];
    }
}

